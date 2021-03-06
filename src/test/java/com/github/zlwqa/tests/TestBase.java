package com.github.zlwqa.tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.zlwqa.config.WebDriverUtil;
import com.github.zlwqa.helpers.AllureAttachments;
import com.github.zlwqa.steps.FeedbackModalWindow;
import com.github.zlwqa.steps.MainPages;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static io.qameta.allure.Allure.step;

public class TestBase {

    MainPages mainpages = new MainPages();
    FeedbackModalWindow feedbackModalWindow = new FeedbackModalWindow();

    @BeforeAll
    public static void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        WebDriverUtil.configure();
    }

    @AfterEach
    public void tearDown() {
        AllureAttachments.screenshotAs("Last screenshot");
        AllureAttachments.pageSource();
        AllureAttachments.browserConsoleLogs();
        AllureAttachments.addVideo();
        step("Закрыть браузер", Selenide::closeWebDriver);
    }


}
