package kerp.sextou.websocketexample.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Room {

    public static final int maxPlayers = 10;
    public static final Integer[] estimationPoints = { 0, 2, 4, 6, 8, 10, 12, 14, 16 };
    private UUID uuid;
    private float currentAverage;
    private List<PlayerPoker> players;
    private List<Vote> votes;

    public Room() {
        this.uuid = UUID.randomUUID();
        this.players = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    public Room(List<PlayerPoker> players) {
        this.uuid = UUID.randomUUID();
        this.players = players;
        this.votes = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public float getCurrentAverage() {
        return currentAverage;
    }

    public void setCurrentAverage(float currentAverage) {
        this.currentAverage = currentAverage;
    }

    public List<PlayerPoker> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerPoker> players) {
        this.players = players;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    private boolean playerIsValidForEstimate(PlayerPoker player) {
        return  player.getState() != null &&
                !player.getState().equals("VIEWER") &&
                player.getUsername() != null &&
                !player.getState().trim().equals("") &&
                player.getEstimation() != null;
    }

    public void addPlayer(PlayerPoker player) throws Exception {
        if (this.players.size() == maxPlayers) {
            throw new Exception("Room is full. ");
        }
        this.players.add(player);
    }

    public void estimate() throws Exception {
        int sum = 0;
        if (players.isEmpty()) {
            throw new Exception("Don't have players. ");
        }
        for (PlayerPoker player : this.players) {
            if (playerIsValidForEstimate(player)) {
                if (player.getEstimation() == 0) {
                    continue;
                }
                if (this.votes.isEmpty()) {
                    this.votes.add(new Vote(player.getEstimation(), 1));
                } else {
                    boolean hasVoteValue = false;
                    for (Vote vote : this.votes) {
                        if (player.getEstimation() == vote.getNumber()) {
                            vote.setQuantity(vote.getQuantity() + 1);
                            hasVoteValue = true;
                            break;
                        }
                    }
                    if (!hasVoteValue) {
                        this.votes.add(new Vote(player.getEstimation(), 1));
                    }
                }
                sum += player.getEstimation();
            }
            this.setCurrentAverage(((float) sum / this.players.size()));
        }
    }
}
