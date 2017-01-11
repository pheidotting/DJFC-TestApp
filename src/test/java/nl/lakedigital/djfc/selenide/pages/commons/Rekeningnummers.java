package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
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


public class Rekeningnummers extends AbstractPagina {
    private SelenideElement voegRekeningToe = $(By.id("voegRekeningToe"));
    private List<Rekeningnummer> rekeningnummers = new ArrayList<>();

    public List<Rekeningnummer> getRekeningnummers() {
        return rekeningnummers;
    }

    public void voegRekeningToe(Logger LOGGER) {
        logKlik(LOGGER, this.voegRekeningToe);
        this.voegRekeningToe.click();
        this.rekeningnummers.add(new Rekeningnummer());
    }

    public void controleerRekeningNummers(Logger LOGGER, List<JsonRekeningNummer> rekeningNummers) {
        List<JsonRekeningNummer> teControlerenRekeningNummers = newArrayList(rekeningNummers);

        for (JsonRekeningNummer rekeningNummer : rekeningNummers) {
            List<Rekeningnummer> gevondenNummers = this.rekeningnummers.stream().filter(rekeningnummer -> rekeningnummer.getRekeningnummernummer().replace(" ", "").equalsIgnoreCase(rekeningNummer.getRekeningnummer().replace(" ", ""))).collect(Collectors.toList());

            if (gevondenNummers.size() == 0) {
                fail("Er zijn geen adressen op het scherm te vinden die voldoen aan de criteria");
            }

            Rekeningnummer teControlerenNummer = gevondenNummers.get(0);

            teControlerenNummer.controleerRekeningNummer(LOGGER, rekeningNummer);
            teControlerenRekeningNummers.remove(rekeningNummer);

        }

        assertThat(teControlerenRekeningNummers.size(), is(0));
    }
}
