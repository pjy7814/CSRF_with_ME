<template>
  <div :class="['wrap', { large: startPoint && startPoint === 'boardModal' }]">
    <div
      class="info"
      :class="{ large: startPoint && startPoint === 'boardModal' }"
    >
      <div class="header">
        <div class="title">
          {{ position.title }}
        </div>
        <div class="close" title="닫기" @click="clickCloseBtn"></div>
      </div>
      <div class="body">
        <div class="img">
          <img :src="position.img" width="73" height="70" />
        </div>
        <div class="desc">
          <div class="ellipsis">{{ position.address }}</div>
        </div>
      </div>
      <div class="footer" v-if="startPoint && startPoint === 'boardModal'">
        <b-button
          variant="primary"
          size="small"
          title="등록"
          @click="registModalAttraction"
        >
          등록하기
        </b-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DestinationInfoItem",
  props: {
    position: Object,
    startPoint: String,
  },
  mounted() {},
  methods: {
    clickCloseBtn() {
      this.$emit("close");
    },
    registModalAttraction() {
      this.$emit("registModalAttraction");
    },
  },
};
</script>

<style scoped>
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
  height: 132px;
}

.wrap.large {
  height: 156px;
}

.wrap * {
  padding: 0;
  margin: 0;
}

.wrap .info {
  width: 286px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
  height: 120px;
}

.wrap .info.large {
  height: 156px;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}

.info .header {
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
}
.info .title {
  padding: 5px 0 0 10px;
  width: 250px;
  overflow: hidden;
  text-overflow: ellipsis;
  height: 30px;
  font-size: 18px;
  font-weight: bold;
}

.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}

.info .close:hover {
  cursor: pointer;
}

.info .body {
  position: relative;
  overflow: hidden;
}

.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}

.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}

.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}

.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}

.info .link {
  color: #5085bb;
}

.info .footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 4px;
  border-top: 1px solid #ddd;
}

.info .register:hover {
  cursor: pointer;
}
</style>
