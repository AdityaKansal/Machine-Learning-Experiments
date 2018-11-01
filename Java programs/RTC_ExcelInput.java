import java.io.File;
import java.io.FileInputStream;
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


public class RTC_ExcelInput {



	//------------------------------------Main Method-----------------------------------------------------------------------------------------------------------	
	public static void main(String[] args) throws IOException, InterruptedException,NullPointerException, EncryptedDocumentException, InvalidFormatException {
		
		//User Inputs for Excel sheets
		String ExcelPath = "C://A_stuff//Learning//RTC_Task Creation//RTC_Task creation_Input Sheet.xlsx";
		String RTCTaskInputSheetName = "RTC_Task Input";
		String RTC_SRRequest_InputSheetName = "RTC_SR_Request";	
		String RTC_LinkTask_InputSheetName = "Link Tasks";

		// User inputs for rational
		String RTC_link = "https://ccm.catamaran.oncloudone.net/ccm/web/projects/Optum-RTC#action=com.ibm.team.dashboard.viewDashboard";
		String User_Name = "adkansal";
		String Password = "Welcome@12345";



		//Setting up IE properties and handling Protected mode/Zoom 100% exceptions

		System.setProperty("webdriver.ie.driver", "C:\\Users\\akansal2\\Downloads\\IEDriverServer.exe");
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true );
		WebDriver d1 = new InternetExplorerDriver(ieCapabilities);

		//Opening RTC link in IE Browser
		d1.navigate().to(RTC_link);
		d1.manage().window().maximize();

