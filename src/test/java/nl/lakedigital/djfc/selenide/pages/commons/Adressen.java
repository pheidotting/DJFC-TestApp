package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;


public class Adressen extends AbstractPagina{
    private final static Logger LOGGER = LoggerFactory.getLogger(Adressen.class);

private SelenideElement voegAdresToe;
    private List<Adres> adressen;

    public Adressen() {
                voegAdresToe=$(By.id("voegAdresToe"));
        adressen=new ArrayList<>();
    }

    public List<Adres> getAdressen() {
        return adressen;
    }

    public void voegAdresToe(){
        logKlik(this.voegAdresToe,LOGGER);
        voegAdresToe.click();
        adressen.add(new Adres());
        wachtFf();
    }
}
