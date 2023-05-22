<template>
  <b-col class="sm-3">
    <b-form-select v-model="sidoCode" :options="sidos" @change="changeSido"></b-form-select>
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
  name: "SelectSido",
  data() {
    return {
      // sidoCode: null,
    };
  },
  props: {
    selectedSido: {
      type: String,
      required: true,
    },
  },

  computed: {
    ...mapState(itemStore, ["sidos"]),
    sidoCode: {
      get() {
        return this.selectedSido;
      },
      set(value) {
        this.$emit("update:selectedSido", value);
      },
    },
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(itemStore, ["getSido"]),
    ...mapMutations(itemStore, ["CLEAR_SIDO_LIST"]),
    changeSido() {
      console.log("시도 선택 ::: " + this.sidoCode);
      this.$emit("select-sido", this.sidoCode);
    },
  },
};
</script>

<style scoped></style>
