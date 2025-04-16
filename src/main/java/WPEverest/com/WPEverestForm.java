package WPEverest.com;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WPEverestForm {

    WebDriver driver;
    WebDriverWait wait;
    String url = "https://demo.wpeverest.com/user-registration/guest-registration-form/";

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);
    }

    @Test
    @DisplayName("Fill and submit user registration form")
    void fillAndSubmitRegistrationForm() throws InterruptedException {

        driver.findElement(By.id("first_name")).sendKeys("Test First"); //firstname

        driver.findElement(By.id("last_name")).sendKeys("Test Last"); //lastname

        randomEmail(); //email

        driver.findElement(By.id("radio_1665627729_Male")).click(); //click on gender

        driver.findElement(By.id("user_pass")).sendKeys("0qwertY1234@"); //password

        datePicker(); //select date of birth

        driver.findElement(By.id("country_1665629257")).sendKeys("Bangladeshi"); //Nationality

        driver.findElement(By.id("input_box_1665629217")).sendKeys("Canadian"); //Country

        numberPicker(); //select phone numbers

        driver.findElement(By.id("number_box_1665629930")).sendKeys("12"); //intended length of stay

        driver.findElement(By.id("input_box_1665630010")).sendKeys("A12"); //room and bed number

        driver.findElement(By.id("textarea_1665630078")).sendKeys("SQA Engineer,Dhaka"); //occupation

        driver.findElement(By.id("radio_1665627931_Yes")).click(); //parking

        driver.findElement(By.id("radio_1665627997_Single Room")).click(); //room preferance

        driver.findElement(By.id("radio_1665628131_None")).click(); //dietary restrictions


        Select option = new Select(driver.findElement(By.id("select_1665628361"))); //what activities will you attend
        option.selectByVisibleText("Luncheon");

        driver.findElement(By.id("privacy_policy_1665633140")).click(); // click terms and conditions

        driver.findElement(By.cssSelector("button[type=submit]")).click(); //click submit

        Thread.sleep(1000);

        String message = driver.findElement(By.cssSelector("div[id='ur-submit-message-node'] ul")).getText();
        Assertions.assertTrue(message.contains("User successfully registered.")); //assertion
    }

    void randomEmail() {
        WebElement email = driver.findElement(By.id("user_email"));
        int randomNum = (int)(Math.random() * 101); // 0 to 100
        String randomNumString = String.valueOf(randomNum);
        String Semail = "johncena" + randomNumString + "@gmail.com";
        email.sendKeys(Semail); //email
    }

    void numberPicker() {
                scroll(0,500);

        List<WebElement> phoneField= driver.findElements(By.id("phone_1665627880")); //phone
        phoneField.get(1).sendKeys("1234567890");

        List<WebElement> EphoneField= driver.findElements(By.id("phone_1665627865")); //emergency contact
        EphoneField.get(1).sendKeys("1234567890");

    }

    void datePicker() {

//        List<WebElement> dateField= driver.findElements(By.id("date_box_1665628538")); //phone
//        dateField.get(0).sendKeys("2025-04-06");


        //working solution

        WebElement dobInput = driver.findElement(By.cssSelector("input.flatpickr-input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('click', { bubbles: true }))", dobInput);


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeContains(dobInput, "class", "active"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flatpickr-calendar.open")));


        WebElement today = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("span.flatpickr-day.today")));
        today.click();

    }

    @AfterAll
    void tearDown() {
        driver.quit();
    }

    public void scroll(int x , int y){ //scroll method
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("window.scroll(" + x + "," + y + ")");

    }
}
