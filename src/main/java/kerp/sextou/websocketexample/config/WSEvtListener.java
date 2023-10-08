package kerp.sextou.websocketexample.config;

import kerp.sextou.websocketexample.vo.KerpMessage;
import kerp.sextou.websocketexample.vo.PlayerPoker;
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
        PlayerPoker player = (PlayerPoker) headerAccessor.getSessionAttributes().get("player");
        if (player != null) {
            System.out.printf("Player disconnected: %s", player.getUsername());
            System.out.println();
            KerpMessage kerpMsg = new KerpMessage("LEAVE", player.getUsername(), "LEAVE");
            messageTemplate.convertAndSend("/topic/public", kerpMsg);
        }
    }
}
