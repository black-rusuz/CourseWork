package ru.sfedu.trainpick.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.trainpick.TrainPickClient;
import ru.sfedu.trainpick.utils.TestData;


public class TestArea extends TestData {
    protected final Logger log = LogManager.getLogger(TestArea.class);
    protected final AbstractDataProvider dp = new DataProviderXml();

    @Test
    void test() {
        TrainPickClient.main("XML searchParts bumper A1BX4".split(" "));
        TrainPickClient.main("XML searchByName bumper".split(" "));
        TrainPickClient.main("XML searchByVin A1BX4".split(" "));
        TrainPickClient.main("XML modifyOrder".split(" "));
        TrainPickClient.main("XML modifyOrder add 31".split(" "));
        TrainPickClient.main("XML addPart 21".split(" "));
        TrainPickClient.main("XML removePart 21".split(" "));
        TrainPickClient.main("XML calculateTotalPrice 52".split(" "));
    }
}
