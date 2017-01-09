package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Opmerking extends AbstractPagina {
    private SelenideElement verwijder = $(By.id("verwijder"));
    private SelenideElement verwijderNieuweOpmerking = $(By.id("verwijderNieuweOpmerking"));
    private ElementsCollection nieuweOpmerking = $$(By.name("nieuweOpmerking"));
    private ElementsCollection opmerking = $$(By.name("wateenrareidisdit"));
    private int i;

    public Opmerking(int i) {
        this.i = i;
    }

    public void vulOpmerking(Logger LOGGER, String opmerking, int welke) {
        logInvullen(LOGGER, this.nieuweOpmerking.get(welke), opmerking);
        this.nieuweOpmerking.get(welke).setValue(opmerking);
    }

    public void vulOpmerking(Logger LOGGER, String opmerking) {
        vulOpmerking(LOGGER, opmerking, 0);
    }

    public SelenideElement getOpmerking() {
        return opmerking.get(i);
    }

    public String getOpmerkingAsString() {
        return getOpmerking().getText();
    }
}
