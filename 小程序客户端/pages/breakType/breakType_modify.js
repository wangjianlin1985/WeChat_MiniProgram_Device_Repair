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

  //提交服务器修改故障类型信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/breakType/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var breakTypelist_page = pages[pages.length - 2];
      var breakTypes = breakTypelist_page.data.breakTypes
      for(var i=0;i<breakTypes.length;i++) {
        if(breakTypes[i].breakTypeId == res.data.breakTypeId) {
          breakTypes[i] = res.data
          break
        }
      }
      breakTypelist_page.setData({breakTypes:breakTypes})
      setTimeout(function () {
        wx.navigateBack({})
      }, 500) 
    })
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
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  },

})

