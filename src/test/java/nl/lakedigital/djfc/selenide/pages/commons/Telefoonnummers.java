package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class Telefoonnummers extends AbstractPagina {
    private SelenideElement voegTelefoonNummerToe;

private    List<Telefoonnummer> telefoonnummers;
    private boolean bijContactpersoon;

    public Telefoonnummers(boolean bijContactpersoon) {
        this.bijContactpersoon=bijContactpersoon;
        if(!bijContactpersoon) {
            voegTelefoonNummerToe = $(By.id("voegTelefoonNummerToe"));
        }else{
            voegTelefoonNummerToe = $(By.id("voegTelefoonNummerToeBijContactpersoon"));
        }

        telefoonnummers=new ArrayList<>();
    }

    public List<Telefoonnummer> getTelefoonnummers() {
        return telefoonnummers;
    }

    public void voegTelefoonnummerToe(Logger LOGGER) {
        logKlik(LOGGER, this.voegTelefoonNummerToe);
        this.voegTelefoonNummerToe.click();
        this.telefoonnummers.add(new Telefoonnummer(bijContactpersoon));
    }

    public void controleerTelefoonnummers(Logger LOGGER, List<JsonTelefoonnummer> telefoonnummers) {
        List<JsonTelefoonnummer> teControlerenTelefoonnummers = newArrayList(telefoonnummers);

        for (JsonTelefoonnummer telefoonnummer : telefoonnummers) {
            List<Telefoonnummer> gevondenNummers = this.telefoonnummers.stream().filter(telnr -> telnr.getTelnummer().replace(" ", "").equalsIgnoreCase(telefoonnummer.getTelefoonnummer().replace(" ", ""))).collect(Collectors.toList());

            if (gevondenNummers.size() == 0) {
                fail("Er zijn geen adressen op het scherm te vinden die voldoen aan de criteria");
            }

            Telefoonnummer teControlerenNummer = gevondenNummers.get(0);

            teControlerenNummer.controleerTelefoonnummer(LOGGER, telefoonnummer);
            teControlerenTelefoonnummers.remove(telefoonnummer);

        }

        assertThat(teControlerenTelefoonnummers.size(), is(0));
    }
}
