package nl.lakedigital.djfc.testapp.teststappen;

import nl.lakedigital.djfc.commons.json.*;
import nl.lakedigital.djfc.testapp.client.DJFCClient;
import nl.lakedigital.djfc.testapp.testcases.TestCase1;
import nl.lakedigital.djfc.testapp.testdata.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Range.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertTrue;

public abstract class AbstractTestStap {
    private Long voortgang;
    protected DJFCClient djfcClient = new DJFCClient();
    protected Personen personen = new Personen();
    protected Adressen adressen = new Adressen();
    protected Telefoonnummers telefoonnummers = new Telefoonnummers();
    protected Rekeningnummers rekeningnummers = new Rekeningnummers();
    protected Polissen polissen = new Polissen();
//    protected String trackAndTraceId;
    protected final Long medewerkerId = 3L;

    protected SessionFactory sessionFactoryDJFC;
    protected SessionFactory sessionFactoryOGA;
    //    protected Transaction transactionDJFC;
    //    protected Transaction transactionOGA;

    protected Session getSessionDJFC() {
        if (sessionFactoryDJFC == null) {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
            sessionFactoryDJFC = (SessionFactory) applicationContext.getBean("sessionFactoryDJFC");
        }
        return sessionFactoryDJFC.getCurrentSession();
    }

    protected Session getSessionOGA() {
        if (sessionFactoryOGA == null) {
            ApplicationContext applicationContextOGA = new ClassPathXmlApplicationContext("classpath:applicationContextOGA.xml");
            sessionFactoryOGA = (SessionFactory) applicationContextOGA.getBean("sessionFactoryOGA");
        }
        return sessionFactoryOGA.getCurrentSession();
    }

    protected void getTransactions() {
        if (getSessionDJFC().getTransaction().getStatus() != TransactionStatus.ACTIVE) {
            getSessionDJFC().getTransaction().begin();
        }
        if (getSessionOGA().getTransaction().getStatus() != TransactionStatus.ACTIVE) {
            getSessionOGA().getTransaction().begin();
        }
    }

    protected void commit() {
        getSessionDJFC().getTransaction().commit();
        getSessionOGA().getTransaction().commit();
    }

    public abstract Long voerUit(Long id, Long medewerkerId, String sessieCode);

    protected String nieuweTrackAndTraceId() {
        TestGegevens.trackAndTraceId = UUID.randomUUID().toString();
        return TestGegevens.trackAndTraceId;
    }

    public Long run(Long id, Long medewerkerId, String sessieCode) {
        setVoortgang(0L);

        Long ret = voerUit(id, medewerkerId, sessieCode);

        setVoortgang(100L);

        return ret;
    }

    public Long getVoortgang() {
        return voortgang;
    }

    public void setVoortgang(Long voortgang) {
        this.voortgang = voortgang;
    }
    protected void controleerRelatieObject(Object obj, JsonRelatie relatie, boolean auditTabel){
        Object[] o = (Object[])obj;

        int offset = 0;
        if(auditTabel){
            offset=2;
        }

        assertThat(    relatie.getId(),is(Long.valueOf(o[1+offset].toString())));
        assertThat(    relatie.getAchternaam(),is(o[5+offset]));
        assertThat(    relatie.getTussenvoegsel(),is(o[6+offset]));
        assertThat(    relatie.getVoornaam(),is(o[7+offset]));
        assertThat(    relatie.getRoepnaam(),is(o[8+offset]));
        assertThat(    relatie.getBsn(),is(o[9+offset]));
        assertThat(    relatie.getBurgerlijkeStaat().substring(0,1),is(o[10+offset]));
        assertThat(    relatie.getGeboorteDatum(),is(zetDatumOm(o[11+offset].toString())));
        assertThat(    relatie.getGeslacht().substring(0,1),is(o[12+offset]));
        assertThat(    relatie.getOverlijdensdatum(),is(zetDatumOm(o[13+offset].toString())));
        if(!auditTabel){
            assertThat(    relatie.getKantoor(),is(Long.valueOf(o[14+offset].toString())));}
        assertThat(    relatie.getIdentificatie(),is(o[15+offset]));
    }

    protected String zetDatumOm(String datum){
        DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterOutput = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return LocalDate.parse(datum,formatterInput).format(formatterOutput);
    }

    protected void controleerAdresObject(Object obj, JsonAdres adres, boolean auditTabel){
        Object[] o = (Object[])obj;

        int offset = 0;
        if(auditTabel){
            offset=2;
        }

//        assertThat(    adres.getId(),is(Long.valueOf(o[0+offset].toString())));
        assertThat(    adres.getHuisnummer(),is(Long.valueOf(o[1+offset].toString())));
        assertThat(    adres.getPlaats(),is(o[2+offset]));
        assertThat(    adres.getPostcode(),is(o[3+offset]));
        assertThat(    adres.getStraat(),is(o[4+offset]));
        assertThat(    adres.getToevoeging(),is(o[5+offset]));
        assertThat(    adres.getSoortAdres(),is(o[6+offset]));
        assertThat(    adres.getEntiteitId(),is(Long.valueOf(o[7+offset].toString())));
        assertThat(    adres.getSoortEntiteit(),is(o[8+offset].toString()));
    }

