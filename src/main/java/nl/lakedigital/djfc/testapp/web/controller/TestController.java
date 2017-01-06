package nl.lakedigital.djfc.testapp.web.controller;

import nl.lakedigital.djfc.testapp.helpers.VerwijderPersoon;
import nl.lakedigital.djfc.testapp.testcases.AbstractTestCase;
import nl.lakedigital.djfc.testapp.testcases.TestCase1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    public enum TestCase {
        TestCase1;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/startTestCase/{testcase}")
    @ResponseBody
    public void startTestCase(@PathVariable("testcase") TestCase testcase) {
        LOGGER.debug("Start TestCase");

        AbstractTestCase testCase=null;
        switch (testcase) {
            case TestCase1:
                LOGGER.debug("TestCase 1");
                testCase=new TestCase1();
        }
        LOGGER.debug("TestCase uitvoeren");
         testCase.run();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijderPersoon/{id}")
    @ResponseBody
    public boolean verwijderPersoon(@PathVariable("id") Long id) {
        new VerwijderPersoon().voeruit(id);
        return true;

    }
}
