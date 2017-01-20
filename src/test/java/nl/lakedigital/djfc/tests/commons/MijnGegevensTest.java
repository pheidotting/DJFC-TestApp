package nl.lakedigital.djfc.tests.commons;

import nl.lakedigital.djfc.selenide.pages.BeherenBedrijf;
import nl.lakedigital.djfc.selenide.pages.LijstBedrijven;
import org.slf4j.Logger;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MijnGegevensTest extends AbstractPaginaTest {
    public MijnGegevensTest(BeherenBedrijf beherenBedrijf, LijstBedrijven lijstBedrijven) {
        super(new BeherenBedrijf(), new LijstBedrijven());
    }

    public void testInvullenNieuweGegevens(Logger LOGGER, String voornaam, String tussenvoegsel, String achternaam, String emailadres) {
        vulGegevens(LOGGER, voornaam, tussenvoegsel, achternaam, emailadres, null, null, true);

        assertThat(mijnGegevens.getMelding(), is("De gegevens zijn opgeslagen"));
        dashboard.testIngelogdeGebruiker(LOGGER, "Hendrik Haverkamp", "De Jonge Financieel Consult");

        vulGegevens(LOGGER, "Bene", "de", "Jonge", emailadres, null, null, true);
        dashboard.testIngelogdeGebruiker(LOGGER, "Bene de Jonge", "De Jonge Financieel Consult");
    }

    public void vulGegevens(Logger LOGGER, String voornaam, String tussenvoegsel, String achternaam, String emailadres, String wachtwoord, String wachtwoordNogmaals, boolean klik) {
        mijnGegevens.vulGegevens(LOGGER, voornaam, tussenvoegsel, achternaam, emailadres, wachtwoord, wachtwoordNogmaals, klik);
    }

    public void testWachtwoord(Logger LOGGER) {
        String wachtwoord = "a";
        String nogmaals = "b";

        //leeg scherm, melding dat wachtwoorden niet overeen komen mag nog niet zichtbaar zijn
        mijnGegevens.assertWachtwoordenKomNietOvereenMeldingOnzichtbaar(LOGGER);
        mijnGegevens.assertOpslaanKnopEnabled(LOGGER);
        mijnGegevens.vulWachtwoord(LOGGER, wachtwoord);
        assertThat(mijnGegevens.getMeldingWachtwoordSterkte(), is("Dit is een zeer zwak wachtwoord!"));

        //nog maar 1 wachtwoord ingevuld, melding mag nog steeds niet zichtbaar zijn
        mijnGegevens.assertWachtwoordenKomNietOvereenMeldingOnzichtbaar(LOGGER);
        mijnGegevens.assertOpslaanKnopEnabled(LOGGER);

        mijnGegevens.vulWachtwoordNogmaals(LOGGER, nogmaals);

        //beide velden gevuld, afwijkend van elkaar, melding tonen
        mijnGegevens.assertWachtwoordenKomNietOvereenMeldingZichtbaar(LOGGER);
        mijnGegevens.assertOpslaanKnopDIsabled(LOGGER);

        mijnGegevens.vulWachtwoordNogmaals(LOGGER, wachtwoord);

        //beide gevuld, met identieke waardes, melding weg
        mijnGegevens.assertWachtwoordenKomNietOvereenMeldingOnzichtbaar(LOGGER);
        mijnGegevens.assertOpslaanKnopEnabled(LOGGER);

        //wachtwoordsterkte testen
        mijnGegevens.vulWachtwoord(LOGGER, UUID.randomUUID().toString());
        assertThat(mijnGegevens.getMeldingWachtwoordSterkte(), is("Dit is een sterk wachtwoord!"));
        mijnGegevens.vulWachtwoord(LOGGER, wachtwoord);
    }
}
