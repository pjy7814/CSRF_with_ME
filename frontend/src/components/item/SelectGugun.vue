<template>
  <b-col class="sm-3">
    <b-form-select v-model="gugunCode" :options="guguns" @change="changeGugun"></b-form-select>
  </b-col>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

/*
  namespaced: true를 사용했기 때문에 선언해줍니다.
  index.js 에서 modules 객체의 '키' 이름입니다.

  modules: {
    키: 값
    memberStore: memberStore,
    houseStore: houseStore
  }
*/
const itemStore = "itemStore";

export default {
  name: "SelectGugun",
  data() {
    return {};
  },
  props: {
    sidoCode: String,
    selectedGugun: {
      type: String,
      required: true,
    },
  },
  computed: {
    ...mapState(itemStore, ["guguns"]),
    gugunCode() {
      return this.selectedGugun || null;
    },
  },
  watch: {
    sidoCode() {
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
  },
  methods: {
    ...mapActions(itemStore, ["getGugun"]),
    ...mapMutations(itemStore, ["CLEAR_GUGUN_LIST"]),
    changeGugun(value) {
      this.$emit("update:selectedGugun", value);
      this.$emit("select-gugun", value);
      console.log(value);
    },
  },
};
</script>

<style scoped></style>
