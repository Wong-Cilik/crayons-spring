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
		// driver.manage().window().maximize();
	}

	@After
	public void tearDown() throws Exception {
		// close the browser window
		Thread.sleep(2000);
		getDriver().quit();
	}

	@Test
	public void testFirstNameFieldEmpty() {

		// 1. Enter eMail < > into the eMail-Login field
		$(TextFieldElement.class).caption(lang.getString("Login") + ": ")
				.first().setValue("client@web.de");

		// 2. Enter password "123456" into the password-Login field
		$(PasswordFieldElement.class)
				.caption(lang.getString("Password") + ": ").first()
				.setValue("123456");

		// 3. Click the "Login" button
		$(ButtonElement.class).caption(lang.getString("Login")).first().click();

		// 4. Click at Menu item "Preferences
		$(ButtonElement.class).caption(lang.getString("Preferences")).first()
				.click();

		$(TextFieldElement.class).get(0).setValue("");

		$(ButtonElement.class).caption(lang.getString("Save")).first().click();

		NotificationElement notification = $(NotificationElement.class).first();
		assertEquals(
				String.format(lang.getString("RequiredField"),
						lang.getString("FirstName")), notification.getCaption());

	}

	@Test
	public void testLastNameFieldEmpty() {

		// 1. Enter eMail < > into the eMail-Login field
		$(TextFieldElement.class).caption(lang.getString("Login") + ": ")
				.first().setValue("client@web.de");

		// 2. Enter password "123456" into the password-Login field
		$(PasswordFieldElement.class)
				.caption(lang.getString("Password") + ": ").first()
				.setValue("123456");

		// 3. Click the "Login" button
		$(ButtonElement.class).caption(lang.getString("Login")).first().click();

		// 4. Click at Menu item "Preferences
		$(ButtonElement.class).caption(lang.getString("Preferences")).first()
				.click();

		$(TextFieldElement.class).get(1).setValue("");

		$(ButtonElement.class).caption(lang.getString("Save")).first().click();

		NotificationElement notification = $(NotificationElement.class).first();
		assertEquals(
				String.format(lang.getString("RequiredField"),
						lang.getString("LastName")), notification.getCaption());

	}

	@Test
	public void testUpdateFirstName() {

		// 1. Enter eMail < > into the eMail-Login field
		$(TextFieldElement.class).caption(lang.getString("Login") + ": ")
				.first().setValue("client@web.de");

		// 2. Enter password "123456" into the password-Login field
		$(PasswordFieldElement.class)
				.caption(lang.getString("Password") + ": ").first()
				.setValue("123456");

		// 3. Click the "Login" button
		$(ButtonElement.class).caption(lang.getString("Login")).first().click();

		// 4. Click at Menu item "Preferences
		$(ButtonElement.class).caption(lang.getString("Preferences")).first()
				.click();

		$(TextFieldElement.class).get(0).setValue("Fabi");

		$(ButtonElement.class).caption(lang.getString("Save")).first().click();

		NotificationElement notification = $(NotificationElement.class).first();
		assertEquals(lang.getString("ProfileUpdatedSuccessfully"),
				notification.getCaption());

	}

	@Test
	public void testUpdateLastName() {

		// 1. Enter eMail < > into the eMail-Login field
		$(TextFieldElement.class).caption(lang.getString("Login") + ": ")
				.first().setValue("client@web.de");

		// 2. Enter password "123456" into the password-Login field
		$(PasswordFieldElement.class)
				.caption(lang.getString("Password") + ": ").first()
				.setValue("123456");

		// 3. Click the "Login" button
		$(ButtonElement.class).caption(lang.getString("Login")).first().click();

		// 4. Click at Menu item "Preferences
		$(ButtonElement.class).caption(lang.getString("Preferences")).first()
				.click();

		$(TextFieldElement.class).get(1).setValue("Foo");

		$(ButtonElement.class).caption(lang.getString("Save")).first().click();

		NotificationElement notification = $(NotificationElement.class).first();
		assertEquals(lang.getString("ProfileUpdatedSuccessfully"),
				notification.getCaption());

	}

}
