package Sinatra;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SinatraStepDefs {

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void setUpTest()
    {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--disable-notifications");
        //System.setProperty("webdriver.chrome.driver", "C:\\libs\\chromedriver.exe");
        driver = new ChromeDriver(opt);
        wait = new WebDriverWait(driver, 30);
        driver.manage().window().setSize(new Dimension(1500, 1000));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @After
    public void tearDownTest()
    {
        driver.quit();
    }


    @Given("I navigate to sinatra page")
    public void iNavigateToSinatraPage() {
        //abrir URL
        driver.get("https://evening-bastion-49392.herokuapp.com/");
        //asegurar que el titulo sea igual a "Songs By Sinatra";
        assertEquals("Songs By Sinatra", driver.getTitle());
    }

    @When("I login with correct credentials")
    public void iLoginWithCorrectCredentials() {
        //dar click en log in
        WebElement linkLogIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href='/login']")));
        linkLogIn.click();

        //driver.findElement(By.cssSelector("[href='/login']"));


        //meter user name y password
        driver.findElement(By.id("username")).sendKeys("frank");
        driver.findElement(By.id("password")).sendKeys("sinatra");
        //dar click en login
        driver.findElement(By.cssSelector("[type='submit']")).click();

    }

    @Then("I can see sinatra Home Page")
    public void iCanSeeSinatraHomePage() {

    //crear cancion nueva [href='/songs/new']
    driver.findElement(By.cssSelector("[href='/songs/new']")).click();

        //meter Title: song[title]
        driver.findElement(By.name("song[title]")).sendKeys("Vampiria");
        // meter lenght: "song[length]"
        driver.findElement(By.name("song[length]")).sendKeys("50");
        //meter date: name="song[released_on]"
        driver.findElement(By.name("song[released_on]")).sendKeys("25/06/2020");
        //meter lyrics: name="song[lyrics]"
        driver.findElement(By.name("song[lyrics]")).sendKeys("Test number 1");
        //save button: [value='Save Song']
        driver.findElement(By.cssSelector("[value='Save Song']")).click();
    }

    private void assertEquals(String songs_by_sinatra, String title) {
    }


    @When("I am in the songs page")
    public void iAmInTheSongsPage()
    {
        //verificar qeu sea la pagina de songs
        //verificar que haya una lista de canciones
        WebElement songsTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section h1")));
        String currentUrl = driver.getCurrentUrl();
        WebElement songsLink = driver.findElement(By.cssSelector("[href='/songs']"));
        String currentClass = songsLink.getAttribute("class");
        List<WebElement> listaCanciones = driver.findElements(By.cssSelector("#songs li"));

        if(songsTitle.isDisplayed()  &&
                currentUrl.endsWith("songs") &&
                currentClass.equals("current") &&
                listaCanciones.size() > 0) {
            System.out.println("Si estoy en la pagina de songs");
        }
        else {
            System.out.println("No estoy en la pagina de songs.");
        }

    }


    @Then("I can click on like a song")
    public void iCanClickOnLikeASong()
    {
        WebElement like = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("like")));
        WebElement nombreCancion = driver.findElement(By.cssSelector("section h1"));
        String nombre = nombreCancion.getText();
        if (like.isDisplayed())
        {
            WebElement darLike = driver.findElement(By.cssSelector("[value='Like']"));
            darLike.click();
            System.out.println("Se le ha dado like a la cancion "+ nombre + " ;) ");
        }
        else
        {
            System.out.println("No hay boton para darle like a la cancion");
        }


    }

    @And("I select song number {int}")
    public void iSelectSongNumber(int numeroCancion)
    {
        List<WebElement> listaCanciones = driver.findElements(By.cssSelector("#songs li a"));
        System.out.println("la lista de caciones es de " +listaCanciones.size() + " canciones");
        WebElement cancion = listaCanciones.get(numeroCancion - 1);
        cancion.click();
    }
}
