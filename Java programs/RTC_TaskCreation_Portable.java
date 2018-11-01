import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RTC_TaskCreation_Portable {



	//Function to read Excel Sheet for Task creation

	public static String[] ReadExcel(String ExcelPath, String Arg_InputSheet,int Row,int Columns) throws EncryptedDocumentException, InvalidFormatException, IOException{


		//Reading Inputs from RTC Task Creation Excel sheet
		File src = new File(ExcelPath);
		FileInputStream Fin = new FileInputStream(src);
		Workbook InputWorkbook = WorkbookFactory.create(Fin);
		Sheet InputSheet = InputWorkbook.getSheet(Arg_InputSheet);


		//Array to store one set of values fetched from one row at a time				
		int Size = Columns -2;
		String a[]= new String[Size];

		//Loop to traverse thru the excel sheets	

		if(InputSheet.getRow(Row).getCell(1)!=null && InputSheet.getRow(Row).getCell(1).getStringCellValue().equalsIgnoreCase("Y")){   // checking null

			for(int i=2;i< Columns;i++){
				if(InputSheet.getRow(Row).getCell(i)!=null){
					if(!InputSheet.getRow(Row).getCell(i).getCellTypeEnum().equals("STRING")){
						InputSheet.getRow(Row).getCell(i).setCellType(CellType.STRING);
					}
					a[i-2]= InputSheet.getRow(Row).getCell(i).getStringCellValue();

				}
				else{
					a[i-2] = "";
				}

			}
			InputWorkbook.close();// connection close
			Fin.close();	
			return a;


		}else{
			InputWorkbook.close();// connection close
			Fin.close();	
			return null;
		}





	}	

	

	//------------------------------------Main Method-----------------------------------------------------------------------------------------------------------	

	public static void main(String[] args) throws IOException, InterruptedException,NullPointerException, EncryptedDocumentException, InvalidFormatException {

	
		//Paramterized approach
		
		String User_Account = System.getProperty("user.home").substring(9);
		
		//User Inputs for Excel sheets
		String ExcelPath = "C://Users//" + User_Account + "//Desktop//RTC_TaskCreation_Portable//RTC_Task creation_Input Sheet_Usage.xlsx";
		String RTCTaskInputSheetName = "RTC_Task Input";
		String RTC_SRRequest_InputSheetName = "RTC_SR_Request";	
		String RTC_LinkTask_InputSheetName = "Link Tasks";


		// User inputs for rational
		String RTC_link = "https://ccm.catamaran.oncloudone.net/ccm/web/projects/Optum-RTC#action=com.ibm.team.dashboard.viewDashboard";
		String User_Name = "adkansal";
		String Password = "Welcome@1234567";



		//Setting up IE properties and handling Protected mode/Zoom 100% exceptions

		System.setProperty("webdriver.ie.driver", "C://Users//" + User_Account + "//Desktop//RTC_TaskCreation_Portable//IEDriverServer.exe" );
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true );
		WebDriver d1 = new InternetExplorerDriver(ieCapabilities);


		//Opening RTC link in IE Browser
		d1.navigate().to(RTC_link);
		d1.manage().window().maximize();

		//Introducing implicit wait to provide enough time to load the page and its objects
		d1.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);


		//Storing all Input values and object address		
		WebElement Wb_UserName = d1.findElement(By.id("jazz_app_internal_LoginWidget_0_userId"));
		WebElement Wb_Password = d1.findElement(By.id("jazz_app_internal_LoginWidget_0_password"));
		WebElement Wb_LogIn = d1.findElement(By.tagName("button"));


		//Actions on WebElements and logging into the account		
		Wb_UserName.sendKeys(User_Name);
		Wb_Password.clear();
		Wb_Password.sendKeys(Password);
		Wb_LogIn.sendKeys(Keys.ENTER);




		// Calling RTC task method using its class's object 

		for(int j=2;j<150;j++){	

			if(ReadExcel(ExcelPath,RTCTaskInputSheetName,j,13) != null){
				String TaskInput[] = ReadExcel(ExcelPath,RTCTaskInputSheetName,j,13);

				try{

					String taskID1[] = RTC(d1,RTC_link,TaskInput[0],TaskInput[1],TaskInput[2],TaskInput[3],TaskInput[4],TaskInput[5],TaskInput[6],TaskInput[7],TaskInput[8],TaskInput[9],TaskInput[10]);


				}
				catch(Exception e){
					e.printStackTrace();
				}	
			}

		}


		//  SR request creation							


		for(int j=2;j<150;j++){	
			if(ReadExcel(ExcelPath,RTC_SRRequest_InputSheetName,j,19) != null){
				String SRRequestInput[] = ReadExcel(ExcelPath,RTC_SRRequest_InputSheetName,j,19);

				try{

					String taskID2[] = RTC_SRRequest(d1,RTC_link,SRRequestInput[0],SRRequestInput[1],SRRequestInput[2],SRRequestInput[3],SRRequestInput[4],SRRequestInput[5],SRRequestInput[6],SRRequestInput[7],SRRequestInput[8],SRRequestInput[9],SRRequestInput[10],SRRequestInput[11],SRRequestInput[12],SRRequestInput[13],SRRequestInput[14],SRRequestInput[15],SRRequestInput[16]);


				}
				catch(Exception e){
					e.printStackTrace();
				}
			}

		}

		//Link Task

		for(int j=1;j<150;j++){	
			if(ReadExcel(ExcelPath,RTC_LinkTask_InputSheetName,j,8) != null){
				String LinkInput[] = ReadExcel(ExcelPath,RTC_LinkTask_InputSheetName,j,8);

				try{

					String comment = LinkTaskMethod(d1,RTC_link,LinkInput[0],LinkInput[1],LinkInput[2],LinkInput[3],LinkInput[4],LinkInput[5]);

				}
				catch(Exception e){
					e.printStackTrace();
				}	
			}
		}


		d1.quit();



	}




	// ----------------------------------------------Task Creation method----------------------------------------------------------------------------------


	public static String[] RTC(WebDriver d1,String RTC_link,String Summary_Variable, String SRNumber_Variable,String  FiledAgainstTeam_Variable,String Activity_Variable,String CapacityPlannedFor_Variable,String InitialEstimate_Variable,String  AssignedTo_Variable,String Priority_Variable,String TestArea_Variable,String Description_Variable,String Comment_Variable) throws InterruptedException, IOException {

		d1.get(RTC_link);
		d1.navigate().refresh();

		boolean Alert_is_present = false;
		try{
			d1.switchTo().alert();
			Alert_is_present = true;
		}catch(Exception e){

		}

		if(Alert_is_present){
			d1.switchTo().alert().accept();

		}

		d1.navigate().to(RTC_link);


		d1.navigate().refresh();

		WebDriverWait wait1 = new WebDriverWait(d1, 30);
		d1.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);




		//Identifying the search input box


		wait1.until(ExpectedConditions.elementToBeClickable(By.className("SearchInputText")));
		WebElement SearchInputBox = d1.findElement(By.className("SearchInputText"));
		SearchInputBox.clear();
		SearchInputBox.sendKeys(Summary_Variable,Keys.ENTER);


		//d1.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor)d1;
		js.executeScript("return document.readyState").toString().equals("complete");




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
			//	RelevantTasks.clear(); ----------------------  uncomment this line for old code
			return new String[]{"Task ID already exists","No Task Generated"};

		}else{

			// Reached on main page and navigating to create task screen

			d1.get(RTC_link);

			wait1.until(ExpectedConditions.elementToBeClickable(By.linkText("Work Items")));
			WebElement Work_Item = d1.findElement(By.linkText("Work Items"));
			Work_Item.sendKeys(Keys.ENTER);
			wait1.until(ExpectedConditions.elementToBeClickable(By.linkText("Task")));
			WebElement Task = d1.findElement(By.linkText("Task"));

			Task.sendKeys(Keys.ENTER);

			//Identifying all mandatory fields while creating tasks

			//Summary of task
			d1.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);



			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".RichTextEditorWidget.ViewBorder.com-ibm-team-workitem-shared-RichText.cke_editable.cke_editable_inline.cke_contents_ltr")));
			WebElement Summary = d1.findElement(By.cssSelector(".RichTextEditorWidget.ViewBorder.com-ibm-team-workitem-shared-RichText.cke_editable.cke_editable_inline.cke_contents_ltr"));




			Summary.sendKeys(Summary_Variable);



			// SR number
			WebElement SRNumber = d1.findElement(By.cssSelector("input[class='ViewBorder']"));
			SRNumber.sendKeys(SRNumber_Variable);


			//Filed Against Team
			WebElement FiledAgainstTeam_DropDown=d1.findElement(By.xpath("//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_1']/div[2]"));
			FiledAgainstTeam_DropDown.sendKeys(Keys.ENTER);
			WebElement FiledAgainstTeam_Input =d1.findElement(By.xpath("/html/body/div[16]/div[1]/input"));
			wait1.until(ExpectedConditions.elementToBeClickable(FiledAgainstTeam_Input));
			FiledAgainstTeam_Input.sendKeys(FiledAgainstTeam_Variable);
			Thread.sleep(1000);
			if(FiledAgainstTeam_Variable.equalsIgnoreCase("Rxclaim QA")){
				FiledAgainstTeam_Input.sendKeys(Keys.DOWN,Keys.DOWN,Keys.DOWN,Keys.ENTER);
			}else{
				FiledAgainstTeam_Input.sendKeys(Keys.DOWN,Keys.ENTER);
			}



			//Activity
			WebElement Activity_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_2']/div[2]"));
			Activity_DropDown.sendKeys(Keys.ENTER);
			WebElement Activity_Input =d1.findElement(By.xpath("/html/body/div[16]/div[1]/input"));
			wait1.until(ExpectedConditions.elementToBeClickable(Activity_Input));
			Activity_Input.sendKeys(Activity_Variable);
			Thread.sleep(1000);
			Activity_Input.sendKeys(Keys.DOWN,Keys.ENTER);


			//Assigned to

			if(!AssignedTo_Variable.equalsIgnoreCase("")){
				WebElement AssignedTo_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_3']/div[2]"));
				AssignedTo_DropDown.sendKeys(Keys.ENTER);
				WebElement AssignedTo_Input =d1.findElement(By.xpath("/html/body/div[16]/div[1]/input"));
				wait1.until(ExpectedConditions.elementToBeClickable(AssignedTo_Input));
				AssignedTo_Input.sendKeys(AssignedTo_Variable);
				Thread.sleep(1000);
				AssignedTo_Input.sendKeys(Keys.DOWN,Keys.ENTER);

			}

			// Priority
			if(!Priority_Variable.equalsIgnoreCase("")){
				WebElement Priority_Dropdown = d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_4']/div[2]"));
				if(Priority_Variable.equalsIgnoreCase("Critical")){
					Priority_Dropdown.sendKeys(Keys.ENTER, Keys.UP,Keys.UP,Keys.UP,Keys.UP,Keys.ENTER);
				}else if(Priority_Variable.equalsIgnoreCase("High")){
					Priority_Dropdown.sendKeys(Keys.ENTER, Keys.UP,Keys.UP,Keys.UP,Keys.ENTER);
				}else if(Priority_Variable.equalsIgnoreCase("Medium")){
					Priority_Dropdown.sendKeys(Keys.ENTER, Keys.UP,Keys.UP,Keys.ENTER);
				}else if(Priority_Variable.equalsIgnoreCase("Low")){
					Priority_Dropdown.sendKeys(Keys.ENTER, Keys.UP,Keys.ENTER);
				}else if(Priority_Variable.equalsIgnoreCase("Emergency")){
					Priority_Dropdown.sendKeys(Keys.ENTER, Keys.UP,Keys.UP,Keys.UP,Keys.UP,Keys.UP,Keys.ENTER);
				}
			}
			//Capacity Planned For
			WebElement CapacityPlannedFor_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_5']/div[2]"));
			CapacityPlannedFor_DropDown.sendKeys(Keys.ENTER);
			WebElement CapacityPlannedFor_Input =d1.findElement(By.xpath("/html/body/div[16]/div[1]/input"));
			wait1.until(ExpectedConditions.elementToBeClickable(CapacityPlannedFor_Input));
			CapacityPlannedFor_Input.sendKeys(CapacityPlannedFor_Variable);
			Thread.sleep(1000);
			CapacityPlannedFor_Input.sendKeys(Keys.DOWN,Keys.ENTER);

			// Test Area

			if(!TestArea_Variable.equalsIgnoreCase(""))
			{
				List<WebElement> TestArea_ComboBoxlist = d1.findElements(By.cssSelector(".ValueHolder.ViewBorder"));
				for(WebElement w: TestArea_ComboBoxlist){
					if(w.getAttribute("aria-label").equalsIgnoreCase("Test Area Not Applicable")){
						w.sendKeys(Keys.ENTER);
					}
				}
				WebElement TestAreaInput = d1.findElement(By.xpath("html/body/div[16]/div[1]/input"));
				wait1.until(ExpectedConditions.elementToBeClickable(TestAreaInput));
				TestAreaInput.sendKeys(TestArea_Variable);
				Thread.sleep(1000);
				TestAreaInput.sendKeys(Keys.DOWN,Keys.ENTER);

			}



			//Initial Estimate
			WebElement InitialEstimate = d1.findElement(By.cssSelector(".readonly-text"));
			InitialEstimate.sendKeys(InitialEstimate_Variable);







			// Description
			if(!Description_Variable.equalsIgnoreCase("")){
				d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_editor_RichTextEditorView_1']/div[2]")).sendKeys(Description_Variable);
			}

			// Comment
			if(!Comment_Variable.equalsIgnoreCase("")){
				d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_editor_RichTextEditorView_2']/div[2]")).sendKeys(Comment_Variable);
			}

			// Save Button
			List<WebElement> Save = d1.findElements(By.tagName("button"));
			for(WebElement s : Save){
				if(s.getText().equalsIgnoreCase("Save")){
					s.sendKeys(Keys.ENTER);
					d1.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
				}
			}



			// Get Task ID

			//Thread.sleep(2000);
			d1.getWindowHandle();
			String TaskLink = d1.getCurrentUrl();
			String TaskID = d1.getCurrentUrl().substring(d1.getCurrentUrl().length() - 5);

			d1.navigate().to(RTC_link);

			boolean AlertPresent = false;
			try{
				WebDriverWait wait2 = new WebDriverWait(d1,3);
				wait2.until(ExpectedConditions.alertIsPresent());
				Alert alert = d1.switchTo().alert();
				alert.accept();;
				AlertPresent = true;

			}catch(Exception e){
			}

			if(!AlertPresent){
				return new String[]{TaskID,TaskLink};
			}else{
				return new String[]{"Task not created","Something went wrong"};	
			}
		}

	}


	// --------------------------------------------------------SRRequest Creation method-----------------------------------------------------------------------------

	public  static String[] RTC_SRRequest(WebDriver d1,String RTC_link,String Summary_Variable,String  PID_Variable,String  ServiceRequest_Variable,String  Client_Variable,String  FiledAgainstTeam1_Variable,String CapacityPlannedFor1_Variable ,String SRType_Variable,String Product_Variable,String TargetedRelease_Variable ,String OwnedBy_Variable,String WorkRequestType_Variable,String TestingRequired_Variable,String BA_Variable,String  Dev_Variable,String Priority_Variable,String Description_Variable,String Comment_Variable 
			) throws InterruptedException {

		d1.get(RTC_link);
		d1.navigate().refresh();

		boolean Alert_is_present = false;
		try{
			d1.switchTo().alert();
			Alert_is_present = true;
		}catch(Exception e){

		}

		if(Alert_is_present){
			d1.switchTo().alert().accept();

		}


		d1.navigate().to(RTC_link);

		WebDriverWait wait1 = new WebDriverWait(d1, 30);
		/*try{
			WebDriverWait wait3 = new WebDriverWait(d1, 30);
			wait3.until(ExpectedConditions.titleContains("Project Dashboard" ));
		}catch(Exception e)
		{
			WebDriverWait wait2 = new WebDriverWait(d1, 30);
			wait2.until(ExpectedConditions.titleContains("Change and Configuration Management" ));
		}*/
		d1.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Identifying the search input box
		wait1.until(ExpectedConditions.elementToBeClickable(By.className("SearchInputText")));
		WebElement SearchInputBox = d1.findElement(By.className("SearchInputText"));
		SearchInputBox.clear();
		SearchInputBox.sendKeys(Summary_Variable,Keys.ENTER);

		//d1.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);	
		//wait1.until(ExpectedConditions.titleContains("Query"));

		//checking if  relevant task already exists

		JavascriptExecutor js = (JavascriptExecutor)d1;
		js.executeScript("return document.readyState").toString().equals("complete");

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
			RelevantSRRequest.clear();
			return new String[]{"Service request already exists","No service request created"};

		}else{

			// Reached on main page and navigating to create task screen
			d1.navigate().to(RTC_link);				
			// Reached on main page and navigating to create task screen
			//wait1.until(ExpectedConditions.titleContains("Project Dashboard"));
			wait1.until(ExpectedConditions.elementToBeClickable(By.linkText("Work Items")));
			WebElement Work_Item = d1.findElement(By.linkText("Work Items"));
			Work_Item.sendKeys(Keys.ENTER);;
			wait1.until(ExpectedConditions.elementToBeClickable(By.linkText("SR Request")));
			WebElement Task = d1.findElement(By.linkText("SR Request"));
			Task.sendKeys(Keys.ENTER);


			//Identifying all mandatory fields while creating tasks

			//Summary of task

			wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_editor_RichTextEditorView_0']/div[2]")));
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
			wait1.until(ExpectedConditions.elementToBeClickable(FiledAgainstTeam_DropDown));
			FiledAgainstTeam_DropDown.sendKeys(Keys.ENTER);
			WebElement FiledAgainstTeam_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
			wait1.until(ExpectedConditions.elementToBeClickable(FiledAgainstTeam_Input));
			FiledAgainstTeam_Input.sendKeys(FiledAgainstTeam1_Variable);
			Thread.sleep(1000);
			if(FiledAgainstTeam1_Variable.equalsIgnoreCase("Rxclaim")){
				FiledAgainstTeam_Input.sendKeys(Keys.ENTER);
			}else{
				FiledAgainstTeam_Input.sendKeys(Keys.DOWN,Keys.ENTER);
			}

			//Capacity Planned For

			WebElement CapacityPlannedFor_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_2']/div[2]"));
			wait1.until(ExpectedConditions.elementToBeClickable(CapacityPlannedFor_DropDown));
			CapacityPlannedFor_DropDown.sendKeys(Keys.ENTER);
			WebElement CapacityPlannedFor_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
			wait1.until(ExpectedConditions.elementToBeClickable(CapacityPlannedFor_Input));
			CapacityPlannedFor_Input.sendKeys(CapacityPlannedFor1_Variable);
			Thread.sleep(2000);
			CapacityPlannedFor_Input.sendKeys(Keys.DOWN,Keys.ENTER);


			//SR type : Base/Retrofit
			String SRType = "Base";
			WebElement SRType_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_3']/div[2]"));
			wait1.until(ExpectedConditions.elementToBeClickable(SRType_DropDown));
			if(!SRType.equalsIgnoreCase(SRType_Variable)){
				SRType_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.ENTER);					
			}

			//Product
			WebElement Product_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_4']/div[2]"));
			wait1.until(ExpectedConditions.elementToBeClickable(Product_DropDown));
			Product_DropDown.sendKeys(Keys.ENTER);
			WebElement Product_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
			wait1.until(ExpectedConditions.elementToBeClickable(Product_Input));
			Product_Input.sendKeys(Product_Variable);
			Thread.sleep(2000);
			Product_Input.sendKeys(Keys.DOWN,Keys.ENTER);

			//Target Release
			WebElement TargetRelease_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_6']/div[2]"));
			wait1.until(ExpectedConditions.elementToBeClickable(TargetRelease_DropDown));
			TargetRelease_DropDown.sendKeys(Keys.ENTER);
			WebElement TargetRelease_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
			wait1.until(ExpectedConditions.elementToBeClickable(TargetRelease_Input));
			TargetRelease_Input.sendKeys(TargetedRelease_Variable);
			Thread.sleep(2000);
			TargetRelease_Input.sendKeys(Keys.DOWN,Keys.ENTER);

			//Owned By
			WebElement OwnedBY_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_7']/div[2]"));
			wait1.until(ExpectedConditions.elementToBeClickable(OwnedBY_DropDown));
			OwnedBY_DropDown.sendKeys(Keys.ENTER);
			WebElement OwnedBY_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
			wait1.until(ExpectedConditions.elementToBeClickable(OwnedBY_Input));
			OwnedBY_Input.sendKeys(OwnedBy_Variable);
			Thread.sleep(2000);
			OwnedBY_Input.sendKeys(Keys.DOWN,Keys.ENTER);


			//Work Request Type
			String WorkRequestType = WorkRequestType_Variable;
			List<WebElement> combobox = d1.findElements(By.cssSelector(".ValueHolder.ViewBorder"));
			for(WebElement w: combobox){

				if(w.getAttribute("aria-label").equalsIgnoreCase("Work Request Type Unassigned")){
					if(WorkRequestType.equalsIgnoreCase("Enhancement")){
						w.sendKeys(Keys.ENTER,Keys.UP,Keys.UP,Keys.UP,Keys.ENTER);
					}else if(WorkRequestType.equalsIgnoreCase("Change Request")){
						w.sendKeys(Keys.ENTER,Keys.UP,Keys.UP,Keys.ENTER);
					}else if(WorkRequestType.equalsIgnoreCase("Production Defect")){
						w.sendKeys(Keys.ENTER,Keys.UP,Keys.ENTER);
					}
				}
			}


			/*//Testing Required : Yes/No/COTO
			String TestingRequired = TestingRequired_Variable;	
			WebElement TestingRequired_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_10']/div[2]"));
			wait1.until(ExpectedConditions.elementToBeClickable(TestingRequired_DropDown));
			if(TestingRequired.equalsIgnoreCase("No")){
				TestingRequired_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.ENTER);					
			}else if(TestingRequired.equalsIgnoreCase(TestingRequired_Variable)){
				TestingRequired_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.DOWN,Keys.ENTER);		
			}*/

			//Priority	

			if(!Priority_Variable.equalsIgnoreCase("")){

				List<WebElement> Priority_combobox = d1.findElements(By.cssSelector(".ValueHolder.ViewBorder"));
				for(WebElement w: Priority_combobox){
					if(w.getAttribute("aria-label").equalsIgnoreCase("Priority Not Assessed")){
						if(Priority_Variable.equalsIgnoreCase("Critical")){
							w.sendKeys(Keys.ENTER, Keys.UP,Keys.UP,Keys.UP,Keys.UP,Keys.ENTER);
						}else if(Priority_Variable.equalsIgnoreCase("High")){
							w.sendKeys(Keys.ENTER, Keys.UP,Keys.UP,Keys.UP,Keys.ENTER);
						}else if(Priority_Variable.equalsIgnoreCase("Medium")){
							w.sendKeys(Keys.ENTER, Keys.UP,Keys.UP,Keys.ENTER);
						}else if(Priority_Variable.equalsIgnoreCase("Low")){
							w.sendKeys(Keys.ENTER, Keys.UP,Keys.ENTER);
						}else if(Priority_Variable.equalsIgnoreCase("Emergency")){
							w.sendKeys(Keys.ENTER, Keys.UP,Keys.UP,Keys.UP,Keys.UP,Keys.UP,Keys.ENTER);
						}
					}}

			}

			//BA	
			if(!BA_Variable.equalsIgnoreCase("")){
				WebElement BA_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_7']/div[2]"));
				wait1.until(ExpectedConditions.elementToBeClickable(BA_DropDown));
				BA_DropDown.sendKeys(Keys.ENTER);
				WebElement BA_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
				wait1.until(ExpectedConditions.elementToBeClickable(BA_Input));
				BA_Input.sendKeys(BA_Variable);
				Thread.sleep(1000);
				BA_Input.sendKeys(Keys.DOWN,Keys.ENTER);

			}
			//Developer	

			if(!Dev_Variable.equalsIgnoreCase("")){
				WebElement Developer_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_8']/div[2]"));
				wait1.until(ExpectedConditions.elementToBeClickable(Developer_DropDown));
				Developer_DropDown.sendKeys(Keys.ENTER);
				WebElement Developer_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
				wait1.until(ExpectedConditions.elementToBeClickable(Developer_Input));
				Developer_Input.sendKeys(Dev_Variable);
				Thread.sleep(1000);
				Developer_Input.sendKeys(Keys.DOWN,Keys.ENTER);
			}

			// Description

			if(!Description_Variable.equalsIgnoreCase("")){
				List<WebElement> Description_combobox = d1.findElements(By.className("RichTextEditorWidget"));
				for(WebElement x: Description_combobox){
					if(x.getAttribute("aria-label")!=null){
						if(x.getAttribute("aria-label").equalsIgnoreCase("Description")){
							x.sendKeys(Description_Variable);
							break;	
						}
					}
				}
			}


			// Comment

			if(!Comment_Variable.equalsIgnoreCase("")){
				List<WebElement> Comment_combobox = d1.findElements(By.className("RichTextEditorWidget"));
				for(WebElement y: Comment_combobox){
					if(y.getAttribute("aria-label")!=null){
						if(y.getAttribute("aria-label").equalsIgnoreCase("Comments")){
							y.sendKeys(Comment_Variable);	
							break;
						}
					}
				}
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
			Thread.sleep(2000);	


			d1.navigate().to(RTC_link);

			boolean AlertPresent = false;
			try{
				WebDriverWait wait2 = new WebDriverWait(d1,3);
				wait2.until(ExpectedConditions.alertIsPresent());
				Alert alert = d1.switchTo().alert();
				alert.accept();;
				AlertPresent = true;

			}catch(Exception e){
			}

			if(!AlertPresent){
				return new String[]{TaskID,TaskLink};
			}else{
				return new String[]{"SR Request not created","Something went wrong"};	
			}




		}
	}


	//-----------------------------------------------Link Task to SR request method----------------------------------------------------------------

	public static String LinkTaskMethod(WebDriver d1,String RTC_link,String SRRequestID,String TaskID1,String TaskID2,String TaskID3,String TaskID4,String TaskID5) throws InterruptedException {

		d1.navigate().refresh();

		boolean Alert_is_present = false;
		try{
			d1.switchTo().alert();
			Alert_is_present = true;
		}catch(Exception e){

		}

		if(Alert_is_present){
			d1.switchTo().alert().accept();

		}


		d1.navigate().to(RTC_link);
		//Identifying the search input box
		WebElement SearchInputBox = d1.findElement(By.className("SearchInputText"));
		SearchInputBox.clear();
		SearchInputBox.sendKeys(SRRequestID,Keys.ENTER);
		//Thread.sleep(10000);

		//Check whether input ID belongs to a SR Request only
		boolean ValidSRRequest = true;
		try{
			WebDriverWait wait123 = new WebDriverWait(d1, 8);   // providing wait to load the complete page
			wait123.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Conditions")));

		}catch(Exception e){
			ValidSRRequest = false;
		}


		if(ValidSRRequest){


			//Identifying Links Tab
			WebDriverWait wait = new WebDriverWait(d1, 8);   // providing wait to load the complete page
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Links")));
			WebElement Links = d1.findElement(By.linkText("Links"));
			Links.sendKeys(Keys.ENTER);

			//Identifying Add Related Tab
			WebElement AddRelated = d1.findElement(By.xpath(".//*[@id='com_ibm_team_rtc_foundation_web_ui_views_ActionDropdownView_0']/div[2]/span[1]"));
			//WebElement AddRelated = d1.findElement(By.linkText("Add Related"));

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

			WebElement SaveButton = d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_ui_internal_view_editor_WorkItemEditorHeader_0']/div[1]/span/span[3]/span/button[2]"));
			if(SaveButton.isEnabled()){
				SaveButton.sendKeys(Keys.ENTER);
			}
			//
			//Thread.sleep(10000);
			return "Linked Successfully";
		}else{
			return "This is invalid SR Request ID";
		}	
	}
}






