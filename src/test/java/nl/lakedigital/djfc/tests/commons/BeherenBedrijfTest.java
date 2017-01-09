package nl.lakedigital.djfc.tests.commons;

import org.slf4j.Logger;

import static org.junit.Assert.assertNull;

public class BeherenBedrijfTest extends AbstractPaginaTest {
    public void testBedrijfsgegevensTabblad(Logger LOGGER, String naam) {
        dashboard.klikNaarZakelijk(LOGGER);

        lijstBedrijven.isZoekTermAanwezig();
        assertNull(lijstBedrijven.zoekBedrijf(naam, false));
        lijstBedrijven.klikToevoegenNieuwBedrijf();

        beherenBedrijf.testFoutmeldingBijNietAlleVerplichteVelden();
        beherenBedrijf.testFoutmeldingWegBijAlleVerplichteVeldenGevuld(naam);
        beherenBedrijf.invullenAlLeVelden(null, getStringOnMillis(), beginHoofdletters(lorem.getWords(1)), lorem.getEmail(), lorem.getUrl(), beginHoofdletters(lorem.getWords(1)), beginHoofdletters(lorem.getWords(1)));
        beherenBedrijf.adresToevoegen();
        beherenBedrijf.voegTelefoonnummerToe();
        beherenBedrijf.voegOpmerkingToeBijRelatie();
        beherenBedrijf.voegContactpersoonToe(LOGGER);
        beherenBedrijf.klikOpslaan();
    }
}
