package com.wxprogram.luckaward.util;



import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/*
 * @breif:
 * @Author: ArDaBao
 * @Date: 2020/2/3 14:59
 * @Month:02
 */
public class AuthUtil {
    public static final String APP_ID = "wx3bf58026cd9ffa44";     //填写自己的APPID
    public static final String APP_SECRET = "00d952e4c6db377f984d1e32ef0eb0fa";   //填写自己的APPSECRET
    public static JSONObject doGetJson(String url) throws Exception, IOException {
        JSONObject jsonObject=null;
        //初始化httpClient
        DefaultHttpClient client=new DefaultHttpClient();
        //用Get方式进行提交
        HttpGet httpGet=new HttpGet(url);
        //发送请求
        HttpResponse response= client.execute(httpGet);
        //获取数据
        HttpEntity entity=response.getEntity();
        //格式转换
        if (entity!=null) {
            String result= EntityUtils.toString(entity,"UTF-8");
            jsonObject= JSONObject.fromObject(result);
        }
        //释放链接
        httpGet.releaseConnection();
        return jsonObject;
    }

}
