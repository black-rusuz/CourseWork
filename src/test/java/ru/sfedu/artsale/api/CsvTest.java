package ru.sfedu.artsale.api;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class CsvTest extends AbstractTest {

    @BeforeEach
    void setUp() throws IOException {
        dataProvider = new DataProviderCsv();
        super.setUp();
    }
}
