package uoc.ds.pr.model;

import edu.uoc.ds.adt.nonlinear.graphs.DirectedGraphImpl;
import edu.uoc.ds.adt.nonlinear.graphs.Vertex;
import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.SportEvents4Club;
import uoc.ds.pr.util.LevelHelper;

import java.time.LocalDate;

public class Player {

    private String id;

    private String name;

    private String surname;

    private List<SportEvent> events;

    private LocalDate birthday;

    private SportEvents4Club.Level level;

    private int numRatings;

    private DirectedGraphImpl<Player, Player> graphFollowers;
    private Vertex<Player> followed;

    public Player(String idUser, String name, String surname, LocalDate birthday) {
        this.setId(idUser);
        this.setName(name);
        this.setSurname(surname);
        this.setBirthday(birthday);
        this.events = new LinkedList<>();
        this.setNumRatings(0);
        this.graphFollowers = new DirectedGraphImpl<Player, Player>();
        this.followed = this.graphFollowers.newVertex(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setLevel(SportEvents4Club.Level level) {
        this.level = level;
    }

    public SportEvents4Club.Level getLevel() {
        return LevelHelper.getLevel(this.getNumRatings());
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public boolean is(String playerID) {
        return id.equals(playerID);
    }

    public void addEvent(SportEvent sportEvent) {
        events.insertEnd(sportEvent);
    }

    public int numEvents() {
        return events.size();
    }

    public int numSportEvents() {
        return events.size();
    }

    public Iterator<SportEvent> getEvents() {
        return events.values();
    }

    // This method evaluate if player has participated in sport event or not.
    public boolean hasParticipatedInEvent(SportEvent sportEvent) {
        if (this.numEvents() == 0) {
            return false;
        }
        for (Iterator<SportEvent> it = this.getEvents(); it.hasNext();) {
            if (it.next().getEventId() == sportEvent.getEventId()) {
                return true;
            }
        }
        return false;
    }

    public int getNumRatings() {
        return this.numRatings;
    }

    public void increaseNumRatings() {
        this.numRatings++;
    }

    public Vertex<Player> getFollowed() {
        return this.followed;
    }

    public void addFollower(Player follower) {
        Vertex<Player> newFollower = this.graphFollowers.newVertex(follower);
        this.graphFollowers.newEdge(this.getFollowed(), newFollower);
    }

    public Iterator<Player> getFollowers() {
        return null;
    }

    public int numFollowers() {
        return this.graphFollowers.numVertexs() - 1;
    }
}
