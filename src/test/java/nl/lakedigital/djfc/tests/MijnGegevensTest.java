package nl.lakedigital.djfc.tests;

import com.codeborne.selenide.Condition;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class MijnGegevensTest extends AbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MijnGegevensTest.class);

    public MijnGegevensTest() {
        super(LOGGER);
    }

    @Test
    @Ignore
    public void testMijnGegevens() {
        dashboard.klikNaarBeheer(LOGGER);

        mijnGegevens.getOpslaan().waitUntil(Condition.appears, timeOut);

        mijnGegevensTest.testWachtwoord(LOGGER);

        mijnGegevensTest.testInvullenNieuweGegevens(LOGGER, "Hendrik", "", "Haverkamp", "info@hendrikhaverkamp.nl");

    }

    @Override
    public void inloggen() {
        setFeatureToggle(beheerToggle, true);

        LOGGER.debug("beheerToggle {}", getFeatureToggle(beheerToggle));

        loginPagina.inloggen(LOGGER, "djfc.bene", "bene", dashboard.getNaarBeheer());
        dashboard.testIngelogdeGebruiker(LOGGER, maakNaamMedewerker(medewerker), medewerker.getKantoor().getNaam());
    }
}
