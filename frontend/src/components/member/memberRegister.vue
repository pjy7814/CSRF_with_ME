<template>
  <div class="container">
    <form @submit.prevent="signUp">
      <div class="form-row">
        <div class="col-md-4 mb-3">
          <label for="member_id" class="text-left">아이디</label>
        </div>
        <div class="col-md-8 mb-3">
          <input
            type="text"
            id="member_id"
            v-model="memberId.value"
            class="form-control"
            required
            @keydown.prevent.space
            @keyup="handleInputChange('member_id')"
          />
          <div class="confirmMessage" id="validConfirmId">
            아이디는 4자 이상, 영문과 숫자만 허용합니다
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-md-4 mb-3">
          <label for="member_name" class="text-left">이름</label>
        </div>
        <div class="col-md-8 mb-3">
          <input
            type="text"
            id="member_name"
            v-model="memberName.value"
            class="form-control"
            required
            @keydown.prevent.space
            @keyup="handleInputChange('member_name')"
          />
          <div class="confirmMessage" id="validConfirmName">
            두글자 이상 입력해주세요
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-md-4 mb-3">
          <label for="member_email" class="text-left">이메일</label>
        </div>
        <div class="col-md-8 mb-3">
          <input
            type="email"
            id="member_email"
            v-model="memberEmail.value"
            class="form-control"
            required
            @keydown.prevent.space
            @keyup="handleInputChange('member_email')"
          />
          <div class="confirmMessage" id="validConfirmEmail">
            유효한 이메일 주소를 입력해주세요.
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-md-4 mb-3">
          <label for="member_password" class="text-left">비밀번호</label>
        </div>
        <div class="col-md-8 mb-3">
          <input
            type="password"
            id="member_password"
            v-model="memberPassword.value"
            class="form-control"
            required
            @keydown.prevent.space
            @keyup="handleInputChange('member_password')"
          />
          <div class="confirmMessage" id="validConfirmPw">
            비밀번호는 8자 이상, 영문 대소문자, 숫자, 특수문자를 모두 포함해야
            합니다
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-md-4 mb-3">
          <label for="member_password" class="text-left">비밀번호 확인</label>
        </div>
        <div class="col-md-8 mb-3">
          <input
            type="password"
            id="member_password_check"
            v-model="memberPasswordCheck.value"
            class="form-control"
            required
            @keydown.prevent.space
            @keyup="handleInputChange('member_password_check')"
          />
          <div class="confirmMessage" id="validConfirmPwCheck">
            동일한 비밀번호가 아닙니다
          </div>
        </div>
      </div>

      <div class="form-row">
        <div class="col-md-4">사는 곳</div>
        <b-row class="col-md-8 mt-3">
          <select-sido @select-sido="selectSido"></select-sido>
          <select-gugun
            :sidoCode="sidoCode.value"
            @select-gugun="selectGugun"
          ></select-gugun>
        </b-row>
      </div>
      <button type="submit" class="btn btn-primary btn-block">가입하기</button>
    </form>
  </div>
</template>

<script>
import SelectSido from "@/components/item/SelectSido.vue";
import SelectGugun from "@/components/item/SelectGugun.vue";
import {
  validateMemberEmail,
  validateMemberId,
  validateMemberName,
  validateMemberPassword,
  validateMemberPasswordCheck,
} from "@/util/index";
import { registMember } from "@/api/member";
export default {
  components: {
    SelectSido,
    SelectGugun,
  },

  data() {
    return {
      memberId: {
        value: "",
        valid: false,
      },
      memberName: {
        value: "",
        valid: false,
      },
      memberEmail: {
        value: "",
        valid: false,
      },
      memberPassword: {
        value: "",
        valid: false,
      },
      memberPasswordCheck: {
        value: "",
        valid: false,
      },
      sidoCode: {
        value: "",
        valid: false,
      },
      gunguCode: {
        value: "",
        valid: false,
      },
    };
  },
  methods: {
    signUp() {
      // 여기에서 API 호출이나 데이터베이스 연동 로직을 구현합니다.
      // 입력된 데이터를 서버로 전송하고 회원가입을 처리하는 로직을 작성해야 합니다.
      let member = {
        memberId: this.memberId.value,
        memberName: this.memberName.value,
        memberEmail: this.memberEmail.value,
        memberPassword: this.memberPassword.value,
        sidoCode: this.sidoCode.value,
        gunguCode: this.gunguCode.value,
      };
      console.log(member);
      if (!this.checkAllInput()) {
        window.alert("회원가입을 위한 정보를 다시 확인해주세요!");
        return;
      }
      registMember(
        member,
        ({ data }) => {
          console.log(data);
          if (data.message == "success") {
            window.alert(
              this.memberName.value + "님 환영합니다! 로그인 후 이용해주세요"
            );
            this.$router.push("/member/login");
          } else {
            window.alert("회원가입에 실패했습니다!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    checkAllInput() {
      console.log(this.sidoCode.valid, this.gunguCode.valid);
      if (
        this.memberId.valid &&
        this.memberEmail.valid &&
        this.memberName.valid &&
        this.memberPassword.valid &&
        this.memberPasswordCheck.valid &&
        this.sidoCode.valid &&
        this.gunguCode.valid
      ) {
        return true;
      }
      return false;
    },

    // 유효성 검사
    handleInputChange(fieldName) {
      let isValid = false;
      switch (fieldName) {
        case "member_id":
          isValid = validateMemberId(this.memberId.value);
          this.changeConfirmMsg(isValid, "validConfirmId", this.memberId);
          break;
        case "member_password":
          isValid = validateMemberPassword(this.memberPassword.value);
          this.changeConfirmMsg(isValid, "validConfirmPw", this.memberPassword);

          isValid = validateMemberPasswordCheck(
            this.memberPassword.value,
            this.memberPasswordCheck.value
          );
          this.changeConfirmMsg(
            isValid,
            "validConfirmPwCheck",
            this.memberPasswordCheck
          );
          break;
        case "member_password_check":
          isValid = validateMemberPasswordCheck(
            this.memberPassword.value,
            this.memberPasswordCheck.value
          );
          this.changeConfirmMsg(
            isValid,
            "validConfirmPwCheck",
            this.memberPasswordCheck
          );
          break;
        case "member_email":
          isValid = validateMemberEmail(this.memberEmail.value);
          this.changeConfirmMsg(isValid, "validConfirmEmail", this.memberEmail);
          break;
        case "member_name":
          isValid = validateMemberName(this.memberName.value);
          this.changeConfirmMsg(isValid, "validConfirmName", this.memberName);
          break;
      }
    },

    changeConfirmMsg(isValid, elementId, data) {
      if (!isValid) {
        // false
        document.getElementById(elementId).style.display = "block";
        data.valid = false;
      } else {
        document.getElementById(elementId).style.display = "none";
        data.valid = true;
      }
    },

    selectSido(sidoCode) {
      this.sidoCode.value = sidoCode;
      if (this.sidoCode.value > 0) {
        this.sidoCode.valid = true;
      } else {
        this.sidoCode.valid = false;
      }
    },
    selectGugun(gugunCode) {
      this.gunguCode.value = gugunCode;
      if (this.gunguCode.value > 0) {
        this.gunguCode.valid = true;
      } else {
        this.gunguCode.valid = false;
      }
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 500px;
}

.form-group {
  margin-bottom: 1.5rem;
}

.confirmMessage {
  color: red;
  margin-top: 0.25rem;
  display: none;
}

.text-left {
  text-align: left;
}

.btn-block {
  margin-top: 1rem;
}
</style>
