package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.common.base.Predicate;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertTrue;

public class LijstBedrijven extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(LijstBedrijven.class);

    private SelenideElement zoekTerm = $(By.id("zoekTerm"));
    private SelenideElement zoeken = $(By.id("zoeken"));
    private SelenideElement toevoegenNieuwBedrijf = $(By.id("toevoegenNieuwBedrijf"));

    private SelenideElement gezochtMetTonen = $(By.id("gezochtMetTonen"));

    private ElementsCollection naam = $$(By.name("naam"));

    public void isZoekTermAanwezig() {
        zoekTerm.waitUntil(Condition.appears, 2500);
        logIsAanwezig(LOGGER, zoekTerm);
        assertTrue(zoekTerm.isDisplayed());
    }

    public void vulZoekTerm(String zoekTerm, boolean enter) {
        logInvullen(LOGGER, this.zoekTerm, zoekTerm);
        this.zoekTerm.setValue(zoekTerm);
        if (enter) {
            logKlik(LOGGER, this.zoekTerm);
            this.zoekTerm.sendKeys(Keys.ENTER);
        } else {
            logKlik(LOGGER, this.zoeken);
            this.zoeken.click();
        }
        this.gezochtMetTonen.waitUntil(Condition.appears, 1500);
        wachtFf();
    }

    public void klikToevoegenNieuwBedrijf() {
        logKlik(LOGGER, toevoegenNieuwBedrijf);
        toevoegenNieuwBedrijf.waitUntil(Condition.appears, 25000);
        toevoegenNieuwBedrijf.click();
    }

    public SelenideElement zoekBedrijf(String zoekterm, boolean recursief) {
        vulZoekTerm(zoekterm, true);

        List<SelenideElement> gevondenItems = newArrayList(filter(naam, new Predicate<SelenideElement>() {
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
                return zoekBedrijf(zoekterm, true);
            }
        }
    }

    public void selecteer(SelenideElement regel, SelenideElement teVerschijnenElement) {
        logKlik(LOGGER, regel);
        regel.click();
        this.gezochtMetTonen.waitUntil(Condition.disappears, 2500);
        if (teVerschijnenElement != null) {
            teVerschijnenElement.waitUntil(Condition.appears, 2500);
        }
    }

    public void zoekEnSelecteer(String zoekterm, SelenideElement teVerschijnenElement) {
        selecteer(zoekBedrijf(zoekterm, false), teVerschijnenElement);
    }
}
