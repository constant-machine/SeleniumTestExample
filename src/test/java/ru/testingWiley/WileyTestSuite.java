package ru.testingWiley;

import java.util.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class WileyTestSuite {
	
	public WebDriver browser;
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	@Before
	public void setUp() {
		//browser = new FirefoxDriver();
		browser = new ChromeDriver();
		browser.get("https://www.wiley.com/en-us");
		// if 'undetected country' banner emerges
		if (ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".changeLocationConfirmBtn")) != null) {
			browser.findElement(By.cssSelector(".changeLocationConfirmBtn")).click();
		}
	}
	
	@After
	public void tearDown() {
		browser.quit();
	}

	@Test
	public void testSequence() {
		// STEP 1!
		// titles are
		WebElement whoWeServe = browser.findElement(By.cssSelector(".navigation-menu-items > li:nth-child(1) > a:nth-child(1)"));
		WebElement subjects = browser.findElement(By.cssSelector(".navigation-menu-items > li:nth-child(2) > a:nth-child(1)"));
		WebElement about = browser.findElement(By.cssSelector(".navigation-menu-items > li:nth-child(4) > a:nth-child(1)"));
		
		Boolean titlesAreTrue = false;
		titlesAreTrue = whoWeServe.getText().equals("WHO WE SERVE") && subjects.getText().equals("SUBJECTS") && about.getText().equals("ABOUT");
		Boolean isTitlesDisplayed = whoWeServe.isDisplayed() && subjects.isDisplayed() && about.isDisplayed();
		
		// assert at checkpoint
		try {
			Assert.assertTrue("STEP 1: Titles at the home page aren`t displayed!", isTitlesDisplayed);
			Assert.assertTrue("STEP 1: Titles at the home page aren`t what they should be!", titlesAreTrue);
		}
		catch (AssertionError e) {
			collector.addError(e);
		}
	
		
		// STEP 2!
		// this test will always fail, because there is subheader called "Bookstores" in WhoWeServe, and there is 12 subheaders
		//against 11 in task
		
		List<WebElement> wwsSubItems = browser.findElements(By.xpath(".//div[@id='Level1NavNode1']/ul/li"));
		
		String[] wwsItemsPattern =  {"Students", "Instructors", "Book Authors", "Professionals", "Researchers", "Institutions", "Librarians",
		"Corporations", "Societies", "Journal Editors", "Government"};
		
		Boolean rightTitlesWWSSubItems = true;
		Boolean rightNumWWSSubItems = true;
		if (wwsSubItems.size() == 11) {
			for (int i = 0; i < wwsSubItems.size(); i++) {
				// textContent shows parameters for hidden elements
				if (!wwsSubItems.get(i).findElement(By.tagName("a")).getAttribute("textContent").contains(wwsItemsPattern[i])) {
					rightTitlesWWSSubItems = false;
				}
			} 
		}
		else {
			rightTitlesWWSSubItems = false;
			rightNumWWSSubItems = false;
		}
		
		// assert at checkpoint
		try {
			Assert.assertTrue("STEP 2: Number of WhoWeServe titles isn`t right!", rightNumWWSSubItems);
			Assert.assertTrue("STEP 2: WhoWeServe titles aren`t what they should be!", rightTitlesWWSSubItems);
		}
		catch (AssertionError e) {
			collector.addError(e);
		}
	
		
		// STEP 3!
		Actions action = new Actions(browser);
		WebDriverWait wait = new WebDriverWait(browser, 15);
		//finding elements needed
		whoWeServe = browser.findElement(By.xpath("/html/body/main/header/div/div[1]/nav/ul[1]/li[1]"));
		WebElement studentsLink = browser.findElement(By.xpath(".//div[@id='Level1NavNode1']/ul/li[1]/a"));
		//chain of actions
		action.moveToElement(whoWeServe).perform();
		wait.until(ExpectedConditions.visibilityOf(studentsLink));
		action.moveToElement(studentsLink).click().build().perform();
		//wait for page refreshing, wait for header 'students'
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[2]/div/div[1]/ul/li[2]")));
		
		//conditions variables
		Boolean isUrlRight = false;
		Boolean isStudentsHeaderDisplayed = false;
		Boolean isStudentsHeaderRight = false;
		Boolean isLinksAlright = true;
		//url check
		if (browser.getCurrentUrl().equals("https://www.wiley.com/en-us/students")) { isUrlRight = true; }
		//header check
		WebElement studHeader = browser.findElement(By.xpath("/html/body/main/div[2]/div/div[1]/ul/li[2]"));
		if (studHeader.getText().equals("Students")) {
			isStudentsHeaderRight = true;
		}
		isStudentsHeaderDisplayed = studHeader.isDisplayed();
		//links check
		WebElement container = browser.findElement(By.cssSelector("div.container:nth-child(3)"));
		List<WebElement> linksLearnMore = container.findElements(By.tagName("a"));
		for (int i=0; i<linksLearnMore.size(); i++) {
			if (!linksLearnMore.get(i).getAttribute("href").contains("www.wileyplus.com")) {
				isLinksAlright = false;
			}
		}
		
		// assert at checkpoint
		try {
			Assert.assertTrue("STEP 3: Students page hasn`t opened!", isUrlRight);
			Assert.assertTrue("STEP 3: Students header isn`t displayed!", isStudentsHeaderDisplayed);
			Assert.assertTrue("STEP 3: Students header isn`t 'Students'!", isStudentsHeaderRight);
			Assert.assertTrue("STEP 3: Links on page don`t direct to 'www.wileyplus.com'", isLinksAlright);
		}
		catch (AssertionError e) {
			collector.addError(e);
		}
		
		
		// STEP 4!
		//finding elements needed
		subjects = browser.findElement(By.xpath("/html/body/main/header/div/div[1]/nav/ul[1]/li[2]"));
		WebElement educationLink = browser.findElement(By.xpath(".//div[@id='Level1NavNode2']/ul/li[9]/a"));
		//chain of actions
		action.moveToElement(subjects).perform();
		wait.until(ExpectedConditions.visibilityOf(educationLink));
		//action.moveToElement(educationLink).perform();
		action.moveToElement(educationLink).click().build().perform();
		//wait for page refreshing
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[3]/div/div/div[1]/ul/li[3]")));
		
		//conditions check
		Boolean isEducationHeaderDisplayed = false;
		Boolean isEducationHeaderRight = false;
		WebElement subjectsHeader = browser.findElement(By.xpath("/html/body/main/div[3]/div/div/div[1]/ul/li[3]"));
		isEducationHeaderDisplayed = subjectsHeader.isDisplayed();
		if (subjectsHeader.getText().equals("Education")) {isEducationHeaderRight = true;}
		
		//side panel items check
		Boolean isItemsRight = true;
		Boolean isItemsDisplayed = true;
		WebElement sidePanel = browser.findElement(By.xpath(".//div[@class='side-panel']"));
		List<WebElement> sidePanelItems = sidePanel.findElements(By.xpath(".//li/a"));
		
		String[] subjectsItemsPattern = {"Information & Library Science", "Education & Public Policy", "K-12 General",
		"Higher Education General", "Vocational Technology", "Conflict Resolution & Mediation (School settings)", 
		"Curriculum Tools- General", "Special Educational Needs", "Theory of Education", "Education Special Topics",
		"Educational Research & Statistics", "Literacy & Reading", "Classroom Management"};
		
		// compare
		if (sidePanelItems.size() == 13) {
			for (int i=0; i<sidePanelItems.size(); i++) {
				if (!sidePanelItems.get(i).isDisplayed()) {isItemsDisplayed = false;}
				if (!sidePanelItems.get(i).getText().contains(subjectsItemsPattern[i])) {
					isItemsRight = false;
				}
			}
		}
		else {
			isItemsRight = false;
			isItemsDisplayed = false;
		}
		
		// assert at checkpoint
		try {
			Assert.assertTrue("STEP 4: Education header isn`t displayed!", isEducationHeaderDisplayed);
			Assert.assertTrue("STEP 4: Header isn`t 'Education!'", isEducationHeaderRight);
			Assert.assertTrue("STEP 4: Subjects list items aren`t right!", isItemsRight);
			Assert.assertTrue("STEP 4: Subjects list isn`t displayed!", isItemsDisplayed);
		}
		catch (AssertionError e) {
			collector.addError(e);
		}
	
		
		// STEP 5!
		WebElement wileyLogo = browser.findElement(By.xpath(".//a[@href='/en-us/']"));
		wait.until(ExpectedConditions.visibilityOf(wileyLogo));
		action.moveToElement(wileyLogo, 50, 10).click().perform();
		wait.until(ExpectedConditions.urlToBe("https://www.wiley.com/en-us"));
				
		Boolean isItHomePage = browser.getCurrentUrl().equals("https://www.wiley.com/en-us");
		
		// assert at checkpoint
		try {
			Assert.assertTrue("STEP 5: Home page hasn`t opened!", isItHomePage);
		}
		catch (Exception e) {
			collector.addError(e);
		}

		
		// STEP 6!
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js-site-search-input")));
		WebElement searchInput = browser.findElement(By.id("js-site-search-input"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".glyphicon")));
		WebElement searchButton = browser.findElement(By.cssSelector(".glyphicon"));
		wileyLogo = browser.findElement(By.cssSelector(".yCmsContentSlot > div:nth-child(1) > a:nth-child(1)"));
		searchInput.clear();
		action.moveToElement(searchButton, 24, 24).click();
		wait.until(ExpectedConditions.visibilityOf(wileyLogo));
		
		Boolean isNothingHappened = browser.getCurrentUrl().equals("https://www.wiley.com/en-us");
		
		// assert at checkpoint
		try {
			Assert.assertTrue("STEP 6: Page changed during the empty search!", isNothingHappened);
		}
		catch (AssertionError e) {
			collector.addError(e);
		}

		
		// STEP 7!
		searchInput.sendKeys("Java");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ui-id-2")));
		WebElement autocompleteContainer = browser.findElement(By.cssSelector("#ui-id-2"));
		// Container itself
		Boolean isAutocompContainerDisplayed = autocompleteContainer.isDisplayed();
		// Suggestions
		Boolean isSuggestionsRight = true;
		Boolean isSuggestionsQuantityRight = true;
		WebElement suggestions = autocompleteContainer.findElement(By.xpath(".//section[1]"));
		List<WebElement> suggestedItems = suggestions.findElements(By.className("search-highlight"));
		for (int i = 0; i < suggestedItems.size(); i++) {
			if (!suggestedItems.get(i).getText().equalsIgnoreCase("java")) {
				isSuggestionsRight = false;
			}
		} 
		if (suggestedItems.size() != 4) { isSuggestionsQuantityRight = false; }
		// Products
		Boolean isProductsRight = true;
		Boolean isProductsQuantityRight = true;
		WebElement products = autocompleteContainer.findElement(By.xpath(".//section[2]"));
		List<WebElement> productItems = products.findElements(By.className("search-highlight"));
		for (int i = 0; i < productItems.size(); i++) {
			if (!productItems.get(i).getText().equalsIgnoreCase("java")) {
				isProductsRight = false;
			}
		} 
		if (productItems.size() != 5) { isProductsQuantityRight = false; }
		
		// assert at checkpoint
		try {
			Assert.assertTrue("STEP 7: 'Suggestions' item doesn't contain 'java' word!", isSuggestionsRight);
			Assert.assertTrue("STEP 7: Autocompletion container hasn`t showed!", isAutocompContainerDisplayed);
			Assert.assertTrue("STEP 7: The quantity of items in 'Suggections' section isnt right!", isSuggestionsQuantityRight);
			Assert.assertTrue("STEP 7: 'Products' item doesn't contain 'java' word!", isProductsRight);
			Assert.assertTrue("STEP 7: The quantity of items in 'Products' section isnt right!", isProductsQuantityRight);
		}
		catch (AssertionError e) {
			collector.addError(e);
		}
	
		
		// STEP 8!
		searchButton.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("products-list")));
		WebElement productsContainer = browser.findElement(By.className("products-list"));
		List<WebElement> productItemsFinalFirstTry = productsContainer.findElements(By.className("product-item"));
		
		Boolean isQuantityItemsRight = true;
		if (productItemsFinalFirstTry.size() != 10) { isQuantityItemsRight = false; }
		
		Boolean areAddButtonsDisplayed = true;
		Boolean isTitlesRight = true;
		ArrayList<String> productItemTitlesSave = new ArrayList<String>(productItemsFinalFirstTry.size());
		for (int i=0; i<productItemsFinalFirstTry.size(); i++) {
			WebElement itemTitleHighlight = productItemsFinalFirstTry.get(i).findElement(By.className("search-highlight"));
			//save the titles for later
			WebElement itemTitle = productItemsFinalFirstTry.get(i).findElement(By.xpath(".//div[2]/h3/a"));
			productItemTitlesSave.add(itemTitle.getText());
			WebElement addButton;
			try {
				addButton = productItemsFinalFirstTry.get(i).findElement(By.xpath(".//button[@class='small-button add-to"
				+ "-cart-button js-add-to-cart']"));
				if (!addButton.isDisplayed()) {areAddButtonsDisplayed = false;}
			} catch (Exception e) {
				areAddButtonsDisplayed = false;
			}
			if (!itemTitleHighlight.getText().equalsIgnoreCase("java")) { isTitlesRight = false; }
		}
		
		// assert at checkpoint
		try {
			Assert.assertTrue("STEP 8: Search items quantity is not 10!", isQuantityItemsRight);
			Assert.assertTrue("STEP 8: 'AddToCart' buttons aren`t displayed!", areAddButtonsDisplayed);
			Assert.assertTrue("STEP 8: Titles of search items doesn`t contain 'java'!", isTitlesRight);
		}
		catch (AssertionError e) {
			collector.addError(e);
		}
	
		
		// STEP 9!
		searchInput = browser.findElement(By.id("js-site-search-input"));
		searchButton = browser.findElement(By.cssSelector(".glyphicon"));
		searchInput.clear();
		searchInput.sendKeys("Java");
		searchButton.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("products-list")));
		productsContainer = browser.findElement(By.className("products-list"));
		List<WebElement> productItemsFinalSecondTry = productsContainer.findElements(By.className("product-item"));
		
		Boolean isListSizeSame = true;
		Boolean isListsTitlesSame = true;
		if (productItemsFinalFirstTry.size() == productItemsFinalSecondTry.size()) {
			for (int i = 0; i < productItemsFinalFirstTry.size(); i++) {
				
				WebElement productTitleSecond = productItemsFinalSecondTry.get(i).findElement(By.xpath(".//div[2]/h3/a"));
				if (!productItemTitlesSave.get(i).equals(productTitleSecond.getText())) {
					isListsTitlesSame = false;
				}
			} 
		}
		else {
			isListSizeSame = false;
		}
		
		
		// assert at checkpoint
		try {
			Assert.assertTrue("STEP 9: Search lists sizes aren`t equal!", isListSizeSame);
			Assert.assertTrue("STEP 9: Items in lists aren`t same!", isListsTitlesSame);
		}
		catch (AssertionError e) {
			collector.addError(e);
		}
	}
}
