package nl.lakedigital.djfc.tests;

import com.codeborne.selenide.Condition;
import nl.lakedigital.djfc.TestCase;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static nl.lakedigital.djfc.TestCase.Case.Case1;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ParticulierTest extends AbstractTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(ParticulierTest.class);

    private String voornamen = null;

    public ParticulierTest() {
        super(LOGGER);
    }

    @Test
    @Ignore
    public void testParticulier() {
        voornamen = voornaam();

        loginPagina.testOngeldigeLogin();
        loginPagina.testOngeldigWachtwoord("djfc.bene");
        inloggen("djfc.bene", "bene", dashboard.getNaarParticulier());

        dashboard.testIngelogdeGebruiker(maakNaamMedewerker(medewerker), medewerker.getKantoor().getNaam());

        testTabbladRelatieGegevens();

        beherenRelatie.
                voegBijlageToeBijRelatie(voornamen, lijstRelaties, dashboard);

        voegExtraOpmerkingToeBijRelatie(voornamen, 1);
        voegExtraOpmerkingToeBijRelatie(voornamen, 5);

        controleerOpmerkingenBijRelatie(voornamen);

        testInvoerenNieuwePolissen();
        testInvoerenSchades();
        dashboard.klikNaarParticulier();

        lijstRelaties.selecteer(lijstRelaties.zoekGebruiker(voornamen, false), beherenRelatie.getOpslaanRelatie());
        beherenRelatie.verwijderRelatie();

        assertNull(lijstRelaties.zoekGebruiker(voornamen, false));
    }

    private void testInvoerenSchades() {
        dashboard.klikNaarParticulier();
        lijstRelaties.selecteer(lijstRelaties.zoekGebruiker(voornamen, false), beherenRelatie.getOpslaanRelatie());

        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.SCHADE_TOEVOEGEN, LOGGER, beherenSchade.getSchadeMeldingOpslaan());

        List<String> polissen = beherenSchade.getPolisVoorSchademelding();
        List<String> statussen = beherenSchade.getStatusSchade();

        for (String polis : polissen) {
            for (String status : statussen) {
                beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.SCHADE_TOEVOEGEN, LOGGER, beherenSchade.getSchadeMeldingOpslaan());

                String schadeNummerMij = getStringOnMillis();
                String schadeNummerTP = getStringOnMillis();
                beherenSchade.vulAlleVelden(polis, schadeNummerMij, schadeNummerTP, "gevallen", "straat", status, LocalDateTime.now(), LocalDateTime.now(), LocalDate.now(), "100", "zo ineens lag ik op straat");
            }
        }
        beherenSchades.klikHomeKnop(LOGGER);
    }

    private void testInvoerenNieuwePolissen() {
        //        dashboard.klikNaarParticulier();
        lijstRelaties.selecteer(lijstRelaties.zoekGebruiker(voornamen, false), beherenRelatie.getOpslaanRelatie());

        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.POLIS_TOEVOEGEN, LOGGER, beherenPolis.getOpslaanPolis());

        List<JsonPolis> aanwezigePolissen = new ArrayList<>();

        //        for (String soortVerzekering : beherenPolis.getSoortVerzekering()) {
        String soortVerzekering = "Auto";
        //            for (String maatschappij : beherenPolis.getVerzekeringsMaatschappij()) {
        String maatschappij = "Aegon";
        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.POLIS_TOEVOEGEN, LOGGER, beherenPolis.getOpslaanPolis());

        Long polisnummer = System.currentTimeMillis();
        String kenmerk = maatschappij + "-" + soortVerzekering;

        JsonPolis polis = maakPolis(maatschappij, soortVerzekering, "Actief", polisnummer + "", kenmerk, "dedekking", "verzzaak", "123", LocalDate.now(), LocalDate.now(), LocalDate.now(), "Kwartaal", "omschrijving");

        aanwezigePolissen.add(polis);
        beherenPolis.vulVelden(polis);

        beherenPolissen.getTitel().get(0).waitUntil(Condition.appears, 2000);

        LOGGER.debug("Controleren {} polissen", aanwezigePolissen.size());

        assertThat(beherenPolissen.getTitelList().size(), is(aanwezigePolissen.size()));

        List<JsonPolis> gecontroleerd = new ArrayList<>();
        for (int i = 0; i < beherenPolissen.getTitelList().size(); i++) {
            for (JsonPolis teControlerenPolis : aanwezigePolissen) {
                String titel = beherenPolissen.getTitel().get(i).getText();

                if (titel.equals(teControlerenPolis.getSoort() + " (" + teControlerenPolis.getPolisNummer() + ")")) {

                    beherenPolissen.controleerPolis(i, teControlerenPolis, LOGGER);

                    gecontroleerd.add(polis);
                }
            }
        }

        assertThat(gecontroleerd.size(), is(aanwezigePolissen.size()));
        //            }
        //        }
        beherenPolissen.klikHomeKnop(LOGGER);
    }

    private void testTabbladRelatieGegevens() {
        dashboard.klikNaarParticulier();

        lijstRelaties.isZoekTermAanwezig();
        assertNull(lijstRelaties.zoekGebruiker(voornamen, false));
        lijstRelaties.klikToevoegenNieuweRelatie();

        beherenRelatie.testTakenNietZichtbaar();

        testFoutmeldingBijNietsIngevuld(beherenRelatie);
        testFoutmeldingAllesIngevuldBehalveDeVerplichteVelden(beherenRelatie);

        testFoutmeldingBijOnjuistMailAdres();
        testFoutmeldingBijOnjuisteGeboorteDatum();

        beherenRelatie.vulPagina(voornamen, naam(), "van", achternaam(), "103127586", new LocalDate(1979, 9, 6), LocalDate.now(), "Man", "Samenwonend", lorem.getEmail());

        voegAdresToe();
        voegRekeningnummerToe();
        voegTelefoonnummerToe();
        voegOpmerkingToeBijRelatie();

        beherenRelatie.klikOpslaan(true);
    }

    @TestCase(testcase = Case1)
    private void testFoutmeldingBijOnjuistMailAdres() {
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst("Vul een correct e-mailadres in.").size(), is(0));
        beherenRelatie.setEmailadres("aa");
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst("Vul een correct e-mailadres in.").size(), is(1));
        beherenRelatie.setEmailadres("aa@bb.cc");
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst("Vul een correct e-mailadres in.").size(), is(0));

    }

    private void testFoutmeldingBijOnjuisteGeboorteDatum() {
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst("Juiste invoerformaat is : dd-mm-eejj").size(), is(0));
        beherenRelatie.setGeboorteDatum("aa");
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst("Juiste invoerformaat is : dd-mm-eejj").size(), is(1));
        beherenRelatie.setGeboorteDatum(LocalDate.now());
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst("Juiste invoerformaat is : dd-mm-eejj").size(), is(0));
    }
}
