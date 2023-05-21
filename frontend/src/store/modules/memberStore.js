import jwtDecode from "jwt-decode";
import router from "@/router";
import { login, findById, tokenRegeneration, logout } from "@/api/member";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    memberInfo: null,
    isValidToken: false,
  },
  getters: {
    checkMemberInfo: function (state) {
      console.log(state.isLogin, state.isLoginError, state.memberInfo);
      return state.memberInfo;
    },
    checkToken: function (state) {
      return state.isValidToken;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_MEMBER_INFO: (state, memberInfo) => {
      state.isLogin = true;
      state.memberInfo = memberInfo;
    },
  },
  actions: {
    async memberConfirm({ commit }, member) {
      await login(
        member,
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("access-token", accessToken);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          /*
          error code 400 : Json parser error => 바디에 값이 아이디와 패스워드가 없음. => 해킹 의심. 일단은, 계속 로그인 시키도록 진행.
          error code 500 : internal server error => 에러 페이지로 보내주는게 맞음.
          */
          switch (error.response.status) {
            case 400:
              //Response.body의 에러 시에 js 코드를 고쳐서 submit 버튼을 시도했음. 로그인 페이지에 경고 메시지를 띄움.
              commit("SET_IS_LOGIN", false);
              commit("SET_IS_LOGIN_ERROR", true);
              commit("SET_IS_VALID_TOKEN", false);
              break;
            case 500:
              // server 에러의 경우, 점검 중 또는 현재 서버에 이상이 있음. 에러 페이지로 이동.
              commit("SET_IS_LOGIN", false);
              commit("SET_IS_LOGIN_ERROR", false);
              commit("SET_IS_VALID_TOKEN", false);
              router.push({
                name: "error",
                params: {
                  msg: "서버 에러입니다! 관리자에게 문의해주세요.",
                },
              });
              break;
            default:
              //범위에 벗어나는 에러일 경우, 의도에 벗어난 에러임. 에러 페이지 이동 및 안내 문구 띄우기
              commit("SET_IS_LOGIN", false);
              commit("SET_IS_LOGIN_ERROR", false);
              commit("SET_IS_VALID_TOKEN", false);
              router.push({
                name: "error",
                params: {
                  msg: "비 정상적인 접근입니다! 사이트를 정상적으로 이용해주세요!",
                },
              });
              break;
          }
        }
      );
    },
    async getMemberInfo({ commit, dispatch }, token) {
      let decodeToken = jwtDecode(token);
      // console.log("2. getUserInfo() decodeToken :: ", decodeToken);
      await findById(
        decodeToken.memberId,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_MEMBER_INFO", data.memberInfo);
            // console.log("3. getUserInfo data >> ", data);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.log(
            "getMemberInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          commit("SET_IS_VALID_TOKEN", false);
          await dispatch("tokenRegeneration");
        }
      );
    },
    async tokenRegeneration({ commit, state }) {
      console.log(
        "토큰 재발급 >> 기존 토큰 정보 : {}",
        sessionStorage.getItem("access-token")
      );
      await tokenRegeneration(
        JSON.stringify(state.memberInfo),
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
            sessionStorage.setItem("access-token", accessToken);
            commit("SET_IS_VALID_TOKEN", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("갱신 실패");
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              state.memberInfo.memberId,
              ({ data }) => {
                if (data.message === "success") {
                  console.log("로그아웃 성공");
                } else {
                  console.log("로그아웃 실패");
                }
                commit("SET_IS_LOGIN", false);
                commit("SET_MEMBER_INFO", null);
                commit("SET_IS_VALID_TOKEN", false);
                router.push({ name: "login" });
              },
              (error) => {
                console.log(error);
                commit("SET_IS_LOGIN", false);
                commit("SET_MEMBER_INFO", null);
              }
            );
          }
        }
      );
    },
    async memberLogout({ commit }, memberId) {
      await logout(
        memberId,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_MEMBER_INFO", null);
            commit("SET_IS_VALID_TOKEN", false);
            commit("SET_IS_LOGIN", false);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default memberStore;
