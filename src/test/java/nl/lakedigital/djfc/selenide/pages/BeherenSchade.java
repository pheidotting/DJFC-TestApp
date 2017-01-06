package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class BeherenSchade extends PaginaMetMenuBalk {
    private final static Logger LOGGER = LoggerFactory.getLogger(BeherenSchade.class);

    private SelenideElement schadeMeldingOpslaan;

    private SelenideElement polisVoorSchademelding;
    private SelenideElement schadeNummerMaatschappij;
    private SelenideElement schadeNummerTussenpersoon;
    private SelenideElement soortSchade;
    private SelenideElement locatieSchade;
    private SelenideElement statusSchade;
    private SelenideElement datumTijdSchade;
    private SelenideElement datumTijdMelding;
    private SelenideElement datumAfgehandeld;
    private SelenideElement eigenRisico;
    private SelenideElement omschrijving;

    public BeherenSchade() {
        polisVoorSchademelding = $(By.id("polisVoorSchademelding"));
        schadeNummerMaatschappij = $(By.id("schadeNummerMaatschappij"));
        schadeNummerTussenpersoon = $(By.id("schadeNummerTussenpersoon"));
        soortSchade = $(By.id("soortSchade"));
        locatieSchade = $(By.id("locatieSchade"));
        statusSchade = $(By.id("statusSchade"));
        datumTijdSchade = $(By.id("datumTijdSchade"));
        datumTijdMelding = $(By.id("datumTijdMelding"));
        datumAfgehandeld = $(By.id("datumAfgehandeld"));
        eigenRisico = $(By.id("eigenRisico"));
        omschrijving = $(By.id("omschrijving"));

        schadeMeldingOpslaan = $(By.id("schadeMeldingOpslaan"));
    }

    public void vulAlleVelden( String polisVoorSchademelding, String schadeNummerMaatschappij, String schadeNummerTussenpersoon, String soortSchade, String locatieSchade, String statusSchade, LocalDateTime datumTijdSchade, LocalDateTime datumTijdMelding, LocalDate datumAfgehandeld, String eigenRisico, String omschrijving) {
        setPolisVoorSchademelding ( polisVoorSchademelding);
        setSchadeNummerMaatschappij ( schadeNummerMaatschappij);
        setSchadeNummerTussenpersoon ( schadeNummerTussenpersoon);
        setSoortSchade ( soortSchade);
        setLocatieSchade ( locatieSchade);
        setStatusSchade ( statusSchade);
        setDatumTijdSchade ( datumTijdSchade);
        setDatumTijdMelding ( datumTijdMelding);
        setDatumAfgehandeld ( datumAfgehandeld);
        setEigenRisico ( eigenRisico);
        setOmschrijving ( omschrijving);

        klikOpslaan();
    }

    public List<String> getPolisVoorSchademelding(){
        wachtFf();
        return optionToStringList(this.polisVoorSchademelding,"Kies een polis uit de lijst..",false);
    }
public List<String> getStatusSchade(){
    return optionToStringList(this.statusSchade,"Kies een status uit de lijst..",false);
}
    public void setPolisVoorSchademelding(String polisVoorSchademelding) {
        logInvullen(this.polisVoorSchademelding,polisVoorSchademelding,LOGGER);
        this.polisVoorSchademelding.selectOption(polisVoorSchademelding);
    }

    public void setSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        logInvullen(this.schadeNummerMaatschappij,schadeNummerMaatschappij,LOGGER);
        this.schadeNummerMaatschappij.setValue( schadeNummerMaatschappij);
    }

    public void setSchadeNummerTussenpersoon(String schadeNummerTussenpersoon) {
        logInvullen(this.schadeNummerTussenpersoon,schadeNummerTussenpersoon,LOGGER);
        this.schadeNummerTussenpersoon .setValue( schadeNummerTussenpersoon);
    }

    public void setSoortSchade(String soortSchade) {
        logInvullen(this.soortSchade,soortSchade,LOGGER);
        this.soortSchade .setValue( soortSchade);
    }

    public void setLocatieSchade(String locatieSchade) {
        logInvullen(this.locatieSchade,locatieSchade,LOGGER);
        this.locatieSchade .setValue( locatieSchade);
    }

    public void setStatusSchade(String statusSchade) {
        logInvullen(this.statusSchade,statusSchade,LOGGER);
        this.statusSchade .setValue( statusSchade);
    }

    public void setDatumTijdSchade(LocalDateTime datumTijdSchade) {
        logInvullen(this.datumTijdSchade,datumTijdSchade.toString("ddMMyyyyHHmm"),LOGGER);
        this.datumTijdSchade.setValue( datumTijdSchade.toString("ddMMyyyyHHmm"));
        this.datumTijdSchade.sendKeys(Keys.TAB);
        logIsGevuldMet(this.datumTijdSchade,datumTijdSchade.toString("dd-MM-yyyy HH:mm"),LOGGER);
    }

    public void setDatumTijdMelding(LocalDateTime datumTijdMelding) {
        logInvullen(this.datumTijdMelding,datumTijdMelding.toString("ddMMyyyyHHmm"),LOGGER);
        this.datumTijdMelding .setValue( datumTijdMelding.toString("ddMMyyyyHHmm"));
        this.datumTijdMelding.sendKeys(Keys.TAB);
        logIsGevuldMet(this.datumTijdMelding,datumTijdMelding.toString("dd-MM-yyyy HH:mm"),LOGGER);
    }

    public void setDatumAfgehandeld(LocalDate datumAfgehandeld) {
        logInvullen(this.datumAfgehandeld,datumAfgehandeld.toString("ddMMyyyy"),LOGGER);
        this.datumAfgehandeld .setValue( datumAfgehandeld.toString("ddMMyyyy"));
        this.datumAfgehandeld.sendKeys(Keys.TAB);
        logIsGevuldMet(this.datumAfgehandeld,datumAfgehandeld.toString("dd-MM-yyyy"),LOGGER);
    }

    public void setEigenRisico(String eigenRisico) {
        logInvullen(this.eigenRisico,eigenRisico,LOGGER);
        this.eigenRisico .setValue(eigenRisico);
    }

    public void setOmschrijving(String omschrijving) {
        logInvullen(this.omschrijving,omschrijving,LOGGER);
        this.omschrijving .setValue( omschrijving);
    }

    public void klikOpslaan(){
        logKlik(this.schadeMeldingOpslaan,LOGGER);
        this.schadeMeldingOpslaan.click();
        this.schadeMeldingOpslaan.waitUntil(Condition.disappears,2500);
    }

    public SelenideElement getSchadeMeldingOpslaan() {
        return schadeMeldingOpslaan;
    }
}
