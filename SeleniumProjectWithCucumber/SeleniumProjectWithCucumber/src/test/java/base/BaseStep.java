package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BaseStep {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor jsExecutor;

    public BaseStep() {
    }

    public void setUp() {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\springBoot\\SeleniumProjectWithCucumber\\chromedriver.exe");
            WebDriverManager.chromedriver().setup(); // sadece bu satır yeterli
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            jsExecutor = (JavascriptExecutor) driver;
    }

    public void getURL() {
        driver.get("https://kariyer.baykartech.com/tr/");
    }

    public void driverQuit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void clickElement(By selector) {
        try {
            jsExecutor.executeScript("arguments[0].style.zIndex = '9999';", findElement(selector));
            findElement(selector).click();
            Thread.sleep(100);
        } catch (Exception e) {
            jsExecutor.executeScript("arguments[0].click();", driver.findElement(selector));
        }
    }


    public void clickElement(WebElement element) {
        try {
            jsExecutor.executeScript("arguments[0].style.zIndex = '9999';", element);
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            jsExecutor.executeScript("arguments[0].click();", element); //Eğer exception düşerse js ile tıklamayı tekrar deniyor
        }
    }


    public void javaScriptClickElement(By selector) {
        jsExecutor.executeScript("arguments[0].click();", driver.findElement(selector));
    }


    public void PageScrolldown() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,300)", "");
    }

    public void PageScrollup() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-300)", "");
    }

    public boolean isElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectFromDropdown(By locator, String value) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        dropdown.sendKeys(value);
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    public String getElementText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }

    public String getElementValue(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getAttribute("value");
    }

    public boolean isCheckboxSelected(By locator) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(locator));
        return checkbox.isSelected();
    }

    public boolean isElementEnabled(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        return element.isEnabled();
    }

    public void waitForPageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void sendKeys(By selector, String text) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(selector)).sendKeys(text);
        } catch (Exception e) {
            jsExecutor.executeScript("arguments[0].value='" + text + "';", driver.findElement(selector));
        }
    }

    public void clearElement(By selector) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(selector)).clear();

        } catch (Exception e) {
            findElement(selector).sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        }
    }

    public String getText(By selector) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(selector)).getText();
        } catch (Exception e) {
            return jsExecutor.executeScript("return arguments[0].text", findElement(selector)).toString();
        }
    }

    public WebElement findElement(By selector) {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(selector),
                    ExpectedConditions.visibilityOfElementLocated(selector)
            ));
            return driver.findElement(selector);
        } catch (Exception e) {
            return (WebElement) jsExecutor.executeScript("return document.evaluate(\"" + selector.toString().substring(10) + "\",document,null,XPathResult.FIRST_ORDERED_NODE_TYPE,null).singleNodeValue;");
        }
    }

    public boolean isElementDisplayed(By selector, int waitSecond) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitSecond));
            return driver.findElement(selector).isDisplayed();
        } catch (WebDriverException e) {
            return false;
        }
    }

    public boolean isElementSelected(By selector, int waitSecond) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitSecond));
            return driver.findElement(selector).isSelected();
        } catch (WebDriverException e) {
            return false;
        }
    }

    public boolean isElementEnabled(By selector, int waitSecond) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitSecond));
            return driver.findElement(selector).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public String getAttribute(By selector, String attribute) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(selector)).getAttribute(attribute);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void closeTab() {
        try {
            if (!driver.getWindowHandles().isEmpty()) {
                driver.close();
            }
            driver.switchTo().defaultContent();
        } catch (org.openqa.selenium.NoSuchWindowException e) {
            System.out.println("Pencere kapalı: " + e.getMessage());
        }
    }

    public void directUrl(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.urlToBe(url));
    }

    public void javaScriptFocusElement(By selector) {
        jsExecutor.executeScript("arguments[0].focus();", driver.findElement(selector));
    }
}