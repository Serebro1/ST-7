package com.mycompany.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Task3 {
    private static final String WEATHER_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44" +
                    "&hourly=temperature_2m,rain&timezone=Europe%2FMoscow&forecast_days=1";

    public static String getWeatherForecast(WebDriver driver) {
        driver.get(WEATHER_URL);
        String jsonText = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")))
                .getText();

        JSONObject json = new JSONObject(jsonText);
        JSONArray times = json.getJSONObject("hourly").getJSONArray("time");
        JSONArray temps = json.getJSONObject("hourly").getJSONArray("temperature_2m");
        JSONArray rains = json.getJSONObject("hourly").getJSONArray("rain");

        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append("Прогноз погоды в Нижнем Новгороде на 24 часа:\n");
        sb.append(String.format("%-3s | %-15s | %-12s | %-10s%n",
                "№", "Дата/время", "Темп., °C", "Осадки"));
        sb.append("--------------------------------------------------\n");
        for (int i = 0; i < times.length(); i++) {
            String rawTime = times.getString(i);
            LocalDateTime dateTime = LocalDateTime.parse(rawTime, inputFormatter);
            String formattedTime = dateTime.format(outputFormatter);

            sb.append(String.format(
                    "%-3d | %-15s | %-12.1f | %-10.2f%n",
                    i + 1,
                    formattedTime,
                    temps.getDouble(i),
                    rains.getDouble(i)
            ));
        }
        return sb.toString();
    }
}
