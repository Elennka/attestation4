package saucedemo;

import PageObject.Locators;
import PageObject.Pages.LoginPage;
import ext.EnvProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;



public class AuthTests {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(EnvProperties.getEnvProperties("url"));
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешная авторизация стандартного пользователя")
    public void testSuccessfulLogin() throws IOException {
        loginPage.login(EnvProperties.getEnvProperties("standard_user"), EnvProperties.getEnvProperties("standard_user_psw"));
        assertTrue(driver.getCurrentUrl().contains(EnvProperties.getEnvProperties("inventory_url")));
    }
    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Авторизация заблокированного пользователя")
    public void testBlockedUserLogin() throws IOException {
        loginPage.login(EnvProperties.getEnvProperties("locked_user"), EnvProperties.getEnvProperties("locked_user_psw"));
        assertTrue(driver.findElement(Locators.ERROR_MESSAGE).isDisplayed());
    }


}
