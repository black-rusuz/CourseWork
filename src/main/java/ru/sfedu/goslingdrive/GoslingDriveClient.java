package ru.sfedu.goslingdrive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.goslingdrive.api.AbstractDataProvider;
import ru.sfedu.goslingdrive.api.DataProviderCsv;
import ru.sfedu.goslingdrive.api.DataProviderJdbc;
import ru.sfedu.goslingdrive.api.DataProviderXml;
import ru.sfedu.goslingdrive.utils.Constants;
import ru.sfedu.goslingdrive.utils.TestData;

public class GoslingDriveClient {
    private static final Logger log = LogManager.getLogger(GoslingDriveClient.class);
    private static AbstractDataProvider dp;

    public static void main(String[] args) {
        checkArgumentsCount(args);
        dp = getDataProvider(args[0]);
        if (dp.getOrders().isEmpty()) loadSampleData();

        switch (args[1].toUpperCase()) {
            case (Constants.SEARCH_PARTS) -> dp.searchParts(args[2], args[3]);
            case (Constants.SEARCH_BY_NAME) -> dp.searchByName(args[2]);
            case (Constants.SEARCH_BY_VIN) -> dp.searchByVin(args[2]);

            case (Constants.MODIFY_ORDER) -> dp.modifyOrder(args.length > 3 ? args[2] : "",
                    args.length > 3 ? Long.parseLong(args[3]) : 0);
            case (Constants.ADD_PART) -> dp.addPart(Long.parseLong(args[2]));
            case (Constants.REMOVE_PART) -> dp.removePart(Long.parseLong(args[2]));
            case (Constants.CALCULATE_TOTAL_PRICE) -> dp.calculateTotalPrice(Long.parseLong(args[2]));
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

        dp.insertBodyPart(td.b1);
        dp.insertBodyPart(td.b2);

        dp.insertElectricPart(td.e1);
        dp.insertElectricPart(td.e2);

        dp.insertRunningPart(td.r1);
        dp.insertRunningPart(td.r2);

        dp.insertUser(td.u1);
        dp.insertUser(td.u2);

        dp.insertOrder(td.o1);
        dp.insertOrder(td.o2);
    }
}