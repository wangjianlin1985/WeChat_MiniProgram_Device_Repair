var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    buildingId: 0, //记录编号
    areaObj_Index: "0", //所在区域
    areaInfos: [],
    buildingName: "", //宿舍名称
    manageMan: "", //管理员
    telephone: "", //门卫电话
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  //所在区域修改事件
  bind_areaObj_change: function (e) {
    this.setData({
      areaObj_Index: e.detail.value
    })
  },

  //提交服务器修改宿舍信息信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/buildingInfo/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var buildingInfolist_page = pages[pages.length - 2];
      var buildingInfos = buildingInfolist_page.data.buildingInfos
      for(var i=0;i<buildingInfos.length;i++) {
        if(buildingInfos[i].buildingId == res.data.buildingId) {
          buildingInfos[i] = res.data
          break
        }
      }
      buildingInfolist_page.setData({buildingInfos:buildingInfos})
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
    var buildingId = params.buildingId
    var url = config.basePath + "api/buildingInfo/get/" + buildingId
    utils.sendRequest(url, {}, function (buildingInfoRes) {
      wx.stopPullDownRefresh()
      self.setData({
        buildingId: buildingInfoRes.data.buildingId,
        areaObj_Index: 1,
        buildingName: buildingInfoRes.data.buildingName,
        manageMan: buildingInfoRes.data.manageMan,
        telephone: buildingInfoRes.data.telephone,
      })

      var areaInfoUrl = config.basePath + "api/areaInfo/listAll" 
      utils.sendRequest(areaInfoUrl, null, function (res) {
        wx.stopPullDownRefresh()
        self.setData({
          areaInfos: res.data,
        })
        for (var i = 0; i < self.data.areaInfos.length; i++) {
          if (buildingInfoRes.data.areaObj.areaId == self.data.areaInfos[i].areaId) {
            self.setData({
              areaObj_Index: i,
            });
            break;
          }
        }
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

