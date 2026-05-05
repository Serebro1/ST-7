package com.mycompany.app;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class Task2 {
    public static String getIP(WebDriver driver) {
        driver.get("https://api.ipify.org/?format=json");
        String jsonText = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")))
                .getText();
        return new JSONObject(jsonText).getString("ip");
    }
}
