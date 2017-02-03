package nl.lakedigital.djfc.tests;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class InlogschermTest extends AbstractTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(InlogschermTest.class);

    public InlogschermTest() {
        super(LOGGER);
    }

    @Test
    public void testHetInlogscherm() {
        loginPagina.testOngeldigeLogin(LOGGER);
        loginPagina.testOngeldigWachtwoord(LOGGER, "djfc.bene");
        loginPagina.inloggen(LOGGER, "djfc.bene", "bene", dashboard.getNaarParticulier());

        dashboard.wachtUitloggenAanwezig(LOGGER);
        dashboard.klikUitloggen(LOGGER);

        loginPagina.isInlogButtonAanwezig(LOGGER);
    }

    @Override
    public void inloggen() {
        //hoeft hier niet, omdat het inloggen juist datgene is wat hier getest wordt.
    }
}
