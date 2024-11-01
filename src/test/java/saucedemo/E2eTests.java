package saucedemo;

import PageObject.Locators;
import PageObject.Pages.*;
import ext.EnvProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.qameta.allure.Allure;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class E2eTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CheckoutStepTwoPage checkoutStepTwoPage;

    @BeforeEach
    public void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(EnvProperties.getEnvProperties("url"));
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepTwoPage = new CheckoutStepTwoPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }


    @ParameterizedTest
    @CsvSource({
            "performance_glitch_user, performance_glitch_user_psw, Total: $58.29,3",
            "standard_user, standard_user_psw, Total: $58.29,3"
    })
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("E2E тест для разных пользователей")
    public void E2ETestFromLoginToCheckout(String username, String password, String expectedSum, int expectedItemCount) throws IOException {
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setDescription("Тест для пользователя: " + username)
        );

        loginPage.login(EnvProperties.getEnvProperties(username), EnvProperties.getEnvProperties(password));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.INVENTORY_LIST));
        inventoryPage.addItemsToCart();
        inventoryPage.goToCart();
        assertEquals(expectedItemCount, cartPage.getCartItemCount(), "Количество товаров в корзине не совпадает с ожидаемым");
        cartPage.checkout();
        checkoutStepOnePage.fillCheckoutForm();
        assertEquals(checkoutStepTwoPage.getTotalPrice(), expectedSum);
        checkoutStepTwoPage.finishCheckout();
    }
}

