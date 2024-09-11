import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v128.network.Network;
import org.openqa.selenium.interactions.Actions;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.AWTException;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class Parser {
    private String email;
    private String password;

    private ChromeDriver driver;
    private List<String> game_links;
    private List<WebElement> all_games;
    private List<WebElement> free_games;
    private List<String> game_titles;

    private WebElement login_btn;
    public Parser(String url, String email, String password) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\projects\\Java\\EGparser\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled"); // Убираем флаг автоматизации
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        //options.addArguments("--headless");
        this.email = email;
        this.password = password;
        this.driver = new ChromeDriver(options);
        this.game_links = new ArrayList();

        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        //login("coffee13102006@mail.ru", "nata155630");
//        this.get_all_games();
//        this.get_free_games();
//        this.get_game_links();
//        this.get_game_titles();
        //driver.close();
        registration();
    }

    private void login(String email, String password) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.epicgames.com/id/login");


        Cookie cookie = new Cookie("exampleCookie", "cookieValue");
        driver.manage().addCookie(cookie); // Добавляем cookie
        driver.navigate().refresh();

        WebElement email_elem = driver.findElement(By.id("email"));
        WebElement password_elem = driver.findElement(By.id("password"));
        email_elem.sendKeys(email);
        password_elem.sendKeys(password);
        System.out.println(password_elem);
    }

    private void registration() {
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
            driver.get("https://www.google.com/search?q=https%3A%2F%2Fwww.epicgames.com%2Fid%2Fregister%2Fdate-of-birth&sca_esv=d9bb93ffaae04d80&sca_upv=1&source=hp&ei=TGTfZr--O7TewPAPute3gAs&iflsig=AL9hbdgAAAAAZt9yXQImzbZ3T-jg8fHzZ02wp39i_h0k&ved=0ahUKEwi_lN6B47aIAxU0LxAIHbrrDbAQ4dUDCA0&uact=5&oq=https%3A%2F%2Fwww.epicgames.com%2Fid%2Fregister%2Fdate-of-birth&gs_lp=Egdnd3Mtd2l6IjNodHRwczovL3d3dy5lcGljZ2FtZXMuY29tL2lkL3JlZ2lzdGVyL2RhdGUtb2YtYmlydGhIvkBQ3gNY9DhwAXgAkAEAmAGRAqABhQSqAQMyLTK4AQPIAQD4AQL4AQGYAgKgAoYCqAIKwgIQEC4YAxjlAhjqAhiMAxiPAcICEBAAGAMY5QIY6gIYjAMYjwGYA_4BkgcFMS4wLjGgB5OHAQ&sclient=gws-wiz");
        } catch (TimeoutException e) {
            System.out.println("Element was not found within the timeout period.");
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(), 'Epic')]")));
        //driver.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        try {
            int x = element.getLocation().getX();
            int y = element.getLocation().getY();

            // Создайте экземпляр Robot
            Robot robot = new Robot();

            // Переместите указатель мыши на координаты элемента
            robot.mouseMove(x, y + 150);

            // Нажмите и отпустите среднюю кнопку мыши
            robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        driver.get("https://www.epicgames.com/id/register/date-of-birth");
        try {
            WebElement element2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("input")));

            WebElement day = driver.findElement(By.id("day"));
            day.click();

            driver.findElement(By.tagName("li")).click();
        } catch (TimeoutException e) {
            System.out.println(e);
        }

        //driver.close();
    }

    private void getAllGames() {
        this.all_games = driver.findElements(By.className("css-1ukp34s"));
        System.out.println(this.all_games.size());
    }

    private void getFreeGames() {
        this.free_games = new ArrayList<>();
        for (WebElement element: this.all_games) {
            if (element.findElement(By.className("css-1magkk1")).getText().equals("FREE NOW")) {
                this.free_games.add(element);
            }
        }
    }

    private void getGameLinks() {
        for (WebElement element: this.free_games) {
            this.game_links.add(element.findElement(By.tagName("a")).getAttribute("href"));
        }
        System.out.println(game_links);
    }

    private void getGameTitles() {
        this.game_titles = new ArrayList<>();

        for (WebElement element: this.free_games) {
           game_titles.add(element.findElement(By.className("css-s98few")).getText());
        }
        System.out.println(game_titles);
    }
}
