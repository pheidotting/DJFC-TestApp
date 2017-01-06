package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.base.Predicate;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.selenide.pages.commons.*;
import org.hamcrest.core.Is;
import org.joda.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BeherenRelatie extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(BeherenRelatie.class);

    private SelenideElement voornaam;
    private SelenideElement roepnaam;
    private SelenideElement tussenvoegsel;
    private SelenideElement achternaam;
    private SelenideElement bsn;
    private SelenideElement geboorteDatum;
    private SelenideElement overlijdensdatum;
    private SelenideElement geslacht;
    private SelenideElement burgerlijkeStaat;
    private SelenideElement emailadres;
    private SelenideElement mailtolink;

    private SelenideElement opslaanRelatie;
    private SelenideElement verwijderen;

    private Adressen adressen;
    private Rekeningnummers rekeningnummers;
    private Telefoonnummers telefoonnummers;
    private Opmerkingen opmerkingen;
    private Taken taken;
    private Bijlages bijlages
            ;

    private Lorem lorem;

    public BeherenRelatie() {
        voornaam = $(By.id("voornaam"));
        roepnaam = $(By.id("roepnaam"));
        tussenvoegsel = $(By.id("tussenvoegsel"));
        achternaam = $(By.id("achternaam"));
        bsn = $(By.id("bsn"));
        geboorteDatum = $(By.id("geboorteDatum"));
        overlijdensdatum = $(By.id("overlijdensdatum"));
        geslacht = $(By.id("geslacht"));
        burgerlijkeStaat = $(By.id("burgerlijkeStaat"));
        emailadres = $(By.id("emailadres"));
        mailtolink = $(By.id("mailtolink"));

        opslaanRelatie = $(By.id("opslaanRelatie"));
        verwijderen = $(By.id("verwijderen"));


        adressen = new Adressen();
        rekeningnummers = new Rekeningnummers();
        telefoonnummers = new Telefoonnummers(false);
        opmerkingen = new Opmerkingen();
        taken = new Taken();bijlages=new Bijlages();

        lorem= LoremIpsum.getInstance();
    }

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

    public void setVoornaam(String voornaam) {
        if (voornaam != null) {
            logInvullen(this.voornaam, voornaam, LOGGER);
            this.voornaam.setValue(voornaam);
        }
    }

    public void setRoepnaam(String roepnaam) {
        if (roepnaam != null) {
            logInvullen(this.roepnaam, roepnaam, LOGGER);
            this.roepnaam.setValue(roepnaam);
        }
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        if (tussenvoegsel != null) {
            logInvullen(this.tussenvoegsel, tussenvoegsel, LOGGER);
            this.tussenvoegsel.setValue(tussenvoegsel);
        }
    }

    public void setAchternaam(String achternaam) {
        if (achternaam != null) {
            logInvullen(this.achternaam, achternaam, LOGGER);
            this.achternaam.setValue(achternaam);
        }
    }

    public void setBsn(String bsn) {
        if (bsn != null) {
            logInvullen(this.bsn, bsn, LOGGER);
            this.bsn.setValue(bsn);
        }
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        if (geboorteDatum != null) {
            logInvullen(this.geboorteDatum, geboorteDatum.toString("ddMMyyyy"), LOGGER);
            this.geboorteDatum.setValue(geboorteDatum.toString("ddMMyyyy"));
            this.geboorteDatum.sendKeys(Keys.TAB);
            logIsGevuldMet(this.geboorteDatum,geboorteDatum.toString("dd-MM-yyyy"),LOGGER);
            assertTrue(this.geboorteDatum.getValue().contains(geboorteDatum.toString("dd-MM-yyyy")));
        }
    }

    public void setGeboorteDatum(String geboorteDatum) {
        if (geboorteDatum != null) {
            logInvullen(this.geboorteDatum, geboorteDatum, LOGGER);
            this.geboorteDatum.setValue(geboorteDatum);
        }
    }

    public void setOverlijdensdatum(LocalDate overlijdensdatum) {
        if (overlijdensdatum != null) {
            logInvullen(this.overlijdensdatum, overlijdensdatum.toString("ddMMyyyy"), LOGGER);
            this.overlijdensdatum.setValue(overlijdensdatum.toString("ddMMyyyy"));
            this.overlijdensdatum.sendKeys(Keys.TAB);
            logIsGevuldMet(this.overlijdensdatum,overlijdensdatum.toString("dd-MM-yyyy"),LOGGER);
            assertTrue(this.overlijdensdatum.getValue().contains(overlijdensdatum.toString("dd-MM-yyyy")));
        }
    }

    public void setGeslacht(String geslacht) {
        if (geslacht != null) {
            logInvullen(this.geslacht, geslacht, LOGGER);
            this.geslacht.selectOption(geslacht);
        }
    }

    public void setBurgerlijkeStaat(String burgerlijkeStaat) {
        if (burgerlijkeStaat != null) {
            logInvullen(this.burgerlijkeStaat, burgerlijkeStaat, LOGGER);
            this.burgerlijkeStaat.selectOption(burgerlijkeStaat);
        }
    }

    public void setEmailadres(String emailadres) {
        if (emailadres != null) {
            logInvullen(this.emailadres, emailadres, LOGGER);
            this.emailadres.setValue(emailadres);
            this.emailadres.sendKeys(Keys.TAB);
            checkMailtolink(emailadres);
        }
    }

    public void checkMailtolink(String mailtolink) {
        if (mailtolink != null) {
            logIsGevuldMet(this.mailtolink, mailtolink, LOGGER);
            assertThat(this.mailtolink.getAttribute("href"), is("mailto:" + mailtolink));
        }
    }

    public void vulPagina(String voornaam, String roepnaam, String tussenvoegsel, String achternaam, String bsn, LocalDate geboorteDatum, LocalDate overlijdensdatum, String geslacht, String burgerlijkeStaat, String emailadres) {
        wachtFf();
        setVoornaam(voornaam);
        setRoepnaam(roepnaam);
        setTussenvoegsel(tussenvoegsel);
        setAchternaam(achternaam);
        setBsn(bsn);
        setGeboorteDatum(geboorteDatum);
        setOverlijdensdatum(overlijdensdatum);
        setGeslacht(geslacht);
        setBurgerlijkeStaat(burgerlijkeStaat);
        setEmailadres(emailadres);
    }

    public void klikOpslaan(boolean wachtOpVerdwijnen) {
        logKlik(this.opslaanRelatie, LOGGER);
        this.opslaanRelatie.click();
        if(wachtOpVerdwijnen){
        this.opslaanRelatie.waitUntil(Condition.disappears,2500);
    }
    }

    public SelenideElement getOpslaanRelatie() {
        return opslaanRelatie;
    }

    public void verwijderRelatie() {
        logKlik(this.verwijderen, LOGGER);
        this.verwijderen.click();
    }

    public List<SelenideElement> zoekFoutmeldingOpTekst(String tekst) {
        return newArrayList(filter(getValidationMessages(), new Predicate<SelenideElement>() {
            @Override
            public boolean apply(@Nullable SelenideElement element) {
                return element.text().equals(tekst);
            }
        }));
    }

    public void testTakenNietZichtbaar() {
        taken.checkNietAanwezig();
    }

    public void voegBijlageToeBijRelatie(String voornamen, LijstRelaties lijstRelaties, Dashboard dashboard) {
        if (WebDriverRunner.getAndCheckWebDriver() instanceof PhantomJSDriver) {
            lijstRelaties.selecteer(lijstRelaties.zoekGebruiker(voornamen, false), null);

            getBijlages().uploadFile(Bijlages.UploadBestand.EEN_PDF);

            klikHomeKnop(LOGGER);
            dashboard.klikNaarParticulier();

            //Controleren
            lijstRelaties.selecteer(lijstRelaties.zoekGebruiker(voornamen, false), null);

            logIsGevuldMet(getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam(),"1.pdf",LOGGER);
            assertThat(getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam().getText(), Is.is("1.pdf"));

            logKlik(getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam(), LOGGER);
            getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam().click();
            wachtFf();
            String nwOmschrijving = lorem.getWords(1,10);
            logInvullen(getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaamEdit(),nwOmschrijving,LOGGER);
            getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaamEdit().setValue(nwOmschrijving);
            logKlik(getBijlages().getBijlages().get(0).getOpslaanOmschrijvingOfBestandsNaam(),LOGGER);
            getBijlages().getBijlages().get(0).getOpslaanOmschrijvingOfBestandsNaam().click();
            getBijlages().getBijlages().get(0).getOpslaanOmschrijvingOfBestandsNaam().waitUntil(Condition.disappears,2500);

            logIsGevuldMet(getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam(),nwOmschrijving,LOGGER);
            assertThat(getBijlages().getBijlages().get(0).getOmschrijvingOfBestandsNaam().getText(), Is.is(nwOmschrijving));

            klikHomeKnop(LOGGER);
            dashboard.klikNaarParticulier();
        }
    }
}
