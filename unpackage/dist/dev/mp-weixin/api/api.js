"use strict";
const utils_request = require("../utils/request.js");
function userLoginApi(data = {}) {
  return utils_request.request({ url: "/user/login", data, method: "post" });
}
function getRandInfo() {
  return utils_request.request({ url: "/info/random" });
}
function getCategoryListApi(categoryId) {
  return utils_request.request({ "url": "/info/category/list?id=" + categoryId });
}
function infoAdd(data = {}) {
  return utils_request.request({ url: "/info/add", data, method: "post" });
}
function getHistoryDataApi(userId) {
  return utils_request.request({ "url": "/user/history/list?id=" + userId });
}
function addHistoryDataApi(data = {}) {
  return utils_request.request({ "url": "/user/history/add", data, method: "post" });
}
function addUserScoreDataApi(data = {}) {
  return utils_request.request({ "url": "/user/score", data, method: "post" });
}
exports.addHistoryDataApi = addHistoryDataApi;
exports.addUserScoreDataApi = addUserScoreDataApi;
exports.getCategoryListApi = getCategoryListApi;
exports.getHistoryDataApi = getHistoryDataApi;
exports.getRandInfo = getRandInfo;
exports.infoAdd = infoAdd;
exports.userLoginApi = userLoginApi;
