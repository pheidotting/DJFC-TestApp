package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class Contactpersonen extends AbstractPagina{
    private SelenideElement voegContactpersoonToe;
    private List<Contactpersoon> contactpersonen;

    public Contactpersonen() {
        voegContactpersoonToe=$(By.id("voegContactpersoonToe"));
        contactpersonen=new ArrayList<>();
    }

    public List<Contactpersoon> getContactpersonen() {
        return contactpersonen;
    }

    public void voegContactpersoonToe(Logger LOGGER) {
        logKlik(LOGGER, this.voegContactpersoonToe);
        voegContactpersoonToe.click();
        contactpersonen.add(new Contactpersoon());
        wachtFf();
    }
}

