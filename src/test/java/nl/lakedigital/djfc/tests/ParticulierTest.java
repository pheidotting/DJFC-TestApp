package nl.lakedigital.djfc.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ParticulierTest extends AbstractTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(ParticulierTest.class);

    private String voornamen = null;

    public ParticulierTest() {
        super(LOGGER);
    }

    @Test
    public void testParticulier() {
        voornamen = voornaam();

        beherenRelatieTest.testTabbladRelatieGegevens(LOGGER, voornamen);
        beherenRelatieTest.controleerIngevuldeRelatie(LOGGER);

        beherenRelatieTest.voegBijlageToeBijRelatie(LOGGER, voornamen, lijstRelaties, dashboard);

        beherenRelatieTest.voegExtraOpmerkingToeBijRelatie(LOGGER, voornamen, 1);
        beherenRelatieTest.voegExtraOpmerkingToeBijRelatie(LOGGER, voornamen, 5);

        beherenRelatieTest.controleerOpmerkingenBijRelatie(LOGGER, voornamen);

        beherenPolisTest.
                testInvoerenNieuwePolissen(LOGGER, voornamen, null);

        beherenSchadeTest.
                testInvoerenSchades(LOGGER, voornamen, null);
        dashboard.klikNaarParticulier(LOGGER);

        lijstRelaties.selecteer(LOGGER, lijstRelaties.zoekGebruiker(LOGGER, voornamen, false), beherenRelatie.getOpslaanRelatie(LOGGER));
        beherenRelatie.verwijderRelatie(LOGGER);

        assertNull(lijstRelaties.zoekGebruiker(LOGGER, voornamen, false));
    }

    private String voornaam() {
        return lorem.getNameMale();
    }

    @Override
    public void inloggen() {
        loginPagina.inloggen(LOGGER, "djfc.bene", "bene", dashboard.getNaarParticulier());
        dashboard.testIngelogdeGebruiker(LOGGER, maakNaamMedewerker(medewerker), medewerker.getKantoor().getNaam());
    }
}
