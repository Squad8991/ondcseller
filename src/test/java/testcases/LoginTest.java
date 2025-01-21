package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.LoginPage;
import testbase.BaseClass;
import utilities.ConfigReader;

public class LoginTest extends BaseClass {

    private ConfigReader configReader;
    private LoginPage lp;
    private String username;
    private String password;

    @BeforeClass
    public void initialize() {
        // Initialize ConfigReader and fetch credentials
        configReader = new ConfigReader();
        username = configReader.getUsername();
        password = configReader.getPassword();

        // Initialize LoginPage with the 'page' object from BaseClass
        if (page == null) {
            throw new IllegalStateException("Page object is not initialized. Ensure setup in BaseClass is correct.");
        }
        lp = new LoginPage(page);
    }

    @Test(priority = 1)
    public void testValidLogin() {
        lp.enterUsername(username);
        lp.enterPassword(password);
        lp.clickLogin();

        // Wait for the page navigation (wait until the page title changes or some element appears)
        page.waitForNavigation(null);  // This will wait for navigation to complete after clicking login

        // Verify page title after login
        String actualTitle = page.title();
        String expectedTitle = "Squadcube | React eCommerce Admin Dashboard";
        Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match after login.");

        // Log out after successful login
        lp.clickProfile();
        lp.clickLogout();
    }

    @Test(priority = 2)
    public void testInvalidLogin() {
        // Enter invalid credentials
        lp.clearUsername();
        lp.clearPassword();
        lp.enterUsername("invalidUser");
        lp.enterPassword("invalidPass");
        lp.clickLogin();

        // Wait for the error message to appear
        page.waitForTimeout(3000);  // Wait for 3 seconds (adjust as needed)

        // Verify that error message is displayed
        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed for invalid login.");
    }

    @Test(priority = 3)
    public void testPasswordMasking() {
        lp.clearUsername();
        lp.clearPassword();
        lp.enterPassword("password123");
        Assert.assertTrue(lp.isPasswordMasked(), "Password is not masked.");
    }
}
