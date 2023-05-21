<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h1>Error!</h1>
    <p>{{ msg }}</p>
  </b-container>
</template>

<script>
/*
fix me!
서버 에러또는 해킹이 의심되는 행위 발생 시, 에러 페이지로 이동.
Msg 에 대해서는 상의 필요...
*/
export default {
  name: "AppError",
  props: ["msg"],
  created() {
    console.log(this.msg);
    // 임의로 아무런 에러나 해킹 시도 없이 그냥 에러 페이지로 접근할 시에는 alert 후, 메인페이지로 이동
    if (!this.msg || typeof this.msg != "string" || this.msg === "") {
      alert("비 정상적인 접근입니다! 웹 사이트를 올바르게 사용해주세요!");
      this.$router.push({ name: "main" });
      return;
    }
  },
};
</script>

<style lang="scss" scoped></style>
