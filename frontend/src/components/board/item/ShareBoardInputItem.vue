<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset" novalidate>
        <b-form-group
          label-cols="2"
          content-cols="10"
          label="제목 :"
          label-for="title"
          :invalid-feedback="boardTitle.invalidText"
          :state="boardTitle.valid"
        >
          <b-form-input
            id="title"
            v-model="boardTitle.value"
            type="text"
            required
            :state="boardTitle.valid"
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group
          label-cols="2"
          content-cols="10"
          label="여행지 입력 :"
          label-for="uploadAttraction"
          :state="boardAttractionInfo.valid"
          :invalid-feedback="boardAttractionInfo.invalidText"
        >
          <b-input-group>
            <b-form-input
              id="uploadAttraction"
              type="text"
              required
              readonly
              :state="boardAttractionInfo.valid"
              v-model="boardAttractionInfo.value.title"
              placeholder="공유할 여행지를 등록해주세요!"
            ></b-form-input>
            <b-button @click="openModal()">등록하기</b-button>
          </b-input-group>
        </b-form-group>
        <b-modal v-model="isModalOpen" @hide="closeModal()" size="lg" centered>
          <template #modal-title> 공유 여행지 등록하기 </template>
          <template #default>
            <AppDestinationInfo
              startPoint="boardModal"
              @registModalAttraction="registModalAttraction"
            ></AppDestinationInfo>
          </template>
          <template #modal-footer>
            <b-form-group
              label-cols="2"
              content-cols="10"
              label="현재 선택된 여행지 :"
              label-for="selectedModalAttraction"
            >
              <b-form-input
                id="selectedModalAttraction"
                v-model="selectedModalAttraction.title"
                type="text"
                required
                readonly
                placeholder="공유할 여행지 한 곳을 등록해주세요!"
              ></b-form-input>
            </b-form-group>
            <b-button
              @click="registAttraction()"
              :disabled="isValidModalAttraction"
              >등록</b-button
            >
            <b-button @click="closeModal()">닫기</b-button>
          </template>
        </b-modal>

        <b-form-group
          label-cols="2"
          content-cols="10"
          label="이미지 등록 :"
          label-for="uploadImage"
        >
          <b-form-file
            id="uploadImage"
            variant="success"
            multiple
            accept="image/jpg, image/jpeg, image/png"
            browse-text="업로드"
            placeholder="이미지를 업로드해주세요"
            :file-name-formatter="printFileName"
            @input="registImgFile"
          ></b-form-file>
        </b-form-group>

        <b-form-group
          id="content-group"
          label="내용:"
          label-for="content"
          :state="boardContent.valid"
          :invalid-feedback="boardContent.invalidText"
        >
          <b-form-textarea
            id="content"
            v-model="boardContent.value"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
            :state="boardContent.valid"
          ></b-form-textarea>
        </b-form-group>

        <vue-recaptcha
          ref="recaptcha"
          :sitekey="envRecaptchaSKey"
          @verify="checkRecaptcha"
        >
        </vue-recaptcha>
        <b-button
          type="submit"
          variant="primary"
          class="m-1"
          v-if="this.type === 'register'"
          >글작성</b-button
        >
        <b-button type="submit" variant="primary" class="m-1" v-else
          >글수정</b-button
        >
        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import { writeArticle, modifyArticle, getArticle } from "@/api/board";
import AppDestinationInfo from "@/views/AppDestinationInfo.vue";
import { validateImgFile, processResError } from "@/util";
import { mapGetters } from "vuex";
const memberStore = "memberStore";
import { VueRecaptcha } from "vue-recaptcha";

