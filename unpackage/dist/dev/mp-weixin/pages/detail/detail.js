"use strict";
const common_vendor = require("../../common/vendor.js");
const api_api = require("../../api/api.js");
require("../../utils/request.js");
if (!Array) {
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  const _easycom_uni_rate2 = common_vendor.resolveComponent("uni-rate");
  (_easycom_uni_icons2 + _easycom_uni_rate2)();
}
const _easycom_uni_icons = () => "../../uni_modules/uni-icons/components/uni-icons/uni-icons.js";
const _easycom_uni_rate = () => "../../uni_modules/uni-rate/components/uni-rate/uni-rate.js";
if (!Math) {
  (_easycom_uni_icons + _easycom_uni_rate)();
}
const _sfc_main = {
  __name: "detail",
  setup(__props) {
    const infoData = common_vendor.ref({});
    const userId = common_vendor.ref();
    common_vendor.onLoad((e) => {
      let res = common_vendor.index.getStorageSync("infoData");
      infoData.value = res[Number(e.index)];
      userId.value = e.id;
    });
    const backToIndex = () => {
      common_vendor.index.navigateBack();
    };
    const scoreValue = common_vendor.ref(2);
    const onChange = () => {
      console.log(scoreValue.value);
      let scoreData = infoData.value;
      scoreData["id"] = userId.value;
      scoreData["score"] = scoreValue.value;
      api_api.addUserScoreDataApi(scoreData);
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(($event) => backToIndex()),
        b: common_vendor.p({
          type: "back",
          size: "22"
        }),
        c: common_vendor.t(infoData.value.agricultureInfomationTitle),
        d: common_vendor.t(infoData.value.categoryName),
        e: infoData.value.imgUrl,
        f: common_vendor.t(infoData.value.detail),
        g: common_vendor.o(onChange),
        h: common_vendor.o(($event) => scoreValue.value = $event),
        i: common_vendor.p({
          modelValue: scoreValue.value
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-eca06f3c"], ["__file", "C:/Users/alan/Documents/HBuilderProjects/recommendation-system/ui/pages/detail/detail.vue"]]);
wx.createPage(MiniProgramPage);
