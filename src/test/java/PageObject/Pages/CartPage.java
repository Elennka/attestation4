package PageObject.Pages;

import PageObject.Locators;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private WebDriver driver;


    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Переход на страницу ввода данных покупателя")
    public void checkout() {

        driver.findElement(Locators.CHECKOUT_BUTTON).click();
    }
    public int getCartItemCount() {
        // Найдите элемент, представляющий количество товаров в корзине, и верните его значение
        driver.findElement(Locators.CART_ITEM_COUNT); // Замените на ваш локатор
        return Integer.parseInt(driver.findElement(Locators.CART_ITEM_COUNT).getText());
    }
}
