package nl.lakedigital.djfc.tests;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class InlogschermTest extends AbstractTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(InlogschermTest.class);

    public InlogschermTest() {
        super(LOGGER);
    }

    @Override
    public List<String> tags() {
        return newArrayList("inlogscherm");
    }

    @Test
    public void testHetInlogscherm() {
        if (uitvoeren) {
        loginPagina.testOngeldigeLogin(LOGGER);
        loginPagina.testOngeldigWachtwoord(LOGGER, "djfc.bene");
            loginPagina.inloggen(LOGGER, "djfc.bene", "bene", null);//dashboard.getNaarParticulier());

            //        dashboard.wachtUitloggenAanwezig(LOGGER);
            //        dashboard.klikUitloggen(LOGGER);

            //        loginPagina.isInlogButtonAanwezig(LOGGER);
    }
    }

    @Override
    public void inloggen() {
        //hoeft hier niet, omdat het inloggen juist datgene is wat hier getest wordt.
    }
}
