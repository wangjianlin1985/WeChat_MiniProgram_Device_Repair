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
    myPhoto: "upload/NoImage.jpg", //个人头像
    myPhotoUrl: "",
    myPhotoList: [],
    workYear: "", //工作年限
    telephone: "", //联系电话
    repairMan: "", //维修人员介绍
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  //选择个人头像上传
  select_myPhoto: function (e) {
    var self = this
    wx.chooseImage({
      count: 1,   //可以上传一张图片
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        var tempFilePaths = res.tempFilePaths
        self.setData({
          myPhotoList: tempFilePaths,
          loadingHide: false, 
        });

        utils.sendUploadImage(config.basePath + "upload/image", tempFilePaths[0], function (res) {
          wx.stopPullDownRefresh()
          setTimeout(function () {
            self.setData({
              myPhoto: res.data,
              loadingHide: true
            })
          }, 200)
        })
      }
    })
  },

  //提交服务器修改维修人员信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/repairMan/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var repairManlist_page = pages[pages.length - 2];
      var repairMans = repairManlist_page.data.repairMans
      for(var i=0;i<repairMans.length;i++) {
        if(repairMans[i].repainManNumber == res.data.repainManNumber) {
          repairMans[i] = res.data
          break
        }
      }
      repairManlist_page.setData({repairMans:repairMans})
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

