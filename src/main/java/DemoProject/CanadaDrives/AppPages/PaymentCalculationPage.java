package DemoProject.CanadaDrives.AppPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import DemoProject.CanadaDrives.AppBase.BaseClass;

@SuppressWarnings("deprecation")
public class PaymentCalculationPage extends BaseClass{
	
	public PaymentCalculationPage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[contains(@class,'vehicle-price-cards')]//div[contains(text(),'Calculate Delivery')]")
	private WebElement CalculateDeliveryButton;

	@FindBy(id = "street_address")
	private WebElement DeliveryAddressTextBox;

	@FindBy(xpath = "//*[contains(@class,'vehicle-price-cards')]//div[contains(text(),'Select Warranty')]")
	private WebElement SelectWarrantyButton;

	@FindBy(xpath="//div[contains(@class,'v-dialog--active')]//span[contains(text(),'Save and Confirm')]")
	private WebElement DeliverySubmitButton;

	@FindBy(xpath = "//button[contains(@class,'sticky__save-and-confirm-btn')]//span[contains(text(),'Save and Confirm')]")
	private WebElement WarrantySubmitButton;
	
	@FindBy(xpath = "div[contains(@class,'warranty-options__item')]")
	private List<WebElement> WarrantyOptions;
	

	public void PaymentCalculateDelivery(String Address, ExtentTest test) throws InterruptedException {
		CalculateDeliveryButton.click();
		test.log(Status.INFO, "Clicked on the Payment Calculate Delivery Button");
		Thread.sleep(2000);
		DeliveryAddressTextBox.sendKeys(Address);
		test.log(Status.INFO, "Enter the Address Successfully");
		Thread.sleep(3000);
		DeliveryAddressTextBox.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		DeliveryAddressTextBox.sendKeys(Keys.TAB);
		DeliverySubmitButton.click();
		test.log(Status.INFO, "Clicked on Submit button");
		
	}

	public void EditWarranty(String Warranty, ExtentTest test) throws InterruptedException {
		String Warrantyxpath = "//div[contains(text(),'"+Warranty+"')]";
		SelectWarrantyButton.click();
		test.log(Status.INFO, "Enter the select warranty Option");
		WebElement warrantEl = driver.findElement(By.xpath(Warrantyxpath));
		Thread.sleep(3000);
		warrantEl.click();
		test.log(Status.INFO, "Clicked on "+Warranty+" warranty");
		WarrantySubmitButton.click();
		test.log(Status.INFO, "selected the warranty successfully");
	}
}
