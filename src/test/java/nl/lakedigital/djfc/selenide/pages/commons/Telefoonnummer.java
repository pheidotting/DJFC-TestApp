package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Telefoonnummer extends AbstractPagina {
    private SelenideElement telnummer;
    private SelenideElement soorttelnummer;
    private SelenideElement telefoonomschrijving;
    private SelenideElement verwijderTelefoonNummer;

    public Telefoonnummer(boolean bijContactpersoon) {
        String bc = "";
        if (bijContactpersoon) {
            bc = "BijContactpersoon";
        }
        telnummer = $(By.id("telnummer" + bc));
        soorttelnummer = $(By.id("soorttelnummer" + bc));
        telefoonomschrijving = $(By.id("telefoonomschrijving" + bc));
        verwijderTelefoonNummer = $(By.id("verwijderTelefoonNummer" + bc));
    }

    public void vulTelefoonnummer(Logger LOGGER, String telnummer, String telnummerLang, String soorttelnummer, String telefoonomschrijving) {
        logInvullen(LOGGER, this.telnummer, telnummer);
        this.telnummer.setValue(telnummer);
        logInvullen(LOGGER, this.soorttelnummer, soorttelnummer);
        this.soorttelnummer.selectOption(soorttelnummer);
        logInvullen(LOGGER, this.telefoonomschrijving, telefoonomschrijving);
        this.telefoonomschrijving.setValue(telefoonomschrijving);

        logIsGevuldMet(LOGGER, this.telnummer, telnummerLang);
        assertThat(this.telnummer.getValue(), is(telnummerLang));
    }

    public String getTelnummer() {
        return telnummer.getValue();
    }

    public void controleerTelefoonnummer(Logger LOGGER, JsonTelefoonnummer telefoonnummer) {
        controleerVeld(LOGGER, this.telnummer, telefoonnummer.getTelefoonnummer());
        logIsGevuldMet(LOGGER, this.soorttelnummer, telefoonnummer.getSoort());
        assertThat(this.soorttelnummer.getSelectedText(), is(telefoonnummer.getSoort()));
        controleerVeld(LOGGER, this.telefoonomschrijving, telefoonnummer.getOmschrijving());
    }
}
