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
      :modifyboardId="boardId"
    ></ShareBoardInputItem>
    <NoticeBoardInputItem
      v-else-if="isNoticeBoard"
      type="modify"
      :modifyboardId="boardId"
    ></NoticeBoardInputItem>
  </b-container>
</template>

<script>
import ShareBoardInputItem from "./item/ShareBoardInputItem";
import NoticeBoardInputItem from "./item/NoticeBoardInputItem.vue";
export default {
  name: "BoardModify",
  computed: {
    isNoticeBoard() {
      return this.$route.name === "noticeboardmodify";
    },
    isShareBoard() {
      return this.$route.name === "shareboardmodify";
    },
    boardId() {
      return this.$route.params.boardId;
    },
  },
  components: {
    ShareBoardInputItem,
    NoticeBoardInputItem,
  },
};
</script>

<style></style>
