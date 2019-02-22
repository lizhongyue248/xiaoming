package cn.echocow.xiaoming.utils;

import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.model.properties.MessageConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-22 01:17
 */
@Component
@Slf4j
public class SubMailUtils {

    private final HttpClient httpClient;
    private final MessageConfig messageConfig;
    private static final String X_SEND = "https://api.mysubmail.com/message/xsend";
    private static final String MULTI_X_SEND = "https://api.mysubmail.com/message/multixsend";

    @Autowired
    public SubMailUtils(HttpClient httpClient, MessageConfig messageConfig) {
        this.httpClient = httpClient;
        this.messageConfig = messageConfig;
    }

    /**
     * 发送一条信息
     *
     * @param to   接收人
     * @param vars 模板变量
     */
    public boolean sendRegisterMessage(String to, JSONObject vars) {
        HttpPost httpPost = new HttpPost(X_SEND);
        JSONObject jsonParam = appInfo(messageConfig.getRegisterTemplate());
        jsonParam.put("to", to);
        jsonParam.put("vars", vars);
        httpPost.setEntity(entityBuilder(jsonParam.toJSONString()));
        HttpResponse resp;
        try {
            resp = httpClient.execute(httpPost);
            log.info(EntityUtils.toString(resp.getEntity(), "UTF-8"));
            return resp.getStatusLine().getStatusCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送作业提醒模板
     *
     * @param users 接受用户
     * @param vars  模板变量
     */
    @Async
    public void sendHomeworkTipMessage(List<SysUser> users, JSONObject vars) {
        if (users.isEmpty()){
            return;
        }
        HttpPost httpPost = new HttpPost(MULTI_X_SEND);
        JSONObject param = appInfo(messageConfig.getHomeworkTipTemplate());
        JSONArray multi = new JSONArray();
        multi.addAll(users.stream()
                .filter(user -> user.getPhone() != null && user.getPhone().length() == 11)
                .map(user -> {
                    JSONObject person = new JSONObject();
                    person.put("to", user.getPhone());
                    person.put("vars", copyJson(vars));
                    return person;
                }).collect(Collectors.toList()));
        param.put("multi", multi);
        httpPost.setEntity(entityBuilder(param.toJSONString()));
        HttpResponse resp;
        try {
            resp = httpClient.execute(httpPost);
            log.info(EntityUtils.toString(resp.getEntity(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject appInfo(String project) {
        JSONObject param = new JSONObject();
        param.put("appid", messageConfig.getAppId());
        param.put("signature", messageConfig.getAppKey());
        param.put("project", project);
        return param;
    }

    private StringEntity entityBuilder(String param) {
        StringEntity entity = new StringEntity(param, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        return entity;
    }

    private JSONObject copyJson(JSONObject old) {
        JSONObject copy = new JSONObject();
        old.forEach(copy::put);
        return copy;
    }
}
