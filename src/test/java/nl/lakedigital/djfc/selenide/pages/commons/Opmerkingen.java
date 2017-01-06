package nl.lakedigital.djfc.selenide.pages.commons;

import java.util.ArrayList;
import java.util.List;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.codeborne.selenide.Selenide.$;


public class Opmerkingen extends AbstractPagina{
    private final static Logger LOGGER = LoggerFactory.getLogger(Opmerkingen.class);

    private SelenideElement             voegOpmerkingToe;
    private List<Opmerking> opmerkingen;


    public Opmerkingen() {
                voegOpmerkingToe=$(By.id("voegOpmerkingToe"));

        this.opmerkingen = new ArrayList<>();
    }

    public void voegOpmerkingToe() {
        logKlik(this.voegOpmerkingToe,LOGGER);
        this.voegOpmerkingToe.click();
        this.opmerkingen.add(new Opmerking(this.opmerkingen.size()));
    }

    public List<Opmerking> getOpmerkingen() {
        return opmerkingen;
    }
}
