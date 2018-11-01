import java.awt.RenderingHints.Key;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class RTC_SR_RequestCreation {

	public  static String[] RTC_SRRequest(String Summary_Variable,String  PID_Variable,String  ServiceRequest_Variable,String  Client_Variable,String  FiledAgainstTeam1_Variable,String CapacityPlannedFor1_Variable ,String SRType_Variable,String Product_Variable,String TargetedRelease_Variable ,String OwnedBy_Variable,String WorkRequestType_Variable,String TestingRequired_Variable 
	) throws InterruptedException {

		
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
				d1.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

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
				List<WebElement> RelevantSRRequest = d1.findElements(By.partialLinkText(Summary_Variable));
				if(!RelevantSRRequest.isEmpty()){
				for(WebElement r: RelevantSRRequest){
					if(r.getText().equalsIgnoreCase(Summary_Variable)){
						Exist = true;
						
						break;
					}
					}
				}
				
		if(Exist){
			d1.quit();
			RelevantSRRequest.clear();
			return new String[]{"Service request already exists","No service request created"};
			
		}else{

		// Reached on main page and navigating to create task screen
				System.out.println("not exist");
				d1.navigate().to(RTC_link);				
		// Reached on main page and navigating to create task screen
				WebElement Work_Item = d1.findElement(By.linkText("Work Items"));
				Work_Item.sendKeys(Keys.ENTER);;
				WebElement Task = d1.findElement(By.linkText("SR Request"));
				Task.sendKeys(Keys.ENTER);
				
				
		//Identifying all mandatory fields while creating tasks
					
				//Summary of task
				WebDriverWait wait = new WebDriverWait(d1, 60);   // providing wait to load the complete page
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_editor_RichTextEditorView_0']/div[2]")));
				WebElement Summary = d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_editor_RichTextEditorView_0']/div[2]"));
				Summary.sendKeys(Summary_Variable);
				
				// PID
				WebElement PID = d1.findElement(By.xpath(".//*[@id='com_ibm_team_rtc_foundation_web_ui_views_TextView_2']/input"));
				PID.sendKeys(PID_Variable);
				
				// SR number
				WebElement SRNumber = d1.findElement(By.xpath(".//*[@id='com_ibm_team_rtc_foundation_web_ui_views_TextView_3']/input"));
				SRNumber.sendKeys(ServiceRequest_Variable);
				
				// Client
				WebElement Client = d1.findElement(By.xpath(".//*[@id='com_ibm_team_rtc_foundation_web_ui_views_TextView_4']/input"));
				Client.sendKeys(Client_Variable);
				
				
				//Filed Against Team
				WebElement FiledAgainstTeam_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_1']/div[2]"));
				FiledAgainstTeam_DropDown.sendKeys(Keys.ENTER);
				WebElement FiledAgainstTeam_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
				wait.until(ExpectedConditions.elementToBeClickable(FiledAgainstTeam_Input));
				FiledAgainstTeam_Input.sendKeys(FiledAgainstTeam1_Variable);
				Thread.sleep(1000);
				FiledAgainstTeam_Input.sendKeys(Keys.DOWN,Keys.ENTER);
				
				
				//Capacity Planned For
				WebElement CapacityPlannedFor_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_2']/div[2]"));
				CapacityPlannedFor_DropDown.sendKeys(Keys.ENTER);
				WebElement CapacityPlannedFor_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
				wait.until(ExpectedConditions.elementToBeClickable(CapacityPlannedFor_Input));
				CapacityPlannedFor_Input.sendKeys(CapacityPlannedFor1_Variable);
				Thread.sleep(2000);
				CapacityPlannedFor_Input.sendKeys(Keys.DOWN,Keys.ENTER);
				
				
				//SR type : Base/Retrofit
				String SRType = "Base";
				WebElement SRType_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_3']/div[2]"));
				if(SRType.equalsIgnoreCase(SRType_Variable)){
				SRType_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.ENTER);					
				}
				
				//Product
				WebElement Product_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_4']/div[2]"));
				Product_DropDown.sendKeys(Keys.ENTER);
				WebElement Product_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
				wait.until(ExpectedConditions.elementToBeClickable(Product_Input));
				Product_Input.sendKeys(Product_Variable);
				Thread.sleep(2000);
				Product_Input.sendKeys(Keys.DOWN,Keys.ENTER);
				
				//Target Release
				WebElement TargetRelease_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_5']/div[2]"));
				TargetRelease_DropDown.sendKeys(Keys.ENTER);
				WebElement TargetRelease_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
				wait.until(ExpectedConditions.elementToBeClickable(TargetRelease_Input));
				TargetRelease_Input.sendKeys(TargetedRelease_Variable);
				Thread.sleep(2000);
				TargetRelease_Input.sendKeys(Keys.DOWN,Keys.ENTER);
				
				//Owned By
				WebElement OwnedBY_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_6']/div[2]"));
				OwnedBY_DropDown.sendKeys(Keys.ENTER);
				WebElement OwnedBY_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
				wait.until(ExpectedConditions.elementToBeClickable(OwnedBY_Input));
				OwnedBY_Input.sendKeys(OwnedBy_Variable);
				Thread.sleep(1000);
				OwnedBY_Input.sendKeys(Keys.DOWN,Keys.ENTER);
				
				
				//Work Request Type
				String WorkRequestType = WorkRequestType_Variable;
				WebElement WorkRequestType_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_9']/div[2]"));
				if(WorkRequestType.equalsIgnoreCase("Enhancement")){
				WorkRequestType_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.ENTER);
				}
				else if(WorkRequestType.equalsIgnoreCase("Change Request")){
					WorkRequestType_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.DOWN,Keys.ENTER);
				}else if(WorkRequestType.equalsIgnoreCase("Production Defect")){
					WorkRequestType_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.DOWN,Keys.DOWN,Keys.ENTER);
				}
				
				//Testing Required : Yes/No/COTO
				String TestingRequired = TestingRequired_Variable;
				WebElement TestingRequired_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_10']/div[2]"));
				if(TestingRequired.equalsIgnoreCase("No")){
				TestingRequired_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.ENTER);					
				}else if(TestingRequired.equalsIgnoreCase(TestingRequired_Variable)){
					TestingRequired_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.DOWN,Keys.ENTER);		
				}
					
			// Save Button
				List<WebElement> Save1 = d1.findElements(By.tagName("button"));
		for(WebElement s : Save1){
			if(s.getText().equalsIgnoreCase("Save")){
				s.sendKeys(Keys.ENTER);
			}
		}
				
				
		// Get Task ID and Task link
				Thread.sleep(5000);
				d1.getWindowHandle();
				String TaskLink = d1.getCurrentUrl(); 
				String TaskID = d1.getCurrentUrl().substring(d1.getCurrentUrl().length() - 5);
				System.out.println(TaskID);
				Thread.sleep(2000);	
				d1.quit();
					
				Thread.sleep(2000);
				d1.quit();
				return new String[]{TaskID,TaskLink};

	}
	}
}
