package io.github.vikindor.tests;

import io.github.vikindor.api.BookStoreApi;
import io.github.vikindor.pages.ProfilePage.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.vikindor.data.TestData.*;
import static io.github.vikindor.specs.BookStoreSpecs.*;
import static io.qameta.allure.Allure.step;

public class BookStoreTests extends TestBase {

    String userId;
    String token;
    String expires;

    @BeforeEach
    void doAuthAndExtractLoginData() {
        Response response = step("Make authorization request", () ->
                BookStoreApi.doAuth(AUTH_DATA)
                            .then()
                            .spec(responseSpec(200))
                            .extract().response()
        );

        this.userId = response.path("userId");
        this.token = response.path("token");
        this.expires = response.path("expires");
    }

    @Test
    void shouldAddBookAndRemoveItFromListThroughApi() {
        step("Make request to add a book", () ->
                BookStoreApi.addBook(userId, token, BOOKS_LIST)
                            .then()
                            .spec(responseSpec(201))
        );

        step("Make request to delete a book", () ->
                BookStoreApi.deleteBook(userId, token, BOOK_ISBN)
                            .then()
                            .spec(responseSpec(204))
        );

        step("Open Profile page and check that list is empty", () ->
                profilePage
                        .addCookie(userId, token, expires)
                        .openPage()
                        .checkThatProfileHasNoBooks(AUTH_DATA.getUserName())
        );
    }

    @Test
    void shouldAddBookAndRemoveItFromListThroughUI() {
        step("Make request to add a book", () ->
                BookStoreApi.addBook(userId, token, BOOKS_LIST)
                            .then()
                            .spec(responseSpec(201))
        );

        step("Open Profile, delete a book, and check that list is empty", () ->
                profilePage
                        .addCookie(userId, token, expires)
                        .openPage()
                        .checkThatProfileHasABook(AUTH_DATA.getUserName(), BOOK_ISBN)
                        .deleteBook(AUTH_DATA.getUserName(), BOOK_ISBN)
                        .checkThatProfileHasNoBooks(AUTH_DATA.getUserName())
        );
    }
}
