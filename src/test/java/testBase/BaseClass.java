package testBase;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

public WebDriver driver;
public Logger logger;      //log4j
public Properties p;

	@BeforeClass
	@Parameters({"os","browser"})
	public void setup(@Optional("windows") String os, @Optional("chrome") String br) throws IOException
	{
		//Loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		logger=LogManager.getLogger(this.getClass());  //log4j2
		
		// if the environment is local, set the driver accordingly
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			logger.info("Remote execution is enabled");

			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			capabilities.setPlatform(Platform.WIN11); // Set the platform to Windows 11
			
			// Set the operating system based on the parameter
			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WINDOWS);
			} else if (os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				logger.error("Invalid OS specified: " + os);
				return;
			}
			
			// Set the browser name based on the parameter
			switch(br.toLowerCase())
			{
			case "chrome" :	
				capabilities.setBrowserName("chrome"); break;
			case "edge" :
				capabilities.setBrowserName("edge"); break;
			case "firefox" :
				capabilities.setBrowserName("firefox"); break;
			default :logger.error("Invalid browser name: " + br);
				return;
			}
			
			// Set the remote WebDriver URL using URI to avoid deprecated URL constructor
			try {
    driver = new RemoteWebDriver(
        URI.create("http://localhost:4444/wd/hub").toURL(),
        capabilities);
} catch (Exception e) {
    logger.error("Failed to create RemoteWebDriver: " + e.getMessage());
    return;
}
		}
			// if the environment is local, set the driver accordingly
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" :		driver=new ChromeDriver(); break;
			case "edge" :
				System.setProperty("webdriver.edge.driver", "C:\\Program Files\\MSEdgeDriver\\msedgedriver.exe");
				driver=new EdgeDriver(); break;
			case "firefox" :	driver=new FirefoxDriver(); break;
			default :logger.error("Invalid browser name: " + br);
				return;
			}	
		}

		

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(p.getProperty("appURL1")); //reading URL from properties file.
		driver.manage().window().maximize();
		
		logger.info("Application launched successfully");
		return;
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