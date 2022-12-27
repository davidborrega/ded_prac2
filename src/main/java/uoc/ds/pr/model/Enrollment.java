package uoc.ds.pr.model;

public class Enrollment {

    Player player;

    boolean isSubtitute;

    public Enrollment(Player player, boolean isSubstitute) {
        this.setPlayer(player);
        this.setSubtitute(isSubstitute);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setSubtitute(boolean subtitute) {
        isSubtitute = subtitute;
    }

    public Player getPlayer() {
        return player;
    }

}
