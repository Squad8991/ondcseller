package pageobjects;

import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    // Locators
    private final String usernameField = "input[name='email']"; 
    private final String passwordField = "input[name='password']"; 
    private final String loginButton = "//button[@type='submit']"; 
    private final String profileIcon = "div.absolute.inset-0.rounded-full.shadow-inner";  
    private final String errorMessageLocator = "//div[contains(text(),'Invalid Email or password!')]"; 
    private final String forgotPasswordLink = "//a[@class='text-sm font-medium text-emerald-500 dark:text-emerald-400 hover:underline']"; 
    private final String recoverPasswordButton = "text=Recover password";
    private final String emailFieldForRecovery = "//input[@placeholder='john@doe.com']"; 
    private final String passwordRecoverySuccessMessage = "div.success-message"; 
    private final String errorMessageRecovery = "div.error-message";

    // Constructor
    public LoginPage(Page page) {
        if (page == null) {
            throw new IllegalArgumentException("Page object cannot be null");
        }
        this.page = page;
    }

    // Actions
    public void enterUsername(String username) {
        page.locator(usernameField).fill(username);
    }

    public void enterPassword(String password) {
        page.locator(passwordField).fill(password);
    }

    public void clickLogin() {
        page.locator(loginButton).click(); 
    }

    public boolean isErrorMessageDisplayed() {
        try {
            page.waitForSelector(errorMessageLocator, new Page.WaitForSelectorOptions().setTimeout(5000));
            return page.locator(errorMessageLocator).isVisible(); 
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordMasked() {
        String typeAttribute = page.locator(passwordField).getAttribute("type");
        return "password".equals(typeAttribute);
    }

    public void clearUsername() {
        page.locator(usernameField).clear();
    }

    public void clearPassword() {
        page.locator(passwordField).clear();
    }

    public void clickProfile() {
        page.locator(profileIcon).click();
    }

    public void clickLogout() {
        page.locator("role=button[name='Log Out']").click();
    }

    // Forgot password actions
    public void clickForgotPassword() {
        page.locator(forgotPasswordLink).click();
    }

    public void enterEmailForPasswordRecovery(String email) {
        page.locator(emailFieldForRecovery).fill(email);
    }

    public void clickRecoverPassword() {
        page.locator(recoverPasswordButton).click();
    }

    public boolean isPasswordRecoverySuccessMessageDisplayed() {
        try {
            page.waitForSelector(passwordRecoverySuccessMessage, new Page.WaitForSelectorOptions().setTimeout(5000));
            return page.locator(passwordRecoverySuccessMessage).isVisible(); 
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayedForRecovery() {
        try {
            page.waitForSelector(errorMessageRecovery, new Page.WaitForSelectorOptions().setTimeout(5000));
            return page.locator(errorMessageRecovery).isVisible(); 
        } catch (Exception e) {
            return false;
        }
    }

    public void clearEmailForPasswordRecovery() {
        page.locator(emailFieldForRecovery).clear();
    }

    public String getEmailFieldForRecovery() {
        return emailFieldForRecovery;
    }

    public String getRecoverPasswordButton() {
        return recoverPasswordButton;
    }

    public String getLoginLink() {
        return "text=Already have an account? Login"; 
    }
}
