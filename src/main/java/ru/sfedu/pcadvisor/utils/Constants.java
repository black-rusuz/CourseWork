package ru.sfedu.pcadvisor.utils;

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
    public static final String BEANS_DELIMITER = "--";

    // CRUD
    public static final String METHOD_NAME_APPEND = "Append";
    public static final String METHOD_NAME_DELETE = "Delete";
    public static final String METHOD_NAME_UPDATE = "Update";

    // CLI
    public static final String XML = "XML";
    public static final String JDBC = "JDBC";
    public static final String CSV = "CSV";

    public static final String COUNT_BUILD_PRICE = "COUNTBUILDPRICE";
    public static final String BUILD_PC = "BUILDPC";
    public static final String ADD_PART = "ADDPART";
    public static final String REMOVE_PART = "REMOVEPART";
    public static final String VALIDATE_BUILD = "VALIDATEBUILD";
    public static final String FIND_BUILD = "FINDBUILD";
    public static final String SHOW_MISSING_PARTS = "SHOWMISSINGPARTS";

    public static final String ADD = "ADD";
    public static final String REMOVE = "REMOVE";

    // Info
    public static final String NOT_FOUND = "%s ID %d not found";
    public static final String TOTAL_PRICE = "Total price: ";
    public static final String YOUR_ORDER = "Your build:\n";
    public static final String ADDED_PART = "Added part: ";
    public static final String REMOVED_PART = "Removed part: ";
    public static final String PART_NOT_INSTALLED = "Part not installed: ";
    public static final String BUILD_VALID = "Build is valid";
    public static final String BUILD_INVALID = "Build is invalid";
    public static final String MISSING_PARTS = "You have the missing parts. Try one of these:\n";

    public static final String FEW_ARGS = "Few arguments";
    public static final String WRONG_DP = "Wrong type of DataProvider";
    public static final String WRONG_ARG = "Wrong argument";
}
