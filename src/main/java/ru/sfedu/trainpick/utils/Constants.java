package ru.sfedu.trainpick.utils;

public class Constants {
    // FILE
    public static final String XML_PATH = "XML_PATH";
    public static final String CSV_PATH = "CSV_PATH";

    // JDBC
    public static final String H2_HOSTNAME = "H2_HOSTNAME";
    public static final String H2_USERNAME = "H2_USERNAME";
    public static final String H2_PASSWORD = "H2_PASSWORD";

    // MongoDB
    public static final String MONGO_ENABLE = "MONGO_ENABLE";
    public static final String MONGO_ACTOR = "MONGO_ACTOR";
    public static final String MONGO_CONNECTION = "MONGO_CONNECTION";
    public static final String MONGO_DATABASE = "MONGO_DATABASE";
    public static final String MONGO_COLLECTION = "MONGO_COLLECTION";


    // Internal
    public static final String ENVIRONMENT_VARIABLE = "env";
    public static final String GET_ID = "getId";
    public static final String SET_ID = "setId";
    public static final String XML_PATTERN = "%s.xml";
    public static final String CSV_PATTERN = "%s.csv";
    public static final String FIELDS_DELIMITER = "::";

    // CRUD
    public static final String METHOD_NAME_APPEND = "Append";
    public static final String METHOD_NAME_DELETE = "Delete";
    public static final String METHOD_NAME_UPDATE = "Update";

    // CLI
    public static final String XML = "XML";
    public static final String JDBC = "JDBC";
    public static final String CSV = "CSV";

    // Info
    public static final String NOT_FOUND = "%s ID %d not found";
    public static final String FEW_ARGS = "Few arguments";
    public static final String WRONG_DP = "Wrong type of DataProvider";
    public static final String WRONG_ARG = "Wrong argument";

    public static final String TRIP_DURATION = "Trip duration: %s";
    public static final String DURATION = "%d day(s), %d hours";
    public static final String COST = "Trip cost: %.2f roubles";
    public static final String TICKET = "Your ticket: %s";
    public static final String NO_TRAINS = "There are no trains from %s to %s";
    public static final String TRAIN = "Found train: %s";
    public static final String PASSENGERS = "Passengers of train %d: \n";
    public static final String TICKET_PAID = "Ticket %d paid successfully";
}
