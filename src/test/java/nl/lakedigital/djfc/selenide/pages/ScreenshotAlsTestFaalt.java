package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.Selenide;
import nl.lakedigital.djfc.tests.AbstractTest;
import org.joda.time.LocalDateTime;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.logging.Level;

import static com.codeborne.selenide.Selenide.getJavascriptErrors;
import static com.codeborne.selenide.Selenide.getWebDriverLogs;
import static org.openqa.selenium.logging.LogType.BROWSER;


public class ScreenshotAlsTestFaalt extends TestWatcher {
    private final static Logger LOGGER = LoggerFactory.getLogger(ScreenshotAlsTestFaalt.class);
    private AbstractTest abstractTest;
    private Map<String, Boolean> toggles;

    public void setAbstractTest(AbstractTest abstractTest) {
        this.abstractTest = abstractTest;
    }

    public void setToggles(Map<String, Boolean> toggles) {
        this.toggles = toggles;
    }

    @Override
    protected void failed(Throwable e, Description description) {
        LOGGER.debug("#################################");
//        for (String s : getWebDriverLogs(BROWSER, Level.ALL)) {
//            LOGGER.debug(s);//.replace("(:)", "").trim());
//        }
        LOGGER.debug("#################################");
        for (String s : getJavascriptErrors()) {
            LOGGER.debug(s);//.replace("(:)", "").trim());
        }
        LOGGER.debug("#################################");



        String tijdstip = LocalDateTime.now().toString("HH:mm:ss");

        Selenide.screenshot(tijdstip + " test failure");

        for (String toggle : toggles.keySet()) {
            abstractTest.setFeatureToggle(toggle, toggles.get(toggle));
        }
    }
}