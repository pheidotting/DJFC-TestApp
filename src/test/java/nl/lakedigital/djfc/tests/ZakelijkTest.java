package nl.lakedigital.djfc.tests;

import com.codeborne.selenide.Condition;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ZakelijkTest extends AbstractTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(ZakelijkTest.class);

    public ZakelijkTest() {
        super(LOGGER);
    }

    private String naam;

    @Test
    public void voeruitTestZakelijk() {

        loginPagina.inloggen("djfc.bene", "bene", dashboard.getNaarParticulier());

        dashboard.klikNaarZakelijk();

        naam = beginHoofdletters(lorem.getWords(2));

        lijstBedrijven.isZoekTermAanwezig();
        assertNull(lijstBedrijven.zoekBedrijf(naam, false));
        lijstBedrijven.klikToevoegenNieuwBedrijf();

        beherenBedrijf.testFoutmeldingBijNietAlleVerplichteVelden();
        beherenBedrijf.testFoutmeldingWegBijAlleVerplichteVeldenGevuld(naam);
        beherenBedrijf.invullenAlLeVelden(null, getStringOnMillis(), beginHoofdletters(lorem.getWords(1)), lorem.getEmail(), lorem.getUrl(), beginHoofdletters(lorem.getWords(1)), beginHoofdletters(lorem.getWords(1)));
        beherenBedrijf.adresToevoegen();
        beherenBedrijf.voegTelefoonnummerToe();
        beherenBedrijf.voegOpmerkingToeBijRelatie();
        beherenBedrijf.voegContactpersoonToe();
        beherenBedrijf.klikOpslaan();

        lijstBedrijven.zoekEnSelecteer(naam, beherenBedrijf.getOpslaanBedrijf());
        beherenSchades.klikHomeKnop(LOGGER);

        testInvoerenNieuwePolissen();
        testInvoerenSchades();
    }

    private void testInvoerenSchades() {
        dashboard.klikNaarZakelijk();
        lijstBedrijven.selecteer(lijstBedrijven.zoekBedrijf(naam, false), beherenBedrijf.getOpslaanBedrijf());

        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.SCHADE_TOEVOEGEN, LOGGER, beherenSchade.getSchadeMeldingOpslaan());

        assertTrue(beherenSchade.getPolisVoorSchademelding().size() > 0);
        List<String> polissen = beherenSchade.getPolisVoorSchademelding();
        List<String> statussen = beherenSchade.getStatusSchade();

        for (String polis : polissen) {
            for (String status : statussen) {
                beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.SCHADE_TOEVOEGEN, LOGGER, beherenSchade.getSchadeMeldingOpslaan());

                String schadeNummerMij = getStringOnMillis();
                String schadeNummerTP = getStringOnMillis();
                beherenSchade.vulAlleVelden(polis, schadeNummerMij, schadeNummerTP, beginHoofdletters(lorem.getWords(2)), "straat", status, LocalDateTime.now(), LocalDateTime.now(), LocalDate.now(), "100", lorem.getWords(20));
            }
        }
        beherenSchades.klikHomeKnop(LOGGER);
    }

    private void testInvoerenNieuwePolissen() {
        dashboard.klikNaarZakelijk();
        lijstBedrijven.selecteer(lijstBedrijven.zoekBedrijf(naam, false), beherenBedrijf.getOpslaanBedrijf());

        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.POLIS_TOEVOEGEN, LOGGER, beherenPolis.getOpslaanPolis());

        assertTrue(beherenPolis.getSoortVerzekering().size() > 1);
        assertTrue(beherenPolis.getVerzekeringsMaatschappij().size() > 1);

        List<JsonPolis> aanwezigePolissen = new ArrayList<>();

        List<String> soortenVerzekering = beherenPolis.getSoortVerzekering();
        List<String> verzekeringsmaatschappijen = beherenPolis.getVerzekeringsMaatschappij();

        assertTrue(soortenVerzekering.size() > 1);
        assertTrue(verzekeringsmaatschappijen.size() > 1);

        Map<Integer, Map<String, String>> combinaties = new HashMap<>();
        Integer teller = 0;

        for (String maatschappij : verzekeringsmaatschappijen) {
            for (String soortVerzekering : soortenVerzekering) {
                Map<String, String> combinatie = new HashMap<>();
                combinatie.put(maatschappij, soortVerzekering);

                combinaties.put(++teller, combinatie);
            }
        }

        List<Integer> keys = new ArrayList<>(combinaties.keySet());
        Collections.shuffle(keys);

        Map<String, String> teTestenCombinaties = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            Map<String, String> combi = combinaties.get(keys.get(i));

            for (String maatschappij : combi.keySet()) {
                teTestenCombinaties.put(maatschappij, combi.get(maatschappij));
            }
        }

        for (String maatschappij : teTestenCombinaties.keySet()) {
            String soortVerzekering = teTestenCombinaties.get(maatschappij);
            beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.POLIS_TOEVOEGEN, LOGGER, beherenPolis.getOpslaanPolis());

            Long polisnummer = System.currentTimeMillis();
            String kenmerk = maatschappij + "-" + soortVerzekering;

            JsonPolis polis = maakPolis(maatschappij, soortVerzekering, "Actief", polisnummer + "", kenmerk, beginHoofdletters(lorem.getWords(1)), beginHoofdletters(lorem.getWords(1)), "123", LocalDate.now(), LocalDate.now(), LocalDate.now(), "Kwartaal", "omschrijving");

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
        }
        beherenPolissen.klikHomeKnop(LOGGER);
    }
}
