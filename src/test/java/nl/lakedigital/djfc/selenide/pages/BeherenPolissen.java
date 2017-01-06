package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.joda.time.LocalDate;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BeherenPolissen extends AbstractPagina {
    private ElementsCollection titel;

    private ElementsCollection tableRowPolisNummer;
    private ElementsCollection tableRowStatus;
    private ElementsCollection tableRowVerzekerdeZaak;
    private ElementsCollection tableRowMaatschappij;

    private ElementsCollection status;
    private ElementsCollection polisNummer;
    private ElementsCollection kenmerk;
    private ElementsCollection ingangsDatum;
    private ElementsCollection eindDatum;
    private ElementsCollection wijzigingsDatum;
    private ElementsCollection prolongatieDatum;
    private ElementsCollection maatschappij;
    private ElementsCollection premie;
    private ElementsCollection betaalfrequentie;
    private ElementsCollection omschrijvingVerzekering;

    public BeherenPolissen() {
        titel = $$(By.name("polis-titel"));

        tableRowPolisNummer = $$(By.name("tableRowPolisNummer"));
        tableRowStatus = $$(By.name("tableRowStatus"));
        tableRowVerzekerdeZaak = $$(By.name("tableRowVerzekerdeZaak"));
        tableRowMaatschappij = $$(By.name("tableRowMaatschappij"));

        status = $$(By.name("status"));
        polisNummer = $$(By.name("polisNummer"));
        kenmerk = $$(By.name("kenmerk"));
        ingangsDatum = $$(By.name("ingangsDatum"));
        eindDatum = $$(By.name("eindDatum"));
        wijzigingsDatum = $$(By.name("wijzigingsDatum"));
        prolongatieDatum = $$(By.name("prolongatieDatum"));
        maatschappij = $$(By.name("maatschappij"));
        premie = $$(By.name("premie"));
        betaalfrequentie = $$(By.name("betaalfrequentie"));
        omschrijvingVerzekering = $$(By.name("omschrijvingVerzekering"));
    }

    public ElementsCollection getTitel() {
        return titel;
    }

    public List<SelenideElement> getTitelList() {
        titel = $$(By.name("polis-titel"));
        return titel.subList(0, titel.getTexts().length);
    }

    public void controleerPolis(int nummer, JsonPolis teControlerenPolis, Logger LOGGER) {
        logIsGevuldMet(tableRowPolisNummer.get(nummer), teControlerenPolis.getPolisNummer(), LOGGER);
        assertThat(tableRowPolisNummer.get(nummer).getText(), is(teControlerenPolis.getPolisNummer()));
        logIsGevuldMet(tableRowMaatschappij.get(nummer), teControlerenPolis.getMaatschappij(), LOGGER);
        assertThat(tableRowMaatschappij.get(nummer).getText(), is(teControlerenPolis.getMaatschappij()));
        logIsGevuldMet(tableRowStatus.get(nummer), teControlerenPolis.getStatus(), LOGGER);
        assertThat(tableRowStatus.get(nummer).getText(), is(teControlerenPolis.getStatus()));
        logIsGevuldMet(tableRowVerzekerdeZaak.get(nummer), teControlerenPolis.getVerzekerdeZaak(), LOGGER);
        assertThat(tableRowVerzekerdeZaak.get(nummer).getText(), is(teControlerenPolis.getVerzekerdeZaak()));

        assertThat(status.get(nummer).isDisplayed(), is(false));

        logKlik(titel.get(nummer), LOGGER);
        titel.get(nummer).click();

        status.get(nummer).waitUntil(Condition.appears, 2500);

        logIsGevuldMet(status.get(nummer), teControlerenPolis.getStatus(), LOGGER);
        assertThat(status.get(nummer).getText(), is(teControlerenPolis.getStatus()));
        logIsGevuldMet(polisNummer.get(nummer), teControlerenPolis.getPolisNummer(), LOGGER);
        assertThat(polisNummer.get(nummer).getText(), is(teControlerenPolis.getPolisNummer()));
        logIsGevuldMet(kenmerk.get(nummer), teControlerenPolis.getKenmerk(), LOGGER);
        assertThat(kenmerk.get(nummer).getText(), is(teControlerenPolis.getKenmerk()));
        logIsGevuldMet(ingangsDatum.get(nummer), new LocalDate(teControlerenPolis.getIngangsDatum()).toString("dd-MM-yyyy"), LOGGER);
        assertThat(ingangsDatum.get(nummer).getText(), is(new LocalDate(teControlerenPolis.getIngangsDatum()).toString("dd-MM-yyyy")));
        //        assertThat(eindDatum.get(nummer))
        //        assertThat(eindDatum.get(nummer).getText(), is(new LocalDate(teControlerenPolis.getEindDatum()).toString("dd-MM-yyyy")));
        logIsGevuldMet(wijzigingsDatum.get(nummer), new LocalDate(teControlerenPolis.getWijzigingsDatum()).toString("dd-MM-yyyy"), LOGGER);
        assertThat(wijzigingsDatum.get(nummer).getText(), is(new LocalDate(teControlerenPolis.getWijzigingsDatum()).toString("dd-MM-yyyy")));
        logIsGevuldMet(prolongatieDatum.get(nummer), new LocalDate(teControlerenPolis.getProlongatieDatum()).toString("dd-MM-yyyy"), LOGGER);
        assertThat(prolongatieDatum.get(nummer).getText(), is(new LocalDate(teControlerenPolis.getProlongatieDatum()).toString("dd-MM-yyyy")));
        logIsGevuldMet(maatschappij.get(nummer), teControlerenPolis.getMaatschappij(), LOGGER);
        assertThat(maatschappij.get(nummer).getText(), is(teControlerenPolis.getMaatschappij()));
        logIsGevuldMet(premie.get(nummer), "€ " + teControlerenPolis.getPremie() + ",00", LOGGER);
        assertThat(premie.get(nummer).getText(), is("€ " + teControlerenPolis.getPremie() + ",00"));
        logIsGevuldMet(betaalfrequentie.get(nummer), teControlerenPolis.getBetaalfrequentie(), LOGGER);
        assertThat(betaalfrequentie.get(nummer).getText(), is(teControlerenPolis.getBetaalfrequentie()));
        logIsGevuldMet(omschrijvingVerzekering.get(nummer), teControlerenPolis.getOmschrijvingVerzekering(), LOGGER);
        assertThat(omschrijvingVerzekering.get(nummer).getText(), is(teControlerenPolis.getOmschrijvingVerzekering()));

        titel.get(nummer).click();

        //        status.get(nummer).waitUntil(Condition.disappears, 2500);
    }

}
