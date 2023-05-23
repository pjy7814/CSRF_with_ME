import { apiInstance, apiFormInstance } from "./index.js";

const api = apiInstance();
const formApi = apiFormInstance();

function listArticle(param, success, fail) {
  api.get(`/board`, { params: param }).then(success).catch(fail);
}

function writeArticle(article, success, fail) {
  formApi.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  formApi.post(`/board`, article).then(success).catch(fail);
}

function getArticle(articleno, success, fail) {
  formApi.get(`/board/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  formApi.put(`/board`, article).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  api.delete(`/board/${articleno}`).then(success).catch(fail);
}

export { listArticle, writeArticle, getArticle, modifyArticle, deleteArticle };
