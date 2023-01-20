package ru.sfedu.artsale.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.artsale.ArtSaleClient;

import java.io.IOException;


public class TestArea {
    protected final Logger log = LogManager.getLogger(TestArea.class);
    protected final AbstractDataProvider dp = new DataProviderXml();

    public TestArea() throws IOException {
    }

    @Test
    void test() throws IOException {
        ArtSaleClient.main("XML viewProducts any 21".split(" "));
        ArtSaleClient.main("XML filterView product".split(" "));
        ArtSaleClient.main("XML orderProduct 21".split(" "));
        ArtSaleClient.main("XML showRelated 21".split(" "));
        ArtSaleClient.main("XML viewUserData 12 true".split(" "));
        ArtSaleClient.main("XML viewUserOrders 12".split(" "));
        ArtSaleClient.main("XML calculateAmount 12".split(" "));
    }
}
