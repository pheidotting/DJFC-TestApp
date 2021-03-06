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
public class ZakelijkTest extends AbstractTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(ZakelijkTest.class);

    public ZakelijkTest() {
        super(LOGGER);
    }

    private String naam;

    @Override
    public List<String> tags() {
        return newArrayList("zakelijk");
    }

    @Test
    public void voeruitTestZakelijk() {
        if (uitvoeren) {
            naam = beginHoofdletters(lorem.getWords(2));

            beherenBedrijfTest.testBedrijfsgegevensTabblad(LOGGER, naam);

            lijstBedrijvenPagina.zoekEnSelecteer(naam, beherenBedrijfPagina.getOpslaanBedrijf());
            beherenSchades.klikHomeKnop(LOGGER);

            beherenPolisTest.
                    testInvoerenNieuwePolissen(LOGGER, null, naam);
            beherenSchadeTest.
                    testInvoerenSchades(LOGGER, null, naam);
        }
    }

    @Override
    public void inloggen() {
        loginPagina.inloggen(LOGGER, "djfc.bene", "bene", null);//dashboard.getNaarZakelijk());
        //        dashboard.testIngelogdeGebruiker(LOGGER, maakNaamMedewerker(medewerker), medewerker.getKantoor().getNaam());
    }
}
