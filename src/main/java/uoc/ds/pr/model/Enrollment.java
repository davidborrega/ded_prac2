package uoc.ds.pr.model;

import java.util.Comparator;

public class Enrollment {

    private Player player;

    private boolean isSubtitute;

    public static Comparator<Enrollment> CMP_PLAYER = (Enrollment p1, Enrollment p2) -> {
        return p1.getPlayer().getLevel().compareTo(p2.getPlayer().getLevel());
    };

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
