package kerp.sextou.websocketexample.vo;

import java.util.UUID;

public class PlayerPoker {

    private UUID uuid;
    private String username;
    private String state;
    private Integer estimation;
    private boolean isHost;

    public PlayerPoker() {}

    public PlayerPoker(String state, String username, Integer estimation) {
        this.uuid = UUID.randomUUID();
        this.state = state;
        this.username = username;
        this.estimation = estimation;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getEstimation() {
        return estimation;
    }

    public void setEstimation(Integer estimation) {
        this.estimation = estimation;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }
}
