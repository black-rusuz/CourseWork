package ru.sfedu.goslingdrive.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.goslingdrive.GoslingDriveClient;
import ru.sfedu.goslingdrive.utils.TestData;


public class TestArea extends TestData {
    protected final Logger log = LogManager.getLogger(TestArea.class);
    protected final AbstractDataProvider dp = new DataProviderXml();

    @Test
    void test() {
        GoslingDriveClient.main("XML searchParts bumper A1BX4".split(" "));
        GoslingDriveClient.main("XML searchByName bumper".split(" "));
        GoslingDriveClient.main("XML searchByVin A1BX4".split(" "));
        GoslingDriveClient.main("XML modifyOrder".split(" "));
        GoslingDriveClient.main("XML modifyOrder add 31".split(" "));
        GoslingDriveClient.main("XML addPart 21".split(" "));
        GoslingDriveClient.main("XML removePart 21".split(" "));
        GoslingDriveClient.main("XML calculateTotalPrice 52".split(" "));
    }
}
