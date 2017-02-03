package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.TestCaseDJFC;
import nl.lakedigital.djfc.selenide.pages.commons.*;
import nl.lakedigital.djfc.tests.AbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static nl.lakedigital.djfc.TestCaseDJFC.Case.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BeherenBedrijf extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(BeherenBedrijf.class);

    private SelenideElement naam = $(By.id("naam"));
    private SelenideElement kvk = $(By.id("kvk"));
    private SelenideElement rechtsvorm = $(By.id("rechtsvorm"));
    private SelenideElement email = $(By.id("email"));
    private SelenideElement internetadres = $(By.id("internetadres"));
    private SelenideElement hoedanigheid = $(By.id("hoedanigheid"));
    private SelenideElement cAoVerplichtingen = $(By.id("cAoVerplichtingen"));

    private SelenideElement opslaanBedrijf = $(By.id("opslaanBedrijf"));
    private SelenideElement annuleren = $(By.id("annuleren"));
    private ElementsCollection validationMessages = $$(By.className("validationMessage"));

    private Adressen adressen = new Adressen();
    private Telefoonnummers telefoonnummers = new Telefoonnummers(false);
    private Opmerkingen opmerkingen = new Opmerkingen();
    private Contactpersonen contactpersonen = new Contactpersonen();

    private Lorem lorem = LoremIpsum.getInstance();

    public void klikOpslaan() {
        logKlik(LOGGER, this.opslaanBedrijf);
        this.opslaanBedrijf.click();
    }

    @TestCaseDJFC(DJFC40)
    public void testFoutmeldingBijNietAlleVerplichteVelden() {
        klikOpslaan();

        getValidationMessages().get(0).waitUntil(Condition.appears, AbstractTest.timeOut);

        assertThat(getValidationMessages().size(), is(1));
    }

    @TestCaseDJFC(DJFC41)
    public void blabla2() {

    }

    @TestCaseDJFC(DJFC42)
    public void testFoutmeldingWegBijAlleVerplichteVeldenGevuld(String naam) {
        setNaam(naam);
        this.naam.sendKeys(Keys.TAB);

        assertThat(getValidationMessages().size(), is(0));
    }

    public void invullenAlLeVelden(String naam, String kvk, String rechtsvorm, String email, String internetadres, String hoedanigheid, String cAoVerplichtingen) {
        if (naam != null) {
            setNaam(naam);
        }
        if (kvk != null) {
            setKvk(kvk);
        }
        if (rechtsvorm != null) {
            setRechtsvorm(rechtsvorm);
        }
        if (email != null) {
            setEmail(email);
        }
        if (internetadres != null) {
            setInternetadres(internetadres);
        }
        if (hoedanigheid != null) {
            setHoedanigheid(hoedanigheid);
        }
        if (cAoVerplichtingen != null) {
            setcAoVerplichtingen(cAoVerplichtingen);
        }
    }

    @TestCaseDJFC(DJFC41)
    public void adresToevoegen() {
        adressen.voegAdresToe(LOGGER);
        Adres adres = adressen.getAdressen().get(0);
        adres.setSoortadres(LOGGER, Adres.SoortAdres.POSTADRES);
        adres.setPostcode(LOGGER, "7891TN");
        adres.setHuisnummer(LOGGER, "24", "7891 TN");
        adres.checkStraatEnPlaatsnaam(LOGGER, "Boogschutter", "KLAZIENAVEEN", "7891 TN");
    }


    @TestCaseDJFC(DJFC47)
    public void voegTelefoonnummerToe() {
        telefoonnummers.voegTelefoonnummerToe(LOGGER);
        Telefoonnummer telefoonnummer = telefoonnummers.getTelefoonnummers().get(0);
        telefoonnummer.vulTelefoonnummer(LOGGER, "0621564744", "06 - 21 56 47 44", "Mobiel", lorem.getWords(15));
    }

    @TestCaseDJFC(DJFC49)
    public void voegOpmerkingToeBijRelatie() {
        voegOpmerkingToeBijRelatie(0);
    }

    @TestCaseDJFC(DJFC49)
    public void voegOpmerkingToeBijRelatie(int nummer) {
        opmerkingen.voegOpmerkingToe(LOGGER);
        Opmerking opmerking = opmerkingen.getOpmerkingen().get(0);
        String opm = lorem.getWords(50);
        opmerking.vulOpmerking(LOGGER, opm, nummer);
        //        opmerkingen.add(opm);
    }

    @TestCaseDJFC(DJFC48)
    public void voegContactpersoonToe(Logger LOGGER) {
        contactpersonen.voegContactpersoonToe(LOGGER);
        Contactpersoon contactpersoon = contactpersonen.getContactpersonen().get(0);

        contactpersoon.vulContactpersoon(LOGGER, lorem.getFirstNameFemale(), null, lorem.getLastName(), lorem.getEmail(), lorem.getWords(3));

        contactpersoon.getTelefoonnummers().voegTelefoonnummerToe(LOGGER);
        Telefoonnummer telefoonnummer = contactpersoon.getTelefoonnummers().getTelefoonnummers().get(0);
        telefoonnummer.vulTelefoonnummer(LOGGER, "0621564744", "06 - 21 56 47 44", "Mobiel", lorem.getWords(15));
    }

    public void setNaam(String naam) {
        logInvullen(LOGGER, this.naam, naam);
        this.naam.setValue(naam);
    }

    public void setKvk(String kvk) {
        logInvullen(LOGGER, this.kvk, kvk);
        this.kvk.setValue(kvk);
    }

    public void setRechtsvorm(String rechtsvorm) {
        logInvullen(LOGGER, this.rechtsvorm, rechtsvorm);
        this.rechtsvorm.setValue(rechtsvorm);
    }

    public void setEmail(String email) {
        logInvullen(LOGGER, this.email, email);
        this.email.setValue(email);
    }

    public void setInternetadres(String internetadres) {
        logInvullen(LOGGER, this.internetadres, internetadres);
        this.internetadres.setValue(internetadres);
    }

    public void setHoedanigheid(String hoedanigheid) {
        logInvullen(LOGGER, this.hoedanigheid, hoedanigheid);
        this.hoedanigheid.setValue(hoedanigheid);
    }

    public void setcAoVerplichtingen(String cAoVerplichtingen) {
        logInvullen(LOGGER, this.cAoVerplichtingen, cAoVerplichtingen);
        this.cAoVerplichtingen.setValue(cAoVerplichtingen);
    }

    public SelenideElement getOpslaanBedrijf() {
        return opslaanBedrijf;
    }
}
