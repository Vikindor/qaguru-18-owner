package io.github.vikindor.tests;

import io.github.vikindor.api.BookStoreApi;
import io.github.vikindor.extensions.*;
import org.junit.jupiter.api.Test;

import static io.github.vikindor.data.TestData.*;
import static io.github.vikindor.specs.BookStoreSpecs.responseSpec;
import static io.qameta.allure.Allure.step;

@WithLogin
public class BookStoreWithLoginTests extends TestBase {

    @Test
    void shouldAddBookAndRemoveItFromListThroughApi() {
        String userId = AuthContext.getUserId();
        String token = AuthContext.getToken();
        String expires = AuthContext.getExpires();

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
        String userId = AuthContext.getUserId();
        String token = AuthContext.getToken();
        String expires = AuthContext.getExpires();

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
