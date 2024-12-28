package steps;

import base.BaseStep;
import io.cucumber.java.en.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginStep extends BaseStep {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor jsExecutor;

    @Given("Baykar Kariyer web sitesi acilir")
    public void baykarKariyerWebSitesiAcilir() {
        setUp();
        getURL();
    }
}