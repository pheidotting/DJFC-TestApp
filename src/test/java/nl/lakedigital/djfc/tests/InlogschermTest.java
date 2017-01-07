package nl.lakedigital.djfc.tests;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InlogschermTest extends AbstractTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(InlogschermTest.class);

    public InlogschermTest() {
        super(LOGGER);
    }

    @Test
    public void testHetInlogscherm() {
        loginPagina.testOngeldigeLogin();
        loginPagina.testOngeldigWachtwoord("djfc.bene");
        inloggen("djfc.bene", "bene", dashboard.getNaarParticulier());

        dashboard.wachtUitloggenAanwezig();
        dashboard.klikUitloggen();

        loginPagina.isInlogButtonAanwezig();
    }
}
