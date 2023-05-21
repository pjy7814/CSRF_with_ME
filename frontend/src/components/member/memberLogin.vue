<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>로그인</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group
              label="아이디:"
              label-for="memberId"
              :invalid-feedback="memberId.invalidText"
              :state="memberId.valid"
            >
              <b-form-input
                id="memberId"
                v-model="memberId.value"
                required
                min="0"
                max="50"
                :state="memberId.valid"
                placeholder="아이디 입력...."
                @keyup.enter="login"
              ></b-form-input>
            </b-form-group>
            <b-form-group
              label="비밀번호:"
              label-for="memberPassword"
              :invalid-feedback="memberPassword.invalidText"
              :state="memberPassword.valid"
            >
              <b-form-input
                type="password"
                id="memberPassword"
                v-model="memberPassword.value"
                :state="memberPassword.valid"
                required
                min="0"
                max="50"
                placeholder="비밀번호 입력...."
                @keyup.enter="login"
              ></b-form-input>
            </b-form-group>
            <b-button type="button" variant="primary" class="m-1" @click="login"
              >로그인</b-button
            >
            <b-button
              type="button"
              variant="success"
              class="m-1"
              @click="movePage"
              >회원가입</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
/*
checklist
- validation은 submit 버튼이 눌러졌을 때만 진행한다. (o)
  - 나올 수 있는 helptext
    - 아이디는 4자 이상, 영문과 숫자만 허용합니다. ( id validation error)
    - 비밀번호는 8자 이상, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다. ( pw validation error )
    - 존재하지 않거나 틀린 아이디입니다. ( id undefined )
    - 비밀번호가 일치하지 않습니다. ( pw is not correct)

  - helptext의 경우, 재사용성을 고려하여 config 파일에 묶는다.
- 아이디와 비밀번호의 validation이 적용이후, 모든 validation이 통과되면 login request를 진행한다. (o)
- 로그인 성공 시에는 홈 페이지로 redirect한다. (o)
- 로그인 실패 시에는 실패 원인을 제공한 input에 helptext를 화면에 띄운다. (status code : 4~~) (o)
- 서버 에러일 경우, 에러 페이지로 이동한다. (o)
*/
import { validateMemberId, validateMemberPassword } from "@/util";
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberLogin",
  data() {
    return {
      // isLoginError: false,
      memberId: {
        value: "",
        valid: null,
        invalidText: "123",
      },
      memberPassword: {
        value: "",
        valid: null,
        invalidText: "1234",
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "memberInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["memberConfirm", "getMemberInfo"]),
    async login() {
      if (!validateMemberId(this.memberId.value)) {
        this.memberId.valid = false;
        this.memberId.invalidText =
          "아이디는 4자 이상, 영문과 숫자만 허용합니다.";
        return;
      } else this.memberId.valid = true;

      if (!validateMemberPassword(this.memberPassword.value)) {
        this.memberPassword.valid = false;
        this.memberPassword.invalidText =
          "비밀번호는 8자 이상, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.";
        return;
      } else this.memberPassword.valid = true;

      await this.memberConfirm({
        memberId: this.memberId.value,
        memberPassword: this.memberPassword.value,
      });
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getMemberInfo(token);
        this.$router.replace({ name: "main" });
      } else if (this.isLoginError) {
        console.log("login error!");
      }
    },
    movePage() {
      this.$router.push({ name: "join" });
    },
  },
};
</script>

<style></style>
