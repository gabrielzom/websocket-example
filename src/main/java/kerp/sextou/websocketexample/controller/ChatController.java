package kerp.sextou.websocketexample.controller;

import kerp.sextou.websocketexample.vo.KerpMessage;
import kerp.sextou.websocketexample.vo.PlayerPoker;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Controller("/")

public class ChatController {

    private static final List<PlayerPoker> players = new ArrayList<>();

    @GetMapping
    public String index() {
        return "index.html";
    }

    @MessageMapping("/chat/send")
    @SendTo("/topic/public")
    public KerpMessage sendMessage(@Payload KerpMessage kerpMsg) {
        return kerpMsg;
    }


    @MessageMapping("/chat/user")
    @SendTo("/topic/public")
    public KerpMessage addUser(@Payload KerpMessage kerpMsg, SimpMessageHeaderAccessor headerAcessor) {
        headerAcessor.getSessionAttributes().put("user", kerpMsg);
        return kerpMsg;
    }

}