		//Introducing implicit wait to provide enough time to load the page and its objects
		d1.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);


		//Storing all Input values and object address		
		WebElement Wb_UserName = d1.findElement(By.id("jazz_app_internal_LoginWidget_0_userId"));
		WebElement Wb_Password = d1.findElement(By.id("jazz_app_internal_LoginWidget_0_password"));
		WebElement Wb_LogIn = d1.findElement(By.tagName("button"));




		//Actions on WebElements and logging into the account		
		Wb_UserName.sendKeys(User_Name);
		Wb_Password.clear();
		Wb_Password.sendKeys(Password);
		Wb_LogIn.sendKeys(Keys.ENTER);

		
		//Reading Inputs from RTC Task Creation Excel sheet
		File src = new File(ExcelPath);
		FileInputStream Fin = new FileInputStream(src);
		Workbook InputWorkbook = WorkbookFactory.create(Fin);
		//XSSFWorkbook InputWorkbook = new XSSFWorkbook(Fin);
		FileOutputStream Fout = new FileOutputStream(src);
		//XSSFWorkbook outputWB = new XSSFWorkbook();
		Sheet InputSheet = InputWorkbook.getSheet(RTCTaskInputSheetName);
		Sheet InputSheet1 = InputWorkbook.getSheet(RTC_SRRequest_InputSheetName);
		Sheet InputSheet2 = InputWorkbook.getSheet(RTC_LinkTask_InputSheetName);
		//FileOutputStream Fout = new FileOutputStream(src);

		//Array to store one set of values fetched from one row at a time				
		String a[]= new String[11];

		//Two loops to traverse thru the excel sheets	

		for(int j=2;j<150;j++){	

			if(InputSheet.getRow(j).getCell(1)!=null && InputSheet.getRow(j).getCell(1).getStringCellValue().equalsIgnoreCase("Y")){   // checking null

				for(int i=2;i< 13;i++){
					//System.out.println(InputSheet.getRow(j).getCell(i).getStringCellValue());
					if(InputSheet.getRow(j).getCell(i)!=null){
						if(!InputSheet.getRow(j).getCell(i).getCellTypeEnum().equals("STRING")){
							InputSheet.getRow(j).getCell(i).setCellType(CellType.STRING);
						}
						a[i-2]= InputSheet.getRow(j).getCell(i).getStringCellValue();
						
					}
					else{
						a[i-2] = "";
					}

				}

				// Assigning values to variables to pass them as arguments while calling the RTC task method	
				String Summary_Variable = a[0];
				String SRNumber_Variable= a[1];
				String FiledAgainstTeam_Variable = a[2];
				String Activity_Variable = a[3];
				String CapacityPlannedFor_Variable = a[4];
				String InitialEstimate_Variable =a[5];
				String AssignedTo_Variable= a[6];
				String Priority_Variable = a[7];
				String TestArea_Variable = a[8];
				String Description_Variable = a[9];
				String Comment_Variable = a[10];

				// Calling RTC task method using its class's object 
				try{
					InputSheet.getRow(j).createCell(13);
					InputSheet.getRow(j).getCell(13).setCellType(CellType.STRING);
					InputSheet.getRow(j).createCell(14);
					InputSheet.getRow(j).getCell(14).setCellType(CellType.STRING);

					String taskID1[] = RTC(d1,RTC_link,Summary_Variable, SRNumber_Variable, FiledAgainstTeam_Variable,Activity_Variable, AssignedTo_Variable,CapacityPlannedFor_Variable,InitialEstimate_Variable,Priority_Variable,TestArea_Variable,Description_Variable,Comment_Variable);

					/*InputSheet.getRow(j).getCell(13).setCellValue(taskID1[0]);
					InputSheet.getRow(j).getCell(14).setCellValue(taskID1[1]);*/
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}




		//  SR request creation							

		// Creating array to store inputs from one row at a time				
		String b[]= new String[17];

		//Two loops to traverse thru all the values
		for(int k=2;k<150;k++){	
			InputSheet1.getRow(k).getCell(1).setCellType(CellType.STRING);

			if(InputSheet1.getRow(k).getCell(1)!=null && InputSheet1.getRow(k).getCell(1).getStringCellValue().equalsIgnoreCase("Y") ){

				for(int l=2;l< 19;l++){

					if(InputSheet1.getRow(k).getCell(l)!=null){
						if(!InputSheet1.getRow(k).getCell(l).getCellTypeEnum().equals("STRING")){
							InputSheet1.getRow(k).getCell(l).setCellType(CellType.STRING);
						}
						b[l-2]= InputSheet1.getRow(k).getCell(l).getStringCellValue();
					}else{
						b[l-2]= "";
					}
				} 

				// Assinging Value to Variables to pass them as arguements while calling method			
				String Summary_Variable1 = b[0];
				String PID_Variable= b[1];
				String ServiceRequest_Variable = b[2];
				String Client_Variable = b[3];
				String FiledAgainstTeam1_Variable= b[4];
				String CapacityPlannedFor1_Variable = b[5];
				String SRType_Variable =b[6];
				String Product_Variable = b[7];
				String TargetedRelease_Variable = b[8];
				String OwnedBy_Variable =b[9];
				String WorkRequestType_Variable= b[10];
				String TestingRequired_Variable = b[11];
				String BA_Variable = b[12];
				String Developer_Variable = b[13];
				String Priority_Variable =b[14];
				String Description_Variable= b[15];
				String Comment_Variable = b[16];


				// Calling mehod using object of SR request class 	
				try{
					InputSheet1.getRow(k).createCell(19);
					InputSheet1.getRow(k).getCell(19).setCellType(CellType.STRING);
					InputSheet1.getRow(k).createCell(20);
					InputSheet1.getRow(k).getCell(20).setCellType(CellType.STRING);

					String taskID2[] = RTC_SRRequest(d1, RTC_link,Summary_Variable1, PID_Variable,ServiceRequest_Variable,  Client_Variable,FiledAgainstTeam1_Variable,CapacityPlannedFor1_Variable ,SRType_Variable,Product_Variable,TargetedRelease_Variable ,OwnedBy_Variable,WorkRequestType_Variable,TestingRequired_Variable, BA_Variable,Developer_Variable, Priority_Variable, Description_Variable, Comment_Variable );
					/*InputSheet1.getRow(k).getCell(19).setCellValue(taskID2[0]);
					InputSheet1.getRow(k).getCell(20).setCellValue(taskID2[1]);
*/
				}
				catch(Exception e){
					e.printStackTrace();
				}

			}
		}

		//Reading Inputs from Link Task sheet			
		//Array to store one set of values fetched from one row at a time				
		String c[]= new String[6];

		//Two loops to traverse thru the excel sheets	
		for(int m=1;m<150;m++){	
			if(InputSheet.getRow(m).getCell(1)!=null && InputSheet2.getRow(m).getCell(1).getStringCellValue().equalsIgnoreCase("Y")){


				for(int n=2;n< 8;n++){
					if(InputSheet.getRow(m).getCell(n)!=null){
						if(!InputSheet2.getRow(m).getCell(n).getCellTypeEnum().equals("STRING")){
							InputSheet2.getRow(m).getCell(n).setCellType(CellType.STRING);
						}
						c[n-2]= InputSheet2.getRow(m).getCell(n).getStringCellValue();

					} 
				}


				// Assigning values to variables to pass them as arguments while calling the RTC task method	
				String SRRequestID = c[0];
				String TaskID1= c[1];
				String TaskID2 = c[2];
				String TaskID3 = c[3];
				String TaskID4= c[4];
				String TaskID5 = c[5];


				// Calling RTC task method using its class's object 

				try{
					String Comment = LinkTaskMethod(d1,RTC_link,SRRequestID,TaskID1,TaskID2,TaskID3,TaskID4,TaskID5);
					/*InputSheet2.getRow(m).createCell(8);
					InputSheet2.getRow(m).getCell(8).setCellType(CellType.STRING);
					InputSheet2.getRow(m).getCell(8).setCellValue(Comment);	*/
				}catch(Exception e){
					e.printStackTrace();
				}

			}
		}


		d1.quit();

		InputWorkbook.write(Fout);		
		InputWorkbook.close();// connection close
		//outputWB.close();
		Fin.close();
		Fout.close();

	}




	// ----------------------------------------------Task Creation method----------------------------------------------------------------------------------


	public static String[] RTC(WebDriver d1,String RTC_link,String Summary_Variable, String SRNumber_Variable,String  FiledAgainstTeam_Variable,String Activity_Variable,String  AssignedTo_Variable,String CapacityPlannedFor_Variable,String InitialEstimate_Variable,String Priority_Variable,String TestArea_Variable,String Description_Variable,String Comment_Variable) throws InterruptedException, IOException {


		d1.navigate().to(RTC_link);
		
		WebDriverWait wait1 = new WebDriverWait(d1, 30);
		d1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


		//Identifying the search input box


		wait1.until(ExpectedConditions.elementToBeClickable(By.className("SearchInputText")));
		WebElement SearchInputBox = d1.findElement(By.className("SearchInputText"));
		SearchInputBox.clear();
		SearchInputBox.sendKeys(Summary_Variable,Keys.ENTER);


		d1.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);


		//wait1.until(ExpectedConditions.titleContains("Query"));


		//checking if  relevant task already exists
		//old code for exist variable
		/*System.out.println("before loop");
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
				System.out.println("after loop");*/

		// new code for exist variable
		/*WebDriverWait wait4 = new WebDriverWait(d1, 2);		
				boolean Exist = false;
				System.out.println("before try");
				try{
				wait4.until(ExpectedConditions.elementToBeClickable(By.className("itemsPerPageText")));
				Exist = true;
				}catch(Exception e){

				}	


				System.out.println("after try");*/


	/*	boolean Exist = false;
		try{
			d1.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			System.out.println("1");
			d1.findElements(By.className("itemsPerPageText"));
			System.out.println("2");
			Exist= true;
		}catch(Exception e){
			System.out.println("3");
			Exist = false;
		}finally{
			d1.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
	
*/
		
		
		boolean Exist = false;
		List<WebElement> RelevantSRRequest = d1.findElements(By.partialLinkText(Summary_Variable));
		if(!RelevantSRRequest.isEmpty()){
			System.out.println("list is not empty");
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
			System.out.println("1");
			d1.navigate().to(RTC_link);
			System.out.println("2");
			
			wait1.until(ExpectedConditions.elementToBeClickable(By.linkText("Work Items")));
			WebElement Work_Item = d1.findElement(By.linkText("Work Items"));
			Work_Item.sendKeys(Keys.ENTER);
			wait1.until(ExpectedConditions.elementToBeClickable(By.linkText("Task")));
			WebElement Task = d1.findElement(By.linkText("Task"));
			
			Task.sendKeys(Keys.ENTER);
			System.out.println("3");
			//Identifying all mandatory fields while creating tasks

			//Summary of task
			d1.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			
			/*//wait1.until(ExpectedConditions.elementToBeClickable(By.className("SearchInputText")));
			d1.findElement(By.className("SearchInputText")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);;
			d1.switchTo().activeElement().sendKeys(Summary_Variable);*/
			
			
			/*wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".RichTextEditorWidget.ViewBorder.com-ibm-team-workitem-shared-RichText.cke_editable.cke_editable_inline.cke_contents_ltr")));
			System.out.println("4");
			WebElement Summary = d1.findElement(By.cssSelector(".RichTextEditorWidget.ViewBorder.com-ibm-team-workitem-shared-RichText.cke_editable.cke_editable_inline.cke_contents_ltr"));
			System.out.println("5");
			Summary.sendKeys(Summary_Variable);
			System.out.println("6");*/
		
			
			d1.findElement(By.xpath(".//*[@id='SummaryLabelId']")).sendKeys(Keys.TAB);
d1.switchTo().activeElement().sendKeys(Summary_Variable);
			
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
			FiledAgainstTeam_Input.sendKeys(Keys.DOWN,Keys.ENTER);


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
				System.out.println("Element is blank");
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

			/*// Save Button
				List<WebElement> Save = d1.findElements(By.tagName("button"));
				for(WebElement s : Save){
					if(s.getText().equalsIgnoreCase("Save")){
						s.sendKeys(Keys.ENTER);
						d1.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
					}
				}*/



			// Get Task ID

			//Thread.sleep(2000);
			d1.getWindowHandle();
			String TaskLink = d1.getCurrentUrl();
			String TaskID = d1.getCurrentUrl().substring(d1.getCurrentUrl().length() - 5);
			//Thread.sleep(2000);
			//RelevantTasks.clear(); -- uncomment it for old code
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

		d1.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);	
		//wait1.until(ExpectedConditions.titleContains("Query"));

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
			FiledAgainstTeam_Input.sendKeys(Keys.DOWN,Keys.ENTER);


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
			WebElement TargetRelease_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_5']/div[2]"));
			wait1.until(ExpectedConditions.elementToBeClickable(TargetRelease_DropDown));
			TargetRelease_DropDown.sendKeys(Keys.ENTER);
			WebElement TargetRelease_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
			wait1.until(ExpectedConditions.elementToBeClickable(TargetRelease_Input));
			TargetRelease_Input.sendKeys(TargetedRelease_Variable);
			Thread.sleep(2000);
			TargetRelease_Input.sendKeys(Keys.DOWN,Keys.ENTER);

			//Owned By
			WebElement OwnedBY_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_6']/div[2]"));
			wait1.until(ExpectedConditions.elementToBeClickable(OwnedBY_DropDown));
			OwnedBY_DropDown.sendKeys(Keys.ENTER);
			WebElement OwnedBY_Input =d1.findElement(By.xpath("html/body/div[18]/div[1]/input"));
			wait1.until(ExpectedConditions.elementToBeClickable(OwnedBY_Input));
			OwnedBY_Input.sendKeys(OwnedBy_Variable);
			Thread.sleep(1000);
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


			//Testing Required : Yes/No/COTO
			String TestingRequired = TestingRequired_Variable;	
			WebElement TestingRequired_DropDown=d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_mvvm_view_queryable_combo_QueryableComboView_10']/div[2]"));
			wait1.until(ExpectedConditions.elementToBeClickable(TestingRequired_DropDown));
			if(TestingRequired.equalsIgnoreCase("No")){
				TestingRequired_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.ENTER);					
			}else if(TestingRequired.equalsIgnoreCase(TestingRequired_Variable)){
				TestingRequired_DropDown.sendKeys(Keys.ENTER,Keys.DOWN,Keys.DOWN,Keys.ENTER);		
			}

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



			/*	// Save Button
						List<WebElement> Save1 = d1.findElements(By.tagName("button"));
				for(WebElement s : Save1){
					if(s.getText().equalsIgnoreCase("Save")){
						s.sendKeys(Keys.ENTER);
					}
				}*/


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



		d1.navigate().to(RTC_link);
		//Identifying the search input box
		WebElement SearchInputBox = d1.findElement(By.className("SearchInputText"));
		SearchInputBox.clear();
		SearchInputBox.sendKeys(SRRequestID,Keys.ENTER);
		Thread.sleep(10000);

		//Check whether input ID belongs to a SR Request only
		boolean ValidSRRequest = true;
		try{
			WebDriverWait wait123 = new WebDriverWait(d1, 40);   // providing wait to load the complete page
			wait123.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Conditions")));

		}catch(Exception e){
			ValidSRRequest = false;
		}
		if(ValidSRRequest){


			//Identifying Links Tab
			WebDriverWait wait = new WebDriverWait(d1, 120);   // providing wait to load the complete page
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

			/*// Clicking save

		WebElement SaveButton = d1.findElement(By.xpath(".//*[@id='com_ibm_team_workitem_web_ui_internal_view_editor_WorkItemEditorHeader_0']/div[1]/span/span[3]/span/button[2]"));
			if(SaveButton.isEnabled()){
				SaveButton.sendKeys(Keys.ENTER);
			}*/
			//
			Thread.sleep(10000);
			return "Linked Successfully";
		}else{
			return "This is invalid SR Request ID";
		}	
	}
}






