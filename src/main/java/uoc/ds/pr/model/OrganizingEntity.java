package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;

public class OrganizingEntity {

    private int organizationId;
    private String description;
    private String name;
    private List<SportEvent> events;

    public OrganizingEntity(int organizationId, String name, String description) {
        this.organizationId = organizationId;
        this.name = name;
        this.description = description;
        events = new LinkedList<>();
    }

}
