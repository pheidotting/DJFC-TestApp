package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Telefoonnummer extends AbstractPagina{
    private final static Logger LOGGER= LoggerFactory.getLogger(Telefoonnummer.class);

private SelenideElement
    telnummer;
    private SelenideElement
            soorttelnummer;
    private SelenideElement
            telefoonomschrijving;
    private SelenideElement

            verwijderTelefoonNummer;

    public Telefoonnummer(boolean bijContactpersoon) {
        String bc = "";
        if(bijContactpersoon){
            bc="BijContactpersoon";
        }
        telnummer=$(By.id("telnummer"+bc));
                soorttelnummer=$(By.id("soorttelnummer"+bc));
                telefoonomschrijving=$(By.id("telefoonomschrijving"+bc));
                verwijderTelefoonNummer=$(By.id("verwijderTelefoonNummer"+bc));
    }

    public void vulTelefoonnummer(String telnummer, String telnummerLang, String soorttelnummer, String telefoonomschrijving) {
        logInvullen(this.telnummer,telnummer,LOGGER);
        this.telnummer.setValue(telnummer);
        logInvullen(this.soorttelnummer,soorttelnummer,LOGGER);
        this.soorttelnummer.selectOption(soorttelnummer);
        logInvullen(this.telefoonomschrijving,telefoonomschrijving,LOGGER);
        this.telefoonomschrijving.setValue(telefoonomschrijving);

        logIsGevuldMet(this.telnummer,telnummerLang,LOGGER);
        assertThat(this.telnummer.getValue(),is(telnummerLang));
    }
}
