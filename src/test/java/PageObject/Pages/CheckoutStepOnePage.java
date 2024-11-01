package PageObject.Pages;

import PageObject.Locators;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class CheckoutStepOnePage {
    private WebDriver driver;

    public CheckoutStepOnePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Заполнение формы данными покупателя")
    public void fillCheckoutForm() {
        Faker faker=new Faker();
        driver.findElement(Locators.FIRST_NAME_FIELD).sendKeys(faker.name().firstName());
        driver.findElement(Locators.LAST_NAME_FIELD).sendKeys(faker.name().lastName());
        driver.findElement(Locators.POSTAL_CODE_FIELD).sendKeys(faker.address().zipCode());
        driver.findElement(Locators.CONTINUE_BUTTON).click();
    }
}
