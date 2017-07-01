package nl.lakedigital.djfc.tests;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ParticulierTest extends AbstractTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(ParticulierTest.class);

    private String voornamen = null;

    public ParticulierTest() {
        super(LOGGER);
    }

    @Override
    public List<String> tags() {
        return newArrayList("particulier");
    }

    @Test
    public void testParticulier() {
        if (uitvoeren) {
            voornamen = voornaam();

            beherenRelatieTest.testTabbladRelatieGegevens(LOGGER, voornamen);
            beherenRelatieTest.controleerIngevuldeRelatie(LOGGER);

            //        beherenRelatieTest.voegBijlageToeBijRelatie(LOGGER, voornamen, lijstRelaties, dashboard);

            beherenRelatieTest.voegExtraOpmerkingToeBijRelatie(LOGGER, voornamen, 1);
            beherenRelatieTest.voegExtraOpmerkingToeBijRelatie(LOGGER, voornamen, 5);

            beherenRelatieTest.controleerOpmerkingenBijRelatie(LOGGER, voornamen);

            beherenPolisTest.
                    testInvoerenNieuwePolissen(LOGGER, voornamen, null);

            beherenSchadeTest.
                    testInvoerenSchades(LOGGER, voornamen, null);

            //MIJN GEGEVENS, NIET BESCHIKBAAR
            //            dashboard.getNaarBeheer().waitUntil(Condition.disappears, timeOut);
            //            dashboard.klikNaarParticulier(LOGGER);
            //
            //            lijstRelaties.selecteer(LOGGER, lijstRelaties.zoekGebruiker(LOGGER, voornamen, false), beherenRelatie.getOpslaanRelatie(LOGGER));
            //            beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.HYPOTHEEK, LOGGER);
            //            beherenHypotheekTest.invullenGegevens(LOGGER);
            //
            //            beherenRelatie.klikHomeKnop(LOGGER, dashboard.getNaarParticulier());
            //            dashboard.klikNaarParticulier(LOGGER);
            //            lijstRelaties.selecteer(LOGGER, lijstRelaties.zoekGebruiker(LOGGER, voornamen, false), beherenRelatie.getOpslaanRelatie(LOGGER));
            //            beherenRelatie.verwijderRelatie(LOGGER);
            //
            //            assertNull(lijstRelaties.zoekGebruiker(LOGGER, voornamen, false));
        }
    }

    private String voornaam() {
        return lorem.getNameMale();
    }

    @Override
    public void inloggen() {
        loginPagina.inloggen(LOGGER, "djfc.bene", "bene", null);//dashboard.getNaarParticulier());
        //        dashboard.testIngelogdeGebruiker(LOGGER, maakNaamMedewerker(medewerker), medewerker.getKantoor().getNaam());
    }
}
