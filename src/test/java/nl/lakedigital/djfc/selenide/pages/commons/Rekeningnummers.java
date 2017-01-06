package nl.lakedigital.djfc.selenide.pages.commons;

import java.util.ArrayList;
import java.util.List;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import static com.codeborne.selenide.Selenide.*;


public class Rekeningnummers  extends AbstractPagina{
    private final static Logger LOGGER = LoggerFactory.getLogger(Rekeningnummers.class);

    private SelenideElement voegRekeningToe;
    private List<Rekeningnummer> rekeningnummers;

    public Rekeningnummers() {
        this.voegRekeningToe = $(By.id("voegRekeningToe"));

        rekeningnummers=new ArrayList<>();
    }

    public List<Rekeningnummer> getRekeningnummers() {
        return rekeningnummers;
    }

    public void voegRekeningToe(){
        logKlik(this.voegRekeningToe,LOGGER);
        this.voegRekeningToe.click();
        this.rekeningnummers.add(new Rekeningnummer());
    }
}
