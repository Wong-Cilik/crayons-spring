package crayonsTestbench;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.NotificationElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class PreferencesTest extends TestBenchTestCase {
    
    private ResourceBundle lang = LanguageService.getInstance().getRes();
    
    @Before
    public void setup() throws Exception {
        String osName = System.getProperty("os.name");
        osName = osName.substring(0, osName.indexOf(' '));
        System.out.println(osName);
        if (osName.equals("Windows")) {
            System.setProperty("webdriver.chrome.driver",
                    "src/main/resources/chromedriver.exe");
        } else if (osName.equals("Mac")) {
            System.setProperty("webdriver.chrome.driver",
                    "src/main/resources/chromedriver");
        }
        setDriver(TestBench.createDriver(new ChromeDriver()));
        // Open the WebPage
        getDriver().get("http://localhost:8080");
//        driver.manage().window().maximize();
    }

    @After
    public void tearDown() throws Exception {
        // close the browser window
       Thread.sleep(2000);
        getDriver().quit();
    }

    @Test
    public void testUpdateUserData() {
        
     // 1. Enter eMail < > into the eMail-Login field
        $(TextFieldElement.class).caption(lang.getString("Login") + ": ").first()
                .setValue("client@web.de");

        // 2. Enter password "123456" into the password-Login field
        $(PasswordFieldElement.class).caption(lang.getString("Password") + ": ").first()
                .setValue("12345678");

        // 3. Click the "Login" button
        $(ButtonElement.class).caption(lang.getString("Login")).first().click();
        
        // 4. Click at Menu item "Preferences
        $(ButtonElement.class).caption("Preferences").first().click();
        
        $(TextFieldElement.class).get(0).setValue("Max");
        
        $(TextFieldElement.class).get(1).setValue("Mustermann");
        
        $(TextFieldElement.class).caption("Email").first().setValue("client@web.de");
        
        $(TextFieldElement.class).caption("New Password").first().setValue("12345678");
        
        $(TextFieldElement.class).caption("Confirm Password").first().setValue("12345678");
        
        $(ButtonElement.class).caption("Save").first().click();
        
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals("Profile updated successfully", notification.getCaption());
        
    }
 
    


}
