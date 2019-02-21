package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.utils.GeetestLib;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-12 14:33
 */
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class ValidateController {
    private String geetestId = "72c7a76c7189527de8f779eb4914de6e";
    private String geetestKey = "72c7a76c7189527de8f779eb4914de6e";
    private boolean newFailBack = true;

    @GetMapping("/code")
    public HttpEntity<?> code(){
        GeetestLib geetestLib = new GeetestLib(geetestId, geetestKey, newFailBack);
        String userid = "test";
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userid);
        param.put("client_type", "web");
        param.put("ip_address", "127.0.0.1");
        int gtServerStatus = geetestLib.preProcess(param);
        System.out.println(geetestLib.gtServerStatusSessionKey + "------" + gtServerStatus);
        System.out.println("userid" + "------" + userid);
        String resStr = geetestLib.getResponseStr();
        System.out.println("resStr" + "------" + resStr);
        return ResponseEntity.ok(resStr);
    }

}
