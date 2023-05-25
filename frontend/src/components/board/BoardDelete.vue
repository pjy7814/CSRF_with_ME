<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글목록</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col><b-alert show variant="danger">삭제처리중...</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { deleteArticle } from "@/api/board";

export default {
  name: "BoardDelete",
  props: {
    boardListType: String,
  },
  created() {
    let param = this.$route.params.boardId;
    deleteArticle(
      param,
      ({ data }) => {
        alert(data.message);
        this.$router.replace({ name: `${this.boardListType}boardlist` });
      },
      (error) => {
        const { message } = error.response.data;
        console.log(error);
        switch (error.response.status) {
          case 500:
            this.$router.replace({ name: "error", params: { message } });
            break;
          default:
            alert(message);
            break;
        }
      }
    );
  },
};
</script>

<style></style>
