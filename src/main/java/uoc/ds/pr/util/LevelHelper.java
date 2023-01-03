package uoc.ds.pr.util;

import uoc.ds.pr.SportEvents4Club;

public class LevelHelper {

    public static SportEvents4Club.Level getLevel(int numActivities) {
        if (numActivities >= 15) {
            return SportEvents4Club.Level.LEGEND;
        } else if (numActivities >= 10) {
            return SportEvents4Club.Level.MASTER;
        } else if (numActivities >= 5) {
            return SportEvents4Club.Level.EXPERT;
        } else if (numActivities >= 2) {
            return SportEvents4Club.Level.PRO;
        }
        return SportEvents4Club.Level.ROOKIE;
    }

}
