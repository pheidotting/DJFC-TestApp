package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;


public class Bijlage extends AbstractPagina {
    private ElementsCollection omschrijvingOfBestandsNaam;
    private ElementsCollection omschrijvingOfBestandsNaamEdit;
    private ElementsCollection opslaanOmschrijvingOfBestandsNaam;
    private int i;

    public Bijlage(int i) {
        omschrijvingOfBestandsNaam = $$(By.name("omschrijvingOfBestandsNaam"));
        omschrijvingOfBestandsNaamEdit = $$(By.name("omschrijvingOfBestandsNaamEdit"));
        opslaanOmschrijvingOfBestandsNaam = $$(By.name("opslaanOmschrijvingOfBestandsNaam"));
    }

    public SelenideElement getOmschrijvingOfBestandsNaam() {
        return omschrijvingOfBestandsNaam.get(i);
    }

    public SelenideElement getOmschrijvingOfBestandsNaamEdit() {
        return omschrijvingOfBestandsNaamEdit.get(i);
    }

    public SelenideElement getOpslaanOmschrijvingOfBestandsNaam() {
        return opslaanOmschrijvingOfBestandsNaam.get(i);
    }
}
