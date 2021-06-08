package io.chia.configs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.io.IOUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HttpClientUtils {

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(25,10, TimeUnit.SECONDS))
            .build();

    public static String postMsg(String url, HashMap<String,Object> mapParam, String sign, String appkey,String nonce,String timestamp) {
        Response response = null;
        try {
            MediaType jsonType = MediaType.parse("application/json;charset=utf-8");
            JSONObject json = new JSONObject(mapParam);
            String content = json.toString();
            RequestBody requestBody = RequestBody.create(jsonType, content);
            Request request = new Request.Builder().url(url)
                    .post(requestBody)
                    .addHeader("appKey",appkey)
                    .addHeader("nonce",nonce)
                    .addHeader("timestamp",timestamp)
                    .addHeader("sign",sign)
                    .build();
            response = okHttpClient.newCall(request).execute();
            String body = IOUtils.toString(response.body().byteStream(), "UTF-8");
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (Objects.nonNull(response)){
                response.close();
            }
        }
        return null;
    }
    public static String postMsg(String url, HashMap<String,String> mapParam) {
        Response response = null;
        try {
            MediaType jsonType = MediaType.parse("application/json;charset=utf-8");
            JSONObject json = new JSONObject();
            String content = "";
            if (mapParam!=null){
                for (String key:mapParam.keySet()){
                    json.put(key,mapParam.get(key));
                }
                content = json.toString();
            }
            RequestBody requestBody = RequestBody.create(jsonType, content);
            Request request = new Request.Builder().url(url)
                    .post(requestBody)
                    .build();
            response = okHttpClient.newCall(request).execute();
            String body = IOUtils.toString(response.body().byteStream(), "UTF-8");
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (Objects.nonNull(response)){
                response.close();
            }
        }
        return null;
    }

    public static String postObject(String url, Object object){
        Map<String, String> map= convertToMap(object);
        return postObject(url,map);
    }

    public static String postObject(String url, HashMap<String,Object> mapParam) {
        Response response = null;
        try {
            MediaType jsonType = MediaType.parse("application/json;charset=utf-8");
            JSONObject json = new JSONObject();
            String content = "";
            if (mapParam!=null){
                for (String key:mapParam.keySet()){
                    json.put(key,mapParam.get(key));
                }
                content = json.toString();
            }
            RequestBody requestBody = RequestBody.create(jsonType, content);
            Request request = new Request.Builder().url(url)
                    .post(requestBody)
                    .build();
            response = okHttpClient.newCall(request).execute();
            String body = IOUtils.toString(response.body().byteStream(), "UTF-8");
            return body;
        } catch (Exception e) {
        }finally {
            if (Objects.nonNull(response)){
                response.close();
            }
        }
        return null;
    }

    public static String postMsg2(String url, HashMap<String,Object> mapParam) {
        Response response = null;
        try {
            MediaType jsonType = MediaType.parse("application/json;charset=utf-8");
            JSONObject json = new JSONObject();
            String content = "";
            if (mapParam!=null){
                for (String key:mapParam.keySet()){
                    json.put(key,mapParam.get(key));
                }
                content = json.toString();
            }
            RequestBody requestBody = RequestBody.create(jsonType, content);
            Request request = new Request.Builder().url(url)
                    .post(requestBody)
                    .build();
            response = okHttpClient.newCall(request).execute();
            String body = IOUtils.toString(response.body().byteStream(), "UTF-8");
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (Objects.nonNull(response)){
                response.close();
            }
        }
        return null;
    }


    /**
     * 将Object对象里面的属性和值转化成Map对象
     * @param obj  对象
     * @return 对象转map
     */
    public static Map<String, String> convertToMap(Object obj) {
        Map result = (Map) JSON.toJSON(obj);
        Map<String, String> sParaNew = paraFilter(result);
        return sParaNew;
    }

    /**
     * map 元素转换
     * @param map
     * @return map字符
     */
    public static Map<String, String> paraFilter(Map<String, Object> map) {
        Map<String, String> result = new HashMap<>();
        if (map == null || map.size() <= 0) {
            return result;
        }
        for (String key : map.keySet()) {
            Object value = map.get(key);

            if (value == null || value.equals("")) {
                continue;
            }
            String v1 ;
            if (value instanceof BigDecimal) {
                v1 = ((BigDecimal) value).toPlainString();
            } else if (value instanceof String) {
                v1 = value.toString();
            } else {
                v1 = JSONObject.toJSONString(value);
            }
            if (value.equals("")) {
                continue;
            }
            result.put(key, v1);
        }
        return result;
    }

}
