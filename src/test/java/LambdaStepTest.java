import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static com.codeborne.selenide.Condition.visible;

public class LambdaStepTest {
    String repository = "svetadrankovich55/qa_guru_3_4";
    String login = Files.readAllLines(Paths.get("src/test/resources/login.txt")).get(0);
    String password = Files.readAllLines(Paths.get("src/test/resources/password.txt")).get(0);
    String title = "My first LambdaStepTest";
    String description = "description";

    public LambdaStepTest() throws IOException {
    }

    @Test
    public void lambdaStepTest() {
        Configuration.browserSize = "1900x1200";

        step("Открыть главную страницу", () -> open("https://github.com"));

        step("Пройти авторизацию", () -> {
            $(linkText("Sign in")).click();
            $("#login_field").val(login);
            $("#password").val(password).pressEnter();
        });

        step("Найти репозиторий", () -> {
            $(".header-search-input").setValue(repository);
            $(linkText("svetadrankovich55/qa_guru_3_4")).click();
        });

        step("Перейти на вкладку Issues", () -> $("[data-content=Issues]").click());

        step("Создать новое Issues", () -> {
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
        });

        step("Проверить успешность создания Issue", () -> {
            $(".js-issue-title").shouldHave(text(title));
            $("#assignees-select-menu").parent().shouldHave(text("svetadrankovich55"));
            $("#labels-select-menu").parent().shouldHave(text("bug"));
            $("#labels-select-menu").parent().shouldHave(text("documentation"));
        });

    }

}
