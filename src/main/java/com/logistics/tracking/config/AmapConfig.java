package com.logistics.tracking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 高德地图配置类，绑定 application.yml 配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "amap") // 绑定前缀：amap.key
public class AmapConfig {

    /**
     * 高德地图 API 密钥（需要在高德开放平台申请）
     */
    private String key;

    /**
     * 地理编码接口（地址 -> 经纬度）
     * 示例：?address=北京市朝阳区&key=xxx
     */
    private String geocodeUrl = "https://restapi.amap.com/v3/geocode/geo";

    /**
     * 逆地理编码接口（经纬度 -> 地址）
     * 示例：?location=116.481488,39.990464&key=xxx
     */
    private String regeoUrl = "https://restapi.amap.com/v3/geocode/regeo";

    /**
     * 距离测量接口（多个点之间测距）
     * 示例：?origins=116.481028,39.989643&destination=116.434446,39.90816&key=xxx
     */
    private String distanceUrl = "https://restapi.amap.com/v3/distance";

    /**
     * 驾车路径规划接口（推荐的驾车路线）
     * 示例：?origin=116.481028,39.989643&destination=116.434446,39.90816&key=xxx
     */
    private String drivingUrl = "https://restapi.amap.com/v3/direction/driving";

    /**
     * 备用路径规划接口（如自行车、步行等）
     * 可在 yml 配置扩展，如 amap.direction-url: https://xxx
     */
    private String directionUrl;
}
