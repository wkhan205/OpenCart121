package testBase;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

public WebDriver driver;
public Logger logger;      //log4j
public Properties p;

	@BeforeClass
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException
	{
		//Loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		logger=LogManager.getLogger(this.getClass());  //log4j2

		switch(br.toLowerCase())
		{
		case "chrome" :		driver=new ChromeDriver(); break;
		case "edge" :
			System.setProperty("webdriver.edge.driver", "C:\\Program Files\\MSEdgeDriver\\msedgedriver.exe");
			driver=new EdgeDriver(); break;
		case "firefox" :	driver=new FirefoxDriver(); break;
        default : System.out.println("Invalid browser name..."); return;
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(p.getProperty("appURL1")); //reading URL from properties file.
		driver.manage().window().maximize();
	}

	@AfterClass
	public void tearDown()
	{

	}

    // Generate a random alphabetic string
	public String randomAlphabetic()
	{
	    RandomStringGenerator generator = new RandomStringGenerator.Builder()
	        .withinRange('a', 'z')
	        .get();
	    return generator.generate(5);
	}

    // Generate a random numeric string
	public String randomNumeric()
	{
	    RandomStringGenerator generator = new RandomStringGenerator.Builder()
	        .withinRange('0', '9')
	        .get();
	    return generator.generate(10);
	}

	// Generate a random alphanumeric string of length 10
	public String randomAlphanumeric()
	{
		RandomStringGenerator generator = new RandomStringGenerator.Builder()
	            .withinRange('0', 'z')
	            .filteredBy(Character::isLetterOrDigit)
	            .get();

	        String randomAlphanumeric = generator.generate(10);
			return randomAlphanumeric;
	}

}