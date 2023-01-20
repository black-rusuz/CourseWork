package ru.sfedu.pcadvisor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.pcadvisor.api.AbstractDataProvider;
import ru.sfedu.pcadvisor.api.DataProviderCsv;
import ru.sfedu.pcadvisor.api.DataProviderJdbc;
import ru.sfedu.pcadvisor.api.DataProviderXml;
import ru.sfedu.pcadvisor.utils.Constants;
import ru.sfedu.pcadvisor.utils.TestData;

public class PCAdvisorClient {
    private static final Logger log = LogManager.getLogger(PCAdvisorClient.class);
    private static AbstractDataProvider dp;

    public static void main(String[] args) {
        checkArgumentsCount(args);
        dp = getDataProvider(args[0]);
        if (dp.getOrders().isEmpty()) loadSampleData();

        switch (args[1].toUpperCase()) {
            case (Constants.COUNT_BUILD_PRICE) -> dp.countBuildPrice(Long.parseLong(args[2]));

            case (Constants.BUILD_PC) -> dp.buildPc(Long.parseLong(args[2]),
                    args.length > 3 ? args[3] : "",
                    args.length > 3 ? Long.parseLong(args[4]) : 0);
            case (Constants.ADD_PART) -> dp.addPart(Long.parseLong(args[2]), Long.parseLong(args[3]));
            case (Constants.REMOVE_PART) -> dp.removePart(Long.parseLong(args[2]), Long.parseLong(args[3]));
            case (Constants.VALIDATE_BUILD) -> dp.validateBuild(Long.parseLong(args[2]));

            case (Constants.FIND_BUILD) -> dp.findBuild(Long.parseLong(args[2]));
            case (Constants.SHOW_MISSING_PARTS) -> dp.showMissingParts(Long.parseLong(args[2]));
            default -> log.error(Constants.WRONG_ARG);
        }
    }

    private static void checkArgumentsCount(String[] args) {
        if (args.length < 2) {
            log.error(Constants.FEW_ARGS);
            System.exit(0);
        }
    }

    private static AbstractDataProvider getDataProvider(String dataProviderSource) {
        if (dataProviderSource.equalsIgnoreCase(Constants.XML)) return new DataProviderXml();
        else if (dataProviderSource.equalsIgnoreCase(Constants.CSV)) return new DataProviderCsv();
        else if (dataProviderSource.equalsIgnoreCase(Constants.JDBC)) return new DataProviderJdbc();
        else {
            log.error(Constants.WRONG_DP);
            System.exit(0);
            return null;
        }
    }

    private static void loadSampleData() {
        TestData td = new TestData();

        dp.insertCpu(td.c1);
        dp.insertCpu(td.c2);

        dp.insertMotherboard(td.m1);
        dp.insertMotherboard(td.m2);

        dp.insertRam(td.r1);
        dp.insertRam(td.r2);

        dp.insertOrder(td.o1);
        dp.insertOrder(td.o2);
    }
}