package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class Rekeningnummer extends AbstractPagina{
    private final static Logger LOGGER = LoggerFactory.getLogger(Rekeningnummer.class);

    private SelenideElement

            rekeningnummernummer;private SelenideElement

            bic;private SelenideElement verwijderRekening;

    public Rekeningnummer() {
        this.rekeningnummernummer = $(By.id("rekeningnummernummer"));
        this.bic = $(By.id("bic"));
        this.verwijderRekening = $(By.id("verwijderRekening"));
    }

    public void vulRekeningnummer(String nummer,String nummerLang, String bic){
        if(nummer!=null){
            logInvullen(this.rekeningnummernummer,nummer,LOGGER);
            this.rekeningnummernummer.setValue(nummer);
            this.rekeningnummernummer.sendKeys(Keys.TAB);
        }if(bic!=null){
            logInvullen(this.bic,bic,LOGGER);this.bic.setValue(bic);
        }
        if(nummerLang!=null){
            logIsGevuldMet(this.rekeningnummernummer,nummerLang,LOGGER);
            assertThat(this.rekeningnummernummer.getValue(),is(nummerLang));
        }
    }
    public void verwijderRekening(){
        logKlik(this.verwijderRekening,LOGGER);
        this.verwijderRekening.click();
    }
}
