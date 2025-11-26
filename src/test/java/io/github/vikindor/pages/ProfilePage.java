package io.github.vikindor.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ProfilePage {

    SelenideElement userName = $("#userName-value");
    SelenideElement tableNoData = $(".rt-noData");
    SelenideElement modalOkButton = $("#closeSmallModal-ok");
    ElementsCollection books = $$(".rt-tr-group");
    String deleteIcon = "#delete-record-undefined";
    String tableNoDataText = "No rows found";

    @Step("Open Profile page")
    public ProfilePage openPage() {
        open("/profile");
        return this;
    }

    @Step("Add cookie to WebDriver")
    public ProfilePage addCookie(String userId, String token, String expires) {
        open("/favicon.png");
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("token", token));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        return this;
    }

    @Step("Check that profile has a book")
    public ProfilePage checkThatProfileHasABook(String user, String bookIsbn) {
        userName.shouldHave(text(user));

        books.stream()
             .filter(el -> el.$("a[href*='" + bookIsbn + "']").exists())
             .findFirst()
             .orElseThrow(() -> new AssertionError("Book with ISBN " + bookIsbn + " not found"));

        return this;
    }

    @Step("Delete a book by ISBN")
    public ProfilePage deleteBook(String user, String bookIsbn) {
        userName.shouldHave(text(user));

        SelenideElement row =
                books.stream()
                     .filter(el -> el.$("a[href*='" + bookIsbn + "']").exists())
                     .findFirst()
                     .orElseThrow(() -> new AssertionError("Book with ISBN " + bookIsbn + " not found"));

        row.$(deleteIcon).click();
        modalOkButton.click();
        return this;
    }

    @Step("Check that profile has no books")
    public ProfilePage checkThatProfileHasNoBooks(String user) {
        userName.shouldHave(text(user));
        tableNoData.shouldBe(visible).shouldHave(text(tableNoDataText));
        return this;
    }
}
