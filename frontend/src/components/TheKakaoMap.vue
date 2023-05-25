<template>
  <div>
    <div
      id="map"
      :style="{
        height: startPoint && startPoint === 'boardModal' ? '500px' : '700px',
      }"
    ></div>
  </div>
</template>

<script>
import Vue from "vue";
import DestinationInfoItem from "./item/DestinationInfoItem.vue";

export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      positions: [],
      curPositionIdx: 0,
      markers: [],
      overlay: null,
    };
  },
  props: {
    destinations: [],
    startPoint: String,
  },
  watch: {
    destinations() {
      this.positions = [];
      this.destinations.forEach((destination) => {
        //fix me! : 여행지 API 보고 프로젝트에 맞게 수정 필요!
        let obj = {};
        obj.contentId = destination.contentId;
        obj.title = destination.title;
        obj.latlng = new kakao.maps.LatLng(
          destination.latitude,
          destination.longitude
        );
        obj.img = destination.firstImage1;
        obj.address = destination.address1 + destination.address2;
        this.positions.push(obj);
      });
      this.loadMaker();
    },
  },
  created() {},
  mounted() {
    // api 스크립트 소스 불러오기 및 지도 출력
    if (window.kakao && window.kakao.maps) {
      setTimeout(() => {
        this.loadMap();
      }, 100);
    } else {
      this.loadScript();
    }
  },
  methods: {
    // api 불러오기
    loadScript() {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=" +
        process.env.VUE_APP_KAKAO_MAP_API_KEY +
        "&autoload=false";
      /* global kakao */
      script.onload = () => window.kakao.maps.load(this.loadMap);

      document.head.appendChild(script);
    },
    // 맵 출력하기
    loadMap() {
      const container = document.getElementById("map");
      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      this.map = new window.kakao.maps.Map(container, options);
      //   this.loadMaker();
    },
    // 지정한 위치에 마커 불러오기
    loadMaker() {
      // 현재 표시되어있는 marker들이 있다면 marker에 등록된 map을 없애준다.
      this.deleteMarker();

      // 마커를 생성합니다
      this.markers = [];
      this.positions.forEach((position) => {
        const marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: position.latlng, // 마커를 표시할 위치
          title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        });
        this.markers.push(marker);

        // 마커 클릭 이벤트 핸들러 등록
        kakao.maps.event.addListener(marker, "click", () => {
          this.openOverlay(position);
        });

        // 마커 지도에 추가
        marker.setMap(this.map);
      });

      // 4. 지도를 이동시켜주기
      // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
      const bounds = this.positions.reduce(
        (bounds, position) => bounds.extend(position.latlng),
        new kakao.maps.LatLngBounds()
      );

      this.map.setBounds(bounds);
    },
    deleteMarker() {
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }
    },
    openOverlay(position) {
      if (this.overlay) this.closeOverlay();

      const DestinationInfoItemComponent = new Vue({
        render: (h) =>
          h(DestinationInfoItem, {
            props: {
              position,
              startPoint: this.startPoint,
            },
            on: {
              close: () => {
                this.closeOverlay();
              },
              registModalAttraction: () => {
                this.closeOverlay();
                this.$emit("registModalAttraction", position);
              },
            },
          }),
      });

      DestinationInfoItemComponent.$mount();

      const overlay = new kakao.maps.CustomOverlay({
        content: DestinationInfoItemComponent.$el,
        map: this.map,
        position: position.latlng,
      });
      overlay.setMap(this.map);

      this.overlay = overlay;
    },
    closeOverlay() {
      if (this.overlay) {
        this.overlay.setMap(null);
        this.overlay = null;
      }
    },
  },
};
</script>

<style>
#map {
  width: 100%;
}
</style>
