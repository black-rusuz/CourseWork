package ru.sfedu.trainpick;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.trainpick.api.AbstractDataProvider;
import ru.sfedu.trainpick.api.DataProviderCsv;
import ru.sfedu.trainpick.api.DataProviderJdbc;
import ru.sfedu.trainpick.api.DataProviderXml;
import ru.sfedu.trainpick.utils.Constants;
import ru.sfedu.trainpick.utils.TestData;

public class TrainPickClient {
    private static final Logger log = LogManager.getLogger(TrainPickClient.class);
    private static AbstractDataProvider dp;

    public static void main(String[] args) {
        checkArgumentsCount(args);
        dp = getDataProvider(args[0]);
        if (dp.getTickets().isEmpty()) loadSampleData();

        switch (args[1].toUpperCase()) {
            case (Constants.ADD_PASSENGER) -> dp.addPassenger(args[2], args[3], Long.parseLong(args[4]), Boolean.parseBoolean(args[5]));
            case (Constants.FIND_TRAIN) -> dp.findTrain(args[2], args[3]);
            case (Constants.CALCULATE_DURATION) -> dp.calculateDuration(args[2], args[3]);
            case (Constants.CALCULATE_PRICE) -> dp.calculatePrice(Double.parseDouble(args[2]), Double.parseDouble(args[3]));

            case (Constants.VIEW_PASSENGERS) -> dp.viewPassengers(Long.parseLong(args[2]), args.length > 3 ? Long.parseLong(args[3]) : 0);
            case (Constants.PAY_TICKET) -> dp.payTicket(Long.parseLong(args[2]));
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

        dp.insertChildPassenger(td.cp1);
        dp.insertChildPassenger(td.cp2);

        dp.insertDiscountPassenger(td.dp1);
        dp.insertDiscountPassenger(td.dp2);

        dp.insertPassenger(td.ps1);
        dp.insertPassenger(td.ps2);

        dp.insertTrain(td.tr1);
        dp.insertTrain(td.tr2);

        dp.insertTicket(td.ti1);
        dp.insertTicket(td.ti2);
    }
}