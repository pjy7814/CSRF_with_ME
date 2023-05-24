<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand href="#">
        <router-link :to="{ name: 'main' }">
          <b-img
            :src="require('@/assets/ssafy_logo.png')"
            id="logo"
            class="d-inline-block align-top"
            alt="logo"
          ></b-img>
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#">
            <router-link :to="{ name: 'destination' }" class="link">
              여행지 정보
            </router-link>
            <router-link :to="{ name: 'noticeboard' }" class="m-2 link">
              공지사항
            </router-link>
            <router-link :to="{ name: 'shareboard' }" class="m-2 link">
              공유 게시판
            </router-link>
          </b-nav-item>
        </b-navbar-nav>

        <!-- after login -->
        <b-navbar-nav class="ml-auto" v-if="memberInfo">
          <b-nav-item class="align-self-center">
            <b-avatar
              variant="primary"
              :text="memberInfo.memberId.charAt(0).toUpperCase()"
            ></b-avatar>
            {{ memberInfo.memberName }}({{ memberInfo.memberId }})님 환영합니다.
          </b-nav-item>
          <b-nav-item class="align-self-center">
            <router-link :to="{ name: 'mypage' }" class="link align-self-center"
              >내정보보기</router-link
            >
          </b-nav-item>
          <b-nav-item
            class="align-self-center link"
            @click.prevent="onClickLogout"
            >로그아웃</b-nav-item
          >
        </b-navbar-nav>
        <!-- before login -->
        <b-navbar-nav class="ml-auto" v-else>
          <b-nav-item-dropdown right>
            <template #button-content>
              <b-icon icon="people" font-scale="2"></b-icon>
            </template>
            <b-dropdown-item
              href="#"
              @click="onClickDropDown('join')"
              class="link"
            >
              <b-icon icon="person-circle"></b-icon> 회원가입
            </b-dropdown-item>
            <b-dropdown-item
              href="#"
              @click="onClickDropDown('login')"
              class="link"
            >
              <b-icon icon="key"></b-icon> 로그인
            </b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "TheHeaderNavbar",
  data() {
    return {};
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "memberInfo"]),
    ...mapGetters(memberStore, ["checkMemberInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["memberLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      this.memberLogout();
    },
    onClickDropDown(name) {
      if (name === this.$route.name) return;
      this.$router.push({ name });
    },
  },
};
</script>

<style scoped>
#logo {
  width: 120px;
}

.link {
  text-decoration: none;
  font-size: 20px;
}
</style>
