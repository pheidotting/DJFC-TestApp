package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class Bijlages extends AbstractPagina {
    public enum UploadBestand {
        EEN_PDF("1.pdf"), TWEE_PDF("2.pdf"), PDF_ZIP("pdf.zip");

        UploadBestand(String bestandsNaam) {
            this.bestandsNaam = bestandsNaam;
        }

        public String getBestandsNaam() {
            return bestandsNaam;
        }

        private String bestandsNaam;
    }

    private SelenideElement uploadknop;
    private List<Bijlage> bijlages;

    public Bijlages() {
        uploadknop=$(By.id("bijlageFile"));

        bijlages=new ArrayList<>();
    }

    public List<Bijlage> getBijlages() {
        return bijlages;
    }

    public void uploadFile(Logger LOGGER, UploadBestand uploadBestand) {
        logKlik(LOGGER, uploadknop);
        uploadknop.uploadFromClasspath(uploadBestand.getBestandsNaam());
        uploadknop.waitUntil(Condition.empty,2500);

        bijlages.add(new Bijlage(bijlages.size()));
    }
}
