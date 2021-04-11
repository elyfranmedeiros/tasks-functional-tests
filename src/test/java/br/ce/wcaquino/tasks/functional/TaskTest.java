package br.ce.wcaquino.tasks.functional;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TaskTest {

	public WebDriver acessarAplicacao() {
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8001/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void testUserCreatedSuccessfully() throws InterruptedException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Task Name");
			driver.findElement(By.id("dueDate")).sendKeys("25/12/2030");
			driver.findElement(By.id("saveButton")).click();
			assertEquals("Success!", driver.findElement(By.id("message")).getText());
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void testShouldNotAddOldDate() throws InterruptedException {
		WebDriver driver = acessarAplicacao();
		try {
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Task Name");
		driver.findElement(By.id("dueDate")).sendKeys("25/12/2010");
		driver.findElement(By.id("saveButton")).click();
		assertEquals("Due date must not be in past", driver.findElement(By.id("message")).getText());
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void testShouldNotAcceptWithoutDescription() throws InterruptedException {
		WebDriver driver = acessarAplicacao();
		try {
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("dueDate")).sendKeys("25/12/2030");
		driver.findElement(By.id("saveButton")).click();
		assertEquals("Fill the task description", driver.findElement(By.id("message")).getText());
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void testShouldNotAcceptWithoutDate() throws InterruptedException {
		WebDriver driver = acessarAplicacao();
		try {
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Task Name");
		driver.findElement(By.id("saveButton")).click();
		assertEquals("Fill the due date", driver.findElement(By.id("message")).getText());
		} finally {
			driver.quit();
		}
	}
}
