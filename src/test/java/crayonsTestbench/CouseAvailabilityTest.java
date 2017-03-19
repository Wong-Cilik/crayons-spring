package crayonsTestbench;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	public void testAddAndRemoveParticipant() {

		// 1. Log in as "client@web.de" and create new course "Algebra"
		logIn("client@web.de", "123456");
		$(ButtonElement.class).caption(lang.getString("Authorlibrary")).first()
				.click();
		$(TabSheetElement.class).$(TextFieldElement.class).first()
				.setValue("Algebra");
		$(TextAreaElement.class).first().setValue(
				"Algebra is one of the broad parts of mathematics.");
		$(ButtonElement.class).caption(lang.getString("CreateCourse")).first()
				.click();

		// 2. Open the tab of the "Algebra" course
		$(TabSheetElement.class).first().openTab("Algebra");

		// 3. Select "student@mail.com" from the List of all students and move
		// them to the Participants
		$(TwinColSelectElement.class)
				.caption(lang.getString("SelectParticipants")).first()
				.selectByText("student@mail.com");

		// 4. Click "Save Students" to save course participants
		$(ButtonElement.class).caption(lang.getString("SaveStudents")).first()
				.click();

		// 5. Click "Logout"
		$(MenuBarElement.class).first().click();

		// 6. Log in as "student@mail.com"
		logIn("student@mail.com", "123456");

		// 7. Click "Userlibrary" to navigate to the user library
		$(ButtonElement.class).caption(lang.getString("Userlibrary")).first()
				.click();

		// 8. Assert that the TabSheet contains "Algera". It means that the
		// course became available
		// for the student
		assertTrue($(TabSheetElement.class).first().getTabCaptions()
				.contains("Algebra"));

		// 9. Click "Logout"
		$(MenuBarElement.class).first().click();

		// 10. Log in as "client@web.de" and navigate to the author library
		logIn("client@web.de", "123456");
		$(ButtonElement.class).caption(lang.getString("Authorlibrary")).first()
				.click();

		// 11. Open the tab of the "Algebra" course
		$(TabSheetElement.class).first().openTab("Algebra");

		// 12. Select "student@mail.com" from the Participants and move them to
		// the List of all students
		$(TwinColSelectElement.class)
				.caption(lang.getString("SelectParticipants")).first()
				.deselectByText("student@mail.com");

		// 13. Click "Save Students" to save course participants
		$(ButtonElement.class).caption(lang.getString("SaveStudents")).first()
				.click();

		// 14. Click "Logout"
		$(MenuBarElement.class).first().click();

		// 15. Log in as "student@mail.com" and navigate to the user library
		logIn("student@mail.com", "123456");
		$(ButtonElement.class).caption(lang.getString("Userlibrary")).first()
				.click();

		// 16. Assert that the TabSheet doesn't contain "Algebra" anymore. It
		// means that the course
		// became unavailable for the student
		if ($(TabSheetElement.class).first() != null)
			assertFalse($(TabSheetElement.class).first().getTabCaptions()
					.contains("Algebra"));

		// 17. Click "Logout"
		$(MenuBarElement.class).first().click();

		// 18. Log in as "client@web.de" and delete the course "Algebra"
		logIn("client@web.de", "123456");
		$(ButtonElement.class).caption(lang.getString("Authorlibrary")).first()
				.click();
		$(TabSheetElement.class).first().openTab("Algebra");
		$(ButtonElement.class).caption(lang.getString("DeleteCourse")).first()
				.click();
		$(MenuBarElement.class).first().click();
	}

	@Test
	public void testParticipantLeavesTheCourse() {

		// 1. Log in as "client@web.de" and create new course "Algebra"
		logIn("client@web.de", "123456");
		$(ButtonElement.class).caption(lang.getString("Authorlibrary")).first()
				.click();
		$(TabSheetElement.class).$(TextFieldElement.class).first()
				.setValue("Algebra");
		$(TextAreaElement.class).first().setValue(
				"Algebra is one of the broad parts of mathematics.");
		$(ButtonElement.class).caption(lang.getString("CreateCourse")).first()
				.click();

		// 2. Open the tab of the "Algebra" course
		$(TabSheetElement.class).first().openTab("Algebra");

		// 3. Select "student@mail.com" from the List of all students and move
		// them to the Participants
		$(TwinColSelectElement.class)
				.caption(lang.getString("SelectParticipants")).first()
				.selectByText("student@mail.com");

		// 4. Click "Save Students" to save course participants
		$(ButtonElement.class).caption(lang.getString("SaveStudents")).first()
				.click();

		// 5. Click "Logout"
		$(MenuBarElement.class).first().click();

		// 6. Log in as "student@mail.com"
		logIn("student@mail.com", "123456");

		// 7. Click "Userlibrary" to navigate to the user library
		$(ButtonElement.class).caption(lang.getString("Userlibrary")).first()
				.click();

		// 8. Assert that the TabSheet contains "Algera". It means that the
		// course became available
		// for the student
		assertTrue($(TabSheetElement.class).first().getTabCaptions()
				.contains("Algebra"));

		// 9. Open the tab of the "Algebra" course
		$(TabSheetElement.class).first().openTab("Algebra");

		// 10. Click "Leave the course"
		$(ButtonElement.class).caption(lang.getString("LeaveTheCourse"))
				.first().click();

		// 11. Assert that the TabSheet doesn't contain "Algebra" anymore.
		if ($(TabSheetElement.class).first() != null)
			assertFalse($(TabSheetElement.class).first().getTabCaptions()
					.contains("Algebra"));

		// 12. Click "Logout"
		$(MenuBarElement.class).first().click();

		// 13. Log in as "client@web.de" and navigate to the author library
		logIn("client@web.de", "123456");
		$(ButtonElement.class).caption(lang.getString("Authorlibrary")).first()
				.click();

		// 14. Open the tab of the "Algebra" course
		$(TabSheetElement.class).first().openTab("Algebra");

		// 15. Assert that the participants list doesn't contain
		// "student@mail.com" anymore
		assertFalse($(TwinColSelectElement.class)
				.caption(lang.getString("SelectParticipants")).first()
				.getValues().contains("student@mail.com"));

		// 16. Click "Delete Course"
		$(ButtonElement.class).caption(lang.getString("DeleteCourse")).first()
				.click();

		// 17. Click "Logout"
		$(MenuBarElement.class).first().click();
	}

	private void logIn(String email, String password) {
		$(TextFieldElement.class).caption(lang.getString("Login") + ": ")
				.first().setValue(email);
		$(PasswordFieldElement.class)
				.caption(lang.getString("Password") + ": ").first()
				.setValue(password);
		$(ButtonElement.class).caption(lang.getString("Login")).first().click();
	}
}
