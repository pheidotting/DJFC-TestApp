package nl.lakedigital.djfc.tests;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MijnGegevensTest extends AbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MijnGegevensTest.class);

    public MijnGegevensTest() {
        super(LOGGER);
    }

    @Test
    @Ignore
    public void testMijnGegevens() {

        dashboard.klikNaarBeheer(LOGGER);

    }

    @Override
    public void inloggen() {
        setFeatureToggle(beheerToggle, true);

        loginPagina.inloggen(LOGGER, "djfc.bene", "bene", dashboard.getNaarBeheer());
        dashboard.testIngelogdeGebruiker(LOGGER, maakNaamMedewerker(medewerker), medewerker.getKantoor().getNaam());
    }
}
