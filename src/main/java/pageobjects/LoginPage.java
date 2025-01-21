package pageobjects;

import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    // Locators
    private final String usernameField = "input[name='email']";  // CSS Selector
    private final String passwordField = "input[name='password']";  // CSS Selector
    private final String loginButton = "button[type='submit']";  // CSS Selector
    private final String profileIcon = "div.absolute.inset-0.rounded-full.shadow-inner";  // CSS Selector
    private final String errorMessageLocator = "//div[contains(@class, 'error-message') or contains(@class, 'alert')]"; // XPath

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
            // Wait for the error message to appear in the DOM (timeout 5 seconds)
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
}
