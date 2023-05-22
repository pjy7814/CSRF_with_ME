import { apiInstance } from "./index.js";

const api = apiInstance();

function listArticle(param, success, fail) {
  api.get(`/board`, { params: param }).then(success).catch(fail);
}

function writeArticle(article, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  api.post(`/board`, JSON.stringify(article)).then(success).catch(fail);
}

function getArticle(articleno, success, fail) {
  api.get(`/board/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  api.put(`/board`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  api.delete(`/board/${articleno}`).then(success).catch(fail);
}

export { listArticle, writeArticle, getArticle, modifyArticle, deleteArticle };
