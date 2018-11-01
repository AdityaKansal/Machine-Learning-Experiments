import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.bcel.generic.GETSTATIC;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.annotations.Until;


public class TaskCreation_RTC {

	public static String[] RTC(String Summary_Variable, String SRNumber_Variable,String  FiledAgainstTeam_Variable,String Activity_Variable,String  AssignedTo_Variable,String CapacityPlannedFor_Variable,String InitialEstimate_Variable) throws InterruptedException, IOException {
// User inputs
		String RTC_link = "https://ccm.catamaran.oncloudone.net/ccm/web/projects/Training%20%28ccm%29";
		//https://ccm.catamaran.oncloudone.net/ccm/web/projects/Training%20%28ccm%29#action=com.ibm.team.dashboard.viewDashboard
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


// Storing all Input values and object address		
		WebElement Wb_UserName = d1.findElement(By.id("widget_jazz_app_internal_LoginWidget_0_userId"));
		WebElement Wb_Password = d1.findElement(By.id("jazz_app_internal_LoginWidget_0_password"));
		WebElement Wb_LogIn = d1.findElement(By.tagName("button"));
		
		
	
//Introducing implicit wait to provide enough time to load the page and its objects
		d1.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);

//Actions on WebElements and logging into the account		
		Wb_UserName.sendKeys(User_Name);
		Wb_Password.clear();
		Wb_Password.sendKeys(Password);
		Wb_LogIn.sendKeys(Keys.ENTER);
		
//Identifying the search input box
		WebDriverWait wait1 = new WebDriverWait(d1, 40);
		wait1.until(ExpectedConditions.elementToBeClickable(By.className("SearchInputText")));
		WebElement SearchInputBox = d1.findElement(By.className("SearchInputText"));
		SearchInputBox.sendKeys(Summary_Variable,Keys.ENTER);
		Thread.sleep(5000);
				
				
//checking if  relevant task already exists
		boolean Exist = false;
		List<WebElement> RelevantTasks = d1.findElements(By.partialLinkText(Summary_Variable));
		if(!RelevantTasks.isEmpty()){
		for(WebElement r: RelevantTasks){
			if(r.getText().equalsIgnoreCase(Summary_Variable)){
				Exist = true;
				break;
			}
			}
		}
		
if(Exist){
	d1.quit();
	RelevantTasks.clear();
	return new String[]{"Task ID already exists","No Task Generated"};
	
}else{

// Reached on main page and navigating to create task screen
		
		d1.navigate().to(RTC_link);
		WebElement Work_Item = d1.findElement(By.linkText("Work Items"));
		Work_Item.sendKeys(Keys.ENTER);
		WebElement Task = d1.findElement(By.linkText("Task"));
		Task.sendKeys(Keys.ENTER);;
		
//Identifying all mandatory fields while creating tasks
		
		//Summary of task
		WebDriverWait wait = new WebDriverWait(d1, 120);   // providing wait to lod the complete page
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".RichTextEditorWidget.ViewBorder.com-ibm-team-workitem-shared-RichText.cke_editable.cke_editable_inline.cke_contents_ltr")));
		WebElement Summary = d1.findElement(By.cssSelector(".RichTextEditorWidget.ViewBorder.com-ibm-team-workitem-shared-RichText.cke_editable.cke_editable_inline.cke_contents_ltr"));
		Summary.sendKeys(Summary_Variable);
		
		// SR number
		WebElement SRNumber = d1.findElement(By.cssSelector("input[class='ViewBorder']"));
		SRNumber.sendKeys(SRNumber_Variable);
		
		
		//Filed Against Team
		WebElement FiledAgainstTeam_DropDown=d1.findElement(By.xpath("//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_1']/div[2]"));
		FiledAgainstTeam_DropDown.sendKeys(Keys.ENTER);
		WebElement FiledAgainstTeam_Input =d1.findElement(By.xpath("/html/body/div[16]/div[1]/input"));
		wait.until(ExpectedConditions.elementToBeClickable(FiledAgainstTeam_Input));
		FiledAgainstTeam_Input.sendKeys(FiledAgainstTeam_Variable);
		Thread.sleep(1000);
		FiledAgainstTeam_Input.sendKeys(Keys.DOWN,Keys.ENTER);
		
	
		//Activity
		WebElement Activity_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_2']/div[2]"));
		Activity_DropDown.sendKeys(Keys.ENTER);
		WebElement Activity_Input =d1.findElement(By.xpath("/html/body/div[16]/div[1]/input"));
		wait.until(ExpectedConditions.elementToBeClickable(Activity_Input));
		Activity_Input.sendKeys(Activity_Variable);
		Thread.sleep(1000);
		Activity_Input.sendKeys(Keys.DOWN,Keys.ENTER);
		
		
		//Assigned to
		WebElement AssignedTo_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_3']/div[2]"));
		AssignedTo_DropDown.sendKeys(Keys.ENTER);
		WebElement AssignedTo_Input =d1.findElement(By.xpath("/html/body/div[16]/div[1]/input"));
		wait.until(ExpectedConditions.elementToBeClickable(AssignedTo_Input));
		AssignedTo_Input.sendKeys(AssignedTo_Variable);
		Thread.sleep(1000);
		AssignedTo_Input.sendKeys(Keys.DOWN,Keys.ENTER);
		
		//Capacity Planned For
		WebElement CapacityPlannedFor_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_5']/div[2]"));
		CapacityPlannedFor_DropDown.sendKeys(Keys.ENTER);
		WebElement CapacityPlannedFor_Input =d1.findElement(By.xpath("/html/body/div[16]/div[1]/input"));
		wait.until(ExpectedConditions.elementToBeClickable(CapacityPlannedFor_Input));
		CapacityPlannedFor_Input.sendKeys(CapacityPlannedFor_Variable);
		Thread.sleep(1000);
		CapacityPlannedFor_Input.sendKeys(Keys.DOWN,Keys.ENTER);
		
		
		//Initial Estimate
		WebElement InitialEstimate = d1.findElement(By.cssSelector(".readonly-text"));
		InitialEstimate.sendKeys(InitialEstimate_Variable);
		
		// Save Button
		List<WebElement> Save = d1.findElements(By.tagName("button"));
		for(WebElement s : Save){
			if(s.getText().equalsIgnoreCase("Save")){
				s.sendKeys(Keys.ENTER);
				d1.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			}
		}
	
		// Get Task ID
		Thread.sleep(5000);
		d1.getWindowHandle();
		String TaskLink = d1.getCurrentUrl();
		String TaskID = d1.getCurrentUrl().substring(d1.getCurrentUrl().length() - 5);
		Thread.sleep(2000);	
		d1.quit();
		RelevantTasks.clear();
		return new String[]{TaskID,TaskLink};
	}

		}
	}


