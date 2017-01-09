package nl.lakedigital.djfc.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.selenide.pages.*;
import nl.lakedigital.djfc.testapp.domein.Kantoor;
import nl.lakedigital.djfc.testapp.domein.Medewerker;
import nl.lakedigital.djfc.tests.commons.BeherenBedrijfTest;
import nl.lakedigital.djfc.tests.commons.BeherenPolisTest;
import nl.lakedigital.djfc.tests.commons.BeherenRelatieTest;
import nl.lakedigital.djfc.tests.commons.BeherenSchadeTest;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public abstract class AbstractTest {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);

    @Rule
    public ScreenshotAlsTestFaalt screenshotAlsTestFaalt = new ScreenshotAlsTestFaalt();
    protected final String todoistToggle = "TODOIST";
    protected final String telefonieToggle = "TELEFONIE";
    private boolean toggleTodoistWas;
    private boolean toggleTelefonieWas;

    protected Medewerker medewerker;

    protected LoginPagina loginPagina;
    protected Dashboard dashboard;
    protected LijstRelaties lijstRelaties;
    protected BeherenRelatie beherenRelatie;
    protected BeherenPolis beherenPolis;
    protected BeherenPolissen beherenPolissen;
    protected BeherenSchade beherenSchade;
    protected BeherenSchades beherenSchades;

    @Inject
    protected BeherenBedrijf beherenBedrijf;
    @Inject
    protected LijstBedrijven lijstBedrijven;

    protected BeherenRelatieTest beherenRelatieTest = new BeherenRelatieTest();
    protected BeherenPolisTest beherenPolisTest = new BeherenPolisTest();
    protected BeherenSchadeTest beherenSchadeTest = new BeherenSchadeTest();

    protected BeherenBedrijfTest beherenBedrijfTest = new BeherenBedrijfTest();

    private String basisUrl;
    private String basisUrlRest;

    protected Lorem lorem;
    private List<String> opmerkingen;

    protected boolean opServer = false;

    public AbstractTest(Logger LOGGER) {
        this.LOGGER = LOGGER;

        loginPagina = new LoginPagina();
        dashboard = new Dashboard();
        lijstRelaties = new LijstRelaties();
        beherenRelatie = new BeherenRelatie();
        beherenPolis = new BeherenPolis();
        beherenPolissen = new BeherenPolissen();
        beherenSchade = new BeherenSchade();
        beherenSchades = new BeherenSchades();
        lorem = LoremIpsum.getInstance();
        opmerkingen = new ArrayList<>();
    }

    public abstract void inloggen();

    @Before
    public void setup() {
        LOGGER.debug("OS {}", System.getProperty("os.name"));
        basisUrl = "http://localhost:8080/";

        Configuration.reportsFolder = "target/screenshots";

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        String os = System.getProperty("os.name").equals("Mac OS X") ? "" : "-linux";
        System.setProperty("phantomjs.binary.path", "src/test/resources/phantomjs" + os);

        //                        WebDriverRunner.setWebDriver(new ChromeDriver());
        WebDriverRunner.setWebDriver(new PhantomJSDriver());

        if (!System.getProperty("os.name").equals("Mac OS X")) {
            opServer = true;
            basisUrl = "http://192.168.91.215:8080/";
            WebDriverRunner.setWebDriver(new PhantomJSDriver());
        }
        basisUrlRest = basisUrl.replace("djfc/", "") + "dejonge/";

        LOGGER.debug("basisUrlRest {}", basisUrlRest);

         toggleTodoistWas = getFeatureToggle(todoistToggle);
         toggleTelefonieWas = getFeatureToggle(telefonieToggle);

        Map<String, Boolean> toggles = new HashMap<>();
        toggles.put(todoistToggle, toggleTodoistWas);
        toggles.put(telefonieToggle, toggleTelefonieWas);
        screenshotAlsTestFaalt.setAbstractTest(this);
        screenshotAlsTestFaalt.setToggles(toggles);
        setFeatureToggle(todoistToggle, true);
        setFeatureToggle(telefonieToggle, true);

        medewerker = new Medewerker();
        medewerker.setVoornaam("Bene");
        medewerker.setTussenvoegsel("de");
        medewerker.setAchternaam("Jonge");
        Kantoor kantoor=new Kantoor();
        kantoor.setNaam("De Jonge Financieel Consult");
        medewerker.setKantoor(kantoor);

        LOGGER.info("Naar de inlogpagina {}index.html#inloggen", basisUrl);
        open(basisUrl + "index.html#inloggen");

        inloggen();
    }

    @After
    public  void afsluiten() {
        assertNoJavascriptErrors();
        close();

        setFeatureToggle(todoistToggle, toggleTodoistWas);
        setFeatureToggle(telefonieToggle, toggleTelefonieWas);
    }


    protected String maakNaamMedewerker(Medewerker medewerker) {
        StringBuilder sb = new StringBuilder();
        sb.append(medewerker.getVoornaam());
        sb.append(" ");
        if (medewerker.getTussenvoegsel() != null) {
            sb.append(medewerker.getTussenvoegsel());
            sb.append(" ");
        }
        sb.append(medewerker.getAchternaam());

        return sb.toString();
    }



    public boolean getFeatureToggle(String toggleNaam) {
        String adres = "rest/authorisatie/togglz/toggles/" + toggleNaam;

        LOGGER.info("Aanroepen via GET " + basisUrlRest + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrlRest + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").header("username", "admin").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        String a = response.getEntity(String.class);
        return "true".equals(a);
    }

    public void setFeatureToggle(String toggleNaam, boolean waarde) {
        String adres = "rest/authorisatie/togglz/toggle?feature=" + toggleNaam + "&toggle=" + waarde;

        LOGGER.info("Aanroepen via GET " + basisUrlRest + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrlRest + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").header("username", "admin").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        response.getEntity(String.class);
    }

    protected String getStringOnMillis() {
        String s = String.valueOf(System.currentTimeMillis());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }
    protected JsonPolis maakPolis(String verzekeringsMaatschappij, String soortVerzekering, String status, String polisNummer, String kenmerk, String dekking, String verzekerdeZaak, String premie, LocalDate ingangsDatumString, LocalDate wijzigingsdatumString, LocalDate prolongatiedatumString, String betaalfrequentie, String omschrijvingVerzekering) {
        JsonPolis polis = new JsonPolis();

        polis.setMaatschappij(verzekeringsMaatschappij);
        polis.setSoort(soortVerzekering);
        polis.setStatus(status);
        polis.setPolisNummer(polisNummer);
        polis.setKenmerk(kenmerk);
        polis.setDekking(dekking);
        polis.setVerzekerdeZaak(verzekerdeZaak);
        polis.setPremie(premie);
        polis.setIngangsDatum(ingangsDatumString.toString("yyyy-MM-dd"));
        polis.setWijzigingsDatum(wijzigingsdatumString.toString("yyyy-MM-dd"));
        polis.setProlongatieDatum(prolongatiedatumString.toString("yyyy-MM-dd"));
        polis.setBetaalfrequentie(betaalfrequentie);
        polis.setOmschrijvingVerzekering(omschrijvingVerzekering);

        return polis;
    }

    protected String beginHoofdletters(String tekst) {
        String[] woorden = tekst.split(" ");
        StringBuffer sb = new StringBuffer();

        for (String woord : woorden) {
            sb.append(woord.substring(0, 1).toUpperCase() + woord.substring(1, woord.length()).toLowerCase());
            sb.append(" ");
        }

        return sb.toString().trim();
    }
}
