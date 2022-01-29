package com.github.zlwqa.tests;

import annotations.JiraIssue;
import annotations.JiraIssues;
import annotations.Layer;
import annotations.Microservice;
import com.github.zlwqa.helpers.AllureAttachments;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Layer("web")
@JiraIssues({@JiraIssue("HOMEWORK-288")})
@DisplayName("Тестирование главной страницы СберМаркет 'Для себя'")
@Owner("vshalunov")
public class SbermarketForYourselfTests extends TestBase {

    @Microservice("Console")
    @Test
    @DisplayName("Журнал консоли страницы не должен содержать ошибок")
    @Tags({@Tag("ForYourself"), @Tag("High")})
    @Feature("Главная страница СберМаркет 'Для себя'")
    @Story("Журнал консоли")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "СберМаркет", url = "https://sbermarket.ru/")
    void consoleShouldNotHaveErrorsTest() {
        mainpages.openMainPageYourself();

        step("Журналы консоли не должны содержать текст 'SEVERE'", () -> {
            String consoleLogs = AllureAttachments.browserConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }

    @Microservice("Search address")
    @ValueSource(strings = {"Самара, Россия",
            "Самарская область, Россия"})
    @Tags({@Tag("ForYourself"), @Tag("High")})
    @DisplayName("Отображение значения")
    @ParameterizedTest(name = "{displayName} {0} в выпадающем списке адресов")
    @Feature("Главная страница СберМаркет 'Для себя'")
    @Story("Поле для поиска адреса")
    @Severity(SeverityLevel.BLOCKER)
    @Link(name = "СберМаркет", url = "https://business.sbermarket.ru/")
    void searchAddressResultsTest(String searchQuery) {
        mainpages.openMainPageYourself();

        step("Ввести в поле поиск Самар", () -> {
            $("input._1H6W1").setValue("Самар");
        });
        step("Отображение значения " + searchQuery + "в выпадающем списке", () -> {
            $("div._2oqP5").shouldHave(text(searchQuery));
        });
    }

    @Microservice("Footer")
    @MethodSource("com.github.zlwqa.tests.Footer#footerColumns")
    @DisplayName("Отображение значений")
    @Tags({@Tag("ForYourself"), @Tag("High")})
    @ParameterizedTest(name = "{displayName} {1} в подвале сайта у колонки с названием {0}")
    @Feature("Подвал")
    @Story("Подвал страницы СберМаркет 'Для себя'")
    @Severity(SeverityLevel.BLOCKER)
    @Link(name = "СберМаркет", url = "https://business.sbermarket.ru/")
    void displayValuesInTheFooterTest(String nameColumnFooter, List<String> footerColumns) {
        mainpages.openMainPageYourself();

        step("Перейти в категорию " + nameColumnFooter, () -> {
            $$("div.footer__column").find(text(nameColumnFooter)).$$("li")
                    .shouldHave(texts(footerColumns));
        });
    }
}
