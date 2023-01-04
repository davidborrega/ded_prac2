package uoc.ds.pr.model;

import uoc.ds.pr.SportEvents4Club;

public class Rating {

    private Player player;

    private String eventId;

    private SportEvents4Club.Rating rating;

    private String message;

    public Rating(Player player, String eventId, SportEvents4Club.Rating rating, String message) {
        this.player = player;
        this.eventId = eventId;
        this.rating = rating;
        this.message = message;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setRating(SportEvents4Club.Rating rating) {
        this.rating = rating;
    }

    public SportEvents4Club.Rating rating() {
        return rating;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}