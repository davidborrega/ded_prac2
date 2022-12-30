package uoc.ds.pr;

import edu.uoc.ds.adt.nonlinear.DictionaryAVLImpl;
import edu.uoc.ds.adt.nonlinear.HashTable;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;

import java.time.LocalDate;

public class SportEvents4ClubImpl implements SportEvents4Club {

    private DictionaryAVLImpl<String, Player> players;
    private int numPlayers;

    private HashTable<String, OrganizingEntity> organizingEntities;
    private int numOrganizingEntities;

    public SportEvents4ClubImpl() {
        // Players
        this.players = new DictionaryAVLImpl<String, Player>();
        this.numPlayers = 0;

        // Organizing Entities
        this.organizingEntities = new HashTable<String, OrganizingEntity>(SportEvents4Club.MAX_NUM_ORGANIZING_ENTITIES);
        this.numOrganizingEntities = 0;


    }

    @Override
    public void addPlayer(String id, String name, String surname, LocalDate dateOfBirth) {
        Player player = this.getPlayer(id);
        if (player != null) {
            player.setName(name);
            player.setSurname(surname);
            player.setBirthday(dateOfBirth);
        } else {
            player = new Player(id, name, surname, dateOfBirth);
            players.put(id, player);
            numPlayers++;
        }
    }

    @Override
    public void addOrganizingEntity(String id, String name, String description) {
        OrganizingEntity organizingEntity = this.getOrganizingEntity(id);
        if (organizingEntity != null) {
            organizingEntity.setName(name);
            organizingEntity.setDescription(description);
        } else {
            organizingEntity = new OrganizingEntity(id, name, description);
            organizingEntities.put(id, organizingEntity);
            numOrganizingEntities++;
        }
    }

    @Override
    public void addFile(String id, String eventId, String orgId, String description, Type type, byte resources, int max, LocalDate startDate, LocalDate endDate) throws OrganizingEntityNotFoundException {

    }

    @Override
    public File updateFile(Status status, LocalDate date, String description) throws NoFilesException {
        return null;
    }

    @Override
    public void signUpEvent(String playerId, String eventId) throws PlayerNotFoundException, SportEventNotFoundException, LimitExceededException {

    }

    @Override
    public double getRejectedFiles() {
        return 0;
    }

    @Override
    public Iterator<SportEvent> getSportEventsByOrganizingEntity(String organizationId) throws NoSportEventsException {
        return null;
    }

    @Override
    public Iterator<SportEvent> getAllEvents() throws NoSportEventsException {
        return null;
    }

    @Override
    public Iterator<SportEvent> getEventsByPlayer(String playerId) throws NoSportEventsException {
        return null;
    }

    @Override
    public void addRating(String playerId, String eventId, Rating rating, String message) throws SportEventNotFoundException, PlayerNotFoundException, PlayerNotInSportEventException {

    }

    @Override
    public Iterator<uoc.ds.pr.model.Rating> getRatingsByEvent(String eventId) throws SportEventNotFoundException, NoRatingsException {
        return null;
    }

    @Override
    public Player mostActivePlayer() throws PlayerNotFoundException {
        return null;
    }

    @Override
    public SportEvent bestSportEvent() throws SportEventNotFoundException {
        return null;
    }

    @Override
    public void addRole(String roleId, String description) {

    }

    @Override
    public void addWorker(String dni, String name, String surname, LocalDate birthDay, String roleId) {

    }

    @Override
    public void assignWorker(String dni, String eventId) throws WorkerNotFoundException, WorkerAlreadyAssignedException, SportEventNotFoundException {

    }

    @Override
    public Iterator<Worker> getWorkersBySportEvent(String eventId) throws SportEventNotFoundException, NoWorkersException {
        return null;
    }

    @Override
    public Iterator<Worker> getWorkersByRole(String roleId) throws NoWorkersException {
        return null;
    }

    @Override
    public Level getLevel(String playerId) throws PlayerNotFoundException {
        return null;
    }

    @Override
    public Iterator<Enrollment> getSubstitutes(String eventId) throws SportEventNotFoundException, NoSubstitutesException {
        return null;
    }

    @Override
    public void addAttender(String phone, String name, String eventId) throws AttenderAlreadyExistsException, SportEventNotFoundException, LimitExceededException {

    }

    @Override
    public Attender getAttender(String phone, String sportEventId) throws SportEventNotFoundException, AttenderNotFoundException {
        return null;
    }

    @Override
    public Iterator<Attender> getAttenders(String eventId) throws SportEventNotFoundException, NoAttendersException {
        return null;
    }

    @Override
    public Iterator<OrganizingEntity> best5OrganizingEntities() throws NoAttendersException {
        return null;
    }

    @Override
    public SportEvent bestSportEventByAttenders() throws NoSportEventsException {
        return null;
    }

    @Override
    public void addFollower(String playerId, String playerFollowerId) throws PlayerNotFoundException {

    }

    @Override
    public Iterator<Player> getFollowers(String playerId) throws PlayerNotFoundException, NoFollowersException {
        return null;
    }

    @Override
    public Iterator<Player> getFollowings(String playerId) throws PlayerNotFoundException, NoFollowingException {
        return null;
    }

    @Override
    public Iterator<Player> recommendations(String playerId) throws PlayerNotFoundException, NoFollowersException {
        return null;
    }

    @Override
    public Iterator<Post> getPosts(String playerId) throws PlayerNotFoundException, NoPostsException {
        return null;
    }

    @Override
    public int numPlayers() {
        return 0;
    }

    @Override
    public int numOrganizingEntities() {
        return 0;
    }

    @Override
    public int numFiles() {
        return 0;
    }

    @Override
    public int numRejectedFiles() {
        return 0;
    }

    @Override
    public int numPendingFiles() {
        return 0;
    }

    @Override
    public int numSportEvents() {
        return 0;
    }

    @Override
    public int numSportEventsByPlayer(String playerId) {
        return 0;
    }

    @Override
    public int numPlayersBySportEvent(String sportEventId) {
        return 0;
    }

    @Override
    public int numSportEventsByOrganizingEntity(String orgId) {
        return 0;
    }

    @Override
    public int numSubstitutesBySportEvent(String sportEventId) {
        return 0;
    }

    @Override
    public Player getPlayer(String playerId) {
        if (this.players.isEmpty()) {
            return null;
        }
        return this.players.get(playerId);
    }

    @Override
    public SportEvent getSportEvent(String eventId) {
        return null;
    }

    @Override
    public OrganizingEntity getOrganizingEntity(String id) {
        if (this.organizingEntities.isEmpty()) {
            return  null;
        }
        return this.organizingEntities.get(id);
    }

    @Override
    public File currentFile() {
        return null;
    }

    @Override
    public int numRoles() {
        return 0;
    }

    @Override
    public Role getRole(String roleId) {
        return null;
    }

    @Override
    public int numWorkers() {
        return 0;
    }

    @Override
    public Worker getWorker(String dni) {
        return null;
    }

    @Override
    public int numWorkersByRole(String roleId) {
        return 0;
    }

    @Override
    public int numWorkersBySportEvent(String sportEventId) {
        return 0;
    }

    @Override
    public int numRatings(String playerId) {
        return 0;
    }

    @Override
    public int numAttenders(String sportEventId) {
        return 0;
    }

    @Override
    public int numFollowers(String playerId) {
        return 0;
    }

    @Override
    public int numFollowings(String playerId) {
        return 0;
    }
}