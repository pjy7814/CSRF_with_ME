<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          label-cols="2"
          content-cols="10"
          label="제목:"
          label-for="subject"
        >
          <b-form-input
            id="subject"
            v-model="article.subject"
            type="text"
            required
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>
        <b-form-group
          label-cols="2"
          content-cols="10"
          label="이미지 등록"
          label-for="input-horizontal"
        >
          <b-form-file
            v-model="selectedImg"
            variant="success"
            multiple
            accept=".jpg, .jpeg, .png, .gif"
            browse-text="업로드"
            @input="changeImgFile()"
          ></b-form-file>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

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

/*
 fix me!
  - 토큰 만료 시, interceptor에서 토큰 초기화 및 로그아웃 response를 반환해줘야 한다.
  - 글 작성 시, request할 시에 서버에서 memberId와 accessToken.memberId를 동일한지 체킹하는 로직 꼭 필요하다. 

  글 작성 페이지
  - 글 작성 입력 사항은 다음과 같다.
    - 제목
    - 공유할 관광지 : 이름, 주소가 저장됨.
      - '등록' 버튼 클릭시, 서브 페이지로 이동한다.
      - 서브 페이지 내부에는 시/도, 구/군, 카테고리, 키워드 검색이 존재한다.
      - 사용자의 경우 마커 클릭 시, 마커위에 창이 뜨며 해당 창 내에서 '등록'을 누를 시에 하단 입력창에 해당 여행지의 이름과 주소가 표시된다.
    - 이미지
      - 최대 5개 등록 가능하다.
      - 이미지 등록 시에, .jpg, .jpeg, .png임을 체크하는 validation이 존재한다.
        - 만약, validation에 불통할 시, 등록 자체를 해주지 않으며 하단에 경고 문구를 띄운다.
    - 본문
      - 최대 1000자로 제한해준다.
      - 띄어쓰기 및 줄 바꿈을 textarea로 구성하기 위해 wrap="hard"를 넣어주며, .replace를 통해 <br>태그를 삽입해준다.

  - 글 등록 시에는, 제목, 본문에 xss 공격 방지를 위한 .replace 함수를 적용시켜준다. 만약, 해당 시도가 존재할 시에 xss공격 방지를 위해 특수 문자로 변경된다.
*/

/*

공유(또는 공지사항) 에서 글을 작성하고 등록을 보낼 때, 공지사항(또는 공유)로 request하도록 f12키를 통한 수정을 한다면?
- 
*/
export default {
  name: "BoardInputItem",
  data() {
    return {
      boardId: {
        value: 0,
        valid: true,
      },
      boardWriterId: {
        value: "",
        valid: null,
      },
      boardTitle: {
        value: "",
        valid: null,
      },
      boardContent: {
        value: "",
        valid: null,
      },
      boardAttractionInfoId: {
        value: "",
        valid: null,
      },
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      let param = this.$route.params.articleno;
      getArticle(
        param,
        ({ data }) => {
          // this.article.articleno = data.article.articleno;
          // this.article.userid = data.article.userid;
          // this.article.subject = data.article.subject;
          // this.article.content = data.article.content;
          this.article = data;
        },
        (error) => {
          console.log(error);
        }
      );
      this.isUserid = true;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.userid &&
        ((msg = "작성자 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err &&
        !this.article.subject &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.moveList();
    },
    registArticle() {
      let param = {
        userid: this.article.userid,
        subject: this.article.subject,
        content: this.article.content,
      };
      writeArticle(
        param,
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    modifyArticle() {
      let param = {
        articleno: this.article.articleno,
        userid: this.article.userid,
        subject: this.article.subject,
        content: this.article.content,
      };
      modifyArticle(
        param,
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    changeImgFile() {
      return;
    },
    moveList() {
      this.$router.push({ name: "boardlist" });
    },
  },
};
</script>

<style></style>
