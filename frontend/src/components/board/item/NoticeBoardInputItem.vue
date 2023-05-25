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
import { mapGetters } from "vuex";
const memberStore = "memberStore";
import { VueRecaptcha } from "vue-recaptcha";
export default {
  name: "NoticeBoardInputItem",
  components: { VueRecaptcha },
  data() {
    return {
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
      recaptchaToken: "",
    };
  },
  computed: {
    ...mapGetters(memberStore, ["checkMemberInfo"]),
    type() {
      switch (this.$route.name) {
        case "noticeboardwrite":
          return "register";
        case "noticeboardmodify":
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
            data.boardDtos;
          this.boardId.value = boardId;
          this.boardWriterId.value = boardWriterId;
          this.boardTitle.value = boardTitle;
          this.boardContent.value = boardContent;
        },
        (error) => {
          switch (error.response.status) {
            case "500":
              this.$router.push({
                name: "error",
                params: {
                  msg: "서버 상태 이상",
                },
              });
          }
        }
      );
    } else if (this.type === "register") {
      this.boardWriterId.value = this.checkMemberInfo.memberId;
    }
  },
  methods: {
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
      formData.append("boardWriterId", this.boardWriterId.value);
      formData.append("boardTitle", this.boardTitle.value);
      formData.append("boardContent", this.boardContent.value);
      formData.append("boardType", "notice");
      formData.append("file", "");
      formData.append("recaptchaToken", this.recaptchaToken);
      writeArticle(
        formData,
        ({ data }) => {
          console.log("에러");
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        },
        (error) => {
          switch (error.response.status) {
            case 401:
              alert("잘못된 접근입니다!");
              break;
            case 500:
              this.$router.push({
                name: "error",
                params: {
                  msg: "서버 상태 이상",
                },
              });
              break;
            default:
              this.$router.push({
                name: "error",
                params: {
                  msg: "잘못된 서비스 사용",
                },
              });
              break;
          }
          console.log(error);
        }
      );
    },
    modifyArticle() {
      const formData = new FormData();
      formData.append("boardId", this.boardId.value);
      formData.append("boardWriterId", this.boardWriterId.value);
      formData.append("boardTitle", this.boardTitle.value);
      formData.append("boardContent", this.boardContent.value);
      formData.append("boardType", "notice");
      formData.append("file", "");
      formData.append("recaptchaToken", this.recaptchaToken);
      modifyArticle(
        formData,
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveList() {
      this.$router.replace({ name: "noticeboardlist" });
    },
    checkRecaptcha(response) {
      this.recaptchaToken = response;
    },
  },
};
</script>

<style scoped></style>
