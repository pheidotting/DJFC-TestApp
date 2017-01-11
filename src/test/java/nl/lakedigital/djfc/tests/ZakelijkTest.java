package nl.lakedigital.djfc.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        naam = beginHoofdletters(lorem.getWords(2));

        beherenBedrijfTest.testBedrijfsgegevensTabblad(LOGGER, naam);

        lijstBedrijvenPagina.zoekEnSelecteer(naam, beherenBedrijfPagina.getOpslaanBedrijf());
        beherenSchades.klikHomeKnop(LOGGER);

        beherenPolisTest.
                testInvoerenNieuwePolissen(LOGGER, null, naam);
        beherenSchadeTest.
                testInvoerenSchades(LOGGER, null, naam);
    }

    @Override
    public void inloggen() {
        loginPagina.inloggen(LOGGER, "djfc.bene", "bene", dashboard.getNaarZakelijk());
        dashboard.testIngelogdeGebruiker(LOGGER, maakNaamMedewerker(medewerker), medewerker.getKantoor().getNaam());
    }
}
