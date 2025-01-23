package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import pageobjects.LoginPage;
import testbase.BaseClass;
import utilities.ConfigReader;
import listeners.CustomListener;

@Listeners(CustomListener.class)
public class LoginTest extends BaseClass {

    private ConfigReader configReader;
    private LoginPage lp;
    private String username;
    private String password;

    @BeforeClass
    public void initialize() {
        configReader = new ConfigReader();
        username = configReader.getUsername();
        password = configReader.getPassword();
        lp = new LoginPage(page);
    }

    @Test(priority = 1)
    public void testValidLogin() throws InterruptedException {
        lp.enterUsername(username);
        lp.enterPassword(password);
        lp.clickLogin();

        Thread.sleep(2000);

        String actualTitle = page.title();
        String expectedTitle = "Squadcube | React eCommerce Admin Dashboard";
        Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match after successful login.");
        
        lp.clickProfile();
        lp.clickLogout();

        Thread.sleep(2000);
    }

    @Test(priority = 2)
    public void testInvalidLogin() throws InterruptedException {
        lp.clearUsername();
        lp.clearPassword();
        lp.enterUsername(username);
        lp.enterPassword("invalidPassword");
        lp.clickLogin();

        Thread.sleep(2000);

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message was not displayed for invalid login.");
    }

    @Test(priority = 3)
    public void testEmptyFieldsLogin() throws InterruptedException {
        lp.clearUsername();
        lp.clearPassword();
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed when both fields are empty.");
    }

    @Test(priority = 4)
    public void testBlankUsernameWithValidPassword() throws InterruptedException {
        lp.clearUsername();
        lp.clearPassword();
        lp.enterPassword(password);
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed for blank username.");
    }

    @Test(priority = 5)
    public void testBlankPasswordWithValidUsername() throws InterruptedException {
        lp.clearUsername();
        lp.clearPassword();
        lp.enterUsername(username);
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed for blank password.");
    }

    @Test(priority = 6)
    public void testPasswordMasking() throws InterruptedException {
        lp.clearUsername();
        lp.clearPassword();
        lp.enterPassword(password);
        Assert.assertTrue(lp.isPasswordMasked(), "Password is not masked.");

    }

    @Test(priority = 7)
    public void testForgotPasswordLink() throws InterruptedException {
        lp.clickForgotPassword();
        Thread.sleep(2000);

        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("forgot-password"), "User was not redirected to Forgot Password page.");

        Assert.assertTrue(page.locator("[name='verifyEmail']").isVisible(), "Email field is not visible.");
        Assert.assertTrue(page.locator("//*[@id='root']/div[2]/div/div/main/div/form/button").isVisible(), "Recover password button is not visible.");
        Assert.assertTrue(page.locator("//*[@id='root']/div[2]/div/div/main/div/p/a").isVisible(), "Login link is not visible.");
    }


    @Test(priority = 8,enabled = false)
    public void testForgotPasswordWithValidEmail() throws InterruptedException {
        lp.clickForgotPassword();
        lp.enterEmailForPasswordRecovery("john.doe@example.com");
        lp.clickRecoverPassword();
        Thread.sleep(2000);

        Assert.assertTrue(lp.isPasswordRecoverySuccessMessageDisplayed(), "Password recovery success message was not displayed.");
    }

    @Test(priority = 9, enabled = false)
    public void testForgotPasswordWithInvalidEmail() throws InterruptedException {
        lp.clickForgotPassword();
        lp.enterEmailForPasswordRecovery("invalidemail.com");
        lp.clickRecoverPassword();
        Thread.sleep(2000);

        Assert.assertTrue(lp.isErrorMessageDisplayedForRecovery(), "Error message was not displayed for invalid email.");
    }

    @Test(priority = 10, enabled = false)
    public void testForgotPasswordWithEmptyEmailField() {
        lp.clickForgotPassword();
        lp.clearEmailForPasswordRecovery();
        lp.clickRecoverPassword();

        Assert.assertTrue(lp.isErrorMessageDisplayedForRecovery(), "Error message not displayed for empty email field.");
    }

    @Test(priority = 11, enabled = false)
    public void testForgotPasswordWithInvalidEmailFormat() {
        lp.clickForgotPassword();
        lp.enterEmailForPasswordRecovery("john.doe.com");
        lp.clickRecoverPassword();

        Assert.assertTrue(lp.isErrorMessageDisplayedForRecovery(), "Error message was not displayed for invalid email format.");
    }

    @Test(priority = 12, enabled = false)
    public void testLoginAfterPasswordResetWithNewPassword() {
        lp.clickForgotPassword();
        lp.enterEmailForPasswordRecovery("john.doe@example.com");
        lp.clickRecoverPassword();

        String newPassword = "newPassword123";
        lp.enterUsername("john.doe@example.com");
        lp.enterPassword(newPassword);
        lp.clickLogin();

        String actualTitle = page.title();
        String expectedTitle = "Squadcube | React eCommerce Admin Dashboard";
        Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match after login with new password.");
    }

    @Test(priority = 13, enabled = false)
    public void testLoginAfterPasswordResetWithOldPassword() {
        lp.enterUsername("john.doe@example.com");
        lp.enterPassword(password);
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed for old password after reset.");
    }
}
