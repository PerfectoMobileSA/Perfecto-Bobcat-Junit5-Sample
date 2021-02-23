package perfecto.utils;

import com.cognifide.qa.bb.junit5.selenium.WebdriverCloseExtension;
import com.cognifide.qa.bb.modules.BobcatRunModule;
import com.cognifide.qa.bb.provider.selenium.webdriver.close.ClosingAwareWebDriver;
import com.cognifide.qa.bb.provider.selenium.webdriver.close.ClosingAwareWebDriverWrapper;
import com.google.inject.Inject;
import com.perfecto.reportium.test.result.TestResultFactory;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import perfecto.reporting.ReportiumManager;

public class CustomWatcher implements TestWatcher, AfterAllCallback {

    private List<TestResultStatus> testResultsStatus = new ArrayList<>();

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        ((ClosingAwareWebDriverWrapper)ReportiumManager.getDriver()).forceShutdown();
    }

    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED;
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println("Test Disabled for test: " + context.getDisplayName());
        testResultsStatus.add(TestResultStatus.DISABLED);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("Test Successful for test: " + context.getDisplayName());
        ReportiumManager.getReportiumClient().testStop(TestResultFactory.createSuccess());
        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("Test Aborted for test: " + context.getDisplayName());
        testResultsStatus.add(TestResultStatus.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("Test Failed for test: " + context.getDisplayName() + " with reason: " + cause.getMessage());
        ReportiumManager.getReportiumClient().testStop(TestResultFactory.createFailure(cause));
        testResultsStatus.add(TestResultStatus.FAILED);
    }

}
