package uoc.ds.pr.model;

import edu.uoc.ds.adt.nonlinear.HashTable;
import edu.uoc.ds.adt.nonlinear.PriorityQueue;
import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.SportEvents4Club;

import java.time.LocalDate;
import java.util.Comparator;

import static uoc.ds.pr.SportEvents4Club.MAX_NUM_ENROLLMENT;

public class SportEvent {

    public static final Comparator<SportEvent> CMP_V = (se1, se2)-> Double.compare(se1.rating(), se2.rating());

    public static final Comparator<String> CMP_K = (k1, k2)-> k1.compareTo(k2);

    private String eventId;
    private String description;
    private SportEvents4Club.Type type;
    private LocalDate startDate;
    private LocalDate endDate;
    private int max;

    private File file;

    private List<Rating> ratings;

    private Queue<Enrollment> players;

    private PriorityQueue<Enrollment> substitutes;
    private int numSubstitutes;

    private List<Worker> workers;

    private HashTable<String, Attender> attenders;

    private OrganizingEntity organizingEntity;

    public SportEvent(String eventId, String description, SportEvents4Club.Type type,
                      LocalDate startDate, LocalDate endDate, int max, File file) {
        this.setEventId(eventId);
        this.setDescription(description);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setType(type);
        this.setMax(max);
        this.setFile(file);
        this.players = new QueueArrayImpl<>(MAX_NUM_ENROLLMENT);
        this.substitutes = new PriorityQueue<Enrollment>(Enrollment.CMP_PLAYER);
        this.ratings = new LinkedList<Rating>();
        this.numSubstitutes = 0;
        this.workers = new LinkedList<Worker>();
        this.attenders = new HashTable<String, Attender>();
        this.organizingEntity = null;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SportEvents4Club.Type getType() {
        return type;
    }

    public void setType(SportEvents4Club.Type type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Iterator<uoc.ds.pr.model.Rating> getRatings() {
        return this.ratings.values();
    }

    public int getTotalRatings() {
        return this.ratings.size();
    }

    public void addRating(Rating rating) {
        this.ratings.insertEnd(rating);
    }

    public Double rating() {
        int numberOfRatings = this.getTotalRatings();
        if (numberOfRatings == 0) {
            return Double.valueOf(0);
        }
        int rating = 0;
        for (Iterator<Rating> it = this.getRatings(); it.hasNext();) {
            rating += it.next().rating().getValue();
        }
        return (double) rating / (double) numberOfRatings;
    }

    public void addPlayer(Player player) {
        this.addEnrollment(player, false);
    }

    public void addSubstitute(Player player) {
        this.addEnrollment(player, true);
        this.numSubstitutes++;
    }

    public void addEnrollment(Player player, boolean isSubstitute) {
        this.players.add(new Enrollment(player, isSubstitute));
    }

    public boolean isFull() {
        return (players.size() >= max);
    }

    public int numPlayers() {
        return players.size();
    }

    public int numSubstitutes() {
        return this.numSubstitutes;
    }

    public Iterator<Enrollment> getSubstitutes() {
        return this.substitutes.values();
    }

    public void setOrganizingEntity(OrganizingEntity organizingEntity) {
        this.organizingEntity = organizingEntity;
    }

    public OrganizingEntity getOrganizingEntity() {
        return this.organizingEntity;
    }

    public void addAttender(Attender attender) {
        this.attenders.put(attender.getPhone(), attender);
    }

    public Attender getAttender(String phone) {
        return this.attenders.get(phone);
    }

    public Iterator<Attender> getAttenders() {
        return this.attenders.values();
    }

    public int numAttenders() {
        return this.attenders.size();
    }

    public boolean isLimitOfAttenders() {
        return ((this.numAttenders() + this.numPlayers()) >= this.getMax());
    }

    public Iterator<Worker> getWorkers() {
        return this.workers.values();
    }

    public Worker getWorker(String dni) {
        if (this.numWorkers() == 0) {
            return null;
        }
        for (Iterator<Worker> it = this.getWorkers(); it.hasNext();) {
            Worker worker = it.next();
            if (worker.getDni().equals(dni)) {
                return worker;
            }
        }
        return null;
    }

    public void addWorker(Worker worker) {
        this.workers.insertEnd(worker);
    }

    public int numWorkers() {
        return this.workers.size();
    }

}
