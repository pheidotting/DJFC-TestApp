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
import nl.lakedigital.djfc.selenide.pages.*;
import nl.lakedigital.djfc.testapp.domein.Kantoor;
import nl.lakedigital.djfc.testapp.domein.Medewerker;
import nl.lakedigital.djfc.tests.commons.*;
import nl.lakedigital.djfc.tests.commons.MijnGegevensTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.google.common.collect.Lists.newArrayList;

public abstract class AbstractTest {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);

    public static Long timeOut = 5000L;
    @Rule
    public ScreenshotAlsTestFaalt screenshotAlsTestFaalt = new ScreenshotAlsTestFaalt();
    protected final String todoistToggle = "TODOIST";
    protected final String telefonieToggle = "TELEFONIE";
    protected final String beheerToggle = "BEHEERPAGINA";
    private boolean toggleTodoistWas;
    private boolean toggleTelefonieWas;
    private boolean toggleBeheerWas;

    protected Medewerker medewerker;

    protected LoginPagina loginPagina;
    protected LijstRelaties lijstRelaties;
    protected BeherenRelatie beherenRelatie;
    protected BeherenPolis beherenPolis;
    protected BeherenPolissen beherenPolissen;
    protected BeherenSchade beherenSchade;
    protected BeherenSchades beherenSchades;

    protected MijnGegevens mijnGegevens = new MijnGegevens();

    @Inject
    protected BeherenBedrijf beherenBedrijfPagina = new BeherenBedrijf();
    @Inject
    protected LijstBedrijven lijstBedrijvenPagina = new LijstBedrijven();
    protected ZoekScherm zoekScherm = new ZoekScherm();

    protected BeherenRelatieTest beherenRelatieTest = new BeherenRelatieTest(beherenBedrijfPagina, lijstBedrijvenPagina);
    protected BeherenPolisTest beherenPolisTest = new BeherenPolisTest(beherenBedrijfPagina, lijstBedrijvenPagina);
    protected BeherenSchadeTest beherenSchadeTest = new BeherenSchadeTest(beherenBedrijfPagina, lijstBedrijvenPagina);
    protected BeherenBedrijfTest beherenBedrijfTest = new BeherenBedrijfTest(beherenBedrijfPagina, lijstBedrijvenPagina);
    protected BeherenHypotheekTest beherenHypotheekTest = new BeherenHypotheekTest(beherenBedrijfPagina, lijstBedrijvenPagina);
    protected MijnGegevensTest mijnGegevensTest = new MijnGegevensTest(beherenBedrijfPagina, lijstBedrijvenPagina);

    private String basisUrl;
    private String basisUrlRest;

    protected Lorem lorem;
    private List<String> opmerkingen;

    protected boolean opServer = false;
    protected boolean uitvoeren = false;

    public AbstractTest(Logger LOGGER) {
        this.LOGGER = LOGGER;

        loginPagina = new LoginPagina();
        lijstRelaties = new LijstRelaties();
        beherenRelatie = new BeherenRelatie();
        beherenPolis = new BeherenPolis();
        beherenPolissen = new BeherenPolissen();
        beherenSchade = new BeherenSchade();
        beherenSchades = new BeherenSchades();
        lorem = LoremIpsum.getInstance();
        opmerkingen = new ArrayList<>();

        //        beherenBedrijfTest = new BeherenBedrijfTest(beherenBedrijf, lijstBedrijvenPagina);

    }

    public abstract void inloggen();

    public abstract List<String> tags();

    @Before
    public void setup() {
        LOGGER.debug("OS waar we op draaien : {}", System.getProperty("os.name"));
        basisUrl = "http://localhost:8080/";

        Configuration.reportsFolder = "target/screenshots";

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        String os = System.getProperty("os.name").equals("Mac OS X") ? "" : "-linux";
        System.setProperty("phantomjs.binary.path", "src/test/resources/phantomjs" + os);


        List<String> teRunnenTags = newArrayList("particulier");
        if (teRunnenTags.isEmpty()) {
            uitvoeren = true;
        } else {
            for (String tag : teRunnenTags) {
                if (tags().contains(tag)) {
                    uitvoeren = true;
                }
            }
        }

        if (!System.getProperty("os.name").equals("Mac OS X")) {
            uitvoeren = true;
            opServer = true;
            basisUrl = "http://192.168.91.215:8080/";
            setupPhantomJSDriver();
            WebDriverRunner.setWebDriver(new PhantomJSDriver());
            WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1920, 1080));
            timeOut = 30000L;
        } else {
            setupPhantomJSDriver();
            //            setupChromeDriver();
            basisUrl = "http://192.168.91.215:8080/";
        }
        if (uitvoeren) {
            basisUrlRest = basisUrl.replace("djfc/", "") + "dejonge/";

            LOGGER.debug("basisUrlRest {}", basisUrlRest);

            toggleTodoistWas = getFeatureToggle(todoistToggle);
            toggleTelefonieWas = getFeatureToggle(telefonieToggle);
            toggleBeheerWas = getFeatureToggle(beheerToggle);

            Map<String, Boolean> toggles = new HashMap<>();
            toggles.put(todoistToggle, toggleTodoistWas);
            toggles.put(telefonieToggle, toggleTelefonieWas);
            screenshotAlsTestFaalt.setAbstractTest(this);
            screenshotAlsTestFaalt.setToggles(toggles);
            setFeatureToggle(todoistToggle, false);
            setFeatureToggle(telefonieToggle, true);
            setFeatureToggle(beheerToggle, false);

            medewerker = new Medewerker();
            medewerker.setVoornaam("Bene");
            medewerker.setTussenvoegsel("de");
            medewerker.setAchternaam("Jonge");
            Kantoor kantoor = new Kantoor();
            kantoor.setNaam("De Jonge Financieel Consult");
            medewerker.setKantoor(kantoor);

            LOGGER.info("Naar de inlogpagina {}index.html#inloggen", basisUrl);
            open(basisUrl + "inloggen.html");

            inloggen();
        }
    }

    @After
    public void afsluiten() {
        if (uitvoeren) {
            assertNoJavascriptErrors();
            close();

            setFeatureToggle(todoistToggle, toggleTodoistWas);
            setFeatureToggle(telefonieToggle, toggleTelefonieWas);
            setFeatureToggle(beheerToggle, toggleBeheerWas);
        }
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
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
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

    protected String beginHoofdletters(String tekst) {
        String[] woorden = tekst.split(" ");
        StringBuffer sb = new StringBuffer();

        for (String woord : woorden) {
            sb.append(woord.substring(0, 1).toUpperCase() + woord.substring(1, woord.length()).toLowerCase());
            sb.append(" ");
        }

        return sb.toString().trim();
    }

    private void setupPhantomJSDriver() {
        WebDriverRunner.setWebDriver(new PhantomJSDriver());
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1920, 1080));
    }

    private void setupChromeDriver() {
        WebDriverRunner.setWebDriver(new ChromeDriver());

    }
}
