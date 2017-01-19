package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import nl.lakedigital.djfc.tests.AbstractTest;
import org.joda.time.LocalDate;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BeherenPolissen extends AbstractPagina {
    private ElementsCollection titel = $$(By.name("polis-titel"));

    private ElementsCollection tableRowPolisNummer = $$(By.name("tableRowPolisNummer"));
    private ElementsCollection tableRowStatus = $$(By.name("tableRowStatus"));
    private ElementsCollection tableRowVerzekerdeZaak = $$(By.name("tableRowVerzekerdeZaak"));
    private ElementsCollection tableRowMaatschappij = $$(By.name("tableRowMaatschappij"));

    private ElementsCollection status = $$(By.name("status"));
    private ElementsCollection polisNummer = $$(By.name("polisNummer"));
    private ElementsCollection kenmerk = $$(By.name("kenmerk"));
    private ElementsCollection ingangsDatum = $$(By.name("ingangsDatum"));
    private ElementsCollection eindDatum = $$(By.name("eindDatum"));
    private ElementsCollection wijzigingsDatum = $$(By.name("wijzigingsDatum"));
    private ElementsCollection prolongatieDatum = $$(By.name("prolongatieDatum"));
    private ElementsCollection maatschappij = $$(By.name("maatschappij"));
    private ElementsCollection premie = $$(By.name("premie"));
    private ElementsCollection betaalfrequentie = $$(By.name("betaalfrequentie"));
    private ElementsCollection omschrijvingVerzekering = $$(By.name("omschrijvingVerzekering"));

    public ElementsCollection getTitel() {
        return titel;
    }

    public List<SelenideElement> getTitelList() {
        titel = $$(By.name("polis-titel"));
        return titel.subList(0, titel.getTexts().length);
    }

    public void controleerPolis(int nummer, JsonPolis teControlerenPolis, Logger LOGGER) {
        logIsGevuldMet(LOGGER, tableRowPolisNummer.get(nummer), teControlerenPolis.getPolisNummer());
        assertThat(tableRowPolisNummer.get(nummer).getText(), is(teControlerenPolis.getPolisNummer()));
        logIsGevuldMet(LOGGER, tableRowMaatschappij.get(nummer), teControlerenPolis.getMaatschappij());
        assertThat(tableRowMaatschappij.get(nummer).getText(), is(teControlerenPolis.getMaatschappij()));
        logIsGevuldMet(LOGGER, tableRowStatus.get(nummer), teControlerenPolis.getStatus());
        assertThat(tableRowStatus.get(nummer).getText(), is(teControlerenPolis.getStatus()));
        logIsGevuldMet(LOGGER, tableRowVerzekerdeZaak.get(nummer), teControlerenPolis.getVerzekerdeZaak());
        assertThat(tableRowVerzekerdeZaak.get(nummer).getText(), is(teControlerenPolis.getVerzekerdeZaak()));

        assertThat(status.get(nummer).isDisplayed(), is(false));

        logKlik(LOGGER, titel.get(nummer));
        titel.get(nummer).click();

        status.get(nummer).waitUntil(Condition.appears, AbstractTest.timeOut);

        logIsGevuldMet(LOGGER, status.get(nummer), teControlerenPolis.getStatus());
        assertThat(status.get(nummer).getText(), is(teControlerenPolis.getStatus()));
        logIsGevuldMet(LOGGER, polisNummer.get(nummer), teControlerenPolis.getPolisNummer());
        assertThat(polisNummer.get(nummer).getText(), is(teControlerenPolis.getPolisNummer()));
        logIsGevuldMet(LOGGER, kenmerk.get(nummer), teControlerenPolis.getKenmerk());
        assertThat(kenmerk.get(nummer).getText(), is(teControlerenPolis.getKenmerk()));
        logIsGevuldMet(LOGGER, ingangsDatum.get(nummer), new LocalDate(teControlerenPolis.getIngangsDatum()).toString("dd-MM-yyyy"));
        assertThat(ingangsDatum.get(nummer).getText(), is(new LocalDate(teControlerenPolis.getIngangsDatum()).toString("dd-MM-yyyy")));
        //        assertThat(eindDatum.get(nummer))
        //        assertThat(eindDatum.get(nummer).getText(), is(new LocalDate(teControlerenPolis.getEindDatum()).toString("dd-MM-yyyy")));
        logIsGevuldMet(LOGGER, wijzigingsDatum.get(nummer), new LocalDate(teControlerenPolis.getWijzigingsDatum()).toString("dd-MM-yyyy"));
        assertThat(wijzigingsDatum.get(nummer).getText(), is(new LocalDate(teControlerenPolis.getWijzigingsDatum()).toString("dd-MM-yyyy")));
        logIsGevuldMet(LOGGER, prolongatieDatum.get(nummer), new LocalDate(teControlerenPolis.getProlongatieDatum()).toString("dd-MM-yyyy"));
        assertThat(prolongatieDatum.get(nummer).getText(), is(new LocalDate(teControlerenPolis.getProlongatieDatum()).toString("dd-MM-yyyy")));
        logIsGevuldMet(LOGGER, maatschappij.get(nummer), teControlerenPolis.getMaatschappij());
        assertThat(maatschappij.get(nummer).getText(), is(teControlerenPolis.getMaatschappij()));
        logIsGevuldMet(LOGGER, premie.get(nummer), "€ " + teControlerenPolis.getPremie() + ",00");
        assertThat(premie.get(nummer).getText(), is("€ " + teControlerenPolis.getPremie() + ",00"));
        logIsGevuldMet(LOGGER, betaalfrequentie.get(nummer), teControlerenPolis.getBetaalfrequentie());
        assertThat(betaalfrequentie.get(nummer).getText(), is(teControlerenPolis.getBetaalfrequentie()));
        logIsGevuldMet(LOGGER, omschrijvingVerzekering.get(nummer), teControlerenPolis.getOmschrijvingVerzekering());
        assertThat(omschrijvingVerzekering.get(nummer).getText(), is(teControlerenPolis.getOmschrijvingVerzekering()));

        titel.get(nummer).click();

        //        status.get(nummer).waitUntil(Condition.disappears, timeOut);
    }

}
