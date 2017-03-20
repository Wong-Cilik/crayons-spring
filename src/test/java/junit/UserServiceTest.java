package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crayons_2_0.CrayonsSpringApplication;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.Language;
import com.crayons_2_0.service.database.UserDAO;
import com.crayons_2_0.service.database.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CrayonsSpringApplication.class)

public class UserServiceTest {

    CrayonsUser dummyUser1, dummyUser2, dummyUser3, dummyUser4;

    @Autowired
    UserService userService;

    List<GrantedAuthority> authorities;

    @Autowired
    UserDAO userDAO;

    List<CrayonsUser> usersList;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {

        authorities = new ArrayList<GrantedAuthority>();

        dummyUser1 = new CrayonsUser("user1", "foo", "user1@web.de", "123456", "German", 2, true, true, false, false,
                authorities);
        dummyUser2 = new CrayonsUser("user2", "boo", "user2@web.de", "123456", "German", 2, true, true, false, false,
                authorities);
        dummyUser3 = new CrayonsUser("user3", "goo", "user3@web.de", "123456", "German", 2, true, true, false, false,
                authorities);
        dummyUser4 = new CrayonsUser("user4", "doo", "user4@web.de", "123456", "German", 2, true, true, false, false,
                authorities);

        usersList = new ArrayList<>();
        for (CrayonsUser user : userService.findAll()) {
            usersList.add(user);
        }
        usersList.add(dummyUser1);
        usersList.add(dummyUser3);
        usersList.add(dummyUser4);
        userService.insertUser(dummyUser1);
        userService.insertUser(dummyUser3);
        userService.insertUser(dummyUser4);

    }

    @After
    public void tearDown() throws Exception {
        userService.removeUser(dummyUser1.getEmail());
        userService.removeUser(dummyUser2.getEmail());
        userService.removeUser(dummyUser3.getEmail());
        userService.removeUser(dummyUser4.getEmail());
    }

    /**
     * if username exsits, expect true
     */
    @Test
    public void testLoadUserByUsername() {
        CrayonsUser result = (CrayonsUser) userService.loadUserByUsername(dummyUser1.getEmail());
        assertEquals(dummyUser1, result);
    }

    /**
     * if users added to DB, expect true
     */
    @Test
    public void testFindAll() {
        List<CrayonsUser> users = userService.findAll();
        assertTrue(usersList.containsAll(users));
    }

    /**
     * if it's new user, expect false (user doesn't exists)
     */
    @Test
    public void testInsertUser() {
        assertTrue(userService.insertUser(dummyUser2));
        assertFalse(userService.insertUser(dummyUser1));
    }
    
    @Test
    public void testUpdateUser() {
        userService.updateUser(dummyUser1, "newuser1@web.de", "Max", "Mustermann", "123456789");
        CrayonsUser user = userService.findByEMail("newuser1@web.de");
        assertEquals("newuser1@web.de", user.getEmail());
        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals("123456789", user.getPassword());
        dummyUser1 = user;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserEmailWrongPattern() {
        userService.updateUser(dummyUser1, "wrongemail", dummyUser1.getFirstName(), dummyUser1.getLastName(), 
                dummyUser1.getPassword());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserEmailTooLong() {
        userService.updateUser(dummyUser1, "veryveryveryverylongemail@mail.com", dummyUser1.getFirstName(), 
                dummyUser1.getLastName(), dummyUser1.getPassword());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserPasswordTooShort() {
        userService.updateUser(dummyUser1, dummyUser1.getEmail(), dummyUser1.getFirstName(), dummyUser1.getLastName(), 
                "123");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserPasswordTooLong() {
        userService.updateUser(dummyUser1, dummyUser1.getEmail(), dummyUser1.getFirstName(), dummyUser1.getLastName(), 
                "1234567891011121314");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserFirstNameEmpty() {
        userService.updateUser(dummyUser1, dummyUser1.getEmail(), "", dummyUser1.getLastName(), dummyUser1.getPassword());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserLastNameEmpty() {
        userService.updateUser(dummyUser1, dummyUser1.getEmail(), dummyUser1.getFirstName(), "", dummyUser1.getPassword());
    }

    /**
     * if language for a specific user been updated, expect true
     */
    @Test
    public void testUpdateUserLanguage() {
        boolean result = userService.updateUserLanguage(dummyUser4, Language.German);
        if (dummyUser4.getLanguage().equals(Language.German))
            result = true;
        assertEquals(true, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserLanguageEmptyLanguage() {
        userService.updateUserLanguage(dummyUser4, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserLanguageEmptyUser() {
        userService.updateUserLanguage(null, Language.German);
    }

    /**
     * if rights for a specific user been updated, expect true
     */
    @Test
    public void testUpdateRightsAuthor() {
        boolean result = userService.updateRights(dummyUser4.getEmail(), "Author");
        assertTrue(result);
        assertEquals(1, userService.findByEMail(dummyUser4.getEmail()).getPermission());
    }
    
    @Test
    public void testUpdateRightsAdmin() {
        boolean result = userService.updateRights(dummyUser4.getEmail(), "Admin");
        assertTrue(result);
        assertEquals(0, userService.findByEMail(dummyUser4.getEmail()).getPermission());
    }
    
    @Test
    public void testUpdateRightsStudent() {
        boolean result = userService.updateRights(dummyUser4.getEmail(), "Student");
        assertTrue(result);
        assertEquals(2, userService.findByEMail(dummyUser4.getEmail()).getPermission());
    }
    
    @Test
    public void testFindByEmail() {
        CrayonsUser user = userService.findByEMail(dummyUser4.getEmail());
        assertEquals(dummyUser4.getEmail(), user.getEmail());
    }
    
    @Test(expected = UsernameNotFoundException.class)
    public void testFindByEmailUserDoesntExist() {
        userService.findByEMail("random@mail.com");
    }
    
    /**
     * if user removed expect true
     */
    @Test
    public void testRemoveUser() {
        assertTrue(userService.removeUser(dummyUser3.getEmail()));
        List<CrayonsUser> users = userService.findAll();
        boolean exist = false;
        for (CrayonsUser user: users) 
            if (user.getEmail().equals(dummyUser3.getEmail()))
                exist = true;
        assertFalse(exist);
    }
    
    @Test
    public void testRemoveUserWhichDoesntExist() {
        assertFalse(userService.removeUser("randommail@mail.com"));
    }

}
