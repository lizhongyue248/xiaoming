package cn.echocow.xiaoming.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-12 13:20
 */
@SuppressWarnings("all")
@Slf4j
public class GeetestLib {

    private final String verName = "4.0";
    private final String sdkLang = "java";
    private final String apiUrl = "http://api.geetest.com";
    private final String registerUrl = "/register.php";
    private final String validateUrl = "/validate.php";
    private final String json_format = "1";

    /**
     * 极验验证二次验证表单数据 chllenge
     */
    public static final String FN_GEETEST_CHALLENGE = "geetest_challenge";

    /**
     * 极验验证二次验证表单数据 validate
     */
    public static final String FN_GEETEST_VALIDATE = "geetest_validate";

    /**
     * 极验验证二次验证表单数据 seccode
     */
    public static final String FN_GEETEST_SECCODE = "geetest_seccode";

    /**
     * 公钥
     */
    private String captchaId = "";

    /**
     * 私钥
     */
    private String privateKey = "";

    /**
     * 是否开启新的failback
     */
    private boolean newFailback = false;

    /**
     * 返回字符串
     */
    private String responseStr = "";

    /**
     * 调试开关，是否输出调试日志
     */
    private boolean debugCode = true;

    /**
     * 极验验证API服务状态Session Key
     */
    public String gtServerStatusSessionKey = "gt_server_status";

    /**
     * 带参数构造函数
     *
     * @param captchaId
     * @param privateKey
     */
    public GeetestLib(String captchaId, String privateKey, boolean newFailback) {

        this.captchaId = captchaId;
        this.privateKey = privateKey;
        this.newFailback = newFailback;
    }

    /**
     * 获取本次验证初始化返回字符串
     *
     * @return 初始化结果
     */
    public String getResponseStr() {
        return responseStr;
    }

    public String getVersionInfo() {
        return verName;
    }

