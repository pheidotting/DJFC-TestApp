package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

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
}
