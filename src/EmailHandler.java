import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class EmailHandler {
    private String token;
    private String username;
    private String password;

    public EmailHandler(String token, String username, String password) {
        this.token = token;
        this.username = username.replaceAll("!", "%21");
        this.username = this.username.replaceAll("@", "%40");
        this.password = password;
        this.password = this.password.replaceAll("!", "%21");
        this.password = this.password.replaceAll("@", "%40");
        getLastMessage();
    }

    private String getLastMessage() {
        try {
            // Создание HttpClient с настройками перенаправлений
            RequestConfig requestConfig = RequestConfig.custom()
                    .setCircularRedirectsAllowed(true) // Разрешаем циклические перенаправления
                    .setMaxRedirects(5) // Устанавливаем максимальное количество перенаправлений
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .build();

            // URL для запроса
            String url = "https://api.firstmail.ltd/v1/mail/one?username=" + this.username + "&password=" + this.password;
            HttpGet httpGet = new HttpGet(url); //https://api.firstmail.ltd/v1/mail/one?username=robertcaldwell1946%40eschaumail.com&password=jtogmhwmX%212991

            // Установка заголовков
            httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("X-API-KEY", this.token);

            // Выполнение запроса
            HttpResponse httpResponse = httpClient.execute(httpGet);

            // Чтение ответа
            String responseBody = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            if (responseBody.indexOf("Api rate limit") != -1) {
                return "limit";
            }
            if (responseBody.indexOf("IP missmatch") != - 1) {
                return "ip missmatch";
            }
            if (responseBody.indexOf("Username is required") != -1) {
                return "username is required";
            }

            String code = responseBody.substring(responseBody.indexOf("<td align=\\\"left\\\" style=\\\"background:#f1f1f1;margin-top:20px;font-family: arial,helvetica,sans-serif; mso-line-height-rule: exactly; font-size:30px; color:#202020; line-height:19px; line-height: 134%; letter-spacing: 10px;text-align: center;padding: 20px 0px !important;letter-spacing: 10px !important;border-radius: 4px;\\\">"));
            code = code.substring(code.indexOf("\\r\\n\\r\\n\\r\\n") + 12);
            code = code.substring(0, code.indexOf("\\r\\n\\r\\n\\r\\n "));
            code = code.replaceAll(" ", "");
            System.out.println(code);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
