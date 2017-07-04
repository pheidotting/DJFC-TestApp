package nl.lakedigital.djfc.tests.commons;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.base.Predicate;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.TestCase;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.commons.json.JsonRelatie;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.selenide.pages.BeherenBedrijf;
import nl.lakedigital.djfc.selenide.pages.LijstBedrijven;
import nl.lakedigital.djfc.selenide.pages.LijstRelaties;
import nl.lakedigital.djfc.selenide.pages.commons.*;
import nl.lakedigital.djfc.tests.AbstractTest;
import org.hamcrest.core.Is;
import org.joda.time.LocalDate;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Lists.newArrayList;
import static nl.lakedigital.djfc.TestCase.Case.Case1;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class BeherenRelatieTest extends AbstractPaginaTest {
    public BeherenRelatieTest(BeherenBedrijf beherenBedrijf, LijstBedrijven lijstBedrijven) {
        super(beherenBedrijf, lijstBedrijven);
    }

    private Lorem lorem = LoremIpsum.getInstance();
    private JsonRelatie relatie;

    public void voegBijlageToeBijRelatie(Logger LOGGER, String voornamen, LijstRelaties lijstRelaties) {
        if (WebDriverRunner.getAndCheckWebDriver() instanceof PhantomJSDriver) {
            lijstRelaties.selecteer(LOGGER, lijstRelaties.zoekGebruiker(LOGGER, voornamen, false), null);

            beherenRelatie.getBijlages().uploadFile(LOGGER, Bijlages.UploadBestand.EEN_PDF);

            beherenRelatie.klikHomeKnop(LOGGER);
            //            dashboard.klikNaarParticulier(LOGGER);

            //Controleren
            lijstRelaties.selecteer(LOGGER, lijstRelaties.zoekGebruiker(LOGGER, voornamen, false), null);

            beherenRelatie.logIsGevuldMet(LOGGER, beherenRelatie.getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam(), "1.pdf");
            assertThat(beherenRelatie.getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam().getText(), Is.is("1.pdf"));

            beherenRelatie.logKlik(LOGGER, beherenRelatie.getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam());
            beherenRelatie.getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam().click();
            wachtFf();
            String nwOmschrijving = lorem.getWords(1, 10);
            beherenRelatie.logInvullen(LOGGER, beherenRelatie.getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaamEdit(), nwOmschrijving);
            beherenRelatie.getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaamEdit().setValue(nwOmschrijving);
            beherenRelatie.logKlik(LOGGER, beherenRelatie.getBijlages().getBijlages().get(0).getOpslaanOmschrijvingOfBestandsNaam());
            beherenRelatie.getBijlages().getBijlages().get(0).getOpslaanOmschrijvingOfBestandsNaam().click();
            beherenRelatie.getBijlages().getBijlages().get(0).getOpslaanOmschrijvingOfBestandsNaam().waitUntil(Condition.disappears, AbstractTest.timeOut);

            beherenRelatie.logIsGevuldMet(LOGGER, beherenRelatie.getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam(), nwOmschrijving);
            assertThat(beherenRelatie.getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam().getText(), Is.is(nwOmschrijving));

            beherenRelatie.klikHomeKnop(LOGGER);
            //            dashboard.klikNaarParticulier(LOGGER);
        }
    }

    public void controleerIngevuldeRelatie(Logger LOGGER) {
        beherenRelatie.klikHomeKnop(LOGGER);
        //        dashboard.klikNaarParticulier(LOGGER);

        //Controleren
        lijstRelaties.selecteer(LOGGER, lijstRelaties.zoekGebruiker(LOGGER, relatie.getVoornaam(), false), null);
        beherenRelatie.controleerPagina(LOGGER, relatie, adressen, rekeningNummers, telefoonnummers);

        beherenRelatie.klikHomeKnop(LOGGER);
        //        dashboard.klikNaarParticulier(LOGGER);
    }

    public void testTabbladRelatieGegevens(Logger LOGGER, String voornamen) {
        zoekScherm.klikNieuweRelatie();
        //        dashboard.klikNaarParticulier(LOGGER);

        //        lijstRelaties.isZoekTermAanwezig(LOGGER);
        //        assertNull(lijstRelaties.zoekGebruiker(LOGGER, voornamen, false));
        //        lijstRelaties.klikToevoegenNieuweRelatie(LOGGER);

        //        beherenRelatie.testTakenNietZichtbaar(LOGGER);
        //
        //        testFoutmeldingBijNietsIngevuld(LOGGER, beherenRelatie);
        //        testFoutmeldingAllesIngevuldBehalveDeVerplichteVelden(LOGGER, beherenRelatie);
        //
        //        testFoutmeldingBijOnjuistMailAdres(LOGGER);
        //        testFoutmeldingBijOnjuisteGeboorteDatum(LOGGER);

        relatie = maakRelatie(voornamen);

        beherenRelatie.vulPagina(LOGGER, relatie);
        beherenRelatie.controleerPagina(LOGGER, relatie, adressen, rekeningNummers, telefoonnummers);

        //        voegAdresToe(LOGGER);
        //        voegRekeningnummerToe(LOGGER);
        //        voegTelefoonnummerToe(LOGGER);
        //        voegOpmerkingToeBijRelatie(LOGGER);

        beherenRelatie.klikOpslaan(LOGGER, true);
    }

    private JsonRelatie maakRelatie(String voornamen) {
        JsonRelatie relatie = new JsonRelatie();

        relatie.setVoornaam(voornamen);
        relatie.setRoepnaam(naam());
        relatie.setTussenvoegsel("van");
        relatie.setAchternaam(achternaam());
        relatie.setBsn("103127586");
        relatie.setGeboorteDatum(new LocalDate(1979, 9, 6).toString("YYYY-MM-dd"));
        relatie.setOverlijdensdatum(LocalDate.now().toString("YYYY-MM-dd"));
        relatie.setGeslacht("Man");
        relatie.setBurgerlijkeStaat("Samenwonend");
        relatie.setEmailadres(lorem.getEmail());

        return relatie;
    }

    @TestCase(testcase = Case1)
    private void testFoutmeldingBijOnjuistMailAdres(Logger LOGGER) {
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst(LOGGER, "Vul een correct e-mailadres in.").size(), is(0));
        beherenRelatie.setEmailadres(LOGGER, "aa");
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst(LOGGER, "Vul een correct e-mailadres in.").size(), is(1));
        beherenRelatie.setEmailadres(LOGGER, "aa@bb.cc");
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst(LOGGER, "Vul een correct e-mailadres in.").size(), is(0));

    }

    private void testFoutmeldingBijOnjuisteGeboorteDatum(Logger LOGGER) {
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst(LOGGER, "Juiste invoerformaat is : dd-mm-eejj").size(), is(0));
        beherenRelatie.setGeboorteDatum(LOGGER, "aa");
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst(LOGGER, "Juiste invoerformaat is : dd-mm-eejj").size(), is(1));
        beherenRelatie.setGeboorteDatum(LOGGER, LocalDate.now());
        assertThat(beherenRelatie.zoekFoutmeldingOpTekst(LOGGER, "Juiste invoerformaat is : dd-mm-eejj").size(), is(0));
    }

    protected void testFoutmeldingBijNietsIngevuld(Logger LOGGER, AbstractPagina pagina) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        beherenRelatie.klikOpslaan(LOGGER, false);
        checkValidationMessages(2, pagina, "Dit veld is verplicht.");
    }

    protected void testFoutmeldingAllesIngevuldBehalveDeVerplichteVelden(Logger LOGGER, AbstractPagina pagina) {
        beherenRelatie.vulPagina(LOGGER, "", "Patrick", "van", "", "103127586", new LocalDate(1979, 9, 6), LocalDate.now(), "Man", "Samenwonend", "patrick@heidotting.nl");
        beherenRelatie.klikOpslaan(LOGGER, false);
        checkValidationMessages(2, pagina, "Dit veld is verplicht.");
    }

    protected void voegAdresToe(Logger LOGGER) {
        beherenRelatie.getAdressen().voegAdresToe(LOGGER);
        Adres adres = beherenRelatie.getAdressen().getAdressen().get(0);
        adres.setSoortadres(LOGGER, Adres.SoortAdres.POSTADRES);
        adres.setPostcode(LOGGER, "7891TN");
        adres.setHuisnummer(LOGGER, "24", "7891 TN");
        adres.checkStraatEnPlaatsnaam(LOGGER, "Boogschutter", "KLAZIENAVEEN", "7891 TN");

        JsonAdres jsonAdres = new JsonAdres();
        jsonAdres.setSoortAdres(Adres.SoortAdres.POSTADRES.name());
        jsonAdres.setPostcode("7891 TN");
        jsonAdres.setHuisnummer(24L);
        jsonAdres.setStraat("Boogschutter");
        jsonAdres.setPlaats("KLAZIENAVEEN");

        adressen.add(jsonAdres);
    }

    protected void voegRekeningnummerToe(Logger LOGGER) {
        beherenRelatie.getRekeningnummers().voegRekeningToe(LOGGER);
        Rekeningnummer rekeningnummer = beherenRelatie.getRekeningnummers().getRekeningnummers().get(0);
        rekeningnummer.vulRekeningnummer(LOGGER, "nl96snsb0907007406", "NL96 SNSB 0907 0074 06", null);

        JsonRekeningNummer jsonRekeningNummer = new JsonRekeningNummer();
        jsonRekeningNummer.setRekeningnummer("NL96 SNSB 0907 0074 06");

        rekeningNummers.add(jsonRekeningNummer);
    }

    protected void voegTelefoonnummerToe(Logger LOGGER) {
        String omschrijving = lorem.getWords(15);
        beherenRelatie.getTelefoonnummers().voegTelefoonnummerToe(LOGGER);
        Telefoonnummer telefoonnummer = beherenRelatie.getTelefoonnummers().getTelefoonnummers().get(0);
        telefoonnummer.vulTelefoonnummer(LOGGER, "0621564744", "06 - 21 56 47 44", "Mobiel", omschrijving);

        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();
        jsonTelefoonnummer.setTelefoonnummer("06 - 21 56 47 44");
        jsonTelefoonnummer.setOmschrijving(omschrijving);
        jsonTelefoonnummer.setSoort("Mobiel");

        telefoonnummers.add(jsonTelefoonnummer);
    }

    protected void voegOpmerkingToeBijRelatie(Logger LOGGER) {
        voegOpmerkingToeBijRelatie(LOGGER, 0);
    }

    protected void voegOpmerkingToeBijRelatie(Logger LOGGER, int nummer) {
        beherenRelatie.getOpmerkingen().voegOpmerkingToe(LOGGER);
        Opmerking opmerking = beherenRelatie.getOpmerkingen().getOpmerkingen().get(0);
        String opm = lorem.getWords(5, 10);
        opmerking.vulOpmerking(LOGGER, opm, nummer);
        opmerkingen.add(opm);
    }

    public void controleerOpmerkingenBijRelatie(Logger LOGGER, String voornamen) {
        lijstRelaties.selecteer(LOGGER, lijstRelaties.zoekGebruiker(LOGGER, voornamen, false), null);

        beherenRelatie.getOpslaanRelatie(LOGGER).waitUntil(Condition.appears, AbstractTest.timeOut);
        //        try {
        //            Thread.sleep(timeOut);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }

        List<String> teControlerenOpmerkingen = opmerkingen;

        for (Opmerking opmerking : beherenRelatie.getOpmerkingen().getOpmerkingen()) {
            String opm = getFirst(filter(teControlerenOpmerkingen, new Predicate<String>() {
                @Override
                public boolean apply(@Nullable String s) {
                    return s.equals(opmerking.getOpmerkingAsString());
                }
            }), null);

            LOGGER.debug("{} == {}", opmerking.getOpmerkingAsString(), opm);
            assertFalse(opm == null);
            teControlerenOpmerkingen.remove(opm);
        }

        assertThat(teControlerenOpmerkingen.size(), is(0));

        beherenRelatie.klikHomeKnop(LOGGER);
        //        dashboard.klikNaarParticulier(LOGGER);
    }

    public void voegExtraOpmerkingToeBijRelatie(Logger LOGGER, String voornamen, int aantal) {
        lijstRelaties.selecteer(LOGGER, lijstRelaties.zoekGebruiker(LOGGER, voornamen, false), null);

        for (int i = 0; i < aantal; i++) {
            voegOpmerkingToeBijRelatie(LOGGER, i);
        }

        beherenRelatie.klikOpslaan(LOGGER, true);
    }

    public void checkValidationMessages(int verwachtAantal, AbstractPagina pagina, String... verwachteMelding) {
        List<SelenideElement> validationMessages = pagina.getValidationMessages();

        //        beherenRelatie.getValidationMessages().get(0).waitUntil(Condition.appears, timeOut);
        List<SelenideElement> messagesInBeeld = newArrayList(filter(validationMessages, new Predicate<SelenideElement>() {
            @Override
            public boolean apply(@Nullable SelenideElement element) {
                return element.isDisplayed();
            }
        }));

        assertThat(messagesInBeeld.size(), is(verwachtAantal));

        int j = 0;
        for (int i = 0; i < messagesInBeeld.size(); i++) {
            j = i;
            if (verwachteMelding.length == 1) {
                j = 0;
            }
            assertThat(messagesInBeeld.get(i).getText(), is(verwachteMelding[j]));
        }
    }

    protected void wachtFf() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected String voornaam() {
        return lorem.getNameMale();
    }

    protected String achternaam() {
        return lorem.getLastName();
    }

    protected String naam() {
        return lorem.getName();
    }
}
