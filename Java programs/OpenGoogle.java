import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class OpenGoogle {

	public static void main(String[] args) throws InterruptedException {
		//Setting up IE properties and handling Protected mode/Zoom 100% exceptions
		
				System.setProperty("webdriver.ie.driver", "C:\\Users\\akansal2\\Downloads\\IEDriverServer.exe");
				
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true );
				WebDriver d1 = new InternetExplorerDriver(ieCapabilities);
				
		//Opening RTC link in IE Browser
				d1.navigate().to("www.google.com");
				Thread.sleep(5000);
				d1.manage().window().maximize();
				//d1.findElement(By.name("q")).sendKeys("Optum");
				d1.findElement(By.linkText("Gmail")).click();
				Thread.sleep(5000);
				
				
				d1.close();

		
	
	
	
	}

}
