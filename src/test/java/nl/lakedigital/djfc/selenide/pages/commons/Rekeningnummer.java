package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class Rekeningnummer extends AbstractPagina {
    private SelenideElement

            rekeningnummernummer;
    private SelenideElement

            bic;
    private SelenideElement verwijderRekening;

    public Rekeningnummer() {
        this.rekeningnummernummer = $(By.id("rekeningnummernummer"));
        this.bic = $(By.id("bic"));
        this.verwijderRekening = $(By.id("verwijderRekening"));
    }

    public void vulRekeningnummer(Logger LOGGER, String nummer, String nummerLang, String bic) {
        if (nummer != null) {
            logInvullen(LOGGER, this.rekeningnummernummer, nummer);
            this.rekeningnummernummer.setValue(nummer);
            this.rekeningnummernummer.sendKeys(Keys.TAB);
        }
        if (bic != null) {
            logInvullen(LOGGER, this.bic, bic);
            this.bic.setValue(bic);
        }
        if (nummerLang != null) {
            logIsGevuldMet(LOGGER, this.rekeningnummernummer, nummerLang);
            assertThat(this.rekeningnummernummer.getValue(), is(nummerLang));
        }
    }

    public void verwijderRekening(Logger LOGGER) {
        logKlik(LOGGER, this.verwijderRekening);
        this.verwijderRekening.click();
    }
}
