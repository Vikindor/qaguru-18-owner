package io.github.vikindor.extensions;

import io.github.vikindor.api.BookStoreApi;
import io.github.vikindor.data.TestData;
import io.github.vikindor.models.LoginBodyModel;
import io.restassured.response.Response;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class WithLoginExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        WithLogin annotation = context.getTestMethod()
                                      .map(m -> m.getAnnotation(WithLogin.class))
                                      .orElseGet(() -> context.getTestClass()
                                                              .map(c -> c.getAnnotation(WithLogin.class))
                                                              .orElse(null));

        if (annotation == null) {
            return;
        }

        LoginBodyModel loginBody;
        if (!annotation.username().isEmpty() || !annotation.password().isEmpty()) {
            loginBody = new LoginBodyModel(annotation.username(), annotation.password());
        } else {
            loginBody = TestData.AUTH_DATA;
        }

        Response response = BookStoreApi.doAuth(loginBody);

        String userId = response.jsonPath().getString("userID");
        if (userId == null) {
            userId = response.jsonPath().getString("userId");
        }
        String token = response.jsonPath().getString("token");
        String expires = response.jsonPath().getString("expires");

        AuthContext.set(token, userId, expires);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        AuthContext.clear();
    }
}
