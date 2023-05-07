var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    repainManNumber: "", //人员编号
    repairManName: "", //人员姓名
    repairManSex: "", //性别
    myPhotoUrl: "", //个人头像
    workYear: "", //工作年限
    telephone: "", //联系电话
    repairMan: "", //维修人员介绍
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var repainManNumber = params.repainManNumber
    var url = config.basePath + "api/repairMan/get/" + repainManNumber
    utils.sendRequest(url, {}, function (repairManRes) {
      wx.stopPullDownRefresh()
      self.setData({
        repainManNumber: repairManRes.data.repainManNumber,
        repairManName: repairManRes.data.repairManName,
        repairManSex: repairManRes.data.repairManSex,
        myPhoto: repairManRes.data.myPhoto,
        myPhotoUrl: repairManRes.data.myPhotoUrl,
        workYear: repairManRes.data.workYear,
        telephone: repairManRes.data.telephone,
        repairMan: repairManRes.data.repairMan,
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

