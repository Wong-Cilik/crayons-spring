package crayonsTestbench;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import com.crayons_2_0.service.Language;
import com.crayons_2_0.service.LanguageService;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TabSheetElement;
import com.vaadin.testbench.elements.TextAreaElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class CourseCreationTest extends TestBenchTestCase {

    private ResourceBundle lang = LanguageService.getInstance().getRes();

    @Before
    public void setup() throws Exception {
        String osName = System.getProperty("os.name");
        osName = osName.substring(0, osName.indexOf(' '));
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

        // 1. Enter eMail < > into the eMail-Login field
        $(TextFieldElement.class).caption(lang.getString("Login") + ": ").first()
                .setValue("client@web.de");

        // 2. Enter password "123456" into the password-Login field
        $(PasswordFieldElement.class).caption(lang.getString("Password") + ": ").first()
                .setValue("123456");

        // 3. Click the "Login" button
        $(ButtonElement.class).caption(lang.getString("Login")).first().click();
        
        $(ButtonElement.class).caption(lang.getString("Authorlibrary")).first().click();
        
        $(TabSheetElement.class).$(TextFieldElement.class).first().setValue("Algebra");
        
        $(TextAreaElement.class).first().setValue("Algebra is one of the broad parts of mathematics.");
        
        $(ButtonElement.class).caption(lang.getString("CreateCourse")).first().click();
        
        boolean include = false;
        for (String caption: $(TabSheetElement.class).first().getTabCaptions()) {
            if (caption.equals("Algebra")) include = true;
        }
        assertTrue(include);
        
        $(ButtonElement.class).caption(lang.getString("ModifyCourse")).first().click();
    }
}
