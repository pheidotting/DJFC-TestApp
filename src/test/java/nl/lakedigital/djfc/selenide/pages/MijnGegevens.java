package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;

public class MijnGegevens extends AbstractPagina {
    private SelenideElement voornaam = $(By.id("voornaam"));
    private SelenideElement tussenvoegsel = $(By.id("tussenvoegsel"));
    private SelenideElement achternaam = $(By.id("achternaam"));
    private SelenideElement emailadres = $(By.id("emailadres"));
    private SelenideElement nieuwWachtwoord = $(By.id("nieuw-wachtwoord"));
    private SelenideElement wachtwoordNogmaals = $(By.id("wachtwoord-nogmaals"));
    private SelenideElement opslaan = $(By.id("opslaan"));

    private SelenideElement wachtwoordSterkteMelding = $(By.id("wachtwoordSterkteMelding"));
    private SelenideElement wachtwoordenKomNietOvereenMelding = $(By.id("wachtwoordenKomNietOvereenMelding"));

    public SelenideElement getOpslaan() {
        return opslaan;
    }

    public void vulGegevens(Logger LOGGER, String voornaam, String tussenvoegsel, String achternaam, String emailadres, String nieuwWachtwoord, String wachtwoordNogmaals, boolean opslaan) {
        if (voornaam != null) {
            logInvullen(LOGGER, this.voornaam, voornaam);
            this.voornaam.setValue(voornaam);
        }
        if (tussenvoegsel != null) {
            logInvullen(LOGGER, this.tussenvoegsel, tussenvoegsel);
            this.tussenvoegsel.setValue(tussenvoegsel);
        }
        if (achternaam != null) {
            logInvullen(LOGGER, this.achternaam, achternaam);
            this.achternaam.setValue(achternaam);
        }
        if (emailadres != null) {
            logInvullen(LOGGER, this.emailadres, emailadres);
            this.emailadres.setValue(emailadres);
        }

        vulWachtwoord(LOGGER, nieuwWachtwoord);
        vulWachtwoordNogmaals(LOGGER, wachtwoordNogmaals);

        if (opslaan) {
            logKlik(LOGGER, this.opslaan);
            this.opslaan.click();
        }
    }

    public String getMeldingWachtwoordSterkte() {
        try {
            this.wachtwoordSterkteMelding.waitUntil(Condition.appears, 2500);
            return this.wachtwoordSterkteMelding.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public void vulWachtwoord(Logger LOGGER, String nieuwWachtwoord) {
        if (nieuwWachtwoord != null) {
            logInvullen(LOGGER, this.nieuwWachtwoord, nieuwWachtwoord);
            this.nieuwWachtwoord.setValue(nieuwWachtwoord);
            this.nieuwWachtwoord.sendKeys(Keys.TAB);
        }
    }

    public void vulWachtwoordNogmaals(Logger LOGGER, String wachtwoordNogmaals) {
        if (wachtwoordNogmaals != null) {
            logInvullen(LOGGER, this.wachtwoordNogmaals, wachtwoordNogmaals);
            this.wachtwoordNogmaals.setValue(wachtwoordNogmaals);
            this.wachtwoordNogmaals.sendKeys(Keys.TAB);
        }
    }

    public void assertWachtwoordenKomNietOvereenMeldingZichtbaar(Logger LOGGER) {
        logIsAanwezig(LOGGER, this.wachtwoordenKomNietOvereenMelding);
        this.wachtwoordenKomNietOvereenMelding.waitUntil(Condition.appears, 2500);
    }

    public void assertWachtwoordenKomNietOvereenMeldingOnzichtbaar(Logger LOGGER) {
        logIsNietAanwezig(LOGGER, this.wachtwoordenKomNietOvereenMelding);
        this.wachtwoordenKomNietOvereenMelding.waitUntil(Condition.disappears, 2500);
    }

    public void assertOpslaanKnopEnabled(Logger LOGGER) {
        logIsAanwezig(LOGGER, this.opslaan);
        this.opslaan.waitUntil(Condition.enabled, 2500);
    }

    public void assertOpslaanKnopDIsabled(Logger LOGGER) {
        logIsNietAanwezig(LOGGER, this.opslaan);
        this.opslaan.waitUntil(Condition.disabled, 2500);
    }

}
