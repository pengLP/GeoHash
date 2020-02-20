<%--
  Created by IntelliJ IDEA.
  User: Lp
  Date: 2019/5/26
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Document</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
  <script src="<%=path%>/js/jquery.js"></script>
  <script src="<%=path%>/js/bootstrap.js"></script>
  <script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.14&key=8a66e1872cf4d05eff1d2282a6964bd3"></script>
  <style>
    #map{
      width:100%;
      height: 600px;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="row clearfix">
    <div class="col-md-12 column">
      <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> <a class="navbar-brand" href="#">Brand</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li class="active">
              <a onclick="searchNear()">显示附近餐馆</a>
            </li>
            <li>
              <a onclick="clearMarker()">删除点标记</a>
            </li>
            <form class="navbar-form navbar-left" role="search">
              <div class="form-group">
                <input type="text" class="form-control" placeholder="经度" id="longitude"/>
                <input type="text" class="form-control" placeholder="纬度" id="latitude"/>
              </div>
              <button type="button" class="btn btn-default" onclick="search()">Submit</button>
            </form>
          </ul>
        </div>
      </nav>

      <div class="alert alert-success alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <h4>
          提示!   当前经纬度：<strong id="add"></strong>
        </h4> <strong>Message!</strong> <span id="text"></span>
      </div>

      <div class="jumbotron" id="map">



      </div>

      <table class="table table-striped" id="table">
        <thead>
        <tr>
          <th>
            饭店名称
          </th>
          <th>
            地址
          </th>
          <th>
            经纬度
          </th>
        </tr>
        </thead>
        <tbody id="list">


        </tbody>
      </table>

    </div>
  </div>
</div>
<script src="https://a.amap.com/jsapi_demos/static/demo-center/js/demoutils.js"></script>
<script>

  //经度
  var longitude = 39.909231;
  //纬度
  var latitude = 116.397428;
  var map = new AMap.Map('map', {
    zoom:11,//级别
    center: [latitude, longitude],//中心点坐标
    viewMode:'3D'//使用3D视图
  });
  var bounds = map.getBounds();
  map.setLimitBounds(bounds);

  document.querySelector("#add").innerText = longitude + " , " +latitude;

  function showInfoClick(e){
    var text = '您在 [ '+e.lnglat.getLng()+','+e.lnglat.getLat()+' ] 的位置单击了地图！'
    document.querySelector("#text").innerText = text;
  }
  function showInfoDbClick(e){
    var text = '您在 [ '+e.lnglat.getLng()+','+e.lnglat.getLat()+' ] 的位置双击了地图！';
    document.querySelector("#text").innerText = text;
    longitude = e.lnglat.getLat();
    latitude = e.lnglat.getLng();
    document.querySelector("#add").innerText = longitude + " , " +latitude;

  }
  function showInfoMove(e){
    var text = '您移动到了 [ '+e.lnglat.getLng()+','+e.lnglat.getLat()+' ] 位置！'
    document.querySelector("#text").innerText = text;
  }

  // 事件绑定
  function clickOn(){
      log.success("绑定事件!");
      map.on('click', showInfoClick);
      map.on('dblclick', showInfoDbClick);
      map.on('mousemove', showInfoMove);
  }

  var markers = [];
  clickOn();

  function sendMessage(lo,la) {
    var s = "";
    $.ajax({
      type : "post",//向后台请求的方式，有post，get两种方法
      url : "/search",//url填写的是请求的路径
      cache : false,//缓存是否打开
      data: {
        longitude:lo,
        latitude: la
      },
      dataType : 'json',//返回的数据类型
      success : function(data) {//请求的返回成功的方法
        for(var i = 0; i < data.length; i++) {
          s += "<tr>" + "<td>"+data[i].name+"</td>" + "<td>"+data[i].address+"</td>" + "<td>"+data[i].location+"</td>"+"</tr>";

          // 创建一个 Marker 实例：
          var str = data[i].location.split(",");
          var marker = new AMap.Marker({
            position: new AMap.LngLat(str[0] , str[1]),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
            title: data[i].name
          });
          // 将创建的点标记添加到已有的地图实例：
          markers.push(marker);
          map.add(marker);
        }

        document.getElementById("list").innerHTML = s;
      },
      error : function(XMLHttpRequest, textStatus, errorThrown) {//请求的失败的返回的方法
        alert(XMLHttpRequest.toString() + " , " + textStatus + " , " + errorThrown);
      }
    });
  }

  //查找附近餐馆
  function searchNear() {
    sendMessage(longitude,latitude);
  }
  
  //搜索经纬度附近餐馆
  function search() {
    longitude = $("#longitude").val();
    latitude = $("#latitude").val();
    document.querySelector("#add").innerText = longitude + " , " +latitude;
    sendMessage(longitude,latitude);
  }



  function clearMarker() {
    document.getElementById("list").innerHTML = "";
    map.remove(markers);
  }

</script>

</body>
</html>
