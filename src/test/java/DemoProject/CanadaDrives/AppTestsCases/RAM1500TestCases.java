package DemoProject.CanadaDrives.AppTestsCases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import DemoProject.CanadaDrives.AppBase.BaseClass;
import DemoProject.CanadaDrives.AppPages.PaymentCalculationPage;
import DemoProject.CanadaDrives.AppPages.VehicleInventoryPage;

public class RAM1500TestCases  extends BaseClass {

	VehicleInventoryPage vehicleInvPageObj;
	PaymentCalculationPage payCalculationPageObj;
	WebDriver driver;
	protected String [] csvdata;
	ExtentReports extent;
	
	@BeforeSuite(alwaysRun=true)
	public void setUp() {
		
		initializaton();
		vehicleInvPageObj = new VehicleInventoryPage();
		 csvdata=readCSVdata();
		 extent=  CreateExtendReport();
	}
	
	@Test(priority =0)
	public void TC01_NavigateToURLandSelectOntarioProvince() {
		ExtentTest logger = extent.createTest("Verify Naviagted to CanadaDrive Page and selected the Ontario Province");
		String provinceValue  = properties.getProperty("Province");
		vehicleInvPageObj.province(provinceValue, logger);
		logger.log(Status.PASS, "Provice Changed to "+provinceValue);
	}
	
	@Test(priority =1)
	public void TC02_SortByPriceLowToHigh() throws InterruptedException {
		ExtentTest logger = extent.createTest("Verfiy Sort By Price Low to High");
		Thread.sleep(3000);
		String SortingBy=properties.getProperty("SortingBy");
		vehicleInvPageObj.sort(SortingBy, logger);
		logger.log(Status.PASS, "Sorted By  "+SortingBy);
	}
	
	@Test(priority =2)
	public void TC07_ApplyFilterRAM1500() throws InterruptedException {
		ExtentTest logger = extent.createTest("Verify Apply Filter RAM 1500 Vehicles");
		Thread.sleep(3000);
		String FilterValue=csvdata[1];
		String SecondFilterValue=csvdata[2];
		String ThirdFilterValue=csvdata[3];
		vehicleInvPageObj.Filter(FilterValue, SecondFilterValue,ThirdFilterValue, logger);	
		logger.log(Status.PASS, "Filter By  "+FilterValue+"/"+SecondFilterValue+"/"+ThirdFilterValue);
	}
	
	@Test(priority =3)
	public void TC03_Favorite3Ram1500Vehicles() throws InterruptedException {
		ExtentTest logger = extent.createTest("Verfiy select first Favorite  3 Ram 1500 Vehicles");
		vehicleInvPageObj.Select_fav(3, logger);
		Thread.sleep(3000); 
		vehicleInvPageObj.Verify_favCount(3, logger);
		logger.log(Status.PASS, "Selected Fav first 3 RAM 1500 Vehicles");
	}
	
	@Test(priority =4)
	public void TC04_PickAnAvailableRam1500VehicleAndClickOnPurchase() throws InterruptedException{
		ExtentTest logger = extent.createTest("Pick An Available Ram 1500 Vehicle And Click On Purchase");
		payCalculationPageObj=vehicleInvPageObj.selectVehicle(logger);
		Thread.sleep(2000);
		logger.log(Status.PASS, "Pick An Available Ram 1500 Vehicle And Click On Purchase");
	}
	
	@Test(priority =6)
	public void TC05_EnterTorontoAddressInCalculateDelivery() throws InterruptedException {
		ExtentTest logger = extent.createTest("Enter Toronto Address In Calculate Delivery");
		String Address=properties.getProperty("Address");
		payCalculationPageObj.PaymentCalculateDelivery(Address, logger);	
		logger.log(Status.PASS, "Enter Toronto Address In Calculate Delivery");
	}
	
	@Test(priority =5)
	public void TC06_Select48MonthsWarranty() throws InterruptedException {
		ExtentTest logger = extent.createTest("Select 48 Months Warranty");
		//String WarrantyPeriod=properties.getProperty("WarrantyPeriod");
	String WarrantyPeriod=csvdata[4];
		payCalculationPageObj.EditWarranty(WarrantyPeriod, logger);
		logger.log(Status.PASS, "Select 48 Months Warranty");
		
	}
	
 @AfterSuite(alwaysRun = true) 
 public void tearDown() { 
	 extent.flush();
	 tearDownMain(); 
	 
 }

	 
	 
	
	
	
	
}
