package DemoProject.CanadaDrives.AppBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.opencsv.CSVReader;

public class BaseClass 
{
	protected static WebDriver driver;
	protected static Properties properties;
	protected static ChromeOptions chromeOptions;
	protected static JavascriptExecutor jsexcutor;
	protected static WebDriverWait wait ;
	protected static CSVReader reader;
	protected static ExtentHtmlReporter reporter;
	protected static ExtentReports extent;
	protected static MediaEntityModelProvider abuildscreenshot;


	
	public BaseClass() {
		try 
		{
			properties = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/DemoProject/CanadaDrives/AppConstants/config.properties");
			properties.load(ip);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			System.out.println("file not found");
		} 
		catch (IOException e) 
		{
			System.out.println("io exception");

		}

	}
	
	private static WebDriver getDriver(String browserName)
	{
		if (browserName.equalsIgnoreCase("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lenovo\\eclipse-workspaces\\DemoSeleniumFramework\\CanadaDrives\\Drivers\\chromedriver.exe");
			chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--start-maximized");
			return new ChromeDriver(chromeOptions);
		} 
		else if (browserName.equalsIgnoreCase("FF")) 
		{
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\Lenovo\\eclipse-workspaces\\DemoSeleniumFramework\\CanadaDrives\\Drivers\\geckodriver.exe");
			return new FirefoxDriver();
		} 
		else if (browserName.equalsIgnoreCase("IE")) 
		{
			System.setProperty("webdriver.ie.driver", "C:\\Users\\Lenovo\\eclipse-workspaces\\DemoSeleniumFramework\\CanadaDrives\\Drivers\\IEDriverServer.exe");
			return new InternetExplorerDriver();
		}
		return null;
	}
	
	protected static void initializaton() 
	{
		String browserName = properties.getProperty("browser");
		driver = getDriver(browserName);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
		driver.get(properties.getProperty("url"));
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	
	protected static String []  readCSVdata(){
		String [] cell = null;
		try {
			reader = new CSVReader(new FileReader("C:\\Users\\Lenovo\\eclipse-workspaces\\DemoSeleniumFramework\\CanadaDrives\\src\\main\\java\\DemoProject\\CanadaDrives\\AppConstants\\TestData.csv"));
		 cell=reader.readNext();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return cell;
	}
	
	public void tearDownMain() 
	{
		driver.manage().deleteAllCookies();
		driver.close();
	}
	
	public static ExtentReports CreateExtendReport() {
		String reportpath = System.getProperty("user.dir")+"./target/ExecutionAutomationTestReport.html";
		reporter = new ExtentHtmlReporter(reportpath);
		reporter.config().setDocumentTitle("Canada Drives End to End Automation Testing Report");
		reporter.config().setReportName("Executed by Ashish Bhadauria");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Enviroment", "Automation Testing");
		extent.setSystemInfo("Host Address", "Canada Drive");
		extent.setSystemInfo("Browser", properties.getProperty("browser"));
		extent.setSystemInfo("URL", properties.getProperty("url"));
		return extent;
	}

	public static String screenshot(String screenshotName) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = "./target/screenshots/"+screenshotName+".png";
		File impPath = new File(path);
		try {
			FileUtils.copyFile(src, impPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	public static  MediaEntityModelProvider getScreenshot(String Screenshotname) {
		
	     try {
		 		String imppath ="."+screenshot(Screenshotname);
				abuildscreenshot = 	MediaEntityBuilder.createScreenCaptureFromPath(imppath).build();
			} catch (IOException e) {	
				e.printStackTrace();
			}
			 return abuildscreenshot;
	}
}
