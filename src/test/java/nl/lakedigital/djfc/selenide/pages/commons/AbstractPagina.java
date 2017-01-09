package nl.lakedigital.djfc.selenide.pages.commons;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.google.common.collect.Iterables.transform;

public abstract class AbstractPagina {
    private SelenideElement alertSucces = $(By.id("alertSucces"));
    private SelenideElement alertDanger = $(By.id("alertDanger"));

    private SelenideElement homeKnop = $(By.id("homeKnop"));

    //Menu
    private SelenideElement beherenRelatie = $(By.id("beherenRelatie"));
    private SelenideElement menuPolissenTop = $(By.id("menuPolissenTop"));
    private SelenideElement polissen = $(By.id("menuPolissen"));
    private SelenideElement polis = $(By.id("menuPolis"));
    private SelenideElement menuSchadesTop = $(By.id("menuSchadesTop"));
    private SelenideElement schades = $(By.id("menuSchades"));
    private SelenideElement schade = $(By.id("menuSchade"));
    private SelenideElement menuHypothekenTop = $(By.id("menuHypothekenTop"));
    private SelenideElement hypotheken = $(By.id("menuHypotheken"));
    private SelenideElement hypotheek = $(By.id("menuHypotheek"));
    private SelenideElement menuBelastingzakenTop = $(By.id("menuBelastingzakenTop"));
    private SelenideElement aangiftes = $(By.id("menuAangiftes"));
    private SelenideElement aangifte = $(By.id("menuAangifte"));

    private List<SelenideElement>     validationMessages = $$(By.className("validationMessage"));


    public enum MenuItem {
        BEHEREN_RELATIE, POLISSEN, POLIS_TOEVOEGEN, SCHADES, SCHADE_TOEVOEGEN, HYPOTHEKEN, HYPOTHEEK, AANGIFTES, AANGIFTE;
    }

    public SelenideElement getAlertSucces() {
        return alertSucces;
    }

    public SelenideElement getAlertDanger() {
        return alertDanger;
    }

    public void klikHomeKnop(Logger LOGGER) {
        logKlik(LOGGER,homeKnop);
        homeKnop.click();
    }

    public void klikMenuItem(MenuItem menuItem, Logger LOGGER) {
        klikMenuItem(menuItem, LOGGER, null);
    }

    public void klikMenuItem(MenuItem menuItem, Logger LOGGER, SelenideElement wachtTotDitElementZichtbaarIs) {
        if (menuItem == MenuItem.BEHEREN_RELATIE) {
            logKlik(LOGGER,beherenRelatie);
            beherenRelatie.click();
        } else if (menuItem == MenuItem.POLISSEN) {
            klikMenuItem(LOGGER,menuPolissenTop, polissen);
        } else if (menuItem == MenuItem.POLIS_TOEVOEGEN) {
            klikMenuItem(LOGGER,menuPolissenTop, polis);
        } else if (menuItem == MenuItem.SCHADES) {
            klikMenuItem(LOGGER,menuSchadesTop, schades);
        } else if (menuItem == MenuItem.SCHADE_TOEVOEGEN) {
            klikMenuItem(LOGGER,menuSchadesTop, schade);
        }

        if (wachtTotDitElementZichtbaarIs != null) {
            wachtTotDitElementZichtbaarIs.waitUntil(Condition.appears, 2000);
        }
    }

    private void logJavascript(Logger LOGGER) {
//        for (String s : getWebDriverLogs(BROWSER, Level.ALL)) {
//            LOGGER.debug(s.replace("(:)", "").trim());
//        }
    }

    private void klikMenuItem(Logger LOGGER,SelenideElement menuElement, SelenideElement element) {
        logKlik(LOGGER,menuElement);
        menuElement.click();
        element.waitUntil(Condition.appears, 2000);

        logKlik(LOGGER,element);
        element.click();
        element.waitUntil(Condition.disappears, 2000);
    }

    public void logInvullen(Logger LOGGER,SelenideElement element, String waarde) {
        logJavascript(LOGGER);
        LOGGER.debug("invullen {} met : {}", element.getAttribute("id"), waarde);
        Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));
    }

    public void logKlik(Logger LOGGER,SelenideElement element) {
        logJavascript(LOGGER);
        try {
            LOGGER.debug("Klik {}", element.getAttribute("id"));
        } catch (Exception nsee) {
        }
        Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));
    }

    public void logIsAanwezig(Logger LOGGER,SelenideElement element) {
        logJavascript(LOGGER);
        LOGGER.debug("Is {} aanwezig?", element.getAttribute("id"));
        Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));
    }

    public void logIsNietAanwezig(Logger LOGGER,SelenideElement element) {
        logJavascript(LOGGER);
        LOGGER.debug("Is niet aanwezig?");
        Selenide.screenshot(bepaalBestandsNaam(LOGGER, element));
    }

    public void logIsGevuldMet(Logger LOGGER,SelenideElement element, String verwachteWaarde) {
        logJavascript(LOGGER);
        LOGGER.debug("Is {} gevuld met {}?", element.getAttribute("id"), verwachteWaarde);
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

    protected void wachtFf() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected List<String> optionToStringList(SelenideElement element, String initieleWaarde, boolean initieleWaardeMeenemen) {
        element.waitUntil(Condition.text(initieleWaarde), 2500);

        Select selectList = new Select(element);
        Iterable<String> ret = transform(selectList.getOptions(), new Function<WebElement, String>() {
            @Override
            public String apply(WebElement webElement) {
                return webElement.getText();
            }
        });

        List<String> a = Lists.newArrayList();
        for (String s : ret) {
            if (s.equals(initieleWaarde)) {
                if (initieleWaardeMeenemen) {
                    a.add(s);
                }
            } else {
                a.add(s);
            }
        }
        return a;
    }

    public List<SelenideElement> getValidationMessages() {
        return validationMessages.stream().filter(new Predicate<SelenideElement>() {
            @Override
            public boolean test(SelenideElement element) {
                return element.isDisplayed();
            }
        }).collect(Collectors.toList());
    }
}
