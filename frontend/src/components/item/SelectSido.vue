<template>
  <b-col class="sm-3">
    <b-form-select v-model="sidoCode" :options="sidos" @change="changeSido"></b-form-select>
  </b-col>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const itemStore = "itemStore";

export default {
  name: "SelectSido",
  data() {
    return {};
  },
  props: {
    selectedSido: {
      type: String,
      required: true,
    },
  },

  computed: {
    ...mapState(itemStore, ["sidos"]),
    sidoCode() {
      return this.selectedSido || null;
    },
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(itemStore, ["getSido"]),
    ...mapMutations(itemStore, ["CLEAR_SIDO_LIST"]),
    changeSido(value) {
      this.$emit("update:selectedSido", value);
      console.log("시도 선택1 ::: " + value);
      this.$emit("select-sido", value);
    },
  },
};
</script>

<style scoped></style>
