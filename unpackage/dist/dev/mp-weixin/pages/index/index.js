"use strict";
const common_vendor = require("../../common/vendor.js");
const api_api = require("../../api/api.js");
require("../../utils/request.js");
if (!Array) {
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  const _easycom_uni_drawer2 = common_vendor.resolveComponent("uni-drawer");
  const _easycom_uni_fab2 = common_vendor.resolveComponent("uni-fab");
  const _easycom_uni_popup2 = common_vendor.resolveComponent("uni-popup");
  (_easycom_uni_icons2 + _easycom_uni_drawer2 + _easycom_uni_fab2 + _easycom_uni_popup2)();
}
const _easycom_uni_icons = () => "../../uni_modules/uni-icons/components/uni-icons/uni-icons.js";
const _easycom_uni_drawer = () => "../../uni_modules/uni-drawer/components/uni-drawer/uni-drawer.js";
const _easycom_uni_fab = () => "../../uni_modules/uni-fab/components/uni-fab/uni-fab.js";
const _easycom_uni_popup = () => "../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";
if (!Math) {
  (_easycom_uni_icons + _easycom_uni_drawer + _easycom_uni_fab + _easycom_uni_popup)();
}
const _sfc_main = {
  __name: "index",
  setup(__props) {
    const categoryName = common_vendor.ref(["各地动态", "惠农政策", "农机", "农品安全", "农业科技", "农资", "市场动态", "现代果业", "乡村振兴", "养殖", "种植"]);
    const index = common_vendor.ref(0);
    const drawerShowLeft = common_vendor.ref(null);
    common_vendor.ref(null);
    const showDrawerLeft = () => {
      getuserHistoryData();
      drawerShowLeft.value.open();
    };
    const popup = common_vendor.ref(null);
    const fabClick = () => {
      popup.value.open();
    };
    const rangomData = common_vendor.ref([]);
    const userData = common_vendor.ref({
      "userId": "",
      "openId": "",
      "token": ""
    });
    common_vendor.onLoad((e) => {
      common_vendor.index.showModal({
        title: "请登录",
        showCancel: false,
        confirmText: "授权登录",
        success() {
          common_vendor.wx$1.login({
            success(res) {
              userlogin(res.code);
              getRandInfoData();
            }
          });
        }
      });
    });
    const userlogin = async (code) => {
      let res = await api_api.userLoginApi({ "code": code });
      userData.value.userId = res.data.id;
      userData.value.openId = res.data.openid;
      userData.value.token = res.data.token;
      console.log(userData.value);
    };
    const getRandInfoData = async () => {
      let res = await api_api.getRandInfo();
      console.log("获取数据中");
      rangomData.value = res.data;
      console.log(rangomData.value);
    };
    const infoAddData = common_vendor.ref({ "title": "", "categoryId": 1, "detail": "" });
    const infoAddCategory = (e) => {
      index.value = e.detail.value;
      infoAddData.value.categoryId = Number(index.value) + 1;
    };
    const infoAddButton = () => {
      api_api.infoAdd(infoAddData.value);
      common_vendor.index.showToast({
        title: "添加成功",
        icon: "none"
      });
      popup.value.close();
    };
    const userHistoryData = common_vendor.ref([]);
    const getuserHistoryData = async () => {
      let res = await api_api.getHistoryDataApi(userData.value.userId);
      userHistoryData.value = res.data;
      console.log(userHistoryData.value);
    };
    common_vendor.ref([]);
    const getCategorylist = async (categoryId) => {
      let res = await api_api.getCategoryListApi(categoryId);
      rangomData.value = res.data;
    };
    common_vendor.onPullDownRefresh(() => {
      common_vendor.index.showLoading({
        title: "数据获取中",
        mask: true
      });
      getRandInfoData();
      common_vendor.index.hideLoading();
      common_vendor.index.stopPullDownRefresh();
    });
    const toDetail = (index2) => {
      let history = rangomData.value[index2];
      history["id"] = userData.value.userId;
      api_api.addHistoryDataApi(history);
      console.log(index2);
      common_vendor.index.setStorageSync("infoData", rangomData.value);
      common_vendor.index.navigateTo({
        url: "/pages/detail/detail?index=" + index2 + "&id=" + userData.value.userId,
        success() {
          console.log("成功跳转");
        }
      });
    };
    const toDetail2 = (index2) => {
      console.log(index2);
      let history = rangomData.value[index2];
      history["id"] = userData.value.userId;
      api_api.addHistoryDataApi(history);
      common_vendor.index.setStorageSync("infoData", userHistoryData.value);
      common_vendor.index.navigateTo({
        url: "/pages/detail/detail?index=" + index2 + "&id=" + userData.value.userId,
        success() {
          console.log("成功跳转" + index2);
        }
      });
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(($event) => showDrawerLeft()),
        b: common_vendor.p({
          type: "bars",
          size: "25"
        }),
        c: common_vendor.f(rangomData.value, (item, index2, i0) => {
          return {
            a: item.imgUrl,
            b: common_vendor.t(item.categoryName),
            c: common_vendor.t(item.count),
            d: common_vendor.t(item.hot),
            e: common_vendor.t(item.agricultureInfomationTitle),
            f: common_vendor.t(item.detail),
            g: index2,
            h: common_vendor.o(($event) => toDetail(index2), index2)
          };
        }),
        d: common_vendor.f(userHistoryData.value.slice(0, 8), (item, index2, i0) => {
          return {
            a: item.imgUrl,
            b: common_vendor.t(item.categoryName),
            c: index2,
            d: common_vendor.o(($event) => getCategorylist(), index2)
          };
        }),
        e: common_vendor.f(userHistoryData.value, (item, index2, i0) => {
          return {
            a: item.imgUrl,
            b: common_vendor.t(item.agricultureInfomationTitle),
            c: index2,
            d: common_vendor.o(($event) => toDetail2(index2), index2)
          };
        }),
        f: common_vendor.sr(drawerShowLeft, "1cf27b2a-1", {
          "k": "drawerShowLeft"
        }),
        g: common_vendor.p({
          mode: "left",
          width: 320
        }),
        h: common_vendor.o(fabClick),
        i: common_vendor.p({
          horizontal: "right",
          vertical: "bottom"
        }),
        j: infoAddData.value.title,
        k: common_vendor.o(($event) => infoAddData.value.title = $event.detail.value),
        l: common_vendor.t(categoryName.value[index.value]),
        m: index.value,
        n: categoryName.value,
        o: common_vendor.o(infoAddCategory),
        p: infoAddData.value.detail,
        q: common_vendor.o(($event) => infoAddData.value.detail = $event.detail.value),
        r: common_vendor.o(($event) => infoAddButton()),
        s: common_vendor.sr(popup, "1cf27b2a-3", {
          "k": "popup"
        }),
        t: common_vendor.p({
          type: "center",
          ["border-radius"]: "10px 10px 10px 10px",
          ["background-color"]: "#FFFFFF"
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-1cf27b2a"], ["__file", "C:/Users/alan/Documents/HBuilderProjects/recommendation-system/ui/pages/index/index.vue"]]);
wx.createPage(MiniProgramPage);
