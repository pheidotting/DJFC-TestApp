package nl.lakedigital.djfc.tests.commons;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.selenide.pages.BeherenBedrijf;
import nl.lakedigital.djfc.selenide.pages.Hypotheek;
import nl.lakedigital.djfc.selenide.pages.LijstBedrijven;
import nl.lakedigital.djfc.selenide.pages.gegevens.HypotheekGegevens;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.google.common.collect.Lists.newArrayList;

public class BeherenHypotheekTest extends AbstractPaginaTest {
    private Lorem lorem = LoremIpsum.getInstance();
    private HypotheekGegevens hypotheekGegevens = new HypotheekGegevens<String>() {
        @Override
        public String bank() {
            return lorem.getWords(3);
        }

        @Override
        public String select_hypotheekVorm() {
            //            Annuiteiten
            //                    Lineair
            //            Aflossingsvrij
            //                    Bankspaar
            //            Spaar
            //                    Hybride
            //            Levens

            return "Annuiteiten";
        }

        @Override
        public String koppelHypotheek() {
            return null;
        }

        @Override
        public String leningNummer() {
            return lorem.getWords(5);
        }

        @Override
        public String hypotheekBedrag() {
            return "123456";
        }

        @Override
        public String boxI() {
            return "123456";
        }

        @Override
        public String boxIII() {
            return "123456";
        }

        @Override
        public String rente() {
            return "2";
        }

        @Override
        public String marktWaarde() {
            return "123456";
        }

        @Override
        public String onderpand() {
            return "123456";
        }

        @Override
        public String koopsom() {
            return "123456";
        }

        @Override
        public String vrijeVerkoopWaarde() {
            return "123456";
        }

        @Override
        public String taxatieDatum() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            return LocalDate.now().format(dateTimeFormatter);
        }

        @Override
        public String wozWaarde() {
            return "123456";
        }

        @Override
        public String waardeVoorVerbouwing() {
            return "123456";
        }

        @Override
        public String waardeNaVerbouwing() {
            return "123456";
        }

        @Override
        public String ingangsDatum() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            return LocalDate.now().format(dateTimeFormatter);
        }

        @Override
        public String duur() {
            return "30";
        }

        @Override
        public String eindDatum() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            return LocalDate.now().format(dateTimeFormatter);
        }

        @Override
        public String ingangsDatumRenteVastePeriode() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            return LocalDate.now().format(dateTimeFormatter);
        }

        @Override
        public String duurRenteVastePeriode() {
            return "10";
        }

        @Override
        public String eindDatumRenteVastePeriode() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            return LocalDate.now().format(dateTimeFormatter);
        }

        @Override
        public String omschrijving() {
            return lorem.getWords(100);
        }

        @Override
        public String opslaanBedrijf() {
            return null;
        }
    };

    public BeherenHypotheekTest(BeherenBedrijf beherenBedrijf, LijstBedrijven lijstBedrijven) {
        super(new BeherenBedrijf(), new LijstBedrijven());
    }

    public void invullenGegevens(HypotheekGegevens hypotheekGegevens) {

    }

    public void logInvullen(Logger LOGGER, SelenideElement element, String waarde) {
        LOGGER.debug("invullen {} met : {}", element.getAttribute("id"), waarde);
        Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));
    }

    private String bepaalBestandsNaam(Logger LOGGER, SelenideElement element) {
        String packa = this.getClass().getPackage().toString();
        String loggerNaam = "package " + LOGGER.getName();
        String tijdstip = LocalDateTime.now().toString("HH:mm:ss");

        String idOfNaam = element.getAttribute("id");
        if (idOfNaam == null || "".equals(idOfNaam)) {
            idOfNaam = element.getAttribute("name");
        }

        try {
            return tijdstip + " " + loggerNaam.replace(packa + ".", "") + " " + idOfNaam;
        } catch (NoSuchElementException nsee) {
            return tijdstip + " " + loggerNaam.replace(packa + ".", "");
        }
    }

    public void invullenGegevens(Logger LOGGER) {
        invullen(LOGGER, new Hypotheek(), hypotheekGegevens);
        hypotheek.klikOpslaan(LOGGER);
    }

    public void invullen(Logger LOGGER, HypotheekGegevens pagina, HypotheekGegevens gegevens) {
        for (Method method : ReflectionUtils.getAllDeclaredMethods(HypotheekGegevens.class)) {
            if (!"opslaanBedrijf".equals(method.getName())) {
                try {
                    SelenideElement selenideElement = (SelenideElement) method.invoke(pagina);
                    Method g = newArrayList(ReflectionUtils.getAllDeclaredMethods(HypotheekGegevens.class)).stream().filter(m -> m.getName().equals(method.getName())).findFirst().get();

                    String inTeVullenGegevens = (String) g.invoke(gegevens);

                    logInvullen(LOGGER, selenideElement, inTeVullenGegevens);

                    if (inTeVullenGegevens != null) {
                        if (method.getName().startsWith("select_")) {
                            selenideElement.selectOption(inTeVullenGegevens);
                        } else {
                            selenideElement.setValue(inTeVullenGegevens);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
