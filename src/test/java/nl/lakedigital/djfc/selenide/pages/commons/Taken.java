package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.Assert.assertFalse;

public class Taken extends AbstractPagina {
    private final static Logger LOGGER = LoggerFactory.getLogger(Taken.class);

    private SelenideElement voegTaakToe;

    public Taken() {
        voegTaakToe = $(By.id("voegTaakToe"));
    }

    public void checkNietAanwezig() {
        assertFalse(voegTaakToe.is(Condition.visible));
    }

}
