import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private WebDriver driver;
    private List<String> game_links;
    private List<WebElement> all_games;
    private List<WebElement> free_games;
    private List<String> game_titles;

    private WebElement login_btn;
    public Parser(String url) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\projects\\Java\\EGparcer\\chromedriver.exe");

        this.driver = new ChromeDriver();
        this.game_links = new ArrayList();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.epicgames.com/id/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        login("coffee13102006@mail.ru", "nata155630");
//        this.get_all_games();
//        this.get_free_games();
//        this.get_game_links();
//        this.get_game_titles();
        driver.close();
    }

    private void login(String email, String password) {
        WebElement email_elem = driver.findElement(By.id("email"));
        WebElement password_elem = driver.findElement(By.id("password"));
        email_elem.sendKeys(email);
        password_elem.sendKeys(password);
        System.out.println(password_elem);
    }

    private void get_all_games() {
        this.all_games = driver.findElements(By.className("css-1ukp34s"));
        System.out.println(this.all_games.size());
    }

    private void get_free_games() {
        this.free_games = new ArrayList<>();
        for (WebElement element: this.all_games) {
            if (element.findElement(By.className("css-1magkk1")).getText().equals("FREE NOW")) {
                this.free_games.add(element);
            }
        }
    }

    private void get_game_links() {
        for (WebElement element: this.free_games) {
            this.game_links.add(element.findElement(By.tagName("a")).getAttribute("href"));
        }
        System.out.println(game_links);
    }

    private void get_game_titles() {
        this.game_titles = new ArrayList<>();

        for (WebElement element: this.free_games) {
           game_titles.add(element.findElement(By.className("css-s98few")).getText());
        }
        System.out.println(game_titles);
    }

}
