package ru.sfedu.artsale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.artsale.api.AbstractDataProvider;
import ru.sfedu.artsale.api.DataProviderCsv;
import ru.sfedu.artsale.api.DataProviderJdbc;
import ru.sfedu.artsale.api.DataProviderXml;
import ru.sfedu.artsale.utils.TestData;

import java.io.IOException;
import java.util.Locale;

public class ArtSaleClient {
    private static final Logger log = LogManager.getLogger(ArtSaleClient.class);
    private static AbstractDataProvider dataProvider;

    public static void main(String[] args) throws IOException {
        checkArgumentsCount(args);
        dataProvider = getDataProvider(args[0]);
        if (dataProvider.getUsers().isEmpty()) loadSampleData();

        switch (args[1].toUpperCase(Locale.ROOT)) {
            case (Constants.ARG_VIEWPRODUCTS) -> dataProvider.viewProducts(args[2], args.length < 4 ? 0 : Long.parseLong(args[3]));
            case (Constants.ARG_FILTERVIEW) -> dataProvider.filterView(args[2]);
            case (Constants.ARG_ORDERPRODUCT) -> dataProvider.orderProduct(Long.parseLong(args[2]));
            case (Constants.ARG_SHOWRELATED) -> dataProvider.showRelated(Long.parseLong(args[2]));
            case (Constants.ARG_VIEWUSERDATA) -> dataProvider.viewUserData(Long.parseLong(args[2]), Boolean.parseBoolean(args[3]));
            case (Constants.ARG_VIEWUSERORDERS) -> dataProvider.viewUserOrders(Long.parseLong(args[2]));
            case (Constants.ARG_CALCULATEAMOUNT) -> dataProvider.calculateAmount(Long.parseLong(args[2]));
            default -> log.error(Constants.WRONG_ARG);
        }
    }

    private static void checkArgumentsCount(String[] args) {
        if (args.length < 2) {
            log.error(Constants.FEW_ARGS);
            System.exit(0);
        }
    }

    private static AbstractDataProvider getDataProvider(String dataProviderSource) throws IOException {
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

        dataProvider.insertUser(td.u1);
        dataProvider.insertUser(td.u2);

        dataProvider.insertProduct(td.p1);
        dataProvider.insertProduct(td.p2);
        dataProvider.insertProduct(td.p3);
        dataProvider.insertProduct(td.p4);

        dataProvider.insertCreationKit(td.ck1);
        dataProvider.insertCreationKit(td.ck2);
        dataProvider.insertCreationKit(td.ck3);
        dataProvider.insertCreationKit(td.ck4);

        dataProvider.insertEndProduct(td.ep1);
        dataProvider.insertEndProduct(td.ep2);
        dataProvider.insertEndProduct(td.ep3);
        dataProvider.insertEndProduct(td.ep4);

        dataProvider.insertOrder(td.o1);
        dataProvider.insertOrder(td.o2);
        dataProvider.insertOrder(td.o3);
        dataProvider.insertOrder(td.o4);
    }
}
