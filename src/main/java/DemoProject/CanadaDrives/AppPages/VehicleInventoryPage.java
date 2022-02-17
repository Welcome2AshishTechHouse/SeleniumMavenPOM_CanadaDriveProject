package DemoProject.CanadaDrives.AppPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import DemoProject.CanadaDrives.AppBase.BaseClass;

public class VehicleInventoryPage extends BaseClass {
	
	public VehicleInventoryPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//span[text()='BC']")
	private WebElement ProvinceButton;
	
	@FindBy(xpath = "//ul[@class='province-dropdown__list']//child::li")
	private List<WebElement> Province_elements;
	
	@FindBy(xpath = "//div[contains(@id,'list-item')]//div[@class='v-list-item__title']")
	private List<WebElement> SortEleList;
	
	@FindBy(xpath = "//span[text()='Make & Model']//parent::button//following-sibling::div[contains(@class,'pl-0')]//child::span")
	private List<WebElement> SubEl_MakeModel;

	@FindBy(xpath = "//div[@class='v-input__control']//div[text()='Featured']")
	private WebElement SortEl;
	
	@FindBy(xpath = "//*[contains(@class,'fa-heart') and @style]")
	private List<WebElement> favOptions;
	
	@FindBy(xpath="//span[@class='v-badge__badge white']")
	private WebElement AddedFav;
	
	@FindBy(xpath="//div[contains(@class,'v-image v-responsive')]//parent::div//div[contains(@class,'v-card__text')]")
	private List<WebElement> AvalabileCars;

	@FindBy(xpath = "//div[contains(@class,'desktop-start-purchase-column')]//span[contains(text(),'Start Purchase')]")
	private WebElement StartPurchaseButton;
	
	@FindBy(xpath="//div[contains(@class,'dialog__card')]")
	private WebElement ConfirmWindow;
	
	@FindBy(xpath="//span[contains(text(),'Confirm')]")
	private WebElement ConfirmButton;
	
	
	public void province(String provinceValue, ExtentTest test){
		ProvinceButton.click();
		test.log(Status.INFO, "Successfully clicked on Provice Button to change the Province");
		int length_provinces = Province_elements.size();
		for (int i = 0; i < length_provinces; i++) {
			String ProvinceName = Province_elements.get(i).getText();
			if (ProvinceName.equalsIgnoreCase(provinceValue)) {
				Province_elements.get(i).click();
				test.log(Status.INFO, "Successfully changed the province to "+ProvinceName);
				break;
			}
		}
	}

	public void sort(String SortBy, ExtentTest test) {
		SortEl.click();
		test.log(Status.INFO, "Clicked on Sort By option");
		String xpathSort="//div[contains(@id,'list-item')]//div[@class='v-list-item__title' and text()='"+SortBy+"']";
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathSort)));
		driver.findElement(By.xpath(xpathSort)).click();	
		test.log(Status.INFO, "Successfully sorted the list by "+SortBy);
	}
	

	public void Filter(String FilterBy, String SubFilterBy, ExtentTest test) {
		String FilterXpath = "//span[text()='" + FilterBy + "']";
		driver.findElement(By.xpath(FilterXpath)).click();
		List<WebElement> SubEl_list = driver.findElements(By.xpath(FilterXpath + "//parent::button//following-sibling::div[contains(@class,'pl-0')]//child::span"));
		for (int i = 0; i < SubEl_list.size(); i++) {
			String SubFilterText = SubEl_list.get(i).getText();
			if (SubFilterText.equals(SubFilterBy)) {
				SortEleList.get(i).click();
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void Filter(String FilterBy, String SecondFilterBy, String ThirdFilterBy, ExtentTest test) {
		String FilterXpath = "//span[text()='" + FilterBy + "']";
		driver.findElement(By.xpath(FilterXpath)).click();
		test.log(Status.INFO, "Clicked on Filter option "+FilterBy);
		String Secondfilter_listXpath = FilterXpath+ "//parent::button//following-sibling::div[contains(@class,'pl-0')]//child::span";
		List<WebElement> Secondfilter_list = driver.findElements(By.xpath(Secondfilter_listXpath));
		for (int i = 0; i < Secondfilter_list.size(); i++) {
			String SecondfilterText = Secondfilter_list.get(i).getText();
			if (SecondfilterText.equals(SecondFilterBy)) {
				EventFiringWebDriver event = new EventFiringWebDriver(driver);
				event.executeScript("document.querySelector('div[class=\"container vehicle-filters lighten-3 pt-12 px-0\"]').scrollDown=1000");
				Secondfilter_list.get(i).click();
				test.log(Status.INFO, "Clicked on  Second Filter option "+SecondFilterBy);
				String ThirdFilter_listXpath = "//span[text()='"+SecondFilterBy+"']//parent::button//following-sibling::div[contains(@class,'pl-0')]//child::span[text()]";
				List<WebElement> ThirdFilter_list = driver.findElements(By.xpath(ThirdFilter_listXpath));
				for (int j = 0; j < ThirdFilter_list.size(); j++) {
					String ThirdFilterText = ThirdFilter_list.get(j).getText();
					if (ThirdFilterText.equals(ThirdFilterBy)) {
						ThirdFilter_list.get(j).click();
						test.log(Status.INFO, "Clicked on  Third Filter option "+ThirdFilterBy);
						break;
					}
				}
			}
		}
	}

	public void Select_fav(int favCount, ExtentTest test) {
		for (int i = 0; i < favCount; i++) {
			favOptions.get(i).click();
			test.log(Status.INFO, "Marked favourite on first "+favCount+" available options");
		}
	}
	
	public void Verify_favCount(int favCount, ExtentTest test) {
		test.log(Status.INFO, "Validation for fav count marked started");
		String addedFav = AddedFav.getText();
		int favAddedCount = Integer.parseInt(addedFav);
		try {
		Assert.assertEquals(favAddedCount,favCount);
		}catch(Exception e){
			test.log(Status.FAIL, "Validation failed for Fav marked actual fav count "+addedFav+" , expected Fav Count"+favCount);
		}
	}
	
	public PaymentCalculationPage selectVehicle(ExtentTest test)  {
		int cars_number = AvalabileCars.size();
		for (int i = 0; i < cars_number; i++) {
			AvalabileCars.get(i).click();
			test.log(Status.INFO, "Clicked on First Car on list");
			// thread.sleep(2000);
			if (StartPurchaseButton.isDisplayed()) {
				test.log(Status.INFO, "Verified that car should be available for purchase");
				StartPurchaseButton.click();
				test.log(Status.INFO, "clicked on Purchase button");
			//	ConfirmButton.click();
				break;
			} else {
				driver.navigate().back();
				test.log(Status.INFO, "Verified that car not available for purchase, go back and select another car");
			}
		}
		return new PaymentCalculationPage();
	}

}
