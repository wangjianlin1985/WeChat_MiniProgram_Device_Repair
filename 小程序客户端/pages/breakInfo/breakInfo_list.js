var utils = require("../../utils/common.js");
var config = require("../../utils/config.js");

Page({
  /**
   * 页面的初始数据
   */
  data: {
    queryViewHidden: true, //是否隐藏查询条件界面
    taskStateObj_Index:"0", //任务状态查询条件
    taskInfoStates: [{"stateId":0,"stateName":"不限制"}],
    repariManObj_Index:"0", //维修人员查询条件
    repairMans: [{"repainManNumber":"","repairManName":"不限制"}],
    breakTypeObj_Index:"0", //故障类型查询条件
    breakTypes: [{"breakTypeId":0,"breakTypeName":"不限制"}],
    buildingObj_Index:"0", //所在宿舍查询条件
    buildingInfos: [{"buildingId":0,"buildingName":"不限制"}],
    breakReason: "", //故障信息查询关键字
    studentObj_Index:"0", //申报学生查询条件
    userInfos: [{"user_name":"","name":"不限制"}],
    commitDate: "", //报修时间查询关键字
    breakInfos: [], //界面显示的故障信息列表数据
    page_size: 8, //每页显示几条数据
    page: 1,  //当前要显示第几页
    totalPage: null, //总的页码数
    loading_hide: true, //是否隐藏加载动画
    nodata_hide: true, //是否隐藏没有数据记录提示
  },

  // 加载故障信息列表
  listBreakInfo: function () {
    var self = this
    var url = config.basePath + "api/breakInfo/list"
    //如果要显示的页码超过总页码不操作
    if(self.data.totalPage != null && self.data.page > self.data.totalPage) return
    self.setData({
      loading_hide: false,  //显示加载动画
    })
    //提交查询参数到服务器查询数据列表
    utils.sendRequest(url, {
      "taskStateObj.stateId": self.data.taskInfoStates[self.data.taskStateObj_Index].stateId,
      "repariManObj.repainManNumber": self.data.repairMans[self.data.repariManObj_Index].repainManNumber,
      "breakTypeObj.breakTypeId": self.data.breakTypes[self.data.breakTypeObj_Index].breakTypeId,
      "buildingObj.buildingId": self.data.buildingInfos[self.data.buildingObj_Index].buildingId,
      "breakReason": self.data.breakReason,
      "studentObj.user_name": self.data.userInfos[self.data.studentObj_Index].user_name,
      "commitDate": self.data.commitDate,
      "page": self.data.page,
      "rows": self.data.page_size,
    }, function (res) { 
      wx.stopPullDownRefresh()
      setTimeout(function () {  
        self.setData({
          breakInfos: self.data.breakInfos.concat(res.data.list),
          totalPage: res.data.totalPage,
          loading_hide: true
        })
      }, 500)
      //如果当前显示的是最后一页，则显示没数据提示
      if(self.data.page == self.data.totalPage) {
        self.setData({
          nodata_hide: false,
        })
      }
    })
  },

  //显示或隐藏查询视图切换
  toggleQueryViewHide: function () {
    this.setData({
      queryViewHidden: !this.data.queryViewHidden,
    })
  },

  //点击查询按钮的事件
  queryBreakInfo: function(e) {
    var self = this
    self.setData({
      page: 1,
      totalPage: null,
      breakInfos: [],
      loading_hide: true, //隐藏加载动画
      nodata_hide: true, //隐藏没有数据记录提示 
      queryViewHidden: true, //隐藏查询视图
    })
    self.listBreakInfo()
  },

  //查询参数报修时间选择事件
  bind_commitDate_change: function (e) {
    this.setData({
      commitDate: e.detail.value
    })
  },
  //清空查询参数报修时间
  clear_commitDate: function (e) {
    this.setData({
      commitDate: "",
    })
  },

  //绑定查询参数输入事件
  searchValueInput: function (e) {
    var id = e.target.dataset.id
    if (id == "breakReason") {
      this.setData({
        "breakReason": e.detail.value,
      })
    }

  },

  //查询参数任务状态选择事件
  bind_taskStateObj_change: function(e) {
    this.setData({
      taskStateObj_Index: e.detail.value
    })
  },

  //查询参数维修人员选择事件
  bind_repariManObj_change: function(e) {
    this.setData({
      repariManObj_Index: e.detail.value
    })
  },

  //查询参数故障类型选择事件
  bind_breakTypeObj_change: function(e) {
    this.setData({
      breakTypeObj_Index: e.detail.value
    })
  },

  //查询参数所在宿舍选择事件
  bind_buildingObj_change: function(e) {
    this.setData({
      buildingObj_Index: e.detail.value
    })
  },

  //查询参数申报学生选择事件
  bind_studentObj_change: function(e) {
    this.setData({
      studentObj_Index: e.detail.value
    })
  },

  init_query_params: function() { 
    var self = this
    var url = null
    //初始化任务状态下拉框
    url = config.basePath + "api/taskInfoState/listAll"
    utils.sendRequest(url,null,function(res){
      wx.stopPullDownRefresh();
      self.setData({
        taskInfoStates: self.data.taskInfoStates.concat(res.data),
      })
    })
    //初始化维修人员下拉框
    url = config.basePath + "api/repairMan/listAll"
    utils.sendRequest(url,null,function(res){
      wx.stopPullDownRefresh();
      self.setData({
        repairMans: self.data.repairMans.concat(res.data),
      })
    })
    //初始化故障类型下拉框
    url = config.basePath + "api/breakType/listAll"
    utils.sendRequest(url,null,function(res){
      wx.stopPullDownRefresh();
      self.setData({
        breakTypes: self.data.breakTypes.concat(res.data),
      })
    })
    //初始化所在宿舍下拉框
    url = config.basePath + "api/buildingInfo/listAll"
    utils.sendRequest(url,null,function(res){
      wx.stopPullDownRefresh();
      self.setData({
        buildingInfos: self.data.buildingInfos.concat(res.data),
      })
    })
    //初始化申报学生下拉框
    url = config.basePath + "api/userInfo/listAll"
    utils.sendRequest(url,null,function(res){
      wx.stopPullDownRefresh();
      self.setData({
        userInfos: self.data.userInfos.concat(res.data),
      })
    })
  },

  //删除故障信息记录
  removeBreakInfo: function (e) {
    var self = this
    var taskId = e.currentTarget.dataset.taskid
    wx.showModal({
      title: '提示',
      content: '确定要删除taskId=' + taskId + '的记录吗？',
      success: function (sm) {
        if (sm.confirm) {
          // 用户点击了确定 可以调用删除方法了
          var url = config.basePath + "api/breakInfo/delete/" + taskId
          utils.sendRequest(url, null, function (res) {
            wx.stopPullDownRefresh();
            wx.showToast({
              title: '删除成功',
              icon: 'success',
              duration: 500
            })
            //删除故障信息后客户端同步删除数据
            var breakInfos = self.data.breakInfos;
            for (var i = 0; i < breakInfos.length; i++) {
              if (breakInfos[i].taskId == taskId) {
                breakInfos.splice(i, 1)
                break
              }
            }
            self.setData({ breakInfos: breakInfos })
          })
        } else if (sm.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.listBreakInfo()
    this.init_query_params()
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var self = this
    self.setData({
      page: 1,  //显示最新的第1页结果
      breakInfos: [], //清空故障信息数据
      nodata_hide: true, //隐藏没数据提示
    })
    self.listBreakInfo()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var self = this
    if (self.data.page < self.data.totalPage) {
      self.setData({
        page: self.data.page + 1, 
      })
      self.listBreakInfo()
    }
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }

})

