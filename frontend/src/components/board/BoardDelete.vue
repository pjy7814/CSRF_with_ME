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
import { processResError } from "@/util";
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
        processResError(error);
      }
    );
  },
};
</script>

<style></style>
