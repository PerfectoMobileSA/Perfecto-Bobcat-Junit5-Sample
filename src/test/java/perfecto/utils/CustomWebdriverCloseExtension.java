package perfecto.utils;

import com.cognifide.qa.bb.junit5.selenium.WebdriverCloseExtension;
import com.cognifide.qa.bb.provider.selenium.webdriver.close.ClosingAwareWebDriverWrapper;

import org.junit.jupiter.api.extension.ExtensionContext;

import perfecto.reporting.ReportiumManager;

public class CustomWebdriverCloseExtension extends WebdriverCloseExtension {

    /**
     * {@inheritDoc}
     */
    @Override
    // we need this for potential failures in BeforeEach methods
    public void afterAll(ExtensionContext context) {

    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {

    }
}


