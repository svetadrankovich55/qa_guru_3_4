import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.linkText;
import static com.codeborne.selenide.Condition.visible;

public class AnnotationStepsTest {
    private final static String repository = "svetadrankovich55/qa_guru_3_4";
    String login = Files.readAllLines(Paths.get("src/test/resources/login.txt")).get(0);
    String password = Files.readAllLines(Paths.get("src/test/resources/password.txt")).get(0);
    String title = "My first AnnotationStepsTest";
    String description = "description";


    public AnnotationStepsTest() throws IOException {
    }

    @Test
    public void annotationStepsTest() {
        Configuration.browserSize = "1900x1200";
        final BaseSteps steps = new BaseSteps();
        steps.openMainPage();
        steps.userLogin(login, password);
        steps.searchForRepository(repository);
        steps.goToIssue();
        steps.createNewIssue(title,description);
    }

    public static class BaseSteps {

        @Step("Открыть главную страницу")
        public void openMainPage() {
            open("https://github.com");
        }

        @Step("Пройти авторизацию")
        public void userLogin(String login, String password) {
            $(linkText("Sign in")).click();
            $("#login_field").val(login).pressTab();
            $("#password").val(password).pressTab();
            $("[value='Sign in']").click();
        }

        @Step("Найти репозиторий")
        public void searchForRepository(String repository) {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(repository);
            $(".header-search-input").submit();
            $(linkText("svetadrankovich55/qa_guru_3_4")).click();
        }

        @Step("Перейти на вкладку Issues")
        public void goToIssue() {
            $("[data-content=Issues]").click();
        }

        @Step("Создать новое Issues")
        public void createNewIssue(String title, String description) {
            $("[data-content='Issues']").click();
            $$(byText("New issue")).find(visible).click();
            $("#issue_title").val(title);
            $("#issue_body").val(description);
            $("#assignees-select-menu").click();
            $$(".js-username").find(text("svetadrankovich55")).click();
            $("#assignees-select-menu").click();
            $("#labels-select-menu").click();
            $$(".name").find(text("bug")).click();
            $$(".name").find(text("documentation")).click();
            $("#labels-select-menu").click();
            $$(".btn.btn-primary").find(text("Submit new issue")).click();
        }
    }
}
