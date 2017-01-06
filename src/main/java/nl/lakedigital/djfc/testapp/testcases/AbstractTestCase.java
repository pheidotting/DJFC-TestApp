package nl.lakedigital.djfc.testapp.testcases;

import nl.lakedigital.djfc.commons.json.JsonRelatie;
import nl.lakedigital.djfc.testapp.client.DJFCClient;
import nl.lakedigital.djfc.testapp.domein.Gebruiker;
import nl.lakedigital.djfc.testapp.domein.Sessie;
import nl.lakedigital.djfc.testapp.repository.GebruikerRepository;
import nl.lakedigital.djfc.testapp.testdata.*;
import nl.lakedigital.djfc.testapp.teststappen.AbstractTestStap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractTestCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractTestCase.class);

    protected Long id;
    protected Adressen adressen = new Adressen();
    protected Telefoonnummers telefoonnummers = new Telefoonnummers();
    protected Rekeningnummers rekeningnummers = new Rekeningnummers();
    protected Polissen polissen = new Polissen();

    protected DJFCClient djfcClient = new DJFCClient();
    protected GebruikerRepository gebruikerRepository;

    protected String sessieCode;
    public static String trackAndTraceId;
    protected final Long medewerkerId = 3L;

    //    @Autowired
    protected SessionFactory sessionFactoryDJFC;
    //    @Autowired
    protected SessionFactory sessionFactoryOGA;
    protected Transaction transactionDJFC;
    protected Transaction transactionOGA;

    private List<AbstractTestStap> testStappen;

    protected String nieuweTrackAndTraceId() {
        trackAndTraceId = UUID.randomUUID().toString();
        return trackAndTraceId;
    }

    protected String nieuweSessieCode() {
        sessieCode = UUID.randomUUID().toString();
        return sessieCode;
    }

    public void setsessionFactoryDJFC(SessionFactory sessionFactoryDJFC) {
        this.sessionFactoryDJFC = sessionFactoryDJFC;
    }

    protected Session getSessionDJFC() {
        return sessionFactoryDJFC.getCurrentSession();
    }

    public void setsessionFactoryOGA(SessionFactory sessionFactoryOGA) {
        this.sessionFactoryOGA = sessionFactoryOGA;
    }

    protected Session getSessionOGA() {
        return sessionFactoryOGA.getCurrentSession();
    }

    protected void getTransactions() {
        transactionDJFC = getSessionDJFC().getTransaction();
        if (transactionDJFC.getStatus() != TransactionStatus.ACTIVE) {
            transactionDJFC.begin();
        }
        transactionOGA = getSessionOGA().getTransaction();
        if (transactionOGA.getStatus() != TransactionStatus.ACTIVE) {
            transactionOGA.begin();
        }
    }

    protected void commit() {
        transactionDJFC.commit();
        transactionOGA.commit();
    }

    protected void voorbereiden() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        gebruikerRepository = (GebruikerRepository) applicationContext.getBean("gebruikerRepository");
        sessionFactoryDJFC = (SessionFactory) applicationContext.getBean("sessionFactoryDJFC");

        Gebruiker gebruiker = gebruikerRepository.lees(medewerkerId);
        Sessie sessie = new Sessie();
        sessie.setSessie(nieuweSessieCode());
        sessie.setIpadres("127.0.0.1");
        gebruiker.getSessies().add(sessie);
        sessie.setGebruiker(gebruiker);
        gebruikerRepository.opslaan(gebruiker);
    }


    public List<AbstractTestStap> getTestStappen() {
        if (testStappen == null) {
            testStappen = new ArrayList<>();
        }
        return testStappen;
    }

    protected void voegTestStapToe(AbstractTestStap testStap) {
        getTestStappen().add(testStap);
    }

    public abstract void initTestStappen();

    public void run() {
        LOGGER.debug("Start TestCase");

        voorbereiden();

        LOGGER.debug("Voorbereiding gedaan");

        initTestStappen();

        LOGGER.debug("Init done");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (AbstractTestStap testStap : testStappen) {
                    LOGGER.debug("Volgende teststap");
                    Long i =
                    testStap.run(id, medewerkerId, sessieCode);
                    if(i !=null){id=i;}
                }
            }
        }).run();
    }
}
