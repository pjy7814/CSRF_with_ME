import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(member, success, fail) {
  await api
    .post(`/member/login`, JSON.stringify(member))
    .then(success)
    .catch(fail);
}

async function findById(memberId, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/member/info/${memberId}`).then(success).catch(fail);
}

async function tokenRegeneration(member, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.post(`/member/refresh`, member).then(success).catch(fail);
}

async function registMember(member, success, fail) {
  await api
    .post(`/member/regist`, JSON.stringify(member))
    .then(success)
    .catch(fail);
}

export { login, findById, tokenRegeneration, registMember };
