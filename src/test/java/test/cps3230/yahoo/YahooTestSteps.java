package test.cps3230.yahoo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class YahooTestSteps{
    WebDriver driver;

    @Given("I am a user of news.yahoo.com")
    public void iAmAUserOfNewsYahooCom() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @When("I visit news.yahoo.com")
    public void iVisitNewsYahooCom() {
        driver.get("https://www.news.yahoo.com");
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);
    }

    @And("I click on the {string} section")
    public void iClickOnTheSection(String arg0) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='"+arg0+"']")));
        WebElement element = driver.findElement(By.xpath("//a[@title='"+arg0+"']"));
        element.click();
    }

    @Then("I should be taken to {string} section")
    public void iShouldBeTakenToSection(String arg0) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String resultPage = "https://www.yahoo.com/news/"+arg0.toLowerCase()+"/";
        wait.until(ExpectedConditions.urlMatches(resultPage));
        Assertions.assertEquals(resultPage, driver.getCurrentUrl());
    }

    @And("the section should have at least {int} stories")
    public void theSectionShouldHaveAtLeastResStories(int int1) {
        List<WebElement> articles = driver.findElements((By.xpath("//li[@class='js-stream-content Pos(r)']")));
        Assertions.assertTrue(articles.size() >= int1);
    }

    @And("I search for stories about {string}")
    public void iSearchForStoriesAbout(String arg0) {
        WebElement searchField = driver.findElement(By.xpath("//input[@data-reactid='45']"));
        searchField.sendKeys(arg0);
        WebElement searchNewsButton = driver.findElement(By.xpath("//button[@data-reactid='50']"));
        searchNewsButton.click();
    }

    @Then("I should see the search results of {string}")
    public void iShouldSeeTheSearchResultsOf(String arg0) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String formattedArg = arg0;
        formattedArg = formattedArg.replace(' ', '+');
        String resultPage = "https://news.search.yahoo.com/search?p=" + formattedArg;
        wait.until(ExpectedConditions.urlContains(resultPage));

        Assertions.assertTrue(driver.getCurrentUrl().contains(resultPage));
    }

    @And("there should be at least {int} stories in the search results")
    public void thereShouldBeAtLeastStoriesInTheSearchResults(int arg0) {
        List<WebElement> newsArticles = driver.findElements(By.className("compArticleList"));

        Assertions.assertTrue(newsArticles.size() >= arg0);
    }

    @When("I click on the first story in the results")
    public void iClickOnTheFirstStoryInTheResults() {
        List<WebElement> newsArticles = driver.findElements(By.className("compArticleList"));
        List<WebElement> firstArticle = newsArticles.get(0).findElements(By.tagName("a"));

        firstArticle.get(1).click();
    }

    @Then("I should be taken to the story")
    public void iShouldBeTakenToTheStory() {
        WebDriverWait wait2 = new WebDriverWait(driver, 15);
        wait2.until(ExpectedConditions.numberOfWindowsToBe(3));

        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(2));
        WebElement articleHeadline = driver.findElement(By.xpath("//h1[@data-test-locator='headline']"));

        Assertions.assertNotNull(articleHeadline);
    }

    @Then("I should see the widget")
    public void iShouldSeeTheWidget() {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,350)");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Col2-5-WeatherSimpleForecast")));

        WebElement weatherWidget = driver.findElement(By.id("Col2-5-WeatherSimpleForecast"));

        Assertions.assertNotNull(weatherWidget);
    }


    @When("I try to get weather about {string}")
    public void iTryToGetWeatherAbout(String arg0) {
        WebElement weatherWidget = driver.findElement(By.id("Col2-5-WeatherSimpleForecast"));
        WebElement button = weatherWidget.findElement(By.tagName("button"));
        button.click();
        driver.switchTo().activeElement().sendKeys(arg0);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);
    }

    @Then("I should see weather forecast of {string}")
    public void iShouldSeeWeatherForecastOf(String arg0) {
        WebElement weatherWidget = driver.findElement(By.id("Col2-5-WeatherSimpleForecast"));
        WebElement city = weatherWidget.findElement(By.tagName("h2"));
        String val = city.getText();

        Assertions.assertEquals(arg0, val);
    }

    @When("I try to click on the widget")
    public void iTryToClickOnTheWidget() {
        WebElement weatherWidget = driver.findElement(By.id("Col2-5-WeatherSimpleForecast"));
        WebElement forecast = weatherWidget.findElement(By.xpath("//a[@aria-label='Full forecast']"));
        forecast.click();
    }

    @Then("I should be redirected to a weather forecast webpage")
    public void iShouldBeRedirectedToAWeatherForecastWebpage() {
        WebDriverWait wait = new WebDriverWait(driver, 15);

        wait.until(ExpectedConditions.urlContains("/news/weather/"));
        String url = driver.getCurrentUrl();

        Assertions.assertTrue(url.contains("/news/weather/"));
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

}
