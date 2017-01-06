package nl.lakedigital.djfc.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;


public class TestTest {
    @Test
    public void testGoogle(){
        Configuration.reportsFolder = "target/screenshots";

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        System.setProperty("phantomjs.binary.path", "src/test/resources/phantomjs-linux");

//                WebDriverRunner.setWebDriver(new ChromeDriver());
        WebDriverRunner.setWebDriver(new PhantomJSDriver());

        open("http://www.heidotting.nl");

        Selenide.screenshot("heidotting");

        close();
    }
}
