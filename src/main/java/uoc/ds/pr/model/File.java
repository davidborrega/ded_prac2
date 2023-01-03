package uoc.ds.pr.model;

import uoc.ds.pr.SportEvents4Club;

import java.time.LocalDate;
import java.util.Comparator;

public class File {

    private String id;
    private final SportEvents4Club.Type type;
    private String eventId;
    private String description;
    private byte resources;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate dateStatus;
    private String descriptionStatus;
    private int max;
    private SportEvents4Club.Status status;

    private String orgId;

    public static Comparator<File> CMP_Q = (File f1, File f2) -> {
      int cmp = f1.getStartDate().compareTo(f2.getStartDate());
      if (cmp == 0) {
          // Aforament
      }
      return cmp;
    };

    public File(String id, String eventId, String description, SportEvents4Club.Type type, LocalDate startDate, LocalDate endDate,
                byte resources, int max, String orgId) {
        this.id = id;
        this.eventId = eventId;
        this.description = description;
        this.type = type;
        this.startDate = startDate;
        this.resources = resources;
        this.endDate = endDate;
        this.max = max;
        this.status = SportEvents4Club.Status.PENDING;
        this.orgId = orgId;
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

    public String getDescriptionStatus() {
        return descriptionStatus;
    }

    public void setDescriptionStatus(String descriptionStatus) {
        this.descriptionStatus = descriptionStatus;
    }

    public String getFileId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(LocalDate dateStatus) {
        this.dateStatus = dateStatus;
    }

    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }

    public SportEvents4Club.Status getStatus() {
        return status;
    }

    public void setStatus(SportEvents4Club.Status status) {
        this.status = status;
    }

    public SportEvents4Club.Type getType() {
        return type;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void update(SportEvents4Club.Status status, LocalDate date, String description) {
        this.setStatus(status);
        this.setDateStatus(date);
        this.setDescriptionStatus(description);
    }

    public boolean isEnabled() {
        return this.status == SportEvents4Club.Status.ENABLED;
    }

    public SportEvent newSportEvent() {
        SportEvent sportEvent = new SportEvent(this.eventId, this.description, this.type,
                this.startDate, this.endDate, this.max, this);
        return sportEvent;
    }

}
