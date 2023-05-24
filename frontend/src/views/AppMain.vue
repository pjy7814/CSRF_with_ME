<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-steelblue"><b-icon icon="house"></b-icon> SSAFY</h3>
    <b-row>
      <b-col></b-col>
      <b-col cols="10">
        <b-jumbotron
          bg-variant="muted"
          text-variant="dark"
          border-variant="dark"
        >
          <template #header>SSAFY TRIP</template>

          <template #lead>
            여행지를 찾아보고, 여행지를 다른 사람과 공유하세요!
          </template>
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "AppMain",
  props: {
    msg: String,
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
}
</style>
