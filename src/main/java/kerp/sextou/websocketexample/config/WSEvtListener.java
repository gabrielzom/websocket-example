package kerp.sextou.websocketexample.config;

import kerp.sextou.websocketexample.vo.KerpMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WSEvtListener {
    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWSDisconnectListener(SessionDisconnectEvent evt) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(evt.getMessage());
        KerpMessage user = (KerpMessage) headerAccessor.getSessionAttributes().get("user");
        if (user != null) {
            System.out.printf("User disconnected: %s", user.getSender());
            System.out.println();
            KerpMessage kerpMsg = new KerpMessage(null, user.getSender(), "LEAVE");
            messageTemplate.convertAndSend("/topic/public", kerpMsg);
        }
    }
}
