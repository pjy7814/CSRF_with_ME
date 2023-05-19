<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-blue"><b-icon icon="map"></b-icon> 여행지 정보 찾기</h3>
    <b-row class="mt-3">
      <select-sido @select-sido="selectSido"></select-sido>
      <select-gugun :sidoCode="sidoCode" @select-gugun="selectGugun"></select-gugun>
      <select-content-type @select-contenttype="selectContentType"></select-content-type>
      <input type="text" v-model="searchQuery" placeholder="검색어를 입력하세요" />
      <button class="btn btn-primary" @click="search">조회</button>
    </b-row>
    <b-row class="mt-3">
      <b-col cols="12">
        <the-kakao-map :chargers="searchingdestination"></the-kakao-map>
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
// import { mapActions, mapMutations } from "vuex";
import SelectSido from "@/components/item/SelectSido.vue";
import SelectGugun from "@/components/item/SelectGugun.vue";
import SelectContentType from "@/components/item/SelectContentType.vue";
import TheKakaoMap from "@/components/TheKakaoMap.vue";
import { destinationList } from "@/api/item/item.js";

export default {
  name: "AppDestinationInfo",
  components: {
    SelectSido,
    SelectGugun,
    SelectContentType,
    TheKakaoMap,
  },
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      contentTypeId: null,
      searchQuery: null,
      searchingdestination: [],
    };
  },
  methods: {
    selectSido(sidoCode) {
      this.sidoCode = sidoCode;
    },
    selectGugun(gugunCode) {
      this.gugunCode = gugunCode;
    },
    selectContentType(contentTypeId) {
      this.contentTypeId = contentTypeId;
    },
    search() {
      const params = {
        sidoCode: this.sidoCode,
        gugunCode: this.gugunCode,
        contentTypeId: this.contentTypeId,
        searchQuery: this.searchQuery,
      };
      destinationList(
        params,
        ({ data }) => {
          this.searchingdestination = data;
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>
<style scoped>
.underline-blue {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(27, 136, 231, 0.3) 30%);
}
</style>
