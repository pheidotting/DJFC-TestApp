package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class ZoekScherm extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(ZoekScherm.class);

    private SelenideElement nieuweRelatie = $(By.id("nieuweRelatie"));
    private SelenideElement nieuwBedrijf = $(By.id("nieuwBedrijf"));

    public void klikNieuweRelatie() {
        logKlik(LOGGER, this.nieuweRelatie);
        klik(this.nieuweRelatie);
    }

    public void klikNieuwBedrijf() {
        logKlik(LOGGER, this.nieuwBedrijf);
        klik(this.nieuwBedrijf);
    }
}
