package com.system.common.component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.system.common.exception.CustomerAuthenticationException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 *  高德地图API
 * @author lutong
 * @data 2025-3-5 005 13:41
 */

@Component
public class GouldComponent {

    // 高德web服务KEY
    private static final String GOULD_KEY= "3d79746b1a2e7e4b33b0c5965a24b656";


    /**
     *  根据地址名称得到两个地址间的距离
     * @param start 起始位置
     * @param start 结束位置
     * @return 两个地址间的距离
     */
    public long getDistanceByAddress(String start, String end) {
        String startLonLat = getLonLat(start);
        String endLonLat = getLonLat(end);
        long dis = getDistance(startLonLat, endLonLat);
        return dis;
    }

    /**
     *  地址转换为经纬度
     * @param address 地址
     * @return 经度,纬度
     */
    public String getLonLat(String address) {
        String url = "http://restapi.amap.com/v3/geocode/geo?key=" + GOULD_KEY + "&address=" + address;
        String result = getResponse(url);
        JSONObject job = JSONObject.parseObject(result);
        this.judgmentApi(job);

        JSONObject jobJSON = JSONObject.parseObject(job.get("geocodes").toString().substring(1, job.get("geocodes").toString().length() - 1));
        return jobJSON.get("location").toString();
    }

    /**
     * 将经纬度getLng， getLat通过getAMapByLngAndLat方法转换地址
     * @param getLng 经度
     * @param getLat 纬度
     * @return 地址名称
     * @throws Exception
     */
    public String getAMapByLngAndLat(String getLng, String getLat) throws Exception {
        try {
            String url = "http://restapi.amap.com/v3/geocode/regeo?output=JSON&location=" + getLng + "," + getLat + "&key="
                    + GOULD_KEY + "&radius=0&extensions=base";
            String result = getResponse(url);
            JSONObject job = JSONObject.parseObject(result);
            this.judgmentApi(job);
            // 将获取结果转为json 数据
            JSONObject obj = JSONObject.parseObject(result);
            if (obj.get("status").toString().equals("1")) {
                // 如果没有返回-1
                JSONObject regeocode = obj.getJSONObject("regeocode");
                if (regeocode.size() > 0) {
                    // 在regeocode中拿到 formatted_address 具体位置
                    String formatted = regeocode.get("formatted_address").toString();
                    return formatted;

                } else {
                    return "-1";
                }
            } else {
                return "-1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     *  根据两个定位点的经纬度算出两点间的距离
     * @param startLonLat 起始经纬度
     * @param endLonLat 结束经纬度（目标经纬度）
     * @return 两个定位点之间的距离
     */
    private long getDistance(String startLonLat, String endLonLat) {
        String url = "http://restapi.amap.com/v3/distance?key=" + GOULD_KEY + "&origins=" + startLonLat + "&destination=" + endLonLat;
        // 调用
        String result = getResponse(url);
        JSONObject job = JSONObject.parseObject(result);
        this.judgmentApi(job);

        JSONArray ja = job.getJSONArray("results");
        JSONObject jobO = JSONObject.parseObject(ja.getString(0));
        return Long.parseLong(jobO.get("distance").toString());
    }


    /**
     *  路径规划
     * @param origin 出发点经纬度
     * @param destination 目的地经纬度
     * @param strategy 模式
     * @return
     */
    public JSONObject pathPlanning(String origin, String destination, Integer strategy) {
        // 调用接口
        String url = "https://restapi.amap.com/v5/direction/driving?key=" + GOULD_KEY +
                "&origin=" + origin + "&destination=" + destination + "&strategy=" + strategy;
        String result = getResponse(url);
        JSONObject job = JSONObject.parseObject(result);
        this.judgmentApi(job);

        return job.getJSONObject("route");
    }


    /**
     *  发送请求
     * @param serverUrl 请求地址
     */
    public static String getResponse(String serverUrl) {
        // 用JAVA发起http请求，并返回json格式的结果
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public void judgmentApi(JSONObject job) {
        // 判断是否获取成功
        String info = job.get("info").toString();
        String infoCode = job.get("infocode").toString();
        if (!Objects.equals(infoCode, "10000") && !Objects.equals(info, "OK")) {
            throw new CustomerAuthenticationException(info);
        }
    }
}
