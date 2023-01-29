import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    void shouldPositiveTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//*[@data-test-id='city']//input").setValue("Екатеринбург");
        String currentDate = generateDate(10, "dd.MM.yyyy");
        $x("//*[@data-test-id='date']//input").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input").sendKeys(currentDate);
        $x("//*[@data-test-id='name']//input").setValue("Куклис-Рошман Наталья");
        $x("//*[@data-test-id='phone']//input").setValue("+79001112233");
        $x("//*[@data-test-id='agreement']").click();
        $x("//div[contains (@class, 'grid-row')]//button").click();
        $x("//div[contains (text(), 'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));
        $x("//div[contains (@class, 'notification__content')]").shouldHave(Condition.text("Встреча успешно забронирована на " + currentDate));
    }
}