package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class Dashboard extends PaginaMetMenuBalk {
    private final static Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

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

    public void klikNaarParticulier() {
        this.naarParticulier.waitUntil(Condition.appears, 2500);
        logKlik(this.naarParticulier, LOGGER);
        naarParticulier.click();
    }

    public void klikNaarZakelijk() {
        this.naarZakelijk.waitUntil(Condition.appears, 2500);
        logKlik(this.naarZakelijk, LOGGER);
        naarZakelijk.click();
    }
}
