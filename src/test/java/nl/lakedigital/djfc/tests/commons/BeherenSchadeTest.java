package nl.lakedigital.djfc.tests.commons;

import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class BeherenSchadeTest extends AbstractPaginaTest {
    public void testInvoerenSchades(Logger LOGGER, String voornamen, String bedrijfsnaam) {
        if (voornamen != null) {
            dashboard.klikNaarParticulier(LOGGER);
            lijstRelaties.selecteer(LOGGER, lijstRelaties.zoekGebruiker(LOGGER, voornamen, false), beherenRelatie.getOpslaanRelatie(LOGGER));
        } else {
            dashboard.klikNaarZakelijk(LOGGER);
            lijstBedrijven.selecteer(lijstBedrijven.zoekBedrijf(bedrijfsnaam, false), beherenBedrijf.getOpslaanBedrijf());
        }

        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.SCHADE_TOEVOEGEN, LOGGER, beherenSchade.getSchadeMeldingOpslaan());

        assertTrue(beherenSchade.getPolisVoorSchademelding().size() > 0);

        List<String> polissen = beherenSchade.getPolisVoorSchademelding();
        List<String> statussen = beherenSchade.getStatusSchade();

        for (String polis : polissen) {
            for (String status : statussen) {
                beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.SCHADE_TOEVOEGEN, LOGGER, beherenSchade.getSchadeMeldingOpslaan());

                String schadeNummerMij = getStringOnMillis();
                String schadeNummerTP = getStringOnMillis();
                beherenSchade.vulAlleVelden(LOGGER, polis, schadeNummerMij, schadeNummerTP, "gevallen", "straat", status, LocalDateTime.now(), LocalDateTime.now(), LocalDate.now(), "100", "zo ineens lag ik op straat");
            }
        }
        beherenSchades.klikHomeKnop(LOGGER);
    }
}
