package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public abstract class PaginaMetMenuBalk extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(PaginaMetMenuBalk.class);

    private SelenideElement ingelogdeGebruiker;
    private SelenideElement uitloggen;

    public PaginaMetMenuBalk() {
        ingelogdeGebruiker = $(By.id("ingelogdeGebruiker"));
        uitloggen = $(By.id("uitloggen"));
    }

    public void testIngelogdeGebruiker(String gebruiker, String kantoor) {
        ingelogdeGebruiker.waitUntil(Condition.appears, 2500);
        String verwachteTekst = "Ingelogd als : " + gebruiker + ", (" + kantoor + ")";
        logIsGevuldMet(ingelogdeGebruiker, verwachteTekst, LOGGER);
        assertThat(ingelogdeGebruiker.getText(), is(verwachteTekst));
        assertTrue(uitloggen.isDisplayed());
    }
}
