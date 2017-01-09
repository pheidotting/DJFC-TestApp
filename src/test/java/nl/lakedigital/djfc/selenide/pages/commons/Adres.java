package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Adres extends AbstractPagina {
    public enum SoortAdres {
        WOONADRES, POSTADRES, RISICOADRES, FACTUURADRES;
    }

    private SelenideElement soortadres;
    private SelenideElement verwijderAdres;
    private SelenideElement postcode;
    private SelenideElement huisnummer;
    private SelenideElement toevoeging;
    private SelenideElement straat;
    private SelenideElement plaats;

    public Adres() {
        soortadres = $(By.name("soortadres"));
        verwijderAdres = $(By.name("verwijderAdres"));
        postcode = $(By.name("postcode"));
        huisnummer = $(By.name("huisnummer"));
        toevoeging = $(By.name("toevoeging"));
        straat = $(By.name("straat"));
        plaats = $(By.name("plaats"));
    }

    public void setSoortadres(Logger LOGGER, SoortAdres soortadres) {
        logInvullen(LOGGER, this.soortadres, soortadres.name());
        this.soortadres.setValue(soortadres.name());
    }

    public void setPostcode(Logger LOGGER, String postcodeKort) {
        logInvullen(LOGGER, this.postcode, postcodeKort);
        this.postcode.setValue(postcodeKort);
        this.postcode.sendKeys(Keys.TAB);
    }

    public void setHuisnummer(Logger LOGGER, String huisnummer, String postcodeLang) {
        logInvullen(LOGGER, this.huisnummer, huisnummer);
        this.huisnummer.setValue(huisnummer);
        this.huisnummer.sendKeys(Keys.TAB);
        this.postcode.waitUntil(Condition.value(postcodeLang), 3000);
    }

    public void checkStraatEnPlaatsnaam(Logger LOGGER, String straat, String plaatsnaam, String postcodeLang) {
        logIsGevuldMet(LOGGER, this.postcode, postcodeLang);
        assertThat(this.postcode.getValue(), is(postcodeLang));
        logIsGevuldMet(LOGGER, this.straat, straat);
        assertThat(this.straat.getValue(), is(straat));
        logIsGevuldMet(LOGGER, this.plaats, plaatsnaam);
        assertThat(this.plaats.getValue(), is(plaatsnaam));
    }

}
