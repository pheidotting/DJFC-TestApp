package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;

public class Contactpersoon extends AbstractPagina {
    private SelenideElement voornaam = $(By.id("voornaam"));
    private SelenideElement tussenvoegsel = $(By.id("tussenvoegsel"));
    private SelenideElement achternaam = $(By.id("achternaam"));
    private SelenideElement emailadres = $(By.id("emailadres"));
    private SelenideElement functie = $(By.id("functie"));

    private Telefoonnummers telefoonnummers = new Telefoonnummers(true);

    public Telefoonnummers getTelefoonnummers() {
        return telefoonnummers;
    }

    public void vulContactpersoon(Logger LOGGER, String voornaam, String tussenvoegsel, String achternaam, String emailadres, String functie) {
        setVoornaam(LOGGER, voornaam);
        setTussenvoegsel(LOGGER, tussenvoegsel);
        setAchternaam(LOGGER, achternaam);
        setEmailadres(LOGGER, emailadres);
        setFunctie(LOGGER, functie);
    }

    public void setVoornaam(Logger LOGGER, String voornaam) {
        logInvullen(LOGGER, this.voornaam, voornaam);
        this.voornaam.setValue(voornaam);
    }

    public void setTussenvoegsel(Logger LOGGER, String tussenvoegsel) {
        logInvullen(LOGGER, this.tussenvoegsel, tussenvoegsel);
        this.tussenvoegsel.setValue(tussenvoegsel);
    }

    public void setAchternaam(Logger LOGGER, String achternaam) {
        logInvullen(LOGGER, this.achternaam, achternaam);
        this.achternaam.setValue(achternaam);
    }

    public void setEmailadres(Logger LOGGER, String emailadres) {
        logInvullen(LOGGER, this.emailadres, emailadres);
        this.emailadres.setValue(emailadres);
    }

    public void setFunctie(Logger LOGGER, String functie) {
        logInvullen(LOGGER, this.functie, functie);
        this.functie.setValue(functie);
    }
}
