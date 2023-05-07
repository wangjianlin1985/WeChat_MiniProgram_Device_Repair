var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    breakTypeId: 0, //类型编号
    breakTypeName: "", //类型名称
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var breakTypeId = params.breakTypeId
    var url = config.basePath + "api/breakType/get/" + breakTypeId
    utils.sendRequest(url, {}, function (breakTypeRes) {
      wx.stopPullDownRefresh()
      self.setData({
        breakTypeId: breakTypeRes.data.breakTypeId,
        breakTypeName: breakTypeRes.data.breakTypeName,
      })
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  }

})

