package uoc.ds.pr.model;

import edu.uoc.ds.adt.helpers.Position;
import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.Traversal;

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

    public void removeWorker(Worker worker) {
        Position<Worker> position;
        for (Traversal<Worker> positions = this.workers.positions(); positions.hasNext();) {
            position = positions.next();
            if (position.getElem().getDni().equals(worker.getDni())) {
                this.workers.delete(position);
                return;
            }
        }
    }

    public int numWorkers() {
        return this.workers.size();
    }

    public Iterator<Worker> getWorkers() {
        return this.workers.values();
    }

}
