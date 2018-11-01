import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class LinkTask {

	public static void LinkTaskMethod(String SRRequestID,String TaskID1,String TaskID2,String TaskID3,String TaskID4,String TaskID5) throws InterruptedException {
	
		// User inputs
		String RTC_link = "https://ccm.catamaran.oncloudone.net/ccm/web/projects/Training%20%28ccm%29";
		String User_Name = "adkansal";
		String Password = "AjhskWelcome_123E#$%&*ka%hPasAKaKNAPranay@30464283Ashvj".substring(35,44);		

		//Setting up IE properties and handling Protected mode/Zoom 100% exceptions
		System.setProperty("webdriver.ie.driver", "C:\\Users\\akansal2\\Downloads\\IEDriverServer.exe");
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true );
		WebDriver d1 = new InternetExplorerDriver(ieCapabilities);
		
		//Opening RTC link in IE Browser
		d1.navigate().to(RTC_link);
		d1.manage().window().maximize();

		//Logging in RTC		
		// Storing all Input values and object address
		WebElement Wb_UserName = d1.findElement(By.id("widget_jazz_app_internal_LoginWidget_0_userId"));
		WebElement Wb_Password = d1.findElement(By.id("jazz_app_internal_LoginWidget_0_password"));
		WebElement Wb_LogIn = d1.findElement(By.tagName("button"));
		
		
		//Introducing implicit wait to provide enough time to load the page and its objects
		d1.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);

		//Actions on WebElements and logging into the account		
		Wb_UserName.sendKeys(User_Name);
		Wb_Password.clear();
		Wb_Password.sendKeys(Password);
		Wb_LogIn.sendKeys(Keys.ENTER);

		
		//Identifying the search input box
		WebElement SearchInputBox = d1.findElement(By.className("SearchInputText"));
		SearchInputBox.sendKeys(SRRequestID,Keys.ENTER);
		Thread.sleep(10000);
		
		//Identifying Links Tab
		WebElement Links = d1.findElement(By.linkText("Links"));
		Links.sendKeys(Keys.ENTER);
		
		//Identifying Add Related Tab
		WebElement AddRelated = d1.findElement(By.xpath(".//*[@id='com_ibm_team_rtc_foundation_web_ui_views_ActionDropdownView_0']/div[2]/span[1]"));
		AddRelated.sendKeys(Keys.ENTER);
		
		
		//linking the task ID 1
		
		if(!TaskID1.equals("")){
		WebElement QueryInput1 = d1.findElement(By.className("QueryInput"));
		QueryInput1.sendKeys(TaskID1);
		Thread.sleep(2000);
		QueryInput1.sendKeys(Keys.DOWN,Keys.SPACE,Keys.ENTER);
		Thread.sleep(1000);
		}
		
		//linking the task ID 2
		if(!TaskID2.equals("")){
		WebElement QueryInput2 = d1.findElement(By.className("QueryInput"));
		QueryInput2.sendKeys(TaskID2);
		Thread.sleep(2000);
		QueryInput2.sendKeys(Keys.DOWN,Keys.SPACE,Keys.ENTER);
		Thread.sleep(1000);
		}
		
		
		// linking the task ID 3
		if(!TaskID3.equals("")){
		WebElement QueryInput3 = d1.findElement(By.className("QueryInput"));	
		QueryInput3.sendKeys(TaskID3);
		Thread.sleep(2000);
		QueryInput3.sendKeys(Keys.DOWN,Keys.SPACE,Keys.ENTER);
		Thread.sleep(1000);
		}
		
		//linking the task ID 4
		if(!TaskID4.equals("")){
		WebElement QueryInput4 = d1.findElement(By.className("QueryInput"));		
		QueryInput4.sendKeys(TaskID4);
		Thread.sleep(2000);
		QueryInput4.sendKeys(Keys.DOWN,Keys.SPACE,Keys.ENTER);
		Thread.sleep(1000);
		}
		
		
		//linking the task ID 5
		if(!TaskID5.equals("")){
		WebElement QueryInput5 = d1.findElement(By.className("QueryInput"));
		QueryInput5.sendKeys(TaskID5);
		Thread.sleep(2000);
		QueryInput5.sendKeys(Keys.DOWN,Keys.SPACE,Keys.ENTER);
		Thread.sleep(1000);
		}
		
		
		//Clicking cancel to come out to the main screen
		WebElement QueryInput = d1.findElement(By.className("QueryInput"));
		QueryInput.sendKeys(Keys.ESCAPE);
		Thread.sleep(2000);
		
		// Clicking save
		d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_ui_internal_view_editor_WorkItemEditorHeader_0']/div[1]/span/span[3]/span/button[2]")).sendKeys(Keys.ENTER);
			
		//
		Thread.sleep(10000);
		d1.quit();
	}

}
