package com.satria.javatestframework.tests;

import com.github.javafaker.Faker;
import com.satria.javatestframework.JavaTestFrameworkApplicationTests;
import com.satria.javatestframework.pageobject.GoogleHomepage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

@SpringBootTest
public class SearchRandomArtist extends JavaTestFrameworkApplicationTests {

    @Autowired
    private GoogleHomepage googleHomepage;
    @Test
    public void searchRandomArtist() throws InterruptedException {
        Faker faker = new Faker();
        googleHomepage.searchOnHomepage(faker.artist().name());

    }

}
