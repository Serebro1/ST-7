package com.mycompany.app;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.mycompany.app.Task2.getIP;
import static com.mycompany.app.Task3.getWeatherForecast;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("D:\\chrome-win64\\chrome.exe");
        WebDriver webDriver = new ChromeDriver(options);
        try {
            System.out.println("Задание 1 – получение сгенерированого пароля");
            String password = getPassword(webDriver);
            System.out.println("Сгенерированный пароль: " + password);
            System.out.println();

            System.out.println("Задание 2 – получение внешнего IP-адреса");
            String ip = getIP(webDriver);
            System.out.println("Внешний IP-адрес: " + ip);
            System.out.println();

            System.out.println("Задание 3 – получение прогноза погоды Нижнего Новгорода");
            String forecastTable = getWeatherForecast(webDriver);
            System.out.println(forecastTable);

            String markdownTable = Task3.getWeatherForecastAsMarkdown(webDriver);
            String fileName = "result/weather_forecast.md";
            try (java.io.FileWriter writer = new java.io.FileWriter(fileName, java.nio.charset.StandardCharsets.UTF_8)) {
                writer.write(markdownTable);
                System.out.println("Таблица сохранена в файл: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
        } finally {
            webDriver.quit();
        }
    }

    private static String getPassword(WebDriver driver) {
        driver.get("https://www.calculator.net/password-generator.html");
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='verybigtext']")))
                .getText().trim();
    }


}
