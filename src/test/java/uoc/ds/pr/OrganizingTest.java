package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.File;
import uoc.ds.pr.model.OrganizingEntity;

import static uoc.ds.pr.util.DateUtils.createLocalDate;

public class OrganizingTest {

    protected SportEvents4Club sportEvents4Club;

    @Before
    public void setUp() throws Exception {
        this.sportEvents4Club = new SportEvents4ClubImpl();
    }

    @After
    public void tearDown() {
        this.sportEvents4Club = null;
    }

    @Test
    public void addOrganTest() throws NoAttendersException, OrganizingEntityNotFoundException, NoFilesException, AttenderAlreadyExistsException, SportEventNotFoundException, AttenderNotFoundException, LimitExceededException {
        this.sportEvents4Club.addOrganizingEntity("ORG-1", "Org1", "Org1");
        this.sportEvents4Club.addOrganizingEntity("ORG-2", "Org2", "Org2");
        this.sportEvents4Club.addOrganizingEntity("ORG-3", "Org3", "Org3");
        this.sportEvents4Club.addOrganizingEntity("ORG-4", "Org4", "Org4");
        this.sportEvents4Club.addOrganizingEntity("ORG-5", "Org5", "Org5");

        this.sportEvents4Club.addFile("F-003", "EV-1103", "ORG-3", "description EV-1101",
                SportEvents4Club.Type.MICRO, SportEvents4Club.FLAG_ALL_OPTS,
                22, createLocalDate("22-11-2022"), createLocalDate("15-12-2022"));
        this.sportEvents4Club.updateFile(SportEvents4Club.Status.ENABLED, createLocalDate("23-11-2022"), "OK: XXX 0");

        this.sportEvents4Club.addFile("F-005", "EV-1105", "ORG-5", "description EV-1105",
                SportEvents4Club.Type.MICRO, SportEvents4Club.FLAG_ALL_OPTS,
                22, createLocalDate("22-11-2022"), createLocalDate("15-12-2022"));
        this.sportEvents4Club.updateFile(SportEvents4Club.Status.ENABLED, createLocalDate("23-11-2022"), "OK: XXX 0");

        this.sportEvents4Club.addFile("F-002", "EV-1102", "ORG-2", "description EV-1102",
                SportEvents4Club.Type.MICRO, SportEvents4Club.FLAG_ALL_OPTS,
                22, createLocalDate("22-11-2022"), createLocalDate("15-12-2022"));
        this.sportEvents4Club.updateFile(SportEvents4Club.Status.ENABLED, createLocalDate("23-11-2022"), "OK: XXX 0");


        this.sportEvents4Club.addAttender("5551", "Pepet", "EV-1103");
        this.sportEvents4Club.addAttender("5552", "Pepet", "EV-1103");
        this.sportEvents4Club.addAttender("5553", "Pepet", "EV-1103");
        this.sportEvents4Club.addAttender("5554", "Pepet", "EV-1103");
        this.sportEvents4Club.addAttender("5555", "Pepet", "EV-1103");
        this.sportEvents4Club.addAttender("5556", "Pepet", "EV-1103");
        this.sportEvents4Club.addAttender("5557", "Pepet", "EV-1103");

        this.sportEvents4Club.addAttender("5558", "Pepet", "EV-1105");
        this.sportEvents4Club.addAttender("5559", "Pepet", "EV-1105");
        this.sportEvents4Club.addAttender("5510", "Pepet", "EV-1105");

        this.sportEvents4Club.addAttender("5511", "Pepet", "EV-1102");
        this.sportEvents4Club.addAttender("5512", "Pepet", "EV-1102");
        this.sportEvents4Club.addAttender("5513", "Pepet", "EV-1102");
        this.sportEvents4Club.addAttender("5514", "Pepet", "EV-1102");


        int i = 0;
        for (Iterator<OrganizingEntity> it = this.sportEvents4Club.best5OrganizingEntities(); it.hasNext();) {
            OrganizingEntity or = it.next();
            System.out.println("TEST - Org: " + or.getOrganizationId() + " => Attenders: " + or.numAttenders());
            i++;
        }
        System.out.println(i);

    }

}
