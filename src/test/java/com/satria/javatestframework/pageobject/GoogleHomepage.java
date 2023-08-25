package com.satria.javatestframework.pageobject;

import com.satria.javatestframework.FE.WebBaseMethod;
import com.satria.javatestframework.utils.SpringAnnotations.Page;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Page
public class GoogleHomepage extends WebBaseMethod {

    @PostConstruct
    public void initGoogleHomepage(){
        PageFactory.initElements(webDriver,this);
    }

    @Autowired
    @Qualifier("createDriver")
    private WebDriver webDriver;

    @FindBy(xpath = "//textarea[@type='search']")
    private WebElement searchTextInput;

    public GoogleHomepage searchOnHomepage(String text) throws InterruptedException {
        webDriver.get("https://google.com");
        sendKeys(searchTextInput,text);
        searchTextInput.sendKeys(Keys.RETURN);
        return this;
    }
}
