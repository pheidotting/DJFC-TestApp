package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;


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
}
