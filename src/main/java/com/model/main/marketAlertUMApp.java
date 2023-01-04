package com.model.main;

import java.util.ArrayList;
import com.model.marketAlertUM.*;
import org.openqa.selenium.chrome.*;;

public class marketAlertUMApp {
    ChromeDriver driver;
    ArrayList <Alert> alerts;
    Alert alert;
    private boolean userLoggedOn = false; 
		
    public marketAlertUMApp() { 

        // random alert (to post)
        alerts = new ArrayList<Alert>(); 
        alert = new Alert(1, "Samsung Galaxy Watch 5 44mm Sapphire", "Know your sleep with our improved sleep tracking technology. Plan your bedtime, detect snoring, understand and track your sleep stages (awake, light, deep, REM) via 8 animal sleep symbols representing your sleep type. Better nights lead to better days.", "https://intercomp.com.mt/product/samsung-galaxy-watch-5-44mm-sapphire/", "https://intercomp.com.mt/wp-content/uploads/2022/12/58401.jpg", 29900); 
        alerts.add(alert);

        // setting selenium properties 
        System.setProperty("webdriver.chrome.driver", "src/resources/chromedriver.exe");
		driver = new ChromeDriver();
    }  

    public boolean isUserLoggedOn(){ 
        return userLoggedOn;
    }

    public boolean alertCreated() { 
        PostService post = new PostService();
        try {
            if (post.postAlerts(alerts)) { 
                return true; 
            } else {
                throw new IllegalStateException();
            }
        } catch (Exception e){ 
            e.printStackTrace();
        }

        return true; 
    }

    public boolean alertsDeleted() { 
        PostService post = new PostService();
        try {
            if (post.removeAllAlerts()) { 
                return true; 
            } else {
                throw new IllegalStateException();
            }
        } catch (Exception e){ 
            e.printStackTrace();
        }
        return false;
    }

    public boolean userValidLogin() { 
        if(driver.toString().contains("(null)")){
            driver = new ChromeDriver();
        }

        MarketAlertLogin login = new MarketAlertLogin(driver);
        login.inputUserID("31a7cb0e-e0e5-4a05-9351-c074815f7b8a");

        if(driver.getCurrentUrl().equals("https://www.marketalertum.com/Alerts/List")){ 
            userLoggedOn = true;
            return true; 
        } else { 
            throw new IllegalStateException();
        }
    }

    public boolean userLoggedOut() { 
        if(userLoggedOn) { 
            MarketAlertList.clickLogOut(driver);
            driver.quit();
            userLoggedOn = false;
            return true; 
        } else { 
            throw new IllegalStateException();
        }       
    }

    public void userViewedAlerts() { 
        MarketAlertList list = new MarketAlertList(driver);
        list.viewAlerts();
    }

}
