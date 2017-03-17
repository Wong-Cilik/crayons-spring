package crayonsTestbench;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.MenuBarElement;
import com.vaadin.testbench.elements.NativeSelectElement;
import com.vaadin.testbench.elements.NotificationElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TableElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class AdminTest extends TestBenchTestCase {

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
    public void testChangeUserRigthsToStudent() {
        
        // 1. Log in as "client@web.de"
        login("client@web.de", "123456");
        
        // 2. Click "User permissions" 
        $(ButtonElement.class).caption(lang.getString("AdminView")).first().click();
        
        // Find an entry for the user "student@mail.com" in the table
        int i = 0;
        while (!($(TableElement.class).first().getCell(i, 0).getText().equals("student@mail.com"))) i++;
        
        // 3. Click on the "student@mail.com" row
        $(TableElement.class).first().getCell(i, 2).click();
        
        // 4. Select "Student" in NativeSelect component
        $(NativeSelectElement.class).caption(lang.getString("Rights")).first()
                .setValue(lang.getString("Student"));
        
        // 5. Click "Save"
        $(ButtonElement.class).caption(lang.getString("Save")).first().click();
        
        // 6. Assert that the user got an appropriate notification
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals(lang.getString("RightsUpdatedSuccessfully"), notification.getCaption());
        
        // 7. Assert that the user permissions information in the table is right
        assertEquals($(TableElement.class).first().getCell(i, 2).getText(), lang.getString("Student"));
        
        // 8. Click "Logout"
        $(MenuBarElement.class).first().click();
        
        // 9. Log in as "student@mail.com"
        login("student@mail.com", "123456");
        
        // 10. Assert that the student can see Userlibrary, Preferences, and Search only
        assertFalse($(ButtonElement.class).caption(lang.getString("Authorlibrary")).exists());
        assertTrue($(ButtonElement.class).caption(lang.getString("Userlibrary")).exists());
        assertTrue($(ButtonElement.class).caption(lang.getString("Preferences")).exists());
        assertTrue($(ButtonElement.class).caption(lang.getString("Search")).exists());
        assertFalse($(ButtonElement.class).caption(lang.getString("AdminView")).exists());
        
        // 11. Click "Logout"
        $(MenuBarElement.class).first().click();
    }
    
    @Test
    public void testChangeUserRigthsToAuthor() {
        
        // 1. Log in as "client@web.de"
        login("client@web.de", "123456");
        
        // 2. Click "User permissions" 
        $(ButtonElement.class).caption(lang.getString("AdminView")).first().click();
        
        // Find an entry for the user "student@mail.com" in the table
        int i = 0;
        while (!($(TableElement.class).first().getCell(i, 0).getText().equals("student@mail.com"))) i++;
        
        // 3. Click on the "student@mail.com" row
        $(TableElement.class).first().getCell(i, 2).click();
        
        // 4. Select "Student" in NativeSelect component
        $(NativeSelectElement.class).caption(lang.getString("Rights")).first()
                .setValue(lang.getString("Author"));
        
        // 5. Click "Save"
        $(ButtonElement.class).caption(lang.getString("Save")).first().click();
        
        // 6. Assert that the user got an appropriate notification
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals(lang.getString("RightsUpdatedSuccessfully"), notification.getCaption());
        
        // 7. Assert that the user permissions information in the table is right
        assertEquals($(TableElement.class).first().getCell(i, 2).getText(), lang.getString("Author"));
        
        // 8. Click "Logout"
        $(MenuBarElement.class).first().click();
        
        // 9. Log in as "student@mail.com"
        login("student@mail.com", "123456");
        
        // 10. Assert that the student can see Authorlibrary, Userlibrary, Preferences, and Search
        assertTrue($(ButtonElement.class).caption(lang.getString("Authorlibrary")).exists());
        assertTrue($(ButtonElement.class).caption(lang.getString("Userlibrary")).exists());
        assertTrue($(ButtonElement.class).caption(lang.getString("Preferences")).exists());
        assertTrue($(ButtonElement.class).caption(lang.getString("Search")).exists());
        assertFalse($(ButtonElement.class).caption(lang.getString("AdminView")).exists());
        
        // 11. Click "Logout"
        $(MenuBarElement.class).first().click();
    }
    
    @Test
    public void testChangeUserRigthsToAdmin() {
        
        // 1. Log in as "client@web.de"
        login("client@web.de", "123456");
        
        // 2. Click "User permission" 
        $(ButtonElement.class).caption(lang.getString("AdminView")).first().click();
        
        // Find an entry for the user "student@mail.com" in the table
        int i = 0;
        while (!($(TableElement.class).first().getCell(i, 0).getText().equals("student@mail.com"))) i++;
        
        // 3. Click on the "student@mail.com" row
        $(TableElement.class).first().getCell(i, 2).click();
        
        // 4. Select "Student" in NativeSelect component
        $(NativeSelectElement.class).caption(lang.getString("Rights")).first()
                .setValue(lang.getString("Admin"));
        
        // 5. Click "Save"
        $(ButtonElement.class).caption(lang.getString("Save")).first().click();
        
        // 6. Assert that the user got an appropriate notification
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals(lang.getString("RightsUpdatedSuccessfully"), notification.getCaption());
        
        // 7. Assert that the user permission information in the table is right
        assertEquals($(TableElement.class).first().getCell(i, 2).getText(), lang.getString("Admin"));
        
        // 8. Click "Logout"
        $(MenuBarElement.class).first().click();
        
        // 9. Log in as "student@mail.com"
        login("student@mail.com", "123456");
        
        // 10. Assert that the admin can see Authorlibrary, Userlibrary, Preferences, Search, and User permissions
        assertTrue($(ButtonElement.class).caption(lang.getString("Authorlibrary")).exists());
        assertTrue($(ButtonElement.class).caption(lang.getString("Userlibrary")).exists());
        assertTrue($(ButtonElement.class).caption(lang.getString("Preferences")).exists());
        assertTrue($(ButtonElement.class).caption(lang.getString("Search")).exists());
        assertTrue($(ButtonElement.class).caption(lang.getString("AdminView")).exists());
        
        // 11. Click "Logout"
        $(MenuBarElement.class).first().click();
    }

    @Test
    public void testDeleteUser() {
        
        // Create a new user
        createNewUser();
        
        // 1. Log in as "max@web.de" (new user)
        login("max@web.de", "123456");
        
        // 2. Click "Logout"
        $(MenuBarElement.class).first().click();
        
        // 3. Log in as "client@web.de"
        login("client@web.de", "123456");
        
        // 4. Click "User permission" 
        $(ButtonElement.class).caption(lang.getString("AdminView")).first().click();
        
        // Find an entry for the user "max@web.de" in the table
        int i = 0;
        while (!($(TableElement.class).first().getCell(i, 0).getText().equals("max@web.de"))) i++;
        
        // 5. Click on the "max@web.de" row
        $(TableElement.class).first().getCell(i, 2).click();

        // 6. Click "Delete user"
        $(ButtonElement.class).caption(lang.getString("DeleteUser")).first().click();
        
        // 7. Assert that the user got an appropriate notification
        NotificationElement notification = $(NotificationElement.class).first();
        assertEquals(lang.getString("UserDeletedSuccessfully"), 
                notification.getCaption());
        
        // 8. Click "Logout"
        $(MenuBarElement.class).first().click();
        
        // 9. Log in as "max@web.de"
        login("max@web.de", "123456");
        
        // 10. Assert that the user got an appropriate notification that the user doesn't exist
        notification =
                $(NotificationElement.class).first();
        assertEquals("Authentication failed: " + String.format(lang.getString("UserWithMailDoesntExists"), 
                "max@web.de"), notification.getCaption());
    }
    
    private void login(String email, String password) {
        $(TextFieldElement.class).caption(lang.getString("Login") + ": ").first()
                .setValue(email);
        $(PasswordFieldElement.class).caption(lang.getString("Password") + ": ").first()
                .setValue(password);
        $(ButtonElement.class).caption(lang.getString("Login")).first().click();
    }
    
    private void createNewUser() {
        $(ButtonElement.class).caption(lang.getString("Register")).first().click();
        $(TextFieldElement.class).get(0).setValue("Max");
        $(TextFieldElement.class).get(1).setValue("Mustermann");
        $(TextFieldElement.class).get(2).setValue("max@web.de");   
        $(PasswordFieldElement.class).first().setValue("123456");
        $(NativeSelectElement.class).caption(lang.getString("SelectYourLanguage"))
                .first().setValue(lang.getString("German"));
        $(ButtonElement.class).caption(lang.getString("CreateUser")).first().click();
    }
}
