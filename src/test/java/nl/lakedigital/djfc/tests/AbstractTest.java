package nl.lakedigital.djfc.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.base.Predicate;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.TestCaseDJFC;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.selenide.pages.*;
import nl.lakedigital.djfc.selenide.pages.commons.*;
import nl.lakedigital.djfc.testapp.domein.Kantoor;
import nl.lakedigital.djfc.testapp.domein.Medewerker;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Lists.newArrayList;
import static nl.lakedigital.djfc.TestCaseDJFC.Case.DJFC54;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class AbstractTest {
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

    @Before
    public void setup() {
        LOGGER.debug("OS {}", System.getProperty("os.name"));
        basisUrl = "http://localhost:8080/djfc/";

        Configuration.reportsFolder = "target/screenshots";

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        String os = System.getProperty("os.name").equals("Mac OS X") ? "" : "-linux";
        System.setProperty("phantomjs.binary.path", "src/test/resources/phantomjs" + os);

        //        WebDriverRunner.setWebDriver(new ChromeDriver());
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
        setFeatureToggle(todoistToggle, false);
        setFeatureToggle(telefonieToggle, false);

        medewerker = new Medewerker();
        medewerker.setVoornaam("Bene");
        medewerker.setTussenvoegsel("de");
        medewerker.setAchternaam("Jonge");
        Kantoor kantoor=new Kantoor();
        kantoor.setNaam("De Jonge Financieel Consult");
        medewerker.setKantoor(kantoor);

        LOGGER.info("Naar de inlogpagina {}index.html#inloggen", basisUrl);
        open(basisUrl + "index.html#inloggen");
    }

    @After
    public  void afsluiten() {
        assertNoJavascriptErrors();
        close();

        setFeatureToggle(todoistToggle, toggleTodoistWas);
        setFeatureToggle(telefonieToggle, toggleTelefonieWas);
    }

    protected String voornaam() {
        return lorem.getNameMale();
    }

    protected String achternaam() {
        return lorem.getLastName();
    }

    protected String naam() {
        return lorem.getName();
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

    @TestCaseDJFC(DJFC54)
    protected void inloggen(String identificatie, String wachtwoord, SelenideElement wachtenOp) {
        loginPagina.inloggen(identificatie, wachtwoord);
        if (wachtenOp != null) {
            wachtenOp.waitUntil(Condition.appears, 2500);
        }
    }

    protected void testFoutmeldingBijNietsIngevuld(AbstractPagina pagina) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        beherenRelatie.klikOpslaan(false);
        checkValidationMessages(2, pagina, "Dit veld is verplicht.");
    }

    protected void testFoutmeldingAllesIngevuldBehalveDeVerplichteVelden(AbstractPagina pagina) {
        beherenRelatie.vulPagina("", "Patrick", "van", "", "103127586", new LocalDate(1979, 9, 6), LocalDate.now(), "Man", "Samenwonend", "patrick@heidotting.nl");
        beherenRelatie.klikOpslaan(false);
        checkValidationMessages(2, pagina,"Dit veld is verplicht.");
    }

    protected void voegAdresToe() {
        beherenRelatie.getAdressen().voegAdresToe();
        Adres adres = beherenRelatie.getAdressen().getAdressen().get(0);
        adres.setSoortadres(Adres.SoortAdres.POSTADRES);
        adres.setPostcode("7891TN");
        adres.setHuisnummer("24", "7891 TN");
        adres.checkStraatEnPlaatsnaam("Boogschutter", "KLAZIENAVEEN", "7891 TN");
    }

    protected void voegRekeningnummerToe() {
        beherenRelatie.getRekeningnummers().voegRekeningToe();
        Rekeningnummer rekeningnummer = beherenRelatie.getRekeningnummers().getRekeningnummers().get(0);
        rekeningnummer.vulRekeningnummer("nl96snsb0907007406", "NL96 SNSB 0907 0074 06", null);
    }

    protected void voegTelefoonnummerToe() {
        beherenRelatie.getTelefoonnummers().voegTelefoonnummerToe();
        Telefoonnummer telefoonnummer = beherenRelatie.getTelefoonnummers().getTelefoonnummers().get(0);
        telefoonnummer.vulTelefoonnummer("0621564744", "06 - 21 56 47 44", "Mobiel", "blabladiebla");
    }

    protected void voegOpmerkingToeBijRelatie() {
        voegOpmerkingToeBijRelatie(0);
    }

    protected void voegOpmerkingToeBijRelatie(int nummer) {
        beherenRelatie.getOpmerkingen().voegOpmerkingToe();
        Opmerking opmerking = beherenRelatie.getOpmerkingen().getOpmerkingen().get(0);
        String opm = lorem.getWords(5, 10);
        opmerking.vulOpmerking(opm, nummer);
        opmerkingen.add(opm);
    }

    protected void controleerOpmerkingenBijRelatie(String voornamen) {
        lijstRelaties.selecteer(lijstRelaties.zoekGebruiker(voornamen, false), null);
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> teControlerenOpmerkingen = opmerkingen;

        for (Opmerking opmerking : beherenRelatie.getOpmerkingen().getOpmerkingen()) {
            String opm = getFirst(filter(teControlerenOpmerkingen, new Predicate<String>() {
                @Override
                public boolean apply(@Nullable String s) {
                    return s.equals(opmerking.getOpmerkingAsString());
                }
            }), null);

            LOGGER.debug("{} == {}", opmerking.getOpmerkingAsString(), opm);
            assertFalse(opm == null);
            teControlerenOpmerkingen.remove(opm);
        }

        assertThat(teControlerenOpmerkingen.size(), is(0));

        beherenRelatie.klikHomeKnop(LOGGER);
        dashboard.klikNaarParticulier();
    }

    protected void voegExtraOpmerkingToeBijRelatie(String voornamen, int aantal) {
        lijstRelaties.selecteer(lijstRelaties.zoekGebruiker(voornamen, false), null);

        for (int i = 0; i < aantal; i++) {
            voegOpmerkingToeBijRelatie(i);
        }

        beherenRelatie.klikOpslaan(true);
    }

    protected void checkValidationMessages(int verwachtAantal, AbstractPagina pagina, String... verwachteMelding) {
        List<SelenideElement> validationMessages = pagina.getValidationMessages();

        //        beherenRelatie.getValidationMessages().get(0).waitUntil(Condition.appears, 2500);
        List<SelenideElement> messagesInBeeld = newArrayList(filter(validationMessages, new Predicate<SelenideElement>() {
            @Override
            public boolean apply(@Nullable SelenideElement element) {
                return element.isDisplayed();
            }
        }));

        assertThat(messagesInBeeld.size(), is(verwachtAantal));

        int j = 0;
        for (int i = 0; i < messagesInBeeld.size(); i++) {
            j = i;
            if (verwachteMelding.length == 1) {
                j = 0;
            }
            assertThat(messagesInBeeld.get(i).getText(), is(verwachteMelding[j]));
        }
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
