<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글보기</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="moveList">목록</b-button>
      </b-col>
      <b-col
        class="text-right"
        v-if="memberInfo.memberId === article.boardWriterId"
      >
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyArticle"
          class="mr-2"
          >글수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="deleteArticle"
          >글삭제</b-button
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${article.boardId}. ${article.boardTitle}</h3><div><h6>${article.boardWriterId}</h6><div>${article.createdTime}</div></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div class="imageWrapper">
              <img
                v-for="image in imageArray"
                :key="image.boardId"
                :src="`${envImgEndPoint}${image.boardImgSrc}`"
                alt="이미지"
              />
            </div>
            <h5 v-if="currentBoard === 'share'">
              공유 여행지 : {{ article.boardAttractionInfoTitle }}({{
                article.boardAttractionInfoAddr1
              }})
            </h5>
            <div v-html="this.article.boardContent"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
// import moment from "moment";
import { getArticle } from "@/api/board";
import { mapState } from "vuex";
import { processResError } from "@/util";
const memberStore = "memberStore";

export default {
  name: "BoardDetail",
  data() {
    return {
      article: {},
      imageArray: {},
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "memberInfo"]),
    envImgEndPoint() {
      return process.env.VUE_APP_IMG_ENDPOINT;
    },
    currentBoard() {
      if (this.$route.name === "noticeboardview") {
        return "notice";
      } else if (this.$route.name === "shareboardview") {
        return "share";
      } else {
        return "";
      }
    },
  },
  created() {
    let param = this.$route.params.boardId;
    getArticle(
      param,
      this.currentBoard,
      ({ data }) => {
        console.log(data);
        const { boardDtos, boardImgDtos } = data.article;
        this.article = boardDtos;
        this.imageArray = boardImgDtos;
        this.article.createdTime = this.formatDate(this.article.createdTime);
      },
      (error) => {
        processResError(error);
      }
    );
  },

  filters: {},
  methods: {
    moveModifyArticle() {
      switch (this.$route.name) {
        case "noticeboardview":
          this.$router.replace({
            name: `noticeboardmodify`,
            params: {
              boardId: this.article.boardId,
            },
          });
          break;
        case "shareboardview":
          this.$router.replace({
            name: `shareboardmodify`,
            params: {
              boardId: this.article.boardId,
            },
          });
          break;
        default:
          this.$router.replace({
            name: `error`,
            params: {
              message: "비 정상적인 접근입니다! 사이트를 제대로 이용해주세요!",
            },
          });
          break;
      }
    },
    deleteArticle() {
      if (confirm("정말로, 게시글을 삭제하시겠습니까?")) {
        switch (this.$route.name) {
          case "noticeboardview":
            this.$router.replace({
              name: "noticeboarddelete",
              params: {
                boardId: this.article.boardId,
                boardListType: "notice",
              },
            });
            break;
          case "shareboardview":
            this.$router.replace({
              name: "shareboarddelete",
              params: { boardId: this.article.boardId, boardListType: "share" },
            });
            break;
          default:
            this.$router.replace({
              name: `error`,
              params: {
                message:
                  "비 정상적인 접근입니다! 사이트를 제대로 이용해주세요!",
              },
            });
            break;
        }
      }
    },
    moveList() {
      this.$router.push({ name: "boardlist" });
    },
    formatDate(value) {
      const date = new Date(value);
      return date.toLocaleString();
    },
  },
};
</script>

<style>
.imageWrapper {
  display: flex;
  justify-content: flex-start;
  gap: 10px;
  align-items: center;
  flex-direction: row;
  flex-wrap: wrap;
  width: 100%;
}

.imageWrapper img {
  width: 300px;
  height: 300px;
}
</style>
