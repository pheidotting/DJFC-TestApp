package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
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

public class LijstRelaties extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(LijstRelaties.class);

    private SelenideElement zoekTerm;
    private SelenideElement zoeken;
    private SelenideElement toevoegenNieuweRelatie;

    private SelenideElement gezochtMetTonen;

    private List<SelenideElement> voornaam;

    public LijstRelaties() {
        zoekTerm = $(By.id("zoekTerm"));
        zoeken = $(By.id("zoeken"));
        toevoegenNieuweRelatie = $(By.id("toevoegenNieuweRelatie"));

        gezochtMetTonen = $(By.id("gezochtMetTonen"));

        voornaam = $$(By.name("voornaam"));
    }

    public void isZoekTermAanwezig() {
        zoekTerm.waitUntil(Condition.appears, 2500);
        logIsAanwezig(zoekTerm, LOGGER);
        assertTrue(zoekTerm.isDisplayed());
    }

    public void vulZoekTerm(String zoekTerm, boolean enter) {
        logInvullen(this.zoekTerm, zoekTerm, LOGGER);
        this.zoekTerm.setValue(zoekTerm);
        if (enter) {
            logKlik(this.zoekTerm, LOGGER);
            this.zoekTerm.sendKeys(Keys.ENTER);
        } else {
            logKlik(this.zoeken, LOGGER);
            this.zoeken.click();
        }
        this.gezochtMetTonen.waitUntil(Condition.appears, 5000);
        wachtFf();
    }

    public void klikToevoegenNieuweRelatie() {
        logKlik(toevoegenNieuweRelatie, LOGGER);
        toevoegenNieuweRelatie.click();
    }

    public SelenideElement zoekGebruiker(String zoekterm, boolean recursief) {
        vulZoekTerm(zoekterm, true);

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
                return zoekGebruiker(zoekterm, true);
            }
        }
    }

    public void selecteer(SelenideElement regel, SelenideElement teVerschijnenElement) {
        logKlik(regel, LOGGER);
        regel.click();
        this.gezochtMetTonen.waitUntil(Condition.disappears, 2500);
        if(teVerschijnenElement!=null) {
            teVerschijnenElement.waitUntil(Condition.appears,2500);
        }
    }
}
