package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage
{

	public LoginPage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtUserName;

	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;

	@FindBy(xpath="//input[@value='Login']")
	WebElement btnLogin;

	@FindBy(xpath="//h2[normalize-space()='My Account']")
	WebElement msgConfirmationLogin;

		public void setUserName(String uname) {
			txtUserName.sendKeys(uname);
		}

		public void setPassword(String lpwd) {
			txtPassword.sendKeys(lpwd);
		}

		public void clickLogin() {
			btnLogin.click();
		}

		public String getConfirmationMsg_Login() {
			try {
				return(msgConfirmationLogin.getText());
			} catch (Exception e) {
				return (e.getMessage());
			}
		}

}
