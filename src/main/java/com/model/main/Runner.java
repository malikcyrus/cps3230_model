package com.model.main;
import org.openqa.selenium.chrome.ChromeDriver;

import com.model.marketAlertUM.*;
public class Runner { 
    public static void main(String args[]){ 
        System.setProperty("webdriver.chrome.driver", "src/resources/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        MarketAlertLogin loginPage = new MarketAlertLogin(driver);
        loginPage.inputUserID("31a7cb0e-e0e5-4a05-9351-c074815f7b8a");
        MarketAlertList.clickLogOut(driver);
        driver.quit();
    }
}