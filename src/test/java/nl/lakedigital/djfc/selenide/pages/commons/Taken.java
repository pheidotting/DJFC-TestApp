package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.Assert.assertFalse;

public class Taken extends AbstractPagina {
    private SelenideElement voegTaakToe = $(By.id("voegTaakToe"));

    public void checkNietAanwezig() {
        assertFalse(voegTaakToe.is(Condition.visible));
    }

}
