package nl.lakedigital.djfc.tests.commons;

import com.codeborne.selenide.Condition;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.selenide.pages.BeherenBedrijf;
import nl.lakedigital.djfc.selenide.pages.LijstBedrijven;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.joda.time.LocalDate;
import org.slf4j.Logger;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BeherenPolisTest extends AbstractPaginaTest {
    public BeherenPolisTest(BeherenBedrijf beherenBedrijf, LijstBedrijven lijstBedrijven) {
        super(beherenBedrijf, lijstBedrijven);
    }

    public void testInvoerenNieuwePolissen(Logger LOGGER, String voornamen, String bedrijfsnaam) {
        if (voornamen != null) {
            lijstRelaties.selecteer(LOGGER, lijstRelaties.zoekGebruiker(LOGGER, voornamen, false), beherenRelatie.getOpslaanRelatie(LOGGER));
        } else {
            //            dashboard.klikNaarZakelijk(LOGGER);
            lijstBedrijven.selecteer(lijstBedrijven.zoekBedrijf(bedrijfsnaam, false), beherenBedrijf.getOpslaanBedrijf());
        }

        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.POLIS_TOEVOEGEN, LOGGER, beherenPolis.getOpslaanPolis());

        List<JsonPolis> aanwezigePolissen = new ArrayList<>();

        List<String> soortenVerzekering = beherenPolis.getSoortVerzekering();
        soortenVerzekering.remove("Kies een soort polis...");
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
            voegPolisToeEnControleer(LOGGER, teTestenCombinaties.get(maatschappij), maatschappij, aanwezigePolissen);
        }

        beherenPolissen.klikHomeKnop(LOGGER);
    }

    private void voegPolisToeEnControleer(Logger LOGGER, String soortVerzekering, String maatschappij, List<JsonPolis> aanwezigePolissen) {
        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.POLIS_TOEVOEGEN, LOGGER, beherenPolis.getOpslaanPolis());

        Long polisnummer = System.currentTimeMillis();
        String kenmerk = maatschappij + "-" + soortVerzekering;

        JsonPolis polis = maakPolis(maatschappij, soortVerzekering, "Actief", polisnummer + "", kenmerk, lorem.getWords(3), lorem.getWords(4), "123", LocalDate.now(), LocalDate.now(), LocalDate.now(), "Kwartaal", lorem.getWords(50));

        aanwezigePolissen.add(polis);
        beherenPolis.vulVelden(LOGGER, polis);

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
}
