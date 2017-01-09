package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.TestCaseDJFC;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static nl.lakedigital.djfc.TestCaseDJFC.Case.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class LoginPagina extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginPagina.class);

    private SelenideElement identificatie;
    private SelenideElement wachtwoord;
    private SelenideElement button;

    public LoginPagina() {
        this.identificatie = $(By.id("identificatie"));
        this.wachtwoord = $(By.id("wachtwoord"));
        this.button = $(By.id("inlogButton"));
    }

    public void setIdentificatie(String identificatie) {
        logInvullen(this.identificatie, identificatie, LOGGER);
        this.identificatie.setValue(identificatie);
    }

    public void setWachtwoord(String wachtwoord) {
        logInvullen(this.wachtwoord, wachtwoord, LOGGER);
        this.wachtwoord.setValue(wachtwoord);
    }

    public void isInlogButtonAanwezig() {
        logIsAanwezig(button, LOGGER);
        this.button.waitUntil(Condition.appears, 2500);
    }

    public void clickButton(boolean klik, boolean disappear) {
        logKlik(this.button, LOGGER);
        if (klik) {
            this.button.click();
        } else {
            this.button.sendKeys(Keys.ENTER);
        }
        if (disappear && this.button.isDisplayed()) {
            this.button.waitUntil(Condition.disappears, 1500);
        }
    }

    @TestCaseDJFC(DJFC54)
    public void inloggen(String identificatie, String wachtwoord, SelenideElement wachtenOp) {
        LOGGER.info("invullen inlog informatie");
        setIdentificatie(identificatie);
        setWachtwoord(wachtwoord);
        clickButton(false, true);
        if (wachtenOp != null) {
            wachtenOp.waitUntil(Condition.appears, 2500);
        }
    }

    @TestCaseDJFC(DJFC54)
    public void inloggen(String identificatie, String wachtwoord) {
        inloggen(identificatie, wachtwoord, null);
    }

    @TestCaseDJFC(DJFC52)
    public void testOngeldigeLogin() {
        setIdentificatie("bestaatniet");
        setWachtwoord("onzin");
        clickButton(false, false);
        getAlertDanger().waitUntil(Condition.appears, 5000);
        assertThat(getAlertDanger().getText(), is("Er is een fout opgetreden : De ingevoerde gebruikersnaam werd niet gevonden"));
    }

    @TestCaseDJFC(DJFC53)
    public void testOngeldigWachtwoord(String identificatie) {
        setIdentificatie(identificatie);
        setWachtwoord("ongeldigwachtwoord");
        clickButton(false, false);
        getAlertDanger().waitUntil(Condition.appears, 5000);
        assertThat(getAlertDanger().getText(), is("Er is een fout opgetreden : Het ingevoerde wachtwoord is onjuist"));
    }

}
