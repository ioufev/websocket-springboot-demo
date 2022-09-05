package com.ioufev.websocketspringbootdemo.controller;

import com.ioufev.websocketspringbootdemo.ws.WebSocketServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {
    //推送数据接口
    @PostMapping("/push/{cid}")
    public Map pushToWeb(@PathVariable String cid, @RequestBody String message) {
        Map<String,Object> result = new HashMap<>();
        WebSocketServer.sendInfo(message, cid);
        result.put("code", cid);
        result.put("msg", message);
        return result;
    }

    @PostMapping("/push")
    public Map pushToWeb2(@RequestBody String message) {
        Map<String,Object> result = new HashMap<>();
        WebSocketServer.sendInfo2(message);
        result.put("msg", message);
        return result;
    }
}
