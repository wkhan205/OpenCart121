package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass
{

	@Test
	public void verify_login()
	{

		logger.info("*** Starting TC002_LoginTest ***");
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyAcccount();
		logger.info("Clicked on MyAccount Link...");

		hp.clickLogin();
		logger.info("Clicked on Register Link...");

		LoginPage lp=new LoginPage(driver);

		logger.info("Providing Customer details...");
		lp.setUserName(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));

		lp.clickLogin();

		logger.info("Validating expected message...");
		String confmsg_login = lp.getConfirmationMsg_Login();
		if(confmsg_login.equals("My Account!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed...");
			logger.debug("Debug logs...");
			Assert.assertTrue(false);
		}

		}
		catch(Exception e)
		{
			Assert.fail();
		}

		logger.info("*** Finished TC002_LoginTest ***");

	}







}

