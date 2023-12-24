package br.com.thiago.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TasksTest {

    private WebDriver acessarAplicacao() {
        System.setProperty("webdriver.chrome.driver","/home/thiago/drivers/chromedriver-linux64/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8001/tasks");
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deverSalvarTask() {
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
    public void deverDarErroTaskComDataPassada() {
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
    public void deverDarErroTaskSemDescricao() {
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
    public void deverDarErroTaskSemData() {
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