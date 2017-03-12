package crayonsTestbench;

import static org.junit.Assert.assertEquals;

import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class LoginTest extends TestBenchTestCase {

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
		//WebDriverWait wait = new WebDriverWait(getDriver(), 3);
		//wait.Until(EC.element_to_be_clickable(By.ID,"ROOT-2521314")); 
		
		driver.manage().window().maximize();
	}

	@After
	public void tearDown() throws Exception {
		// close the browser window
	    Thread.sleep(5000); 
		getDriver().quit();
	}

	@Test
	public void loginTest() {

		// 1. Enter eMail < > into the eMail-Login field
		$(TextFieldElement.class).caption(lang.getString("Login") + ": ").first()
				.setValue("client@web.de");

		// 2. Enter password "123456" into the password-Login field
		$(PasswordFieldElement.class).caption(lang.getString("Password") + ": ").first()
				.setValue("123456");

		// 3. Click the "Login" button
		$(ButtonElement.class).caption(lang.getString("Login")).first().click();
		



		$(ButtonElement.class).caption("Einstellungen").first().click();
		
        TextFieldElement emailTextField = $(TextFieldElement.class).caption("E-Mail").first();
        assertEquals("client@web.de",emailTextField.getValue());
		
		
		

	}

}