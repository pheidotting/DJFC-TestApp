package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.tests.AbstractTest;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;

public class Dashboard extends PaginaMetMenuBalk {
    private SelenideElement naarParticulier = $(By.id("naarParticulier"));
    private SelenideElement naarZakelijk = $(By.id("naarZakelijk"));
    private SelenideElement naarBeheer = $(By.id("naarBeheer"));

    public SelenideElement getNaarParticulier() {
        return naarParticulier;
    }

    public SelenideElement getNaarZakelijk() {
        return naarZakelijk;
    }

    public SelenideElement getNaarBeheer() {
        return naarBeheer;
    }

    public void klikNaarParticulier(Logger LOGGER) {
        this.naarParticulier.waitUntil(Condition.appears, AbstractTest.timeOut);
        logKlik(LOGGER, this.naarParticulier);
        naarParticulier.click();
    }

    public void klikNaarZakelijk(Logger LOGGER) {
        this.naarZakelijk.waitUntil(Condition.appears, AbstractTest.timeOut);
        logKlik(LOGGER, this.naarZakelijk);
        naarZakelijk.click();
    }

    public void klikNaarBeheer(Logger LOGGER) {
        this.naarBeheer.waitUntil(Condition.appears, AbstractTest.timeOut);
        logKlik(LOGGER, this.naarBeheer);
        naarBeheer.click();
    }
}
