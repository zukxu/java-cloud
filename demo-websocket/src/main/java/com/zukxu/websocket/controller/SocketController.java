package com.zukxu.websocket.controller;

import com.zukxu.websocket.server.SocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zukxu
 * CreateTime: 2021/7/11 0011 18:07
 */
@Controller
@RequestMapping("/api/socket")
public class SocketController {
    @GetMapping("/index/{userId}")
    public String socket(@PathVariable String userId) {
        ModelAndView view = new ModelAndView("/socket");
        view.addObject("userId", userId);
        return "socket";
    }

    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public Map pushToWeb(@PathVariable String cid, String message) {
        Map<String, Object> map = new HashMap<>();
        SocketServer.sendInfo(message, cid);
        map.put("code", cid);
        map.put("msg", message);
        return map;
    }
}
