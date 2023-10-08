package kerp.sextou.websocketexample.controller;

import kerp.sextou.websocketexample.vo.KerpMessage;
import kerp.sextou.websocketexample.vo.PlayerPoker;
import kerp.sextou.websocketexample.vo.Room;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@RestController("/")

public class ChatController {

    private static final Room room = new Room();

    @GetMapping
    public String index() {
        return "index.html";
    }

    @GetMapping("/estimation-values")
    public Integer[] getEstimationValues() {
        return Room.estimationPoints;
    }

    @GetMapping("/estimate")
    public Room estimate() throws Exception {
        room.estimate();
        return room;
    }


    @MessageMapping("/planning-poker/vote")
    @SendTo("/topic/public")
    public PlayerPoker vote(@Payload PlayerPoker player) {
        for (PlayerPoker playerPoker : room.getPlayers()) {
            if (playerPoker.getUuid().equals(player.getUuid())) {
                playerPoker.setEstimation(player.getEstimation());
                player.setHost(playerPoker.isHost());
                break;
            }
        }
        return player;
    }


    @MessageMapping("/planning-poker/user")
    @SendTo("/topic/public")
    public PlayerPoker addPlayer(@Payload PlayerPoker player, SimpMessageHeaderAccessor headerAcessor) throws Exception {
        if (room.getPlayers().isEmpty()) {
            player.setUuid(UUID.randomUUID());
            player.setHost(true);
            room.addPlayer(player);
        } else if (player.getUuid() == null && room.getPlayers().stream().anyMatch(playerPoker -> playerPoker.getUuid() != player.getUuid())) {
            player.setUuid(UUID.randomUUID());
            room.addPlayer(player);
        }
        Objects.requireNonNull(headerAcessor.getSessionAttributes()).put("player", player);
        return player;
    }

}
