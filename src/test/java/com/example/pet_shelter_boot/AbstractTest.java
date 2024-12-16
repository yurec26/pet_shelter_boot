package com.example.pet_shelter_boot;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
public class AbstractTest {

    private static volatile boolean isStarted = false;

    public static PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("2212");

    static {
        if (!isStarted) {
            POSTGRES_CONTAINER.start();
            isStarted = true;
        }
    }

    @DynamicPropertySource
    static void dataSourceProp(DynamicPropertyRegistry registry) {
        registry.add("test.postgres.port", POSTGRES_CONTAINER::getFirstMappedPort);
    }

    @EventListener
    public void closeContainer(ContextStartedEvent event) {
        POSTGRES_CONTAINER.stop();
    }
}
