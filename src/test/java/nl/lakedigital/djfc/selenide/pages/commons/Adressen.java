package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;


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
}
