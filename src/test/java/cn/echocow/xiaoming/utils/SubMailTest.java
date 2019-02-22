package cn.echocow.xiaoming.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-21 22:27
 */
public class SubMailTest {

    @Test
    public void messageOneTest() throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        String url = "https://api.mysubmail.com/message/xsend";
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("appid", "");
        jsonParam.put("signature", "");
        jsonParam.put("project", "");
        jsonParam.put("to", "");
        JSONObject vars = new JSONObject();
        vars.put("code", "1234");
        vars.put("time", "30");
        jsonParam.put("vars", vars.toJSONString());
        StringEntity entity = new StringEntity(jsonParam.toJSONString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpResponse resp = httpclient.execute(httpPost);
        String respContent = null;
        if (resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he, "UTF-8");
        }
        System.out.println(respContent);
    }

    @Test
    public void messageManyTest() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "https://api.mysubmail.com/message/multixsend";
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("appid", "");
        jsonParam.put("signature", "");
        jsonParam.put("project", "");

        JSONObject vars = new JSONObject();
        vars.put("code", "1234");
        vars.put("time", "30");

        JSONObject person1 = new JSONObject();
        person1.put("to", "");
        person1.put("vars", vars);

        JSONObject person2 = new JSONObject();
        person2.put("to", "");
        person2.put("vars", vars);

        JSONArray multi = new JSONArray();
        multi.add(person1);
        multi.add(person2);

        jsonParam.put("multi", multi.toJSONString());

        System.out.println(jsonParam.toString());

        StringEntity entity = new StringEntity(jsonParam.toJSONString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpResponse resp = httpclient.execute(httpPost);
        String respContent = null;
        if (resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he, "UTF-8");
        }
        System.out.println(respContent);
    }
}

