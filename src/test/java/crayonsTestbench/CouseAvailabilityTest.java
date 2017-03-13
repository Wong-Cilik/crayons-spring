package crayonsTestbench;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.MenuBarElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TabSheetElement;
import com.vaadin.testbench.elements.TextAreaElement;
import com.vaadin.testbench.elements.TextFieldElement;
import com.vaadin.testbench.elements.TwinColSelectElement;

public class CouseAvailabilityTest extends TestBenchTestCase {
    
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
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() throws Exception {
        // close the browser window
        getDriver().quit();
    }

    @Test
    public void test() {
        
        // 1. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).caption(lang.getString("Login") + ": ").first()
                .setValue("client@web.de");
        $(PasswordFieldElement.class).caption(lang.getString("Password") + ": ").first()
                .setValue("123456");
        $(ButtonElement.class).caption(lang.getString("Login")).first().click();
        $(ButtonElement.class).caption(lang.getString("Authorlibrary")).first().click();
        $(TabSheetElement.class).$(TextFieldElement.class).first().setValue("Algebra");
        $(TextAreaElement.class).first().setValue("Algebra is one of the broad parts of mathematics.");
        $(ButtonElement.class).caption(lang.getString("CreateCourse")).first().click();
        
        $(TwinColSelectElement.class).caption(lang.getString("SelectParticipants"))
                .first().selectByText("student@mail.com");
        
        $(ButtonElement.class).caption(lang.getString("SaveStudents")).first().click();
        
        $(MenuBarElement.class).first().click();
        
        // 1. Enter "student@mail.com" into the Email field
        $(TextFieldElement.class).caption(lang.getString("Login") + ": ").first()
                .setValue("student@mail.com");
        $(PasswordFieldElement.class).caption(lang.getString("Password") + ": ").first()
                .setValue("123456");
        $(ButtonElement.class).caption(lang.getString("Login")).first().click();
        
        // 4. Click "Userlibrary" to navigate to the author library
        $(ButtonElement.class).caption(lang.getString("Userlibrary")).first().click();
        
        assertTrue($(TabSheetElement.class).first().getTabCaptions().contains("Algebra"));
        
        $(MenuBarElement.class).first().click();
        
        // 1. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).caption(lang.getString("Login") + ": ").first()
                .setValue("client@web.de");
        $(PasswordFieldElement.class).caption(lang.getString("Password") + ": ").first()
                .setValue("123456");
        $(ButtonElement.class).caption(lang.getString("Login")).first().click();
        $(ButtonElement.class).caption(lang.getString("Authorlibrary")).first().click();
        
        $(TabSheetElement.class).first().openTab("Algebra");
        
        $(TwinColSelectElement.class).caption(lang.getString("SelectParticipants"))
                .first().deselectByText("student@mail.com");
        
        $(ButtonElement.class).caption(lang.getString("SaveStudents")).first().click();
        
        $(MenuBarElement.class).first().click();
        
        // 1. Enter "student@mail.com" into the Email field
        $(TextFieldElement.class).caption(lang.getString("Login") + ": ").first()
                .setValue("student@mail.com");
        $(PasswordFieldElement.class).caption(lang.getString("Password") + ": ").first()
                .setValue("123456");
        $(ButtonElement.class).caption(lang.getString("Login")).first().click();
        
        // 4. Click "Userlibrary" to navigate to the author library
        $(ButtonElement.class).caption(lang.getString("Userlibrary")).first().click();
        
        if ($(TabSheetElement.class).first() != null)
            assertFalse($(TabSheetElement.class).first().getTabCaptions().contains("Algebra"));
        
        $(MenuBarElement.class).first().click();
        
        // 1. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).caption(lang.getString("Login") + ": ").first()
                .setValue("client@web.de");
        $(PasswordFieldElement.class).caption(lang.getString("Password") + ": ").first()
                .setValue("123456");
        $(ButtonElement.class).caption(lang.getString("Login")).first().click();
        $(ButtonElement.class).caption(lang.getString("Authorlibrary")).first().click();
        
        $(TabSheetElement.class).first().openTab("Algebra");
        
        $(ButtonElement.class).caption(lang.getString("DeleteCourse")).first().click();
    }
}
