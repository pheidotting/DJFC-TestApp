package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;


public class Opmerkingen extends AbstractPagina{
    private SelenideElement             voegOpmerkingToe;
    private List<Opmerking> opmerkingen;


    public Opmerkingen() {
                voegOpmerkingToe=$(By.id("voegOpmerkingToe"));

        this.opmerkingen = new ArrayList<>();
    }

    public void voegOpmerkingToe(Logger LOGGER) {
        logKlik(LOGGER, this.voegOpmerkingToe);
        this.voegOpmerkingToe.click();
        this.opmerkingen.add(new Opmerking(this.opmerkingen.size()));
    }

    public List<Opmerking> getOpmerkingen() {
        return opmerkingen;
    }
}
