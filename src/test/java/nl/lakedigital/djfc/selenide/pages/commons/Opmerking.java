package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Opmerking extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(Opmerking.class);

    private SelenideElement verwijder;
    private SelenideElement verwijderNieuweOpmerking;
    private ElementsCollection nieuweOpmerking;
    private ElementsCollection opmerking;
    private int i;

    public Opmerking(int i) {
        verwijder = $(By.id("verwijder"));
        verwijderNieuweOpmerking = $(By.id("verwijderNieuweOpmerking"));
        nieuweOpmerking = $$(By.name("nieuweOpmerking"));
        opmerking = $$(By.name("wateenrareidisdit"));
        this.i = i;
    }

    public void vulOpmerking(String opmerking, int welke) {
        logInvullen(this.nieuweOpmerking.get(welke), opmerking, LOGGER);
        this.nieuweOpmerking.get(welke).setValue(opmerking);
    }

    public void vulOpmerking(String opmerking) {
        vulOpmerking(opmerking, 0);
    }

    public SelenideElement getOpmerking() {
        return opmerking.get(i);
    }

    public String getOpmerkingAsString() {
        return getOpmerking().getText();
    }
}
