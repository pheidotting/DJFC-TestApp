package nl.lakedigital.djfc.tests;

import com.codeborne.selenide.Condition;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Ignore
public class MijnGegevensTest extends AbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MijnGegevensTest.class);

    public MijnGegevensTest() {
        super(LOGGER);
    }

    @Override
    public List<String> tags() {
        return newArrayList("mijngegevens");
    }

    @Test
    public void testMijnGegevens() {
        if (uitvoeren) {
            //            dashboard.klikNaarBeheer(LOGGER);

            mijnGegevens.getOpslaan().waitUntil(Condition.appears, timeOut);

            mijnGegevensTest.testWachtwoord(LOGGER);

            mijnGegevensTest.testInvullenNieuweGegevens(LOGGER, "Hendrik", "", "Haverkamp", "info@hendrikhaverkamp.nl");
        }
    }

    @Override
    public void inloggen() {
        setFeatureToggle(beheerToggle, true);

        LOGGER.debug("beheerToggle {}", getFeatureToggle(beheerToggle));

        loginPagina.inloggen(LOGGER, "djfc.bene", "bene", null);//dashboard.getNaarBeheer());
        //        dashboard.testIngelogdeGebruiker(LOGGER, maakNaamMedewerker(medewerker), medewerker.getKantoor().getNaam());
    }
}
