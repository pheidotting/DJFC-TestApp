package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


public class Adressen extends AbstractPagina {
    private SelenideElement voegAdresToe;
    private List<Adres> adressen;

    public Adressen() {
        voegAdresToe = $(By.id("voegAdresToe"));
        adressen = new ArrayList<>();
    }

    public List<Adres> getAdressen() {
        return adressen;
    }

    public void voegAdresToe(Logger LOGGER) {
        logKlik(LOGGER, this.voegAdresToe);
        voegAdresToe.click();
        adressen.add(new Adres());
        wachtFf();
    }

    public void controleerAdressen(Logger LOGGER, List<JsonAdres> adressen) {
        List<JsonAdres> teControlerenAdressen = newArrayList(adressen);

        for (JsonAdres adres : adressen) {

            List<Adres> gevondenAdressen = this.adressen.stream().filter(a -> a.getStraat().equalsIgnoreCase(adres.getStraat()) && a.getPlaats().equalsIgnoreCase(adres.getPlaats())).collect(Collectors.toList());

            if (gevondenAdressen.size() == 0) {
                fail("Er zijn geen adressen op het scherm te vinden die voldoen aan de criteria");
            }

            Adres teControlerenAdres = gevondenAdressen.get(0);

            teControlerenAdres.controleerAdres(LOGGER, adres);
            teControlerenAdressen.remove(adres);
        }

        assertThat(teControlerenAdressen.size(), is(0));
    }
}
