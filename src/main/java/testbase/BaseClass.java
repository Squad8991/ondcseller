package testbase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.microsoft.playwright.*;
import utilities.ConfigReader;

public class BaseClass {
    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    // Setup Playwright
    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        
        // Open URL
        String url = new ConfigReader().getURL();
        page.navigate(url);
    }
    
    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();        
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}