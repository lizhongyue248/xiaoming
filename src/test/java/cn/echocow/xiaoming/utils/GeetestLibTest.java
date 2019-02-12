package cn.echocow.xiaoming.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-12 13:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GeetestLibTest {
    private String geetestId = "72c7a76c7189527de8f779eb4914de6e";
    private String geetestKey = "72c7a76c7189527de8f779eb4914de6e";
    private boolean newFailBack = true;
    @Test
    public void api1Test(){
        GeetestLib geetestLib = new GeetestLib(geetestId, geetestKey, newFailBack);
        String userid = "test";
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
        //进行验证预处理
        int gtServerStatus = geetestLib.preProcess(param);
        System.out.println(geetestLib.gtServerStatusSessionKey + "------" + gtServerStatus);
        System.out.println("userid" + "------" + userid);
        String resStr = geetestLib.getResponseStr();
        System.out.println("resStr" + "------" + resStr);
    }
    @Test
    public void api2Test(){
        GeetestLib geetestLib = new GeetestLib(geetestId, geetestKey, newFailBack);
        String challenge = "baa8f420ad52cfea95e661b94a5987db";
        String validate = "72c7a76c7189527de8f779eb4914de6e";
        String seccode = "123";

        //从session中获取gt-server状态
        int gt_server_status_code = 1;
        //从session中获取userid
        String userid = "test";
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        int gtResult = 0;
        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = geetestLib.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            System.out.println("failback:use your own server captcha validate");
            gtResult = geetestLib.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }

        if (gtResult == 1) {
            // 验证成功
            JSONObject data = new JSONObject();
            try {
                data.put("status", "success");
                data.put("version", geetestLib.getVersionInfo());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(data.toString());
        }
        else {
            // 验证失败
            JSONObject data = new JSONObject();
            try {
                data.put("status", "fail");
                data.put("version", geetestLib.getVersionInfo());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(data.toString());
        }
    }
}
