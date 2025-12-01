package io.github.vikindor.tests;

import com.codeborne.selenide.Configuration;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.vikindor.config.ProjectConfig;
import io.github.vikindor.helpers.Attach;
import io.github.vikindor.pages.ProfilePage;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Method;
import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    ProfilePage profilePage = new ProfilePage();

    private static final ProjectConfig cfg = ConfigFactory.create(ProjectConfig.class, System.getProperties());

    @BeforeAll
    static void setupConfig() {
        String env = System.getProperty("env", "local");
        System.setProperty("env", env);

        Configuration.remote           = cfg.remoteUrl();
        Configuration.browser          = cfg.browser();
        Configuration.browserVersion   = cfg.browserVersion();
        Configuration.browserSize      = cfg.browserSize();
        Configuration.baseUrl          = cfg.baseUrl();
        Configuration.pageLoadStrategy = "eager";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        RestAssured.baseURI = cfg.baseUrl();
    }

    @BeforeEach
    void addListener() { SelenideLogger.addListener("AllureSelenide", new AllureSelenide()); }

    @AfterEach
    void addAttachments(TestInfo testInfo) {
        String methodName = testInfo.getTestMethod().map(Method::getName).get();

        Attach.screenshotAs("Screenshot_" + methodName);
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

        closeWebDriver();
    }
}
