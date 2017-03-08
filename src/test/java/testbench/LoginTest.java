package testbench;

import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.FormLayoutElement;
import com.vaadin.testbench.elements.TextFieldElement;
import com.vaadin.testbench.parallel.ParallelTest;

public class LoginTest extends ParallelTest {
	
	
	//-------MAVEN-------------
	/*
	
	<dependency>
		<groupId>com.vaadin</groupId>
		<artifactId>vaadin-testbench</artifactId>
		<version>4.1.0.beta2</version>
		<scope>test</scope>
	</dependency>
	
	*/
	//------------------------
	
	
	
	
	private static final String URL="http://localhost";
    private static final String PORT="8080";
    
    private ResourceBundle lang = LanguageService.getInstance().getRes();
    
    @Before
    public void setup() throws Exception {
    	setDriver(new ChromeDriver());
    	// Open the WebPage
    	getDriver().get(URL + ":" + PORT);
    }
    
    @After
    public void tearDown() throws Exception {
        //close the browser window
        getDriver().quit();
    }

    @Test
    public void test() {
    	
    	String testEMail = "client@web.de";
    	String testPassword = "123456"; 
    	
    	// 1. Enter eMail "client@web.de" into the eMail-Login field
    	$(FormLayoutElement.class).$(TextFieldElement.class).caption(lang.getString("Login") + ": ").first().setValue(testEMail);
    	
    	// 2. Enter password "123456" into the password-Login field
    	$(FormLayoutElement.class).$(TextFieldElement.class).caption(lang.getString("Password") + ": ").first().setValue(testPassword);
    	
    	// 3. Click the "Login" button
    	$(ButtonElement.class).caption(lang.getString("Login")).first().click();
    	
    }

}
