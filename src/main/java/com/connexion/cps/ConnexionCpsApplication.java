package com.connexion.cps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * main run
 * 
 * -cps
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ConnexionCpsApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ConnexionCpsApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Connexion-cps   ლ(´ڡ`ლ)ﾞ  \n" +
                " _________                                    .__                                    .___      .__        \n" +
                " \\_   ___ \\  ____   ____   ____   ____ ___  __|__| ____   ____           _____     __| _/_____ |__| ____      \n" +
                " /    \\  \\/ /  _ \\ /    \\ /    \\_/ __ \\\\  \\/  /  |/  _ \\ /    \\   ______ \\__  \\   / __ |/     \\|  |/    \\ \n" +
                "\\     \\___(  <_> )   |  \\   |  \\  ___/ >    <|  (  <_> )   |  \\ /_____/  / __ \\_/ /_/ |  Y Y  \\  |   |  \\\n" +
                "  \\______  /\\____/|___|  /___|  /\\___  >__/\\_ \\__|\\____/|___|  /         (____  /\\____ |__|_|  /__|___|  / \n" +
                "         \\/            \\/     \\/     \\/      \\/              \\/               \\/      \\/     \\/        \\/ \n"
                );
    }
}