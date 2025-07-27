package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{

	@Test
	public void verify_accouunt_registration()
	{

		logger.info("*** Starting TC001_AccountRegistrationTest ***");
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyAcccount();
		logger.info("Clicked on MyAccount Link...");

		hp.clickRegister();
		logger.info("Clicked on Register Link...");

		AccountRegistrationPage repage=new AccountRegistrationPage(driver);

		logger.info("Providing Customer details...");
		repage.setFirstName(randomAlphabetic().toUpperCase());
		repage.setLastName(randomAlphabetic().toUpperCase());
		repage.setEmail(randomAlphabetic()+"@gmail.com");
		repage.setTelephone(randomNumeric());

		String password= randomAlphanumeric();

		repage.setpassword(password);
		repage.setConfirmPassword(password);

		repage.setPrivacyPolicy();
		repage.clickContinue();

		logger.info("Validating expected message...");
		String confmsg = repage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!"))
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

		logger.info("*** Finished TC001_AccountRegistrationTest ***");

	}







}

