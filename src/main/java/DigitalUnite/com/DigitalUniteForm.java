package DigitalUnite.com;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DigitalUniteForm {

    WebDriver driver;
    String url = "https://www.digitalunite.com/practice-webform-learners";

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }

    @Test
    @DisplayName("Fill up and submit the form")
    public void fillAndSubmitForm() throws InterruptedException {
        driver.get(url);

        driver.findElement(By.id("onetrust-accept-btn-handler")).click(); //accept all cookies from pop-up window

        driver.findElement(By.id("edit-name")).sendKeys("Test"); //name
        driver.findElement(By.id("edit-number")).sendKeys("12345678"); //number


        WebElement date= driver.findElement(By.id("edit-date"));
        date.click();
        date.sendKeys("15042025"); //date

        driver.findElement(By.id("edit-email")).sendKeys("test@test.com"); //email
        driver.findElement(By.id("edit-tell-us-a-bit-about-yourself-")).sendKeys("I am automation king!"); //something

        scroll(0,500);

        String filePath = "E:\\Downloads (Chrome)\\demo.jpg"; //file upload
        driver.findElement(By.id("edit-uploadocument-upload")).sendKeys(filePath);

        Thread.sleep(2000); //for upload wait

        driver.findElement(By.id("edit-age")).click(); //(check this box when you have completed the form)

        Thread.sleep(1000);

        driver.findElement(By.id("edit-submit")).click();

        //next-page
        String message = driver.findElement(By.id("block-pagetitle-2")).getText();
        Assertions.assertTrue(message.contains("Thank you for your submission!")); //assertion
    }

    public void scroll(int x , int y){ //scroll method
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("window.scroll(" + x + "," + y + ")");

    }

    @AfterAll
    void tearDown() {
        driver.quit(); //quit browser
    }
}