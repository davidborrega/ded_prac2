package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.traversal.Iterator;

public class OrganizingEntity {

    private String organizationId;
    private String description;
    private String name;
    private List<SportEvent> sportEvents;

    public OrganizingEntity(String organizationId, String name, String description) {
        this.organizationId = organizationId;
        this.name = name;
        this.description = description;
        this.sportEvents = new LinkedList<SportEvent>();
    }

    public String getName() {
        return name;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getDescription() {
        return description;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEvent(SportEvent sportEvent) {
        this.sportEvents.insertEnd(sportEvent);
    }

    public int numEvents() {
        return this.sportEvents.size();
    }

    public Iterator<SportEvent> sportEvents() {
        return this.sportEvents.values();
    }

    public int numAttenders() {
        int numAttenders = 0;
        for (Iterator<SportEvent> it = this.sportEvents(); it.hasNext();) {
            numAttenders += it.next().numAttenders();
        }
        return numAttenders;
    }

}
