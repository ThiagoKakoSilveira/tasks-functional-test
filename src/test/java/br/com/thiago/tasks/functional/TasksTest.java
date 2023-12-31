package br.com.thiago.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    private WebDriver acessarAplicacao() throws MalformedURLException {
//        System.setProperty("webdriver.chrome.driver","/home/thiago/drivers/chromedriver-linux64/chromedriver");
//        WebDriver driver = new ChromeDriver();
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.18:4444/wd/hub"), desiredCapabilities);
        driver.navigate().to("http://192.168.0.38:8001/tasks");
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deverSalvarTask() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar no Add
            WebElement buttonAdd = driver.findElement(By.id("addTodo"));
            buttonAdd.click();
            //escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
            //escrever data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2024");
            //clicar no Salvar
            WebElement buttonSave = driver.findElement(By.id("saveButton"));
            buttonSave.click();
            //validar mensagem de sucesso
            String mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", mensagem);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void deverDarErroTaskComDataPassada() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar no Add
            WebElement buttonAdd = driver.findElement(By.id("addTodo"));
            buttonAdd.click();
            //escrever descrição
            driver.findElement(By.id("task"))
                    .sendKeys("Data Passada");
            //escrever data
            driver.findElement(By.id("dueDate"))
                    .sendKeys("10/10/2023");
            //clicar no Salvar
            WebElement buttonSave = driver.findElement(By.id("saveButton"));
            buttonSave.click();
            //validar mensagem de sucesso
            String mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", mensagem);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void deverDarErroTaskSemDescricao() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar no Add
            WebElement buttonAdd = driver.findElement(By.id("addTodo"));
            buttonAdd.click();
            //escrever data
            driver.findElement(By.id("dueDate"))
                    .sendKeys("10/10/2024");
            //clicar no Salvar
            WebElement buttonSave = driver.findElement(By.id("saveButton"));
            buttonSave.click();
            //validar mensagem de sucesso
            String mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", mensagem);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void deverDarErroTaskSemData() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar no Add
            WebElement buttonAdd = driver.findElement(By.id("addTodo"));
            buttonAdd.click();
            //escrever descrição
            driver.findElement(By.id("task"))
                    .sendKeys("Teste sem data");
            //clicar no Salvar
            WebElement buttonSave = driver.findElement(By.id("saveButton"));
            buttonSave.click();
            //validar mensagem de sucesso
            String mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", mensagem);
        } finally {
            driver.quit();
        }
    }
}