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

    public Player getPlayer() {
        return player;
    }

    public String getEventId() {
        return eventId;
    }

    public SportEvents4Club.Rating rating() {
        return rating;
    }

    public SportEvents4Club.Rating getRating() {
        return rating;
    }

}