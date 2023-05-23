<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글작성</h3></b-alert>
      </b-col>
    </b-row>
    <ShareBoardInputItem
      v-if="isShareBoard"
      type="register"
    ></ShareBoardInputItem>
    <NoticeBoardInputItem
      v-else-if="isNoticeBoard"
      type="register"
    ></NoticeBoardInputItem>
  </b-container>
</template>

<script>
import ShareBoardInputItem from "./item/ShareBoardInputItem.vue";
import NoticeBoardInputItem from "./item/NoticeBoardInputItem.vue";

export default {
  name: "BoardWrite",
  props: {
    currentBoard: String,
  },
  computed: {
    isNoticeBoard() {
      return this.currentBoard === "noticeboard";
    },
    isShareBoard() {
      return this.currentBoard === "shareboard";
    },
  },
  created() {
    console.log(this.currentBoard);
  },
  components: {
    ShareBoardInputItem,
    NoticeBoardInputItem,
  },
};
</script>

<style></style>
