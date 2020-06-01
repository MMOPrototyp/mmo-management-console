package com.jukusoft.mmo.management.console;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/*@RunWith(SpringRunner.class)//TODO: use @SpringBootTest instead
@TestPropertySource(locations = "classpath:bootstrap.properties")*/
@ActiveProfiles("test")
@SpringBootTest
public class ServerMainTest {

    /*@Before
    public void before() {
        Config.loadFromResourcesDir("testconfig");
    }

    @After
    public void after() {
        Config.clear();
    }*/

    /**
     * this test starts a complete Spring Boot context to ensure, that startup on webapp works
     */
    @Test
    public void contextLoads() {
        //
    }

}
