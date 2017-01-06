package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.TestCaseDJFC;
import nl.lakedigital.djfc.selenide.pages.commons.*;
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
    private final static Logger LOGGER = LoggerFactory.getLogger(BeherenRelatie.class);

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

    private Adressen adressen=new Adressen();
    private Telefoonnummers telefoonnummers=new Telefoonnummers(false);
    private Opmerkingen opmerkingen=new Opmerkingen();
    private Contactpersonen contactpersonen=new Contactpersonen();

    private Lorem lorem= LoremIpsum.getInstance();

    public void klikOpslaan() {
        logKlik(this.opslaanBedrijf, LOGGER);
        this.opslaanBedrijf.click();
    }

    @TestCaseDJFC(DJFC40)
    public void testFoutmeldingBijNietAlleVerplichteVelden() {
        klikOpslaan();

        getValidationMessages().get(0).waitUntil(Condition.appears, 2500);

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
    public void adresToevoegen(){
        adressen.voegAdresToe();
        Adres adres = adressen.getAdressen().get(0);
        adres.setSoortadres(Adres.SoortAdres.POSTADRES);
        adres.setPostcode("7891TN");
        adres.setHuisnummer("24", "7891 TN");
        adres.checkStraatEnPlaatsnaam("Boogschutter", "KLAZIENAVEEN", "7891 TN");
    }


    @TestCaseDJFC(DJFC47)
    public void voegTelefoonnummerToe() {
        telefoonnummers.voegTelefoonnummerToe();
        Telefoonnummer telefoonnummer = telefoonnummers.getTelefoonnummers().get(0);
        telefoonnummer.vulTelefoonnummer("0621564744", "06 - 21 56 47 44", "Mobiel", lorem.getWords(15));
    }

    @TestCaseDJFC(DJFC49)
    public void voegOpmerkingToeBijRelatie() {
        voegOpmerkingToeBijRelatie(0);
    }

    @TestCaseDJFC(DJFC49)
    public void voegOpmerkingToeBijRelatie(int nummer) {
        opmerkingen.voegOpmerkingToe();
        Opmerking opmerking = opmerkingen.getOpmerkingen().get(0);
        String opm = lorem.getWords(50);
        opmerking.vulOpmerking(opm, nummer);
//        opmerkingen.add(opm);
    }

    @TestCaseDJFC(DJFC48)
    public void voegContactpersoonToe(){
        contactpersonen.voegContactpersoonToe();
        Contactpersoon contactpersoon=contactpersonen.getContactpersonen().get(0);

        contactpersoon.vulContactpersoon(lorem.getFirstNameFemale(),null,lorem.getLastName(),lorem.getEmail(),lorem.getWords(3));

        contactpersoon.getTelefoonnummers().voegTelefoonnummerToe();
        Telefoonnummer telefoonnummer=contactpersoon.getTelefoonnummers().getTelefoonnummers().get(0);
        telefoonnummer.vulTelefoonnummer("0621564744", "06 - 21 56 47 44", "Mobiel", lorem.getWords(15));
    }

    public void setNaam(String naam) {
        logInvullen(this.naam, naam, LOGGER);
        this.naam.setValue(naam);
    }

    public void setKvk(String kvk) {
        logInvullen(this.kvk, kvk, LOGGER);
        this.kvk.setValue(kvk);
    }

    public void setRechtsvorm(String rechtsvorm) {
        logInvullen(this.rechtsvorm, rechtsvorm, LOGGER);
        this.rechtsvorm.setValue(rechtsvorm);
    }

    public void setEmail(String email) {
        logInvullen(this.email, email, LOGGER);
        this.email.setValue(email);
    }

    public void setInternetadres(String internetadres) {
        logInvullen(this.internetadres, internetadres, LOGGER);
        this.internetadres.setValue(internetadres);
    }

    public void setHoedanigheid(String hoedanigheid) {
        logInvullen(this.hoedanigheid, hoedanigheid, LOGGER);
        this.hoedanigheid.setValue(hoedanigheid);
    }

    public void setcAoVerplichtingen(String cAoVerplichtingen) {
        logInvullen(this.cAoVerplichtingen, cAoVerplichtingen, LOGGER);
        this.cAoVerplichtingen.setValue(cAoVerplichtingen);
    }

    public SelenideElement getOpslaanBedrijf() {
        return opslaanBedrijf;
    }
}
