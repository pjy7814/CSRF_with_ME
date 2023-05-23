<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글수정</h3></b-alert>
      </b-col>
    </b-row>
    <ShareBoardInputItem
      v-if="isShareBoard"
      type="modify"
    ></ShareBoardInputItem>
    <NoticeBoardInputItem
      v-else-if="isNoticeBoard"
      type="modify"
    ></NoticeBoardInputItem>
  </b-container>
</template>

<script>
import ShareBoardInputItem from "./item/ShareBoardInputItem";
import NoticeBoardInputItem from "./item/NoticeBoardInputItem.vue";
export default {
  name: "BoardModify",
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
  components: {
    ShareBoardInputItem,
    NoticeBoardInputItem,
  },
};
</script>

<style></style>
