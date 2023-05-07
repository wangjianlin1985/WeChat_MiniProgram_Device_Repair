var utils = require("../../utils/common.js");
var config = require("../../utils/config.js");

Page({
  /**
   * 页面的初始数据
   */
  data: {
    repainManNumber: "", //人员编号
    repairManName: "", //人员姓名
    repairManSex: "", //性别
    myPhoto: "upload/NoImage.jpg", //个人头像
    myPhotoList: [],
    workYear: "", //工作年限
    telephone: "", //联系电话
    repairMan: "", //维修人员介绍
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /*提交添加维修人员到服务器 */
  formSubmit: function (e) {
    var self = this;
    var formData = e.detail.value;
    var url = config.basePath + "api/repairMan/add";
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '发布成功',
        icon: 'success',
        duration: 500
      })

      //提交成功后重置表单数据
      self.setData({
        repainManNumber: "",
    repairManName: "",
    repairManSex: "",
        myPhoto: "upload/NoImage.jpg",
        myPhotoList: [],
    workYear: "",
    telephone: "",
    repairMan: "",
        loadingHide: true,
        loadingText: "网络操作中。。",
      })
    });
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
          loadingHide: false
        });
        utils.sendUploadImage(config.basePath + "upload/image", tempFilePaths[0], function (res) {
          wx.stopPullDownRefresh()
          setTimeout(function () {
            self.setData({
              myPhoto: res.data,
              loadingHide: true
            });
          }, 200);
        });
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var token = wx.getStorageSync('authToken');
    if (!token) {
      wx.navigateTo({
        url: '../mobile/mobile',
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  }
})

