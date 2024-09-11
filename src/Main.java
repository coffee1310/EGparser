
public class Main {
    public static void main(String[] args) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler("jdbc:mysql://localhost/EGparser?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
        dataBaseHandler.readEmails("emails.txt");
        EmailHandler emailHandler = new EmailHandler("36daad20-5851-40d7-948b-e2a50d84c136", "robertcaldwell1946@eschaumail.com", "jtogmhwmX!2991");
        Parser parser = new Parser("", "robertcaldwell1946@eschaumail.com", "jtogmhwmX!2991");
    }
}