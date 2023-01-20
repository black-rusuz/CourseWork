package ru.sfedu.goslingdrive.utils;

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

    public static final String SEARCH_PARTS = "SEARCHPARTS";
    public static final String SEARCH_BY_NAME = "SEARCHBYNAME";
    public static final String SEARCH_BY_VIN = "SEARCHBYVIN";
    public static final String MODIFY_ORDER = "MODIFYORDER";
    public static final String ADD_PART = "ADDPART";
    public static final String REMOVE_PART = "REMOVEPART";
    public static final String CALCULATE_TOTAL_PRICE = "CALCULATETOTALPRICE";

    public static final String ADD = "ADD";
    public static final String REMOVE = "REMOVE";

    // Info
    public static final String NOT_FOUND = "%s ID %d not found";
    public static final String FOUND_PARTS = "Found parts:\n";
    public static final String MODIFIED_ORDER = "Modified order:\n";
    public static final String ADDED_PART = "Added part: ";
    public static final String REMOVED_PART = "Removed part: ";
    public static final String PART_NOT_INSTALLED = "Part not installed: ";
    public static final String TOTAL_PRICE = "Total price: ";

    public static final String FEW_ARGS = "Few arguments";
    public static final String WRONG_DP = "Wrong type of DataProvider";
    public static final String WRONG_ARG = "Wrong argument";
}
