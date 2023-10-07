package kerp.sextou.websocketexample.vo;

public class PlayerPoker {

    private String username;
    private String state;
    private Integer estimation;

    public PlayerPoker(String state) {
        this.state = state;
    }

    public PlayerPoker(String state, String username, Integer estimation) {
        this.state = state;
        this.username = username;
        this.estimation = estimation;
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

}
