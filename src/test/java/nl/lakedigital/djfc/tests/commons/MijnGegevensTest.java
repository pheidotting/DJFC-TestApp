package nl.lakedigital.djfc.tests.commons;

import nl.lakedigital.djfc.selenide.pages.BeherenBedrijf;
import nl.lakedigital.djfc.selenide.pages.LijstBedrijven;
import org.slf4j.Logger;

public class MijnGegevensTest extends AbstractPaginaTest {
    public MijnGegevensTest(BeherenBedrijf beherenBedrijf, LijstBedrijven lijstBedrijven) {
        super(new BeherenBedrijf(), new LijstBedrijven());
    }

    public void vulGegevens(Logger LOGGER, String voornaam, String tussenvoegsel, String achternaam, String emailadres, String wachtwoord, String wachtwoordNogmaals, boolean klik) {
        mijnGegevens.vulGegevens(LOGGER, voornaam, tussenvoegsel, achternaam, emailadres, wachtwoord, wachtwoordNogmaals, klik);
    }
}
