package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;

public class Role {

    private String roleId;

    private String description;

    private List<Worker> workers;

    public Role(String roleId, String description) {
        this.setRoleId(roleId);
        this.setDescription(description);
        this.workers = new LinkedList<Worker>();
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getDescription() {
        return description;
    }

    public void addWorker(Worker worker) {
        this.workers.insertEnd(worker);
    }
}
