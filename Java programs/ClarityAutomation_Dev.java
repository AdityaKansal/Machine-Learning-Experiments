
	

		import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

		import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

		import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

		public class ClarityAutomation_Dev {

			/**
			 * @param args
			 * @throws IOException 
			 * @throws BiffException 
			 */
			public static void main(String[] args) throws BiffException, IOException {
				// TODO Auto-generated method stub
				String FilePath = "C:\\A_stuff\\Learning\\SCR\\SCR.xls";
				FileInputStream fs = new FileInputStream(FilePath);
				Workbook wb = Workbook.getWorkbook(fs);

				// TO get the access to the sheet
				Sheet sh = wb.getSheet("Dev");

				// To get the number of rows present in sheet
				int totalNoOfRows = sh.getRows();
				System.out.println("number of rows present in sheet :: " + totalNoOfRows);

				// To get the number of columns present in sheet
				int totalNoOfCols = sh.getColumns();
				System.out.println("number of columns present in sheet :: " + totalNoOfCols);

				// Opening Browser and entering URL
				//WebDriver driver = null;
				System.setProperty("webdriver.ie.driver", "C:\\Users\\akansal2\\Downloads\\IEDriverServer.exe" );
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true );
				
				WebDriver driver = new InternetExplorerDriver(ieCapabilities);
				driver.manage().window().maximize();
				
				for(int row=1;row<1000;row++)
				{
					if(!sh.getCell(0, row).getContents().equalsIgnoreCase("N")){
						driver.get(sh.getCell(1, row).getContents());
						driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
						driver.findElement(By.xpath("//input[@id='ppm_login_username']")).sendKeys(new String[]{"Pratyusha.Garapati"});
						driver.findElement(By.xpath("//input[@id='ppm_login_password']")).sendKeys(new String[]{"Nani*1984"});
						driver.findElement(By.xpath("//input[@id='ppm_login_button']")).sendKeys(Keys.ENTER);
						driver.findElement(By.xpath("//input[contains(@name, 'sr_number')]")).sendKeys(new String[]{sh.getCell(2, row).getContents()});
						driver.findElement(By.xpath("//button[@name='filter']")).sendKeys(Keys.ENTER);
						((JavascriptExecutor) driver).executeScript("scroll(0,450);", args);
						driver.findElement(By.xpath("//a[contains(text(), "+"'"+sh.getCell(3, row).getContents()+"'"+")]")).sendKeys(Keys.ENTER);
						driver.findElement(By.xpath("//a[@title='Tasks']")).sendKeys(Keys.ENTER);
						((JavascriptExecutor) driver).executeScript("scroll(0,450);", args);
						if(sh.getCell(4, row).getContents().equals("Y"))
						{
							driver.findElement(By.xpath("//a[@title='Build']")).sendKeys(Keys.ENTER);
//							driver.findElement(By.xpath("//form[@name='page_projmgr.odfTaskProperties']/div/table/tbody/tr/td/table[@class='ppm_attributes_table']/tbody/tr/td/table/tbody/tr/td/select[@name='prstatus']")).sendKeys(Keys.ENTER);
//							driver.findElement(By.xpath("//form[@name='page_projmgr.odfTaskProperties']/div/table/tbody/tr/td/table[@class='ppm_attributes_table']/tbody/tr/td/table/tbody/tr/td/select[@name='prstatus']/option[text()="+"'"+sh.getCell(5, row).getContents()+"'"+"]")).sendKeys(Keys.ENTER);
							driver.findElement(By.xpath("//button[contains(text(), 'Save And Return')]")).sendKeys(Keys.ENTER);
						}
						if(sh.getCell(6, row).getContents().equals("Y"))
						{
							driver.findElement(By.xpath("//a[@title='Development']")).sendKeys(Keys.ENTER);
//							driver.findElement(By.xpath("//form[@name='page_projmgr.odfTaskProperties']/div/table/tbody/tr/td/table[@class='ppm_attributes_table']/tbody/tr/td/table/tbody/tr/td/select[@name='prstatus']")).sendKeys(Keys.ENTER);
//							driver.findElement(By.xpath("//form[@name='page_projmgr.odfTaskProperties']/div/table/tbody/tr/td/table[@class='ppm_attributes_table']/tbody/tr/td/table/tbody/tr/td/select[@name='prstatus']/option[text()="+"'"+sh.getCell(7, row).getContents()+"'"+"]")).sendKeys(Keys.ENTER);
							driver.findElement(By.xpath("//input[@name='prstart']")).clear();
							driver.findElement(By.xpath("//input[@name='prstart']")).sendKeys(new String[]{sh.getCell(8, row).getContents()});
							driver.findElement(By.xpath("//input[@name='prfinish']")).clear();
							driver.findElement(By.xpath("//input[@name='prfinish']")).sendKeys(new String[]{sh.getCell(9, row).getContents()});
							driver.findElement(By.xpath("//button[contains(text(), 'Save And Return')]")).sendKeys(Keys.ENTER);
						}
						if(sh.getCell(10, row).getContents().equals("Y"))
						{
							driver.findElement(By.xpath("//a[@title='Build Signoff']")).sendKeys(Keys.ENTER);
//							driver.findElement(By.xpath("//form[@name='page_projmgr.odfTaskProperties']/div/table/tbody/tr/td/table[@class='ppm_attributes_table']/tbody/tr/td/table/tbody/tr/td/select[@name='prstatus']")).sendKeys(Keys.ENTER);
//							driver.findElement(By.xpath("//form[@name='page_projmgr.odfTaskProperties']/div/table/tbody/tr/td/table[@class='ppm_attributes_table']/tbody/tr/td/table/tbody/tr/td/select[@name='prstatus']/option[text()="+"'"+sh.getCell(11, row).getContents()+"'"+"]")).sendKeys(Keys.ENTER);
							driver.findElement(By.xpath("//input[@name='prfinish']")).clear();
							driver.findElement(By.xpath("//input[@name='prfinish']")).sendKeys(new String[]{sh.getCell(12, row).getContents()});
							driver.findElement(By.xpath("//button[contains(text(), 'Save And Return')]")).sendKeys(Keys.ENTER);
						}
						driver.findElement(By.xpath("//a[@id='ppm_header_logout']")).sendKeys(Keys.ENTER);
						}
					}
				}


			}



	


