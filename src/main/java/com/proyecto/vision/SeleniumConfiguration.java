package com.proyecto.vision;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class SeleniumConfiguration {

    /*@PostConstruct
    void postConstruct(){
        Path path = Paths.get(Constantes.pathRecursos + Constantes.pathDrivers + Constantes.slash + "chromedriver.exe");
        String ruta = path.toAbsolutePath().toString();
        System.setProperty("webdriver.chrome.driver", ruta);
    }

    @Bean
    public ChromeDriver driver(){
        final ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("start-maximized");
        //chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--disable-extensions");

        return new ChromeDriver(chromeOptions);
    }*/
}
