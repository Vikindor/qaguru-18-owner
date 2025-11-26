package io.github.vikindor.data;

import io.github.vikindor.config.ProjectConfig;
import io.github.vikindor.models.IsbnModel;
import io.github.vikindor.models.LoginBodyModel;
import org.aeonbits.owner.ConfigFactory;

import java.util.List;

public class TestData {

    private static final ProjectConfig cfg = ConfigFactory.create(ProjectConfig.class, System.getProperties());

    protected static String userName = cfg.userName();
    protected static String userPassword = cfg.userPassword();

    public static final LoginBodyModel AUTH_DATA = new LoginBodyModel(userName, userPassword);
    public static final String BOOK_ISBN = "9781491904244"; // Book: "You Don't Know JS"
    public static final List<IsbnModel> BOOKS_LIST = List.of(new IsbnModel(BOOK_ISBN));
}
