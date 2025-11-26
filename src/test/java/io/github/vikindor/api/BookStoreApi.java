package io.github.vikindor.api;

import io.github.vikindor.models.AddBooksModel;
import io.github.vikindor.models.IsbnModel;
import io.github.vikindor.models.LoginBodyModel;
import io.github.vikindor.models.RemoveBookModel;
import io.restassured.response.Response;

import java.util.List;

import static io.github.vikindor.specs.BookStoreSpecs.requestSpec;
import static io.restassured.RestAssured.given;

public final class BookStoreApi {
    private BookStoreApi() {}

    public static Response doAuth(LoginBodyModel authData) {
        return given(requestSpec())
                .body(authData)
                .when()
                .post("/Account/v1/Login");
    }

    public static Response addBook(String userId, String token, List<IsbnModel> booksList) {
        return given(requestSpec())
                .header("Authorization", "Bearer " + token)
                .body(new AddBooksModel(userId, booksList))
                .when()
                .post("/BookStore/v1/Books");
    }

    public static Response deleteBook(String userId, String token, String bookIsbn) {
        return given(requestSpec())
                .header("Authorization", "Bearer " + token)
                .body(new RemoveBookModel(userId, bookIsbn))
                .when()
                .delete("/BookStore/v1/Book");
    }
}