    /**
     * 预处理失败后的返回格式串
     *
     * @return 格式串
     */
    private String getFailPreProcessRes() {

        Long rnd1 = Math.round(Math.random() * 100);
        Long rnd2 = Math.round(Math.random() * 100);
        String md5Str1 = md5Encode(rnd1 + "");
        String md5Str2 = md5Encode(rnd2 + "");
        String challenge = md5Str1 + md5Str2.substring(0, 2);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("success", 0);
            jsonObject.put("gt", this.captchaId);
            jsonObject.put("challenge", challenge);
            jsonObject.put("new_captcha", this.newFailback);
        } catch (JSONException e) {
            gtlog("json dumps error");
        }
        return jsonObject.toString();

    }

    /**
     * 预处理成功后的标准串
     */
    private String getSuccessPreProcessRes(String challenge) {

        gtlog("challenge:" + challenge);

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("success", 1);
            jsonObject.put("gt", this.captchaId);
            jsonObject.put("challenge", challenge);

        } catch (JSONException e) {

            gtlog("json dumps error");

        }

        return jsonObject.toString();

    }

    /**
     * 验证初始化预处理
     *
     * @return 1表示初始化成功，0表示初始化失败
     */
    public int preProcess(HashMap<String, String> data) {
        if (registerChallenge(data) != 1) {
            this.responseStr = this.getFailPreProcessRes();
            return 0;
        }
        return 1;
    }

    /**
     * 用captchaID进行注册，更新challenge
     *
     * @return 1表示注册成功，0表示注册失败
     */
    private int registerChallenge(HashMap<String, String> data) {
        try {
            String userId = data.get("user_id");
            String clientType = data.get("client_type");
            String ipAddress = data.get("ip_address");

            String getUrl = apiUrl + registerUrl + "?";
            String param = "gt=" + this.captchaId + "&json_format=" + this.json_format;

            if (userId != null) {
                param = param + "&user_id=" + userId;
            }
            if (clientType != null) {
                param = param + "&client_type=" + clientType;
            }
            if (ipAddress != null) {
                param = param + "&ip_address=" + ipAddress;
            }

            gtlog("GET_URL:" + getUrl + param);
            String result_str = readContentFromGet(getUrl + param);
            if (result_str == "fail") {

                gtlog("gtServer register challenge failed");
                return 0;

            }

            gtlog("result:" + result_str);
            JSONObject jsonObject = new JSONObject(result_str);
            String return_challenge = jsonObject.getString("challenge");

            gtlog("return_challenge:" + return_challenge);

            if (return_challenge.length() == 32) {

                this.responseStr = this.getSuccessPreProcessRes(DigestUtils.md5DigestAsHex((return_challenge + this.privateKey).getBytes()));

                return 1;

            } else {

                gtlog("gtServer register challenge error");

                return 0;

            }
        } catch (Exception e) {

            gtlog(e.toString());
            gtlog("exception:register api");

        }
        return 0;
    }

    /**
     * 判断一个表单对象值是否为空
     *
     * @param gtObj 表单对象值
     * @return res
     */
    private boolean objIsEmpty(Object gtObj) {
        if (gtObj == null) {
            return true;
        }
        return gtObj.toString().trim().length() == 0;
    }

    /**
     * 检查客户端的请求是否合法,三个只要有一个为空，则判断不合法
     *
     * @param challenge challenge
     * @param validate validate
     * @param seccode seccode
     * @return
     */
    private boolean resquestIsLegal(String challenge, String validate, String seccode) {
        return !objIsEmpty(challenge) && !objIsEmpty(validate) && !objIsEmpty(seccode);
    }


    /**
     * 服务正常的情况下使用的验证方式,向gt-server进行二次验证,获取验证结果
     *
     * @param challenge challenge
     * @param validate validate
     * @param seccode seccode
     * @return 验证结果, 1表示验证成功0表示验证失败
     */
    public int enhencedValidateRequest(String challenge, String validate, String seccode, HashMap<String, String> data) {

        if (!resquestIsLegal(challenge, validate, seccode)) {
            return 0;
        }
        gtlog("request legitimate");
        String userId = data.get("user_id");
        String clientType = data.get("client_type");
        String ipAddress = data.get("ip_address");

        String postUrl = this.apiUrl + this.validateUrl;
        String param = String.format("challenge=%s&validate=%s&seccode=%s&json_format=%s",
                challenge, validate, seccode, this.json_format);
        if (userId != null) {
            param = param + "&user_id=" + userId;
        }
        if (clientType != null) {
            param = param + "&client_type=" + clientType;
        }
        if (ipAddress != null) {
            param = param + "&ip_address=" + ipAddress;
        }
        gtlog("param:" + param);
        String response = "";
        try {
            if (validate.length() <= 0) {
                return 0;
            }
            if (!checkResultByPrivate(challenge, validate)) {
                return 0;
            }
            gtlog("checkResultByPrivate");
            response = readContentFromPost(postUrl, param);
            gtlog("response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String return_seccode = "";
        try {
            JSONObject returnMap = new JSONObject(response);
            return_seccode = returnMap.getString("seccode");
            gtlog("md5: " + md5Encode(return_seccode));
            if (return_seccode.equals(md5Encode(seccode))) {
                return 1;
            } else {
                return 0;
            }
        } catch (JSONException e) {
            gtlog("json load error");
            return 0;
        }
    }

    /**
     * md5
     *
     * @param str 加密前
     * @return 加密后
     */
    private String md5Encode(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * failback使用的验证方式
     *
     * @param challenge challenge
     * @param validate validate
     * @param seccode seccode
     * @return 验证结果, 1表示验证成功0表示验证失败
     */
    public int failbackValidateRequest(String challenge, String validate, String seccode) {
        gtlog("in failback validate");
        if (!resquestIsLegal(challenge, validate, seccode)) {
            return 0;
        }
        gtlog("request legitimate");
        return 1;
    }

    /**
     * 输出debug信息，需要开启debugCode
     *
     * @param message debug信息
     */
    private void gtlog(String message) {
        if (debugCode) {
            log.debug("gtlog: " + message);
        }
    }

    private boolean checkResultByPrivate(String challenge, String validate) {
        String encodeStr = DigestUtils.md5DigestAsHex((privateKey + "geetest" + challenge).getBytes());
        return validate.equals(encodeStr);
    }

    /**
     * 发送GET请求，获取服务器返回结果
     *
     * @param url GET请求
     * @return 服务器返回结果
     * @throws IOException IO
     */
    private String readContentFromGet(String url) throws IOException {

        URL getUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();

        // 设置连接主机超时（单位：毫秒）
        connection.setConnectTimeout(2000);
        // 设置从主机读取数据超时（单位：毫秒）
        connection.setReadTimeout(2000);

        // 建立与服务器的连接，并未发送数据
        connection.connect();

        if (connection.getResponseCode() == 200) {
            // 发送数据到服务器并使用Reader读取返回的数据
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inStream = null;
            byte[] buf = new byte[1024];
            inStream = connection.getInputStream();
            for (int n; (n = inStream.read(buf)) != -1; ) {
                stringBuilder.append(new String(buf, 0, n, StandardCharsets.UTF_8));
            }
            inStream.close();
            connection.disconnect();
            return stringBuilder.toString();
        } else {
            return "fail";
        }
    }

    /**
     * 发送POST请求，获取服务器返回结果
     *
     * @param url POST请求
     * @return 服务器返回结果
     * @throws IOException IO
     */
    private String readContentFromPost(String url, String data) throws IOException {

        gtlog(data);
        URL postUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) postUrl
                .openConnection();

        // 设置连接主机超时（单位：毫秒）
        connection.setConnectTimeout(2000);
        // 设置从主机读取数据超时（单位：毫秒）
        connection.setReadTimeout(2000);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // 建立与服务器的连接，并未发送数据
        connection.connect();

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
        outputStreamWriter.write(data);
        outputStreamWriter.flush();
        outputStreamWriter.close();

        if (connection.getResponseCode() == 200) {
            // 发送数据到服务器并使用Reader读取返回的数据
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inStream = null;
            byte[] buf = new byte[1024];
            inStream = connection.getInputStream();
            for (int n; (n = inStream.read(buf)) != -1; ) {
                stringBuilder.append(new String(buf, 0, n, StandardCharsets.UTF_8));
            }
            inStream.close();
            connection.disconnect();// 断开连接
            return stringBuilder.toString();
        } else {
            return "fail";
        }
    }
}
