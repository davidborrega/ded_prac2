package uoc.ds.pr;

import edu.uoc.ds.adt.nonlinear.DictionaryAVLImpl;
import edu.uoc.ds.adt.nonlinear.HashTable;
import edu.uoc.ds.adt.nonlinear.PriorityQueue;
import edu.uoc.ds.adt.nonlinear.graphs.*;
import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.util.OrderedVector;

import java.time.LocalDate;
import java.util.ArrayList;

public class SportEvents4ClubImpl implements SportEvents4Club {

    private DictionaryAVLImpl<String, Player> players;
    private int numPlayers;
    private Player mostActivePlayer;

    private HashTable<String, OrganizingEntity> organizingEntities;
    private int numOrganizingEntities;
    private OrderedVector<OrganizingEntity> bestOrganizationEntities;

    private PriorityQueue<File> files;
    private int totalFiles;
    private int rejectedFiles;

    private DictionaryAVLImpl<String, SportEvent> sportEvents;
    private OrderedVector<SportEvent> bestSportEvents;

    private HashTable<String, Worker> workers;
    private int numWorkers;
    private Role[] roles;
    private int numRoles;

    // Social network
    private DirectedGraphImpl<Player, String> socialNetwork;

    public SportEvents4ClubImpl() {
        // Players
        this.players = new DictionaryAVLImpl<String, Player>();
        this.numPlayers = 0;
        this.mostActivePlayer = null;

        // Organizing Entities
        this.organizingEntities = new HashTable<String, OrganizingEntity>(SportEvents4Club.MAX_NUM_ORGANIZING_ENTITIES);
        this.numOrganizingEntities = 0;
        this.bestOrganizationEntities = new OrderedVector<OrganizingEntity>(SportEvents4Club.MAX_NUM_ORGANIZING_ENTITIES, OrganizingEntity.CMP_MOST_ATTENDERS);

        // Files
        this.files = new PriorityQueue<File>(File.CMP_Q);
        this.totalFiles = 0;
        this.rejectedFiles = 0;

        // Sport Events
        this.sportEvents = new DictionaryAVLImpl<String, SportEvent>();
        this.bestSportEvents = new OrderedVector<SportEvent>(SportEvents4Club.MAX_NUM_SPORT_EVENTS, SportEvent.CMP_V);

        // Roles
        this.roles = new Role[250]; // TODO: define limit of roles
        this.numRoles = 0;

        // Workers
        this.workers = new HashTable<String, Worker>();
        this.numWorkers = 0;

        // Social network
        this.socialNetwork = new DirectedGraphImpl<>();
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
            // Social network, create new vertex
            socialNetwork.newVertex(player);
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
            this.organizingEntities.put(id, organizingEntity);
            this.numOrganizingEntities++;
        }
    }

    @Override
    public void addFile(String id, String eventId, String orgId, String description, Type type, byte resources, int max, LocalDate startDate, LocalDate endDate) throws OrganizingEntityNotFoundException {
        OrganizingEntity organization = this.organizingEntities.get(orgId);
        if (organization == null) {
            throw new OrganizingEntityNotFoundException();
        }
        this.files.add(new File(id, eventId, description, type, startDate, endDate, resources, max, organization));
        this.totalFiles++;
    }

    @Override
    public File updateFile(Status status, LocalDate date, String description) throws NoFilesException {
        if (this.files.isEmpty()) {
            throw new NoFilesException();
        }
        // Get the file of priority queue of files and remove it.
        File file = this.files.poll();
        if (status == Status.ENABLED) {
            // Update file
            file.setDescription(description);
            file.setStatus(status);
            file.setEndDate(date);
            // Add new event into sport events.
            SportEvent sportEvent = file.newSportEvent();
            // Add new event into linked list of organizing entity.
            OrganizingEntity organizingEntity = file.getOrganization();
            if (organizingEntity != null) {
                sportEvent.setOrganizingEntity(organizingEntity);
            }
            this.sportEvents.put(file.getEventId(), sportEvent);
            // Add new event into best sport events vector.
            this.bestSportEvents.update(sportEvent);
        } else if (status == Status.DISABLED) {
            // Increase the number of rejected files and no save data.
            this.rejectedFiles++;
        }
        return file;
    }

    @Override
    public void signUpEvent(String playerId, String eventId) throws PlayerNotFoundException, SportEventNotFoundException, LimitExceededException {
        Player player = getPlayer(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        SportEvent sportEvent = getSportEvent(eventId);
        if (sportEvent == null) {
            throw new SportEventNotFoundException();
        }
        player.addEvent(sportEvent);
        if (!sportEvent.isLimitOfAttenders()) {
            sportEvent.addPlayer(player);
        } else {
            sportEvent.addSubstitute(player);
            throw new LimitExceededException();
        }
        updateMostActivePlayer(player);
    }

    private void updateMostActivePlayer(Player player) {
        if (mostActivePlayer == null) {
            mostActivePlayer = player;
        } else if (player.numSportEvents() > mostActivePlayer.numSportEvents()) {
            mostActivePlayer = player;
        }
    }

    @Override
    public double getRejectedFiles() {
        return (double) this.numRejectedFiles() / (double) this.numFiles();
    }

    @Override
    public Iterator<SportEvent> getSportEventsByOrganizingEntity(String organizationId) throws NoSportEventsException {
        OrganizingEntity organizingEntity = this.getOrganizingEntity(organizationId);
        if (organizingEntity == null) {
            throw new NoSportEventsException();
        }
        if (organizingEntity.numEvents() == 0) {
            throw new NoSportEventsException();
        }
        return organizingEntity.sportEvents();
    }

    @Override
    public Iterator<SportEvent> getAllEvents() throws NoSportEventsException {
        if (this.sportEvents.size() == 0) {
            // If sport event not found, throw custom exception.
            throw new NoSportEventsException();
        }
        return this.sportEvents.values();
    }

    @Override
    public Iterator<SportEvent> getEventsByPlayer(String playerId) throws NoSportEventsException {
        Player player = this.getPlayer(playerId);
        if ((player == null) || (player.numEvents() == 0)) {
            // If player not found, throw custom exception.
            throw new NoSportEventsException();
        }
        return player.getEvents();
    }

    @Override
    public void addRating(String playerId, String eventId, Rating rating, String message) throws SportEventNotFoundException, PlayerNotFoundException, PlayerNotInSportEventException {
        // Get sport event from the ordered vector
        SportEvent sportEvent = this.getSportEvent(eventId);
        if (sportEvent == null) {
            // If sport event not found, throw custom exception.
            throw new SportEventNotFoundException();
        }
        // Get player by id
        Player player = this.getPlayer(playerId);
        if (player == null) {
            // If player not found, throw custom exception.
            throw new PlayerNotFoundException();
        }
        // Check if player has participated in sport event or not
        if (!player.hasParticipatedInEvent(sportEvent)) {
            throw new PlayerNotInSportEventException();
        }
        // Create new rating and add into sport event.
        uoc.ds.pr.model.Rating newRating = new uoc.ds.pr.model.Rating(player, eventId, rating, message);
        sportEvent.addRating(newRating);
        // Reorder best sport events vector.
        this.bestSportEvents.update(sportEvent);
        player.increaseNumRatings();
    }

    @Override
    public Iterator<uoc.ds.pr.model.Rating> getRatingsByEvent(String eventId) throws SportEventNotFoundException, NoRatingsException {
        SportEvent sportEvent = this.getSportEvent(eventId);
        if (sportEvent == null) {
            // If sport event not found, throw custom exception.
            throw new SportEventNotFoundException();
        }
        if (sportEvent.getTotalRatings() == 0) {
            // If not ratings found, throw custom exception.
            throw  new NoRatingsException();
        }
        return sportEvent.getRatings();
    }

    @Override
    public Player mostActivePlayer() throws PlayerNotFoundException {
        if (this.mostActivePlayer == null) {
            throw new PlayerNotFoundException();
        }
        return this.mostActivePlayer;
    }

    @Override
    public SportEvent bestSportEvent() throws SportEventNotFoundException {
        if (this.bestSportEvents.size() == 0) {
            throw new SportEventNotFoundException();
        }
        return this.bestSportEvents.elementAt(0);
    }

    @Override
    public void addRole(String roleId, String description) {
        Role role = getRole(roleId);
        if (role != null) {
            role.setDescription(description);
        } else {
            this.roles[this.numRoles()] = new Role(roleId, description);
            this.numRoles++;
        }
    }

    @Override
    public void addWorker(String dni, String name, String surname, LocalDate birthDay, String roleId) {
        Worker worker = this.getWorker(dni);
        if (worker == null) {
            Worker newWorker = new Worker(dni, name, surname, birthDay, roleId);
            this.workers.put(dni, newWorker);
            this.addWorkerIntoRole(roleId, newWorker);
            this.numWorkers++;
        } else {
            // Set worker variables.
            worker.setName(name);
            worker.setSurname(surname);
            worker.setBirthDay(birthDay);
            // Replace role if it's not the same
            if (!worker.getRoleId().equals(roleId)) {
                // Remove the worker from the old role
                Role oldRole = this.getRole(worker.getRoleId());
                oldRole.removeWorker(worker);
                // Add the worker to the new role
                this.addWorkerIntoRole(roleId, worker);
                // Update the role ID
                worker.setRoleId(roleId);
            }
        }
    }

    private void addWorkerIntoRole(String roleId, Worker worker) {
        Role role = getRole(roleId);
        if (role != null) {
            role.addWorker(worker);
        }
    }

    @Override
    public void assignWorker(String dni, String eventId) throws WorkerNotFoundException, WorkerAlreadyAssignedException, SportEventNotFoundException {
        SportEvent sportEvent = getSportEvent(eventId);
        if (sportEvent == null) {
            throw new SportEventNotFoundException();
        }
        Worker worker = getWorker(dni);
        if (worker == null) {
            throw new WorkerNotFoundException();
        }
        if (sportEvent.getWorker(dni) != null) {
            throw new WorkerAlreadyAssignedException();
        }
        sportEvent.addWorker(worker);
    }

    @Override
    public Iterator<Worker> getWorkersBySportEvent(String eventId) throws SportEventNotFoundException, NoWorkersException {
        SportEvent sportEvent = getSportEvent(eventId);
        if (sportEvent == null) {
            throw new SportEventNotFoundException();
        }
        if (sportEvent.numWorkers() == 0) {
            throw new NoWorkersException();
        }
        return sportEvent.getWorkers();
    }

    @Override
    public Iterator<Worker> getWorkersByRole(String roleId) throws NoWorkersException {
        Role role = getRole(roleId);
        if (role == null) {
            return null;
        }
        if (role.numWorkers() == 0) {
            throw new NoWorkersException();
        }
        return role.getWorkers();
    }

    @Override
    public Level getLevel(String playerId) throws PlayerNotFoundException {
        Player player = getPlayer(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        return player.getLevel();
    }

    @Override
    public Iterator<Enrollment> getSubstitutes(String eventId) throws SportEventNotFoundException, NoSubstitutesException {
        SportEvent sportEvent = getSportEvent(eventId);
        if (sportEvent == null) {
            throw new SportEventNotFoundException();
        }
        if (sportEvent.numSubstitutes() == 0) {
            throw new NoSubstitutesException();
        }
        return sportEvent.getSubstitutes();
    }

    @Override
    public void addAttender(String phone, String name, String eventId) throws AttenderAlreadyExistsException, SportEventNotFoundException, LimitExceededException {
        SportEvent sportEvent = getSportEvent(eventId);
        if (sportEvent == null) {
            throw new SportEventNotFoundException();
        }
        if (sportEvent.getAttender(phone) != null) {
            throw new AttenderAlreadyExistsException();
        }
        if (sportEvent.isLimitOfAttenders()) {
            throw new LimitExceededException();
        }
        sportEvent.addAttender(new Attender(phone, name, eventId));
        // Update ordered vectors
        this.bestOrganizationEntities.delete(sportEvent.getOrganizingEntity());
        this.bestOrganizationEntities.update(sportEvent.getOrganizingEntity());
        this.bestSportEvents.update(sportEvent);
    }

    @Override
    public Attender getAttender(String phone, String sportEventId) throws SportEventNotFoundException, AttenderNotFoundException {
        SportEvent sportEvent = getSportEvent(sportEventId);
        if (sportEvent == null) {
            throw new SportEventNotFoundException();
        }
        Attender attender = sportEvent.getAttender(phone);
        if (attender == null) {
            throw new AttenderNotFoundException();
        }
        return attender;
    }

    @Override
    public Iterator<Attender> getAttenders(String eventId) throws SportEventNotFoundException, NoAttendersException {
        SportEvent sportEvent = getSportEvent(eventId);
        if (sportEvent == null) {
            throw new SportEventNotFoundException();
        }
        if (sportEvent.numAttenders() == 0) {
            throw new NoAttendersException();
        }
        return sportEvent.getAttenders();
    }

    @Override
    public Iterator<OrganizingEntity> best5OrganizingEntities() throws NoAttendersException {
        if (this.bestOrganizationEntities.isEmpty()) {
            throw new NoAttendersException();
        }
        return this.bestOrganizationEntities.values();
    }

    @Override
    public SportEvent bestSportEventByAttenders() throws NoSportEventsException {
        if (this.bestSportEvents.isEmpty()) {
            throw new NoSportEventsException();
        }
        return this.bestSportEvents.last();
    }

    @Override
    public void addFollower(String playerId, String playerFollowerId) throws PlayerNotFoundException {
        Player playerToFollow = getPlayer(playerId);
        if (playerToFollow == null) {
            throw new PlayerNotFoundException();
        }
        Player follower = getPlayer(playerFollowerId);
        if (follower == null) {
            throw new PlayerNotFoundException();
        }
        if (playerId != playerFollowerId) {
            //System.out.println("Followed: " + playerToFollow.getId());
            //System.out.println("Follower: " + follower.getId());
            // Add Follower
            Edge<String, Player> edge1 = socialNetwork.newEdge(socialNetwork.getVertex(playerToFollow), socialNetwork.getVertex(follower));
            edge1.setLabel("Follower");
            // Add Following
            Edge<String, Player> edge2 = socialNetwork.newEdge(socialNetwork.getVertex(follower), socialNetwork.getVertex(playerToFollow));
            edge2.setLabel("Followed");
        }
    }

    @Override
    public Iterator<Player> getFollowers(String playerId) throws PlayerNotFoundException, NoFollowersException {
        List<Player> followers = this.getFollowByType(playerId, "Follower");
        if (followers.isEmpty()) {
            throw new NoFollowersException();
        }
        return followers.values();

    }

    @Override
    public Iterator<Player> getFollowings(String playerId) throws PlayerNotFoundException, NoFollowingException {
        List<Player> following = this.getFollowByType(playerId, "Followed");
        if (following.isEmpty()) {
            throw new NoFollowingException();
        }
        return following.values();
    }

    private List<Player> getFollowByType(String playerId, String type) throws PlayerNotFoundException {
        Player player = getPlayer(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        DirectedVertexImpl<Player, String> mainPlayer = (DirectedVertexImpl<Player, String>) socialNetwork.getVertex(player);
        List<Player> followers = new LinkedList<>();
        for (Iterator<Edge<String, Player>> edges = mainPlayer.edges(); edges.hasNext();) {
            DirectedEdge<String, Player> currentEdge = (DirectedEdge<String, Player>) edges.next();
            if (currentEdge.getLabel() == type && currentEdge.getVertexSrc().getValue().getId() == playerId) {
                followers.insertEnd(currentEdge.getVertexDst().getValue());
            }
        }
        return followers;
    }

    @Override
    public Iterator<Player> recommendations(String playerId) throws PlayerNotFoundException, NoFollowersException {
        Player player = getPlayer(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        if (this.numFollowers(playerId) == 0) {
            throw new NoFollowersException();
        }
        List<Player> recommendations = new LinkedList<>();
        ArrayList<String> newRecommendations = new ArrayList<>();

        // Get followers by player
        Iterator<Player> followers = this.getFollowers(playerId);
        ArrayList<String> playerIds = new ArrayList<>();

        // First loop to make a new array list to will compare.
        for (Iterator<Player> it = followers; it.hasNext();) {
            Player currentPlayer = it.next();
            playerIds.add(currentPlayer.getId());
        }

        // Iterate each follower to find recommendations never used previously
        for (Iterator<Player> it = this.getFollowers(playerId); it.hasNext();) {
            Player currentPlayer = it.next();
            if (this.numFollowers(currentPlayer.getId()) > 0) {
                for (Iterator<Player> followersByCurrentFollower = this.getFollowers(currentPlayer.getId()); followersByCurrentFollower.hasNext();) {
                    // Check follower is inside of list
                    Player currentFollowerByFollower = followersByCurrentFollower.next();
                    if (!playerIds.contains(currentFollowerByFollower.getId())
                            && currentFollowerByFollower.getId() != playerId
                            && !newRecommendations.contains(currentFollowerByFollower.getId())) {
                        // Add recomendation into linked list
                        recommendations.insertEnd(currentFollowerByFollower);
                        newRecommendations.add(currentFollowerByFollower.getId());
                    }
                }
            }
        }
        return recommendations.values();
    }

    @Override
    public Iterator<Post> getPosts(String playerId) throws PlayerNotFoundException, NoPostsException {
        Player player = getPlayer(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }

        //Iterator<Player> followings = this.getFollowings(playerId);

        // Recorrer followings


        return null;
    }

    @Override
    public int numPlayers() {
        return this.numPlayers;
    }

    @Override
    public int numOrganizingEntities() {
        return this.numOrganizingEntities;
    }

    @Override
    public int numFiles() {
        return this.totalFiles;
    }

    @Override
    public int numRejectedFiles() {
        return this.rejectedFiles;
    }

    @Override
    public int numPendingFiles() {
        return this.files.size();
    }

    @Override
    public int numSportEvents() {
        return this.sportEvents.size();
    }

    @Override
    public int numSportEventsByPlayer(String playerId) {
        Player player = getPlayer(playerId);
        if (player != null) {
            return player.numSportEvents();
        }
        return 0;
    }

    @Override
    public int numPlayersBySportEvent(String sportEventId) {
        SportEvent sportEvent = getSportEvent(sportEventId);
        if (sportEvent != null) {
            return sportEvent.numPlayers() + sportEvent.numSubstitutes();
        }
        return 0;
    }

    @Override
    public int numSportEventsByOrganizingEntity(String orgId) {
        OrganizingEntity organizingEntity = getOrganizingEntity(orgId);
        if (organizingEntity != null) {
            return organizingEntity.numEvents();
        }
        return 0;
    }

    @Override
    public int numSubstitutesBySportEvent(String sportEventId) {
        SportEvent sportEvent = getSportEvent(sportEventId);
        if (sportEvent != null) {
            return sportEvent.numSubstitutes();
        }
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
        return sportEvents.get(eventId);
    }

    @Override
    public OrganizingEntity getOrganizingEntity(String id) {
        if (this.organizingEntities.isEmpty()) {
            return null;
        }
        return this.organizingEntities.get(id);
    }

    @Override
    public File currentFile() {
        if (this.files.size() == 0) {
            return null;
        }
        return this.files.peek();
    }

    @Override
    public int numRoles() {
        return this.numRoles;
    }

    @Override
    public Role getRole(String roleId) {
        for (int i = 0; i < this.numRoles(); i++) {
            if (this.roles[i].getRoleId() == roleId) {
                return this.roles[i];
            }
        }
        return null;
    }

    @Override
    public int numWorkers() {
        return this.numWorkers;
    }

    @Override
    public Worker getWorker(String dni) {
        if (this.workers.isEmpty()) {
            return null;
        }
        return this.workers.get(dni);
    }

    @Override
    public int numWorkersByRole(String roleId) {
        Role role = getRole(roleId);
        if (role != null) {
            return role.numWorkers();
        }
        return 0;
    }

    @Override
    public int numWorkersBySportEvent(String sportEventId) {
        SportEvent sportEvent = getSportEvent(sportEventId);
        if (sportEvent == null) {
            return 0;
        }
        return sportEvent.numWorkers();
    }

    @Override
    public int numRatings(String playerId) {
        Player player = getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        return player.getNumRatings();
    }

    @Override
    public int numAttenders(String sportEventId) {
        SportEvent sportEvent = getSportEvent(sportEventId);
        if (sportEvent == null) {
            return 0;
        }
        return sportEvent.numAttenders();
    }

    @Override
    public int numFollowers(String playerId) {
        return numFollowByType(playerId, "Follower");
    }

    @Override
    public int numFollowings(String playerId) {
        return numFollowByType(playerId, "Followed");
    }

    private int numFollowByType(String playerId, String type) {
        Player player = getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int cont = 0;
        DirectedVertexImpl<Player, String> mainPlayer = (DirectedVertexImpl<Player, String>) socialNetwork.getVertex(player);
        for (Iterator<Edge<String, Player>> edges = mainPlayer.edges(); edges.hasNext();) {
            DirectedEdge<String, Player> currentEdge = (DirectedEdge<String, Player>) edges.next();
            if (currentEdge.getLabel() == type && currentEdge.getVertexSrc().getValue().getId() == playerId) {
                cont++;
            }
        }
        return cont;
    }

}
