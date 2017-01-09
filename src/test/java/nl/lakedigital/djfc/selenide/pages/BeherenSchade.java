package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class BeherenSchade extends PaginaMetMenuBalk {
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

    public void vulAlleVelden(Logger LOGGER, String polisVoorSchademelding, String schadeNummerMaatschappij, String schadeNummerTussenpersoon, String soortSchade, String locatieSchade, String statusSchade, LocalDateTime datumTijdSchade, LocalDateTime datumTijdMelding, LocalDate datumAfgehandeld, String eigenRisico, String omschrijving) {
        setPolisVoorSchademelding(LOGGER, polisVoorSchademelding);
        setSchadeNummerMaatschappij(LOGGER, schadeNummerMaatschappij);
        setSchadeNummerTussenpersoon(LOGGER, schadeNummerTussenpersoon);
        setSoortSchade(LOGGER, soortSchade);
        setLocatieSchade(LOGGER, locatieSchade);
        setStatusSchade(LOGGER, statusSchade);
        setDatumTijdSchade(LOGGER, datumTijdSchade);
        setDatumTijdMelding(LOGGER, datumTijdMelding);
        setDatumAfgehandeld(LOGGER, datumAfgehandeld);
        setEigenRisico(LOGGER, eigenRisico);
        setOmschrijving(LOGGER, omschrijving);

        klikOpslaan(LOGGER);
    }

    public List<String> getPolisVoorSchademelding() {
        wachtFf();
        return optionToStringList(this.polisVoorSchademelding, "Kies een polis uit de lijst..", false);
    }

    public List<String> getStatusSchade() {
        return optionToStringList(this.statusSchade, "Kies een status uit de lijst..", false);
    }

    public void setPolisVoorSchademelding(Logger LOGGER, String polisVoorSchademelding) {
        logInvullen(LOGGER, this.polisVoorSchademelding, polisVoorSchademelding);
        this.polisVoorSchademelding.selectOption(polisVoorSchademelding);
    }

    public void setSchadeNummerMaatschappij(Logger LOGGER, String schadeNummerMaatschappij) {
        logInvullen(LOGGER, this.schadeNummerMaatschappij, schadeNummerMaatschappij);
        this.schadeNummerMaatschappij.setValue(schadeNummerMaatschappij);
    }

    public void setSchadeNummerTussenpersoon(Logger LOGGER, String schadeNummerTussenpersoon) {
        logInvullen(LOGGER, this.schadeNummerTussenpersoon, schadeNummerTussenpersoon);
        this.schadeNummerTussenpersoon.setValue(schadeNummerTussenpersoon);
    }

    public void setSoortSchade(Logger LOGGER, String soortSchade) {
        logInvullen(LOGGER, this.soortSchade, soortSchade);
        this.soortSchade.setValue(soortSchade);
    }

    public void setLocatieSchade(Logger LOGGER, String locatieSchade) {
        logInvullen(LOGGER, this.locatieSchade, locatieSchade);
        this.locatieSchade.setValue(locatieSchade);
    }

    public void setStatusSchade(Logger LOGGER, String statusSchade) {
        logInvullen(LOGGER, this.statusSchade, statusSchade);
        this.statusSchade.setValue(statusSchade);
    }

    public void setDatumTijdSchade(Logger LOGGER, LocalDateTime datumTijdSchade) {
        logInvullen(LOGGER, this.datumTijdSchade, datumTijdSchade.toString("ddMMyyyyHHmm"));
        this.datumTijdSchade.setValue(datumTijdSchade.toString("ddMMyyyyHHmm"));
        this.datumTijdSchade.sendKeys(Keys.TAB);
        logIsGevuldMet(LOGGER, this.datumTijdSchade, datumTijdSchade.toString("dd-MM-yyyy HH:mm"));
    }

    public void setDatumTijdMelding(Logger LOGGER, LocalDateTime datumTijdMelding) {
        logInvullen(LOGGER, this.datumTijdMelding, datumTijdMelding.toString("ddMMyyyyHHmm"));
        this.datumTijdMelding.setValue(datumTijdMelding.toString("ddMMyyyyHHmm"));
        this.datumTijdMelding.sendKeys(Keys.TAB);
        logIsGevuldMet(LOGGER, this.datumTijdMelding, datumTijdMelding.toString("dd-MM-yyyy HH:mm"));
    }

    public void setDatumAfgehandeld(Logger LOGGER, LocalDate datumAfgehandeld) {
        logInvullen(LOGGER, this.datumAfgehandeld, datumAfgehandeld.toString("ddMMyyyy"));
        this.datumAfgehandeld.setValue(datumAfgehandeld.toString("ddMMyyyy"));
        this.datumAfgehandeld.sendKeys(Keys.TAB);
        logIsGevuldMet(LOGGER, this.datumAfgehandeld, datumAfgehandeld.toString("dd-MM-yyyy"));
    }

    public void setEigenRisico(Logger LOGGER, String eigenRisico) {
        logInvullen(LOGGER, this.eigenRisico, eigenRisico);
        this.eigenRisico.setValue(eigenRisico);
    }

    public void setOmschrijving(Logger LOGGER, String omschrijving) {
        logInvullen(LOGGER, this.omschrijving, omschrijving);
        this.omschrijving.setValue(omschrijving);
    }

    public void klikOpslaan(Logger LOGGER) {
        logKlik(LOGGER, this.schadeMeldingOpslaan);
        this.schadeMeldingOpslaan.click();
        this.schadeMeldingOpslaan.waitUntil(Condition.disappears, 2500);
    }

    public SelenideElement getSchadeMeldingOpslaan() {
        return schadeMeldingOpslaan;
    }
}
