package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.selenide.pages.commons.Opmerkingen;
import org.joda.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BeherenPolis extends PaginaMetMenuBalk {
    private final static Logger LOGGER = LoggerFactory.getLogger(BeherenPolis.class);

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

    public void vulVelden(String verzekeringsMaatschappij, String soortVerzekering, String status, String polisNummer, String kenmerk, String dekking, String verzekerdeZaak, String premie, LocalDate ingangsDatumString, LocalDate wijzigingsdatumString, LocalDate prolongatiedatumString, String betaalfrequentie, String omschrijvingVerzekering) {
        this.setVerzekeringsMaatschappij(verzekeringsMaatschappij);
        this.setSoortVerzekering(soortVerzekering);
        this.setStatus(status);
        this.setPolisNummer(polisNummer);
        this.setKenmerk(kenmerk);
        this.setDekking(dekking);
        this.setVerzekerdeZaak(verzekerdeZaak);
        this.setPremie(premie);
        this.setIngangsDatumString(ingangsDatumString);
        this.setWijzigingsdatumString(wijzigingsdatumString);
        this.setProlongatiedatumString(prolongatiedatumString);
        this.setBetaalfrequentie(betaalfrequentie);
        this.setOmschrijvingVerzekering(omschrijvingVerzekering);
        klikOpslaanPolis();
    }

    public void vulVelden(JsonPolis polis) {
        this.vulVelden(polis.getMaatschappij(), polis.getSoort(), polis.getStatus(), polis.getPolisNummer(), polis.getKenmerk(), polis.getDekking(), polis.getVerzekerdeZaak(), polis.getPremie(), new LocalDate(polis.getIngangsDatum()), new LocalDate(polis.getWijzigingsDatum()), new LocalDate(polis.getProlongatieDatum()), polis.getBetaalfrequentie(), polis.getOmschrijvingVerzekering());
    }

    public List<String> getVerzekeringsMaatschappij() {
        return optionToStringList(verzekeringsMaatschappij, "Kies een maatschappij...", false);
    }

    public List<String> getSoortVerzekering() {
        return optionToStringList(soortVerzekering, "Aansprakelijkheid", true);
    }

    public void setVerzekeringsMaatschappij(String verzekeringsMaatschappij) {
        this.verzekeringsMaatschappij.waitUntil(Condition.not(Condition.empty),2500);
        logInvullen(this.verzekeringsMaatschappij, verzekeringsMaatschappij, LOGGER);
        this.verzekeringsMaatschappij.selectOption(verzekeringsMaatschappij);
    }

    public void setSoortVerzekering(String soortVerzekering) {
        logInvullen(this.soortVerzekering, soortVerzekering, LOGGER);
        this.soortVerzekering.selectOption(soortVerzekering);
    }

    public void setStatus(String status) {
        logInvullen(this.status, status, LOGGER);
        this.status.selectOption(status);
    }

    public void setPolisNummer(String polisNummer) {
        logInvullen(this.polisNummer, polisNummer, LOGGER);
        this.polisNummer.setValue(polisNummer);
    }

    public void setKenmerk(String kenmerk) {
        logInvullen(this.kenmerk, kenmerk, LOGGER);
        this.kenmerk.setValue(kenmerk);
    }

    public void setDekking(String dekking) {
        logInvullen(this.dekking, dekking, LOGGER);
        this.dekking.setValue(dekking);
    }

    public void setVerzekerdeZaak(String verzekerdeZaak) {
        logInvullen(this.verzekerdeZaak, verzekerdeZaak, LOGGER);
        this.verzekerdeZaak.setValue(verzekerdeZaak);
    }

    public void setPremie(String premie) {
        logInvullen(this.premie, premie, LOGGER);
        this.premie.setValue(premie);
    }

    public void setIngangsDatumString(LocalDate ingangsDatumString) {
        logInvullen(this.ingangsDatumString, ingangsDatumString.toString("ddMMyyyy"), LOGGER);
        this.ingangsDatumString.setValue(ingangsDatumString.toString("ddMMyyyy"));
        this.ingangsDatumString.sendKeys(Keys.TAB);
        logIsGevuldMet(this.ingangsDatumString, ingangsDatumString.toString("dd-MM-yyyy"), LOGGER);
        assertThat(this.ingangsDatumString.getValue(), is(ingangsDatumString.toString("dd-MM-yyyy")));
        logIsGevuldMet(this.prolongatiedatumString, ingangsDatumString.plusYears(1).toString("dd-MM-yyyy"), LOGGER);
        assertThat(this.prolongatiedatumString.getValue(), is(ingangsDatumString.plusYears(1).toString("dd-MM-yyyy")));
    }

    public void setWijzigingsdatumString(LocalDate wijzigingsdatumString) {
        logInvullen(this.wijzigingsdatumString, wijzigingsdatumString.toString("ddMMyyyy"), LOGGER);
        this.wijzigingsdatumString.setValue(wijzigingsdatumString.toString("ddMMyyyy"));
        this.wijzigingsdatumString.sendKeys(Keys.TAB);
        logIsGevuldMet(this.wijzigingsdatumString, wijzigingsdatumString.toString("dd-MM-yyyy"), LOGGER);
        assertThat(this.wijzigingsdatumString.getValue(), is(wijzigingsdatumString.toString("dd-MM-yyyy")));
    }

    public void setProlongatiedatumString(LocalDate prolongatiedatumString) {
        logInvullen(this.prolongatiedatumString, prolongatiedatumString.toString("ddMMyyyy"), LOGGER);
        this.prolongatiedatumString.setValue(prolongatiedatumString.toString("ddMMyyyy"));
        this.prolongatiedatumString.sendKeys(Keys.TAB);
        logIsGevuldMet(this.prolongatiedatumString, prolongatiedatumString.toString("dd-MM-yyyy"), LOGGER);
        assertThat(this.prolongatiedatumString.getValue(), is(prolongatiedatumString.toString("dd-MM-yyyy")));
    }

    public void setBetaalfrequentie(String betaalfrequentie) {
        logInvullen(this.betaalfrequentie, betaalfrequentie, LOGGER);
        this.betaalfrequentie.setValue(betaalfrequentie);
    }

    public void setOmschrijvingVerzekering(String omschrijvingVerzekering) {
        logInvullen(this.omschrijvingVerzekering, omschrijvingVerzekering, LOGGER);
        this.omschrijvingVerzekering.setValue(omschrijvingVerzekering);
    }

    public void klikOpslaanPolis() {
        logKlik(this.opslaanPolis, LOGGER);
        this.opslaanPolis.click();
        this.opslaanPolis.waitUntil(Condition.disappears, 2500);
    }

    public SelenideElement getOpslaanPolis() {
        return opslaanPolis;
    }
}
