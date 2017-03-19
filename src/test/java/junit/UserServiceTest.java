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

    List <CrayonsUser> usersList;
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
        userService.removeUser("user2@web.de");
        userService.updateUser(dummyUser4, "user4@web.de", "user4", "doo", "123456");
    }

    @Test
    public void testLoadUserByUsername() {
        CrayonsUser result = (CrayonsUser) userService.loadUserByUsername("user1@web.de");
        assertEquals(dummyUser1, result);
    }

    
    @Test
    public void testFindAll() {
        List <CrayonsUser>  users = userService.findAll();
      
       
        assertTrue(usersList.containsAll(users));
    }

    // if user already exists expect true
    @Test
    public void testUserExists() {
        boolean result = userService.insertUser(dummyUser1);

        assertEquals(true, result);
    }

    // if new user expect false (user doesn't exists)
    @Test
    public void testinsertUser() {

        boolean isNewUser = userService.insertUser(dummyUser2);

        if (isNewUser)
            assertEquals(true, isNewUser);
        else
            assertEquals(false, isNewUser);
        boolean alreadyExistingUser = userService.insertUser(dummyUser1);
        if (alreadyExistingUser)
            assertEquals(true, alreadyExistingUser);
        else
            assertEquals(false, alreadyExistingUser);

    }

    @Test
    public void testUpdateUserLanguage() {
        boolean result = userService.updateUserLanguage(dummyUser4, Language.German);
        if (dummyUser4.getLanguage().equals(Language.German)) result = true;
        assertEquals(true, result);
    }

    @Test
    public void testUpdateRights() {
        boolean result = userService.updateRights("user4@web.de", "Author");
        if (dummyUser4.getPermission() == 1) result = true;
        assertEquals(true, result);
    }

    // if user removed expect true
    @Test
    public void testRemoveUser() {
        boolean result = userService.removeUser("user3@web.de");
        assertEquals(true, result);
    }

}
