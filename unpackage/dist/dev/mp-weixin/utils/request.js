"use strict";
const common_vendor = require("../common/vendor.js");
const BASE_URL = "http://localhost:8080/";
function request(config = {}) {
  let {
    url,
    method = "GET",
    header = {
      "Accept": "application/json",
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json"
    },
    data = {}
  } = config;
  url = BASE_URL + url;
  return new Promise((resolve, reject) => {
    common_vendor.index.request({
      url,
      data,
      method,
      header,
      success: (res) => {
        if (res.data.code === 1) {
          resolve(res.data);
        } else if (res.data.code === 0) {
          common_vendor.index.showModal({
            title: "错误提示",
            content: res.data.message,
            showCancel: false
          });
          reject(res.data);
        } else {
          common_vendor.index.showToast({
            title: "访问错误",
            icon: "none"
          });
          reject(res.data);
        }
      },
      fail: (err) => {
        reject(err);
      }
    });
  });
}
exports.request = request;
