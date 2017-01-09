package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;

public class Dashboard extends PaginaMetMenuBalk {
    private SelenideElement naarParticulier;
    private SelenideElement naarZakelijk;

    public Dashboard() {
        super();

        naarParticulier = $(By.id("naarParticulier"));
        naarZakelijk = $(By.id("naarZakelijk"));
    }

    public SelenideElement getNaarParticulier() {
        return naarParticulier;
    }

    public SelenideElement getNaarZakelijk() {
        return naarZakelijk;
    }

    public void klikNaarParticulier(Logger LOGGER) {
        this.naarParticulier.waitUntil(Condition.appears, 2500);
        logKlik(LOGGER, this.naarParticulier);
        naarParticulier.click();
    }

    public void klikNaarZakelijk(Logger LOGGER) {
        this.naarZakelijk.waitUntil(Condition.appears, 2500);
        logKlik(LOGGER, this.naarZakelijk);
        naarZakelijk.click();
    }
}
