package uoc.ds.pr.model;

public class Post {

    private String playerId;

    private String message;

    public Post(String playerId, String message) {
        this.setPlayerId(playerId);
        this.setMessage(message);
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String message() {
        return message;
    }

}
