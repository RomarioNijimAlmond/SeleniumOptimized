package pages.BasePages;

import enums.ApplicationUrl;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {


    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected Alert alert;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofMillis(15));
        actions = new Actions(driver);
    }

    public void clickElement(Object elementOrLocator) {
        if (elementOrLocator instanceof WebElement) {
            WebElement element = (WebElement) elementOrLocator;
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
            } finally {
                element.click();
            }
        } else if (elementOrLocator instanceof String) {
            String locator = (String) elementOrLocator;
            By by;
            if (locator.startsWith("//") || locator.startsWith("/")) {
                by = By.xpath(locator);
            } else {
                by = By.cssSelector(locator);
            }
            WebElement locatedElement = driver.findElement(by);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locatedElement));
            } finally {
                locatedElement.click();
            }
        } else {
            throw new IllegalArgumentException("Unsupported type of element " + elementOrLocator);
        }
    }

    public void fillText(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
    }

    public void waitVisibilityOfElement(Object element) {
        WebElement el;
        if (element instanceof WebElement) {
            el = (WebElement) element;
            wait.until(ExpectedConditions.visibilityOf(el));
        } else if (element instanceof String) {
            String stringElement = (String) element;
            WebElement stringLocator = driver.findElement(By.xpath(stringElement));

            wait.until(ExpectedConditions.visibilityOf(stringLocator));
        }
    }

    public void waitFotInvisibilityOfElement(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void hover(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    public void alertAccept() {
        alert = driver.switchTo().alert();
        alert.accept();
    }

    public String getText(Object element) {
        WebElement el;
        if (element instanceof WebElement) {
            el = (WebElement) element;
            return el.getText();
        } else if (element instanceof String) {
            String locator = (String) element;
            WebElement stringElement = driver.findElement(By.cssSelector(locator));
            return stringElement.getText();
        }
        throw new IllegalArgumentException("text not found");
    }

    public void selectItemByValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void returnInputFieldValues(WebElement element) {

    }

    public void switchToIframe(WebElement element) {
        driver.switchTo().frame(element);
    }

    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }

    public void sleep(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goTo(ApplicationUrl applicationUrl) {
        String url = applicationUrl.getUrl();
        driver.get(url);
    }

    public void waitUntilElementIsPresent(Object element) {
        if (element instanceof WebElement) {
            WebElement el = (WebElement) element;
        }
        wait.until(ExpectedConditions.presenceOfElementLocated((By) element));
    }
}
