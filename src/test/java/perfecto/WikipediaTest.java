package perfecto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.cognifide.qa.bb.junit5.BobcatExtension;
import com.cognifide.qa.bb.junit5.selenium.WebdriverCloseExtension;
import com.cognifide.qa.bb.page.BobcatPageFactory;
import com.google.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import com.cognifide.qa.bb.junit5.guice.Modules;

import com.cognifide.qa.bb.modules.BobcatRunModule;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.test.TestContext;

import perfecto.pageobjects.WikipediaPage;
import perfecto.pageobjects.DefinitionPage;
import perfecto.reporting.ReportiumManager;
import perfecto.utils.CustomWatcher;
import perfecto.utils.CustomWebdriverCloseExtension;

@Modules(BobcatRunModule.class)
@ExtendWith(CustomWatcher.class)
public class WikipediaTest<TestRule> {

  private static final String SEARCH_QUERY = "hello world";
  private static final String HEADING = "\"Hello, World!\" program";

  @Inject
  private BobcatPageFactory bobcatPageFactory;

  @Inject
  private DefinitionPage definitionPage;

  @Inject
  private ReportiumManager reportiumManager;

  private ReportiumClient reportiumClient;


  @Inject
  private WebDriver driver;


  @Test
  public void wikipediaSearchTest() {
    WikipediaPage homePage = bobcatPageFactory.create("https://www.wikipedia.org", WikipediaPage.class);
    reportiumClient.stepStart("Search for "+SEARCH_QUERY);
    homePage.open().getSearchComponent().searchForQuery(SEARCH_QUERY);
    reportiumClient.stepStart("Search result validation.");
    assertThat(definitionPage.getHeading(), is(HEADING));
    reportiumClient.reportiumAssert("Verify result.",definitionPage.getHeading().equals(HEADING));
  }

  @Test
  public void wikipediaSearchTest2() {
      WikipediaPage homePage = bobcatPageFactory.create("https://www.wikipedia.org", WikipediaPage.class);
      reportiumClient.stepStart("Search for "+SEARCH_QUERY);
      homePage.open().getSearchComponent().searchForQuery(SEARCH_QUERY);
      reportiumClient.stepStart("Search result validation.");
      assertThat(definitionPage.getHeading(), is(HEADING));
      reportiumClient.reportiumAssert("Verify result.",definitionPage.getHeading().equals(HEADING));
  }

  @BeforeEach
  public void before(TestInfo testInfo){
    String methodName = testInfo.getDisplayName().replace("(","").replace(")","");
    ReportiumManager.startReportiumClient(driver);
    reportiumClient = ReportiumManager.getReportiumClient();
    reportiumClient.testStart(methodName, new TestContext("wiki1", "wiki2"));
  }


}
