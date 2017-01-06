package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Adres extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(Adres.class);

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

    public void setSoortadres(SoortAdres soortadres) {
        logInvullen(this.soortadres, soortadres.name(), LOGGER);
        this.soortadres.setValue(soortadres.name());
    }

    public void setPostcode(String postcodeKort) {
        logInvullen(this.postcode, postcodeKort, LOGGER);
        this.postcode.setValue(postcodeKort);
        this.postcode.sendKeys(Keys.TAB);
    }

    public void setHuisnummer(String huisnummer, String postcodeLang) {
        logInvullen(this.huisnummer, huisnummer, LOGGER);
        this.huisnummer.setValue(huisnummer);
        this.huisnummer.sendKeys(Keys.TAB);
        this.postcode.waitUntil(Condition.value(postcodeLang), 3000);
    }

    public void checkStraatEnPlaatsnaam(String straat, String plaatsnaam, String postcodeLang) {
        logIsGevuldMet(this.postcode, postcodeLang, LOGGER);
        assertThat(this.postcode.getValue(), is(postcodeLang));
        logIsGevuldMet(this.straat, straat, LOGGER);
        assertThat(this.straat.getValue(), is(straat));
        logIsGevuldMet(this.plaats, plaatsnaam, LOGGER);
        assertThat(this.plaats.getValue(), is(plaatsnaam));
    }

}
