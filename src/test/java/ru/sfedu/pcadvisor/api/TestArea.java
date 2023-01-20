package ru.sfedu.pcadvisor.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.pcadvisor.PCAdvisorClient;
import ru.sfedu.pcadvisor.utils.TestData;


public class TestArea extends TestData {
    protected final Logger log = LogManager.getLogger(TestArea.class);
    protected final AbstractDataProvider dp = new DataProviderJdbc();

    @Test
    void test() {
        PCAdvisorClient.main("XML countBuildPrice 41".split(" "));
        PCAdvisorClient.main("XML buildPc 41".split(" "));
        PCAdvisorClient.main("XML buildPc 41 add 21".split(" "));
        PCAdvisorClient.main("XML addPart 41 31".split(" "));
        PCAdvisorClient.main("XML removePart 41 31".split(" "));
        PCAdvisorClient.main("XML validateBuild 41".split(" "));
        PCAdvisorClient.main("XML findBuild 41".split(" "));
        PCAdvisorClient.main("XML showMissingParts 41".split(" "));
    }
}
