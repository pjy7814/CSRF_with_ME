<template>
  <b-col class="sm-3">
    <b-form-select
      v-model="contentTypeId"
      :options="contentTypes"
      @change="changeContentType"
    ></b-form-select>
  </b-col>
</template>

<script>
import { mapState } from "vuex";

const itemStore = "itemStore";

export default {
  name: "SelectContentType",
  data() {
    return {
      contentTypeId: null,
    };
  },
  computed: {
    ...mapState(itemStore, ["contentTypes"]),
  },
  created() {},
  methods: {
    changeContentType() {
      console.log("관광 선택 ::: " + this.contentTypeId);
      this.$emit("select-contenttype", this.contentTypeId);
    },
  },
};
</script>

<style scoped></style>