    protected void controleerRekeningNummerObject(Object obj, JsonRekeningNummer rekeningNummer, boolean auditTabel){
        Object[] o = (Object[])obj;

        int offset = 0;
        if(auditTabel){
            offset=2;
        }

//        assertThat(    adres.getId(),is(Long.valueOf(o[0+offset].toString())));
        assertThat(    rekeningNummer.getBic(),is(o[1+offset]));
        assertThat(   rekeningNummer.getRekeningnummer(),is(o[2+offset]));
        assertThat(    rekeningNummer.getEntiteitId(),is(Long.valueOf(o[3+offset].toString())));
        assertThat(    rekeningNummer.getSoortEntiteit(),is(o[4+offset].toString()));
    }
    protected void controleerTelefoonnummerObject(Object obj, JsonTelefoonnummer telefoonnummer, boolean auditTabel){
        Object[] o = (Object[])obj;

        int offset = 0;
        if(auditTabel){
            offset=2;
        }

        assertThat(    telefoonnummer.getSoort(),is(o[1+offset]));
        assertThat(   telefoonnummer.getTelefoonnummer(),is(o[2+offset]));
        assertThat(   telefoonnummer.getOmschrijving(),is(o[3+offset]));
        assertThat(    telefoonnummer.getEntiteitId(),is(Long.valueOf(o[4+offset].toString())));
        assertThat(    telefoonnummer.getSoortEntiteit(),is(o[5+offset].toString()));
    }
    protected void controleerPolisObject(Object obj, JsonPolis polis, boolean auditTabel){
        Object[] o = (Object[])obj;

        int offset = 0;
        if(auditTabel){
            offset=2;
        }

assertThat(polis.getStatus(),is(o[2+offset]));
assertThat(polis.getBetaalfrequentie(),is(o[3+offset]));
assertThat(polis.getIngangsDatum(),is(zetDatumOm(o[4+offset].toString())));
assertThat(polis.getEindDatum(),is(zetDatumOm(o[5+offset].toString())));
assertThat(polis.getPolisNummer(),is(o[6+offset]));
assertThat(polis.getKenmerk(),is(o[7+offset]));
assertThat(polis.getPremie().replace(",","."),is(o[8+offset].toString()));
assertThat(polis.getWijzigingsDatum(),is(zetDatumOm(o[9+offset].toString())));
assertThat(polis.getProlongatieDatum(),is(zetDatumOm(o[10+offset].toString())));
//assertThat(polis.getBedrijf(),is(o[11+offset]));
assertThat(polis.getMaatschappij(),is(o[12+offset].toString()));
assertThat(polis.getOmschrijvingVerzekering(),is(o[13+offset]));
//assertThat(polis.getRelatie().toString(),is(o[14+offset].toString()));
assertThat(polis.getDekking(),is(o[15+offset]));
assertThat(polis.getVerzekerdeZaak(),is(o[16+offset]));
    }


    protected void controleerRevDJFC(Long revId){
        List<Object> revs = getSessionDJFC().createSQLQuery("select * from REVINFO where id = "+revId).list();

        for(Object rev:revs){
            Object[] o = (Object[])rev;

            assertThat(o[1],notNullValue());
            assertThat(Long.valueOf(o[2].toString()),is(medewerkerId));
            assertThat(o[3].toString(),is(TestGegevens.trackAndTraceId));
        }

        List<Object> inkomendeRequests = getSessionDJFC().createSQLQuery("select * from INKOMENDREQUEST where trackandtraceid = '"+TestGegevens.trackAndTraceId+"'").list();
        assertTrue(inkomendeRequests.size()>0);
    }
    protected void controleerRevOGA(Long revId){
        List<Object> revs = getSessionOGA().createSQLQuery("select * from REVINFO where id = "+revId).list();

        for(Object rev:revs){
            Object[] o = (Object[])rev;

            assertThat(o[1],notNullValue());
            assertThat(Long.valueOf(o[2].toString()),is(medewerkerId));
            assertThat(o[3].toString(),is(TestGegevens.trackAndTraceId));
        }

        List<Object> inkomendeRequests = getSessionDJFC().createSQLQuery("select * from INKOMENDREQUEST where trackandtraceid = '"+TestGegevens.trackAndTraceId+"'").list();
        assertTrue(inkomendeRequests.size()>0);
    }
}
