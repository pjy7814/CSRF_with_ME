<template>
  <div class="container">
    <form @submit.prevent="registerUser" class="needs-validation" novalidate>
      <div class="form-row">
        <div class="col-md-4 mb-3">
          <label for="member_id" class="text-left">아이디</label>
        </div>
        <div class="col-md-8 mb-3">
          <input type="text" id="member_id" v-model="memberId" class="form-control" required />
        </div>
      </div>
      <div class="form-row">
        <div class="col-md-4 mb-3">
          <label for="member_name" class="text-left">이름</label>
        </div>
        <div class="col-md-8 mb-3">
          <input type="text" id="member_name" v-model="memberName" class="form-control" required />
          <div class="invalid-feedback">이름을 입력해주세요.</div>
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
            v-model="memberEmail"
            class="form-control"
            required
          />
          <div class="invalid-feedback">유효한 이메일 주소를 입력해주세요.</div>
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
            v-model="memberPassword"
            class="form-control"
            required
          />
          <div class="invalid-feedback">비밀번호를 입력해주세요.</div>
        </div>
      </div>

      <div class="form-row">
        <b-row class="mt-3">
          <select-sido @select-sido="selectSido"></select-sido>
          <select-gugun :sidoCode="sidoCode" @select-gugun="selectGugun"></select-gugun>
        </b-row>
      </div>
      <button type="submit" class="btn btn-primary btn-block">가입하기</button>
    </form>
  </div>
</template>

<script>
import SelectSido from "@/components/item/SelectSido.vue";
import SelectGugun from "@/components/item/SelectGugun.vue";

export default {
  components: {
    SelectSido,
    SelectGugun,
  },

  data() {
    return {
      memberId: "",
      memberName: "",
      memberEmail: "",
      memberPassword: "",
      sidoCode: "",
      gugunCode: "",
    };
  },
  methods: {
    registerUser() {
      // 여기에서 API 호출이나 데이터베이스 연동 로직을 구현합니다.
      // 입력된 데이터를 서버로 전송하고 회원가입을 처리하는 로직을 작성해야 합니다.
    },
    selectSido(sidoCode) {
      this.sidoCode = sidoCode;
    },
    selectGugun(gugunCode) {
      this.gugunCode = gugunCode;
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

.invalid-feedback {
  color: red;
  margin-top: 0.25rem;
}

.text-left {
  text-align: left;
}

.btn-block {
  margin-top: 1rem;
}
</style>
