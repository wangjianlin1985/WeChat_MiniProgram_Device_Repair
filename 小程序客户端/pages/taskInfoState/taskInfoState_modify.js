var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    stateId: 0, //状态编号
    stateName: "", //状态名称
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  //提交服务器修改任务状态信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/taskInfoState/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var taskInfoStatelist_page = pages[pages.length - 2];
      var taskInfoStates = taskInfoStatelist_page.data.taskInfoStates
      for(var i=0;i<taskInfoStates.length;i++) {
        if(taskInfoStates[i].stateId == res.data.stateId) {
          taskInfoStates[i] = res.data
          break
        }
      }
      taskInfoStatelist_page.setData({taskInfoStates:taskInfoStates})
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
    var stateId = params.stateId
    var url = config.basePath + "api/taskInfoState/get/" + stateId
    utils.sendRequest(url, {}, function (taskInfoStateRes) {
      wx.stopPullDownRefresh()
      self.setData({
        stateId: taskInfoStateRes.data.stateId,
        stateName: taskInfoStateRes.data.stateName,
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

