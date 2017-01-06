package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.codeborne.selenide.Selenide.$;

public class Contactpersoon extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(Contactpersoon.class);

    private SelenideElement voornaam = $(By.id("voornaam"));
    private SelenideElement tussenvoegsel = $(By.id("tussenvoegsel"));
    private SelenideElement achternaam = $(By.id("achternaam"));
    private SelenideElement emailadres = $(By.id("emailadres"));
    private SelenideElement functie = $(By.id("functie"));

    private Telefoonnummers telefoonnummers=new Telefoonnummers(true);

    public Telefoonnummers getTelefoonnummers() {
        return telefoonnummers;
    }

    public void vulContactpersoon(String voornaam, String tussenvoegsel, String achternaam, String emailadres, String functie) {
        setVoornaam(voornaam);setTussenvoegsel(tussenvoegsel);setAchternaam(achternaam);setEmailadres(emailadres);setFunctie(functie);
    }

    public void setVoornaam(String voornaam) {
        logInvullen(this.voornaam,voornaam,LOGGER);
        this.voornaam .setValue( voornaam);
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        logInvullen(this.tussenvoegsel,tussenvoegsel,LOGGER);
        this.tussenvoegsel .setValue( tussenvoegsel);
    }

    public void setAchternaam(String achternaam) {
        logInvullen(this.achternaam,achternaam,LOGGER);
        this.achternaam .setValue( achternaam);
    }

    public void setEmailadres(String emailadres) {
        logInvullen(this.emailadres,emailadres,LOGGER);
        this.emailadres .setValue( emailadres);
    }

    public void setFunctie(String functie) {
        logInvullen(this.functie,functie,LOGGER);
        this.functie .setValue( functie);
    }
}
