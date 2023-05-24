<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글작성</h3></b-alert>
      </b-col>
    </b-row>
    <ShareBoardInputItem v-if="isShareBoard"></ShareBoardInputItem>
    <NoticeBoardInputItem v-else-if="isNoticeBoard"></NoticeBoardInputItem>
  </b-container>
</template>

<script>
import ShareBoardInputItem from "./item/ShareBoardInputItem.vue";
import NoticeBoardInputItem from "./item/NoticeBoardInputItem.vue";

export default {
  name: "BoardWrite",
  computed: {
    isNoticeBoard() {
      return this.$route.name === "noticeboardwrite";
    },
    isShareBoard() {
      return this.$route.name === "shareboardwrite";
    },
  },
  components: {
    ShareBoardInputItem,
    NoticeBoardInputItem,
  },
};
</script>

<style></style>
