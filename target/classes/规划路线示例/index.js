
// 基本地图加载
var map = new AMap.Map("container", {
    resizeEnable: true,
    center: [116.397428, 39.90923], // 地图中心点
    zoom: 13 // 地图显示的缩放级别
});

// 构造路线导航类
var driving = new AMap.Driving({
    map: map,
    panel: "panel"
});

// 根据起终点经纬度规划驾车导航路线
driving.search(new AMap.LngLat(116.472455, 39.909208), new AMap.LngLat(116.315419, 39.983342), function (status, result) {
    if (status === 'complete') {
        console.log('绘制驾车路线完成');
    } else {
        console.error('获取驾车数据失败：' + result);
    }
});