var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    buildingId: 0, //记录编号
    areaObj: "", //所在区域
    buildingName: "", //宿舍名称
    manageMan: "", //管理员
    telephone: "", //门卫电话
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var buildingId = params.buildingId
    var url = config.basePath + "api/buildingInfo/get/" + buildingId
    utils.sendRequest(url, {}, function (buildingInfoRes) {
      wx.stopPullDownRefresh()
      self.setData({
        buildingId: buildingInfoRes.data.buildingId,
        areaObj: buildingInfoRes.data.areaObj.areaName,
        buildingName: buildingInfoRes.data.buildingName,
        manageMan: buildingInfoRes.data.manageMan,
        telephone: buildingInfoRes.data.telephone,
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

