<template>
  <div>
    <div class="wrap">
      <div class="info">
        <div class="title">
          {{ position.title }}
          <div class="close" @click="closeOverlay()" title="닫기"></div>
        </div>
        <div class="body">
          <div class="img"><img :src="position.img" width="73" height="70" /></div>
          <div class="desc">
            <div class="ellipsis">{{ position.address }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DestinationInfoItem",
  components: {},
  data() {
    return {
      message: "",
      overlay: null,
    };
  },
  props: {
    position: null,
  },
  created() {
    console.log(this.position);
  },
  methods: {
    closeOverlay() {
      console.log("닫아!!!");
      // if (this.overlay) {
      //   // 오버레이 닫기
      //   this.overlay.setMap(null);
      //   this.overlay = null;
      // }
    },
  },
};
</script>

<style scoped></style>
