package ru.sfedu.artsale.api;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class JdbcTest extends AbstractTest {

    @BeforeEach
    void setUp() throws IOException {
        dataProvider = new DataProviderJdbc();
        super.setUp();
    }
}
