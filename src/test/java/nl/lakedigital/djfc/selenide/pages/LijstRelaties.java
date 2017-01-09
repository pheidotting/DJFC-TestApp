package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.google.common.base.Predicate;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertTrue;

public class LijstRelaties extends AbstractPagina {
    private SelenideElement zoekTerm = $(By.id("zoekTerm"));
    private SelenideElement zoeken = $(By.id("zoeken"));
    private SelenideElement toevoegenNieuweRelatie = $(By.id("toevoegenNieuweRelatie"));

    private SelenideElement gezochtMetTonen = $(By.id("gezochtMetTonen"));

    private List<SelenideElement> voornaam = $$(By.name("voornaam"));

    public void isZoekTermAanwezig(Logger LOGGER) {
        zoekTerm.waitUntil(Condition.appears, 2500);
        logIsAanwezig(LOGGER, zoekTerm);
        assertTrue(zoekTerm.isDisplayed());
    }

    public void vulZoekTerm(Logger LOGGER, String zoekTerm, boolean enter) {
        logInvullen(LOGGER, this.zoekTerm, zoekTerm);
        this.zoekTerm.setValue(zoekTerm);
        if (enter) {
            logKlik(LOGGER, this.zoekTerm);
            this.zoekTerm.sendKeys(Keys.ENTER);
        } else {
            logKlik(LOGGER, this.zoeken);
            this.zoeken.click();
        }
        this.gezochtMetTonen.waitUntil(Condition.appears, 5000);
        wachtFf();
    }

    public void klikToevoegenNieuweRelatie(Logger LOGGER) {
        logKlik(LOGGER, toevoegenNieuweRelatie);
        toevoegenNieuweRelatie.click();
    }

    public SelenideElement zoekGebruiker(Logger LOGGER, String zoekterm, boolean recursief) {
        vulZoekTerm(LOGGER, zoekterm, true);

        List<SelenideElement> gevondenItems = newArrayList(filter(voornaam, new Predicate<SelenideElement>() {
            @Override
            public boolean apply(@Nullable SelenideElement element) {
                return element.getText().equals(zoekterm);
            }
        }));

        if (gevondenItems.size() > 0) {
            return gevondenItems.get(0);
        } else {
            if (recursief) {
                return null;
            } else {
                wachtFf();
                return zoekGebruiker(LOGGER, zoekterm, true);
            }
        }
    }

    public void selecteer(Logger LOGGER, SelenideElement regel, SelenideElement teVerschijnenElement) {
        logKlik(LOGGER, regel);
        regel.click();
        this.gezochtMetTonen.waitUntil(Condition.disappears, 2500);
        if (teVerschijnenElement != null) {
            teVerschijnenElement.waitUntil(Condition.appears, 2500);
        }
    }
}
