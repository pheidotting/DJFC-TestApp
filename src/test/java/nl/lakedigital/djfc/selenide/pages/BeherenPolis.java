package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.selenide.pages.commons.Opmerkingen;
import nl.lakedigital.djfc.tests.AbstractTest;
import org.joda.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BeherenPolis extends PaginaMetMenuBalk {
    private SelenideElement verzekeringsMaatschappij;
    private SelenideElement soortVerzekering;
    private SelenideElement status;
    private SelenideElement polisNummer;
    private SelenideElement kenmerk;
    private SelenideElement dekking;
    private SelenideElement verzekerdeZaak;
    private SelenideElement premie;
    private SelenideElement ingangsDatumString;
    private SelenideElement wijzigingsdatumString;
    private SelenideElement prolongatiedatumString;
    private SelenideElement betaalfrequentie;
    private SelenideElement omschrijvingVerzekering;
    private SelenideElement opslaanPolis;

    private Opmerkingen opmerkingen;

    public BeherenPolis() {
        verzekeringsMaatschappij = $(By.id("verzekeringsMaatschappijen"));
        soortVerzekering = $(By.id("soortVerzekering"));
        status = $(By.id("status"));
        polisNummer = $(By.id("polisNummer"));
        kenmerk = $(By.id("kenmerk"));
        dekking = $(By.id("dekking"));
        verzekerdeZaak = $(By.id("verzekerdeZaak"));
        premie = $(By.id("premie"));
        ingangsDatumString = $(By.id("ingangsDatumString"));
        wijzigingsdatumString = $(By.id("wijzigingsdatumString"));
        prolongatiedatumString = $(By.id("prolongatiedatumString"));
        betaalfrequentie = $(By.id("betaalfrequentie"));
        omschrijvingVerzekering = $(By.id("omschrijvingVerzekering"));
        opslaanPolis = $(By.id("opslaanPolis"));

        opmerkingen = new Opmerkingen();
    }

    public void vulVelden(Logger LOGGER, String verzekeringsMaatschappij, String soortVerzekering, String status, String polisNummer, String kenmerk, String dekking, String verzekerdeZaak, String premie, LocalDate ingangsDatumString, LocalDate wijzigingsdatumString, LocalDate prolongatiedatumString, String betaalfrequentie, String omschrijvingVerzekering) {
        this.setVerzekeringsMaatschappij(LOGGER, verzekeringsMaatschappij);
        this.setSoortVerzekering(LOGGER, soortVerzekering);
        this.setStatus(LOGGER, status);
        this.setPolisNummer(LOGGER, polisNummer);
        this.setKenmerk(LOGGER, kenmerk);
        this.setDekking(LOGGER, dekking);
        this.setVerzekerdeZaak(LOGGER, verzekerdeZaak);
        this.setPremie(LOGGER, premie);
        this.setIngangsDatumString(LOGGER, ingangsDatumString);
        this.setWijzigingsdatumString(LOGGER, wijzigingsdatumString);
        this.setProlongatiedatumString(LOGGER, prolongatiedatumString);
        this.setBetaalfrequentie(LOGGER, betaalfrequentie);
        this.setOmschrijvingVerzekering(LOGGER, omschrijvingVerzekering);
        klikOpslaanPolis(LOGGER);
    }

    public void vulVelden(Logger LOGGER, JsonPolis polis) {
        this.vulVelden(LOGGER, polis.getMaatschappij(), polis.getSoort(), polis.getStatus(), polis.getPolisNummer(), polis.getKenmerk(), polis.getDekking(), polis.getVerzekerdeZaak(), polis.getPremie(), new LocalDate(polis.getIngangsDatum()), new LocalDate(polis.getWijzigingsDatum()), new LocalDate(polis.getProlongatieDatum()), polis.getBetaalfrequentie(), polis.getOmschrijvingVerzekering());
    }

    public List<String> getVerzekeringsMaatschappij() {
        return optionToStringList(verzekeringsMaatschappij, "Kies een maatschappij...", false);
    }

    public List<String> getSoortVerzekering() {
        return optionToStringList(soortVerzekering, "Kies een soort polis...", true);
    }

    public void setVerzekeringsMaatschappij(Logger LOGGER, String verzekeringsMaatschappij) {
        this.verzekeringsMaatschappij.waitUntil(Condition.not(Condition.empty), AbstractTest.timeOut);
        logInvullen(LOGGER, this.verzekeringsMaatschappij, verzekeringsMaatschappij);
        this.verzekeringsMaatschappij.selectOption(verzekeringsMaatschappij);
    }

    public void setSoortVerzekering(Logger LOGGER, String soortVerzekering) {
        logInvullen(LOGGER, this.soortVerzekering, soortVerzekering);
        this.soortVerzekering.selectOption(soortVerzekering);
    }

    public void setStatus(Logger LOGGER, String status) {
        logInvullen(LOGGER, this.status, status);
        this.status.selectOption(status);
    }

    public void setPolisNummer(Logger LOGGER, String polisNummer) {
        logInvullen(LOGGER, this.polisNummer, polisNummer);
        this.polisNummer.setValue(polisNummer);
    }

    public void setKenmerk(Logger LOGGER, String kenmerk) {
        logInvullen(LOGGER, this.kenmerk, kenmerk);
        this.kenmerk.setValue(kenmerk);
    }

    public void setDekking(Logger LOGGER, String dekking) {
        logInvullen(LOGGER, this.dekking, dekking);
        this.dekking.setValue(dekking);
    }

    public void setVerzekerdeZaak(Logger LOGGER, String verzekerdeZaak) {
        logInvullen(LOGGER, this.verzekerdeZaak, verzekerdeZaak);
        this.verzekerdeZaak.setValue(verzekerdeZaak);
    }

    public void setPremie(Logger LOGGER, String premie) {
        logInvullen(LOGGER, this.premie, premie);
        this.premie.setValue(premie);
    }

    public void setIngangsDatumString(Logger LOGGER, LocalDate ingangsDatumString) {
        logInvullen(LOGGER, this.ingangsDatumString, ingangsDatumString.toString("ddMMyyyy"));
        this.ingangsDatumString.setValue(ingangsDatumString.toString("ddMMyyyy"));
        this.ingangsDatumString.sendKeys(Keys.TAB);
        logIsGevuldMet(LOGGER, this.ingangsDatumString, ingangsDatumString.toString("dd-MM-yyyy"));
        assertThat(this.ingangsDatumString.getValue(), is(ingangsDatumString.toString("dd-MM-yyyy")));
        logIsGevuldMet(LOGGER, this.prolongatiedatumString, ingangsDatumString.plusYears(1).toString("dd-MM-yyyy"));
        assertThat(this.prolongatiedatumString.getValue(), is(ingangsDatumString.plusYears(1).toString("dd-MM-yyyy")));
    }

    public void setWijzigingsdatumString(Logger LOGGER, LocalDate wijzigingsdatumString) {
        logInvullen(LOGGER, this.wijzigingsdatumString, wijzigingsdatumString.toString("ddMMyyyy"));
        this.wijzigingsdatumString.setValue(wijzigingsdatumString.toString("ddMMyyyy"));
        this.wijzigingsdatumString.sendKeys(Keys.TAB);
        logIsGevuldMet(LOGGER, this.wijzigingsdatumString, wijzigingsdatumString.toString("dd-MM-yyyy"));
        assertThat(this.wijzigingsdatumString.getValue(), is(wijzigingsdatumString.toString("dd-MM-yyyy")));
    }

    public void setProlongatiedatumString(Logger LOGGER, LocalDate prolongatiedatumString) {
        logInvullen(LOGGER, this.prolongatiedatumString, prolongatiedatumString.toString("ddMMyyyy"));
        this.prolongatiedatumString.setValue(prolongatiedatumString.toString("ddMMyyyy"));
        this.prolongatiedatumString.sendKeys(Keys.TAB);
        logIsGevuldMet(LOGGER, this.prolongatiedatumString, prolongatiedatumString.toString("dd-MM-yyyy"));
        assertThat(this.prolongatiedatumString.getValue(), is(prolongatiedatumString.toString("dd-MM-yyyy")));
    }

    public void setBetaalfrequentie(Logger LOGGER, String betaalfrequentie) {
        logInvullen(LOGGER, this.betaalfrequentie, betaalfrequentie);
        this.betaalfrequentie.setValue(betaalfrequentie);
    }

    public void setOmschrijvingVerzekering(Logger LOGGER, String omschrijvingVerzekering) {
        logInvullen(LOGGER, this.omschrijvingVerzekering, omschrijvingVerzekering);
        this.omschrijvingVerzekering.setValue(omschrijvingVerzekering);
    }

    public void klikOpslaanPolis(Logger LOGGER) {
        logKlik(LOGGER, this.opslaanPolis);
        this.opslaanPolis.click();
        this.opslaanPolis.waitUntil(Condition.disappears, AbstractTest.timeOut * 2);
    }

    public SelenideElement getOpslaanPolis() {
        return opslaanPolis;
    }
}
