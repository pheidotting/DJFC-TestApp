package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.TestCaseDJFC;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import nl.lakedigital.djfc.tests.AbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;
import static nl.lakedigital.djfc.TestCaseDJFC.Case.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class LoginPagina extends AbstractPagina {
    private SelenideElement identificatie = $(By.id("identificatie"));
    private SelenideElement wachtwoord = $(By.id("wachtwoord"));
    private SelenideElement button = $(By.id("inlogButton"));

    public void setIdentificatie(Logger LOGGER, String identificatie) {
        logInvullen(LOGGER, this.identificatie, identificatie);
        this.identificatie.setValue(identificatie);
    }

    public void setWachtwoord(Logger LOGGER, String wachtwoord) {
        logInvullen(LOGGER, this.wachtwoord, wachtwoord);
        this.wachtwoord.setValue(wachtwoord);
    }

    public void isInlogButtonAanwezig(Logger LOGGER) {
        logIsAanwezig(LOGGER, button);
        this.button.waitUntil(Condition.appears, AbstractTest.timeOut);
    }

    public void clickButton(Logger LOGGER, boolean klik, boolean disappear) {
        logKlik(LOGGER, this.button);
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
    public void inloggen(Logger LOGGER, String identificatie, String wachtwoord, SelenideElement wachtenOp) {
        LOGGER.info("invullen inlog informatie");
        setIdentificatie(LOGGER, identificatie);
        setWachtwoord(LOGGER, wachtwoord);
        clickButton(LOGGER, false, true);
        if (wachtenOp != null) {
            wachtenOp.waitUntil(Condition.appears, AbstractTest.timeOut);
        }
    }

    @TestCaseDJFC(DJFC54)
    public void inloggen(Logger LOGGER, String identificatie, String wachtwoord) {
        inloggen(LOGGER, identificatie, wachtwoord, null);
    }

    @TestCaseDJFC(DJFC52)
    public void testOngeldigeLogin(Logger LOGGER) {
        setIdentificatie(LOGGER, "bestaatniet");
        setWachtwoord(LOGGER, "onzin");
        clickButton(LOGGER, false, false);
        getAlertDanger().waitUntil(Condition.appears, 5000);
        assertThat(getAlertDanger().getText(), is("Er is een fout opgetreden : De ingevoerde gebruikersnaam werd niet gevonden"));
    }

    @TestCaseDJFC(DJFC53)
    public void testOngeldigWachtwoord(Logger LOGGER, String identificatie) {
        setIdentificatie(LOGGER, identificatie);
        setWachtwoord(LOGGER, "ongeldigwachtwoord");
        clickButton(LOGGER, false, false);
        getAlertDanger().waitUntil(Condition.appears, 5000);
        assertThat(getAlertDanger().getText(), is("Er is een fout opgetreden : Het ingevoerde wachtwoord is onjuist"));
    }

}
