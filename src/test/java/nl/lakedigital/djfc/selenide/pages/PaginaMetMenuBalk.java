package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public abstract class PaginaMetMenuBalk extends AbstractPagina {
    private SelenideElement ingelogdeGebruiker;
    private SelenideElement uitloggen;

    public PaginaMetMenuBalk() {
        ingelogdeGebruiker = $(By.id("ingelogdeGebruiker"));
        uitloggen = $(By.id("uitloggen"));
    }

    public void testIngelogdeGebruiker(Logger LOGGER, String gebruiker, String kantoor) {
        ingelogdeGebruiker.waitUntil(Condition.appears, 2500);
        String verwachteTekst = "Ingelogd als : " + gebruiker + ", (" + kantoor + ")";
        logIsGevuldMet(LOGGER, ingelogdeGebruiker, verwachteTekst);
        assertThat(ingelogdeGebruiker.getText(), is(verwachteTekst));
        assertTrue(uitloggen.isDisplayed());
    }

    public void wachtUitloggenAanwezig(Logger LOGGER) {
        logIsAanwezig(LOGGER, uitloggen);
        uitloggen.waitUntil(Condition.appears, 2500);
    }

    public void klikUitloggen(Logger LOGGER) {
        logKlik(LOGGER, uitloggen);
        uitloggen.click();
    }
}
