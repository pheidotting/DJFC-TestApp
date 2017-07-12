package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.google.common.base.Predicate;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.commons.json.JsonRelatie;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.selenide.pages.commons.*;
import nl.lakedigital.djfc.tests.AbstractTest;
import org.joda.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BeherenRelatie extends AbstractPagina {
    private SelenideElement voornaam = $(By.id("voornaam"));
    private SelenideElement roepnaam = $(By.id("roepnaam"));
    private SelenideElement tussenvoegsel = $(By.id("tussenvoegsel"));
    private SelenideElement achternaam = $(By.id("achternaam"));
    private SelenideElement bsn = $(By.id("bsn"));
    private SelenideElement geboorteDatum = $(By.id("geboorteDatum"));
    private SelenideElement overlijdensdatum = $(By.id("overlijdensdatum"));
    private SelenideElement geslacht = $(By.id("geslacht"));
    private SelenideElement burgerlijkeStaat = $(By.id("burgerlijkeStaat"));
    private SelenideElement emailadres = $(By.id("emailadres"));
    private SelenideElement mailtolink = $(By.id("mailtolink"));

    private SelenideElement opslaanRelatie = $(By.id("opslaanRelatie"));
    private SelenideElement verwijderen = $(By.id("verwijderen"));

    private Adressen adressen = new Adressen();
    private Rekeningnummers rekeningnummers = new Rekeningnummers();
    private Telefoonnummers telefoonnummers = new Telefoonnummers(false);
    private Opmerkingen opmerkingen = new Opmerkingen();
    private Taken taken = new Taken();

    private Bijlages bijlages = new Bijlages();

    private Lorem lorem = LoremIpsum.getInstance();

    public SelenideElement getVoornaam() {
        return voornaam;
    }

    public Adressen getAdressen() {
        return adressen;
    }

    public Rekeningnummers getRekeningnummers() {
        return rekeningnummers;
    }

    public Telefoonnummers getTelefoonnummers() {
        return telefoonnummers;
    }

    public Opmerkingen getOpmerkingen() {
        return opmerkingen;
    }

    public Bijlages getBijlages() {
        return bijlages;
    }

    public void setVoornaam(Logger LOGGER, String voornaam) {
        if (voornaam != null) {
            logInvullen(LOGGER, this.voornaam, voornaam);
            this.voornaam.setValue(voornaam);
        }
    }

    public void setRoepnaam(Logger LOGGER, String roepnaam) {
        if (roepnaam != null) {
            logInvullen(LOGGER, this.roepnaam, roepnaam);
            this.roepnaam.setValue(roepnaam);
        }
    }

    public void setTussenvoegsel(Logger LOGGER, String tussenvoegsel) {
        if (tussenvoegsel != null) {
            logInvullen(LOGGER, this.tussenvoegsel, tussenvoegsel);
            this.tussenvoegsel.setValue(tussenvoegsel);
        }
    }

    public void setAchternaam(Logger LOGGER, String achternaam) {
        if (achternaam != null) {
            logInvullen(LOGGER, this.achternaam, achternaam);
            this.achternaam.setValue(achternaam);
        }
    }

    public void setBsn(Logger LOGGER, String bsn) {
        if (bsn != null) {
            logInvullen(LOGGER, this.bsn, bsn);
            this.bsn.setValue(bsn);
        }
    }

    public void setGeboorteDatum(Logger LOGGER, LocalDate geboorteDatum) {
        if (geboorteDatum != null) {
            logInvullen(LOGGER, this.geboorteDatum, geboorteDatum.toString("ddMMyyyy"));
            this.geboorteDatum.setValue(geboorteDatum.toString("ddMMyyyy"));
            this.geboorteDatum.sendKeys(Keys.TAB);
            logIsGevuldMet(LOGGER, this.geboorteDatum, geboorteDatum.toString("dd-MM-yyyy"));
            assertTrue(this.geboorteDatum.getValue().contains(geboorteDatum.toString("dd-MM-yyyy")));
        }
    }

    public void setGeboorteDatum(Logger LOGGER, String geboorteDatum) {
        if (geboorteDatum != null) {
            logInvullen(LOGGER, this.geboorteDatum, geboorteDatum);
            this.geboorteDatum.setValue(geboorteDatum);
        }
    }

    public void setOverlijdensdatum(Logger LOGGER, LocalDate overlijdensdatum) {
        if (overlijdensdatum != null) {
            logInvullen(LOGGER, this.overlijdensdatum, overlijdensdatum.toString("ddMMyyyy"));
            this.overlijdensdatum.setValue(overlijdensdatum.toString("ddMMyyyy"));
            this.overlijdensdatum.sendKeys(Keys.TAB);
            logIsGevuldMet(LOGGER, this.overlijdensdatum, overlijdensdatum.toString("dd-MM-yyyy"));
            assertTrue(this.overlijdensdatum.getValue().contains(overlijdensdatum.toString("dd-MM-yyyy")));
        }
    }

    public void setGeslacht(Logger LOGGER, String geslacht) {
        if (geslacht != null) {
            logInvullen(LOGGER, this.geslacht, geslacht);
            this.geslacht.selectOption(geslacht);
        }
    }

    public void setBurgerlijkeStaat(Logger LOGGER, String burgerlijkeStaat) {
        if (burgerlijkeStaat != null) {
            logInvullen(LOGGER, this.burgerlijkeStaat, burgerlijkeStaat);
            this.burgerlijkeStaat.selectOption(burgerlijkeStaat);
        }
    }

    public void setEmailadres(Logger LOGGER, String emailadres) {
        if (emailadres != null) {
            logInvullen(LOGGER, this.emailadres, emailadres);
            this.emailadres.setValue(emailadres);
            this.emailadres.sendKeys(Keys.TAB);
            checkMailtolink(LOGGER, emailadres);
        }
    }

    public void checkMailtolink(Logger LOGGER, String mailtolink) {
        if (mailtolink != null) {
            logIsGevuldMet(LOGGER, this.mailtolink, mailtolink);
            assertThat(this.mailtolink.getAttribute("href"), is("mailto:" + mailtolink));
        }
    }

    private LocalDate stringToLocalDate(String datum) {
        if (datum == null) {
            return null;
        }
        return LocalDate.parse(datum);
    }

    public void vulPagina(Logger LOGGER, JsonRelatie relatie) {
        vulPagina(LOGGER, relatie.getVoornaam(), relatie.getRoepnaam(), relatie.getTussenvoegsel(), relatie.getAchternaam(), relatie.getBsn(), stringToLocalDate(relatie.getGeboorteDatum()), stringToLocalDate(relatie.getOverlijdensdatum()), relatie.getGeslacht(), relatie.getBurgerlijkeStaat(), relatie.getEmailadres());
    }

    public void controleerPagina(Logger LOGGER, JsonRelatie relatie, List<JsonAdres> adressen, List<JsonRekeningNummer> rekeningNummers, List<JsonTelefoonnummer> teControlerenTelefoonnummers) {
        controleerVeld(LOGGER, this.voornaam, relatie.getVoornaam());
        controleerVeld(LOGGER, this.roepnaam, relatie.getRoepnaam());
        controleerVeld(LOGGER, this.tussenvoegsel, relatie.getTussenvoegsel());
        controleerVeld(LOGGER, this.achternaam, relatie.getAchternaam());
        controleerVeld(LOGGER, this.bsn, relatie.getBsn());
        if (relatie.getGeboorteDatum() != null) {
            controleerVeld(LOGGER, this.geboorteDatum, stringToLocalDate(relatie.getGeboorteDatum()).toString("dd-MM-YYYY"));
        }
        if (relatie.getOverlijdensdatum() != null) {
            controleerVeld(LOGGER, this.overlijdensdatum, stringToLocalDate(relatie.getOverlijdensdatum()).toString("dd-MM-YYYY"));
        }
        controleerVeld(LOGGER, this.geslacht, relatie.getGeslacht());
        controleerVeld(LOGGER, this.burgerlijkeStaat, relatie.getBurgerlijkeStaat());
        controleerVeld(LOGGER, this.emailadres, relatie.getEmailadres());

        //Controleer adressen
        getAdressen().controleerAdressen(LOGGER, adressen);
        getRekeningnummers().controleerRekeningNummers(LOGGER, rekeningNummers);
        getTelefoonnummers().controleerTelefoonnummers(LOGGER, teControlerenTelefoonnummers);
    }

    public void vulPagina(Logger LOGGER, String voornaam, String roepnaam, String tussenvoegsel, String achternaam, String bsn, LocalDate geboorteDatum, LocalDate overlijdensdatum, String geslacht, String burgerlijkeStaat, String emailadres) {
        wachtFf();
        setVoornaam(LOGGER, voornaam);
        setRoepnaam(LOGGER, roepnaam);
        setTussenvoegsel(LOGGER, tussenvoegsel);
        setAchternaam(LOGGER, achternaam);
        setBsn(LOGGER, bsn);
        //        setGeboorteDatum(LOGGER, geboorteDatum);
        //        setOverlijdensdatum(LOGGER, overlijdensdatum);
        setGeslacht(LOGGER, geslacht);
        setBurgerlijkeStaat(LOGGER, burgerlijkeStaat);
        setEmailadres(LOGGER, emailadres);
    }

    public void klikOpslaan(Logger LOGGER, boolean wachtOpVerdwijnen) {
        logKlik(LOGGER, this.opslaanRelatie);
        this.opslaanRelatie.click();
        if (wachtOpVerdwijnen) {
            this.opslaanRelatie.waitUntil(Condition.disappears, AbstractTest.timeOut);
        }
    }

    public SelenideElement getOpslaanRelatie(Logger LOGGER) {
        return opslaanRelatie;
    }

    public void verwijderRelatie(Logger LOGGER) {
        logKlik(LOGGER, this.verwijderen);
        this.verwijderen.click();
    }

    public List<SelenideElement> zoekFoutmeldingOpTekst(Logger LOGGER, String tekst) {
        return newArrayList(filter(getValidationMessages(), new Predicate<SelenideElement>() {
            @Override
            public boolean apply(@Nullable SelenideElement element) {
                return element.text().equals(tekst);
            }
        }));
    }

    public void testTakenNietZichtbaar(Logger LOGGER) {
        taken.checkNietAanwezig();
    }


}