export default {
  name: "ShareBoardInputItem",
  data() {
    return {
      isModalOpen: false,
      selectedModalAttraction: {
        id: 0,
        title: "",
      },
      boardId: {
        value: 0,
        valid: null,
      },
      boardWriterId: {
        value: "",
        valid: null,
      },
      boardTitle: {
        value: "",
        valid: null,
        invalidText: "제목은 1자 이상, 50자 이하입니다.",
      },
      boardContent: {
        value: "",
        valid: null,
        invalidText: "본문은 1자 이상, 1000자 이하입니다.",
      },
      boardAttractionInfo: {
        value: {
          contentId: "",
          title: "",
        },
        invalidText: "공유 여행지는 꼭 등록되어야 합니다.",
        valid: null,
      },
      boardImgFile: {
        value: [],
        valid: null,
      },
      boardImgUrl: {
        value: null,
        valid: null,
      },
      recaptchaToken: "",
    };
  },
  computed: {
    isValidModalAttraction() {
      const { contentId = 0, title = "" } = this.selectedModalAttraction;
      return contentId === 0 || title.length === 0;
    },
    ...mapGetters(memberStore, ["checkMemberInfo"]),
    type() {
      switch (this.$route.name) {
        case "shareboardwrite":
          return "register";
        case "shareboardmodify":
          return "modify";
        default:
          return "error";
      }
    },
    modifyboardId() {
      return this.$route.params.boardId;
    },
    envRecaptchaSKey() {
      return process.env.VUE_APP_RECAPTCHA_SITE_KEY;
    },
  },
  created() {
    if (this.type === "modify") {
      getArticle(
        this.modifyboardId,
        ({ data }) => {
          const { boardId, boardWriterId, boardTitle, boardContent } =
            data.article.boardDtos;
          this.boardId.value = boardId;
          this.boardWriterId.value = boardWriterId;
          this.boardTitle.value = boardTitle;
          this.boardContent.value = boardContent;
        },
        (error) => {
          processResError(error);
        }
      );
    } else if (this.type === "register") {
      this.boardWriterId.value = this.checkMemberInfo.memberId;
    }
  },
  methods: {
    openModal() {
      this.isModalOpen = true;
    },
    closeModal() {
      this.isModalOpen = false;
    },

    onSubmit(event) {
      event.preventDefault();
      if (!this.recaptchaToken) {
        alert("검증 프로그램을 체킹해주세요!");
        return;
      }
      let err = true;
      if (err && !this.boardTitle.value) {
        this.boardTitle.valid = false;
        err = false;
        this.$refs.boardTitle.focus();
      } else {
        this.boardTitle.valid = true;
      }

      if (
        err &&
        (!this.boardAttractionInfo.value.contentId ||
          !this.boardAttractionInfo.value.title)
      ) {
        this.boardAttractionInfo.valid = false;
        err = false;
        this.$refs.boardAttractionInfo.focus();
      } else {
        this.boardAttractionInfo.valid = true;
      }

      if (err && !this.boardContent.value) {
        this.boardContent.valid = false;
        err = false;
        this.$refs.boardContent.focus();
      } else {
        this.boardContent.valid = true;
      }

      if (!err) {
        return;
      } else
        this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.moveList();
    },
    registArticle() {
      const formData = new FormData();
      this.boardImgFile.value.forEach((curFile) => {
        formData.append("file", curFile);
      });

      formData.append("boardWriterId", this.boardWriterId.value);
      formData.append("boardTitle", this.boardTitle.value);
      formData.append("boardContent", this.boardContent.value);
      formData.append(
        "boardAttractionInfoId",
        this.boardAttractionInfo.value.contentId
      );
      formData.append("boardType", "share");
      formData.append("recaptchaToken", this.recaptchaToken);
      writeArticle(
        formData,
        ({ data }) => {
          alert(data.message);
          this.moveList();
        },
        (error) => {
          processResError(error);
        }
      );
    },
    modifyArticle() {
      const formData = new FormData();
      this.boardImgFile.value.forEach((curFile) => {
        formData.append("file", curFile);
      });
      formData.append("boardId", this.boardId.value);
      formData.append("boardWriterId", this.boardWriterId.value);
      formData.append("boardTitle", this.boardTitle.value);
      formData.append("boardContent", this.boardContent.value);
      formData.append(
        "boardAttractionInfoId",
        this.boardAttractionInfo.value.contentId
      );
      formData.append("boardType", "share");
      formData.append("recaptchaToken", this.recaptchaToken);
      modifyArticle(
        formData,
        ({ data }) => {
          alert(data.message);
          this.moveList();
        },
        (error) => {
          processResError(error);
        }
      );
    },
    registImgFile(files) {
      if (files.length === 0) return;
      const curImgFiles = [];
      const maxSizeInBytes = 10 * 1024 * 1024;
      files.forEach((curFile) => {
        if (validateImgFile(curFile.type) && curFile.size <= maxSizeInBytes) {
          curImgFiles.push(curFile);
        }
      });

      this.boardImgFile.value = curImgFiles.length != 0 ? curImgFiles : [];
    },
    printFileName() {
      let fileNameStr = "";
      this.boardImgFile.value.forEach((curFile, idx) => {
        fileNameStr += curFile.name;
        if (idx !== this.boardImgFile.value.length - 1) {
          fileNameStr += ", ";
        }
      });
      return fileNameStr;
    },
    registModalAttraction(position) {
      this.selectedModalAttraction = {
        contentId: position.contentId,
        title: position.title,
      };
    },
    registAttraction() {
      this.boardAttractionInfo.value = { ...this.selectedModalAttraction };
      this.selectedModalAttraction = {
        contentId: "",
        title: "",
      };
      this.closeModal();
    },
    moveList() {
      this.$router.replace({ name: "shareboardlist" });
    },
    checkRecaptcha(response) {
      this.recaptchaToken = response;
    },
  },
  components: { AppDestinationInfo, VueRecaptcha },
};
</script>

<style>
.custom-popup {
  width: 800px;
  height: 800px;
}
</style>
