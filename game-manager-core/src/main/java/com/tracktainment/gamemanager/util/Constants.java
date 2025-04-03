package com.tracktainment.gamemanager.util;

public class Constants {

    public Constants() {
        throw new IllegalStateException("Cannot instantiate an util class.");
    }

    // Default values;
    public static final String DEFAULT_OFFSET = "0";
    public static final String DEFAULT_LIMIT = "10";
    public static final int MIN_OFFSET = 0;
    public static final int MIN_LIMIT = 1;
    public static final int MAX_LIMIT = 100;
    public static final String DEFAULT_ORDER = "TITLE";
    public static final String DEFAULT_DIRECTION = "ASC";


    // Required fields validation
    public static final String TITLE_MANDATORY_MSG = "'title' is mandatory.";
    public static final String PLATFORM_MANDATORY_MSG = "'platform' is mandatory.";


    // Regex
    public static final String ID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    public static final String TITLE_REGEX = "^[A-Za-z0-9\\s\\-,\\.\\'\\\";!?()&]{1,200}$";
    public static final String PLATFORM_REGEX = "^[A-Za-z0-9\\\\-\\\\s]{1,50}$";
    public static final String GENRE_REGEX = "^[A-Za-z\\\\s\\\\-]{1,50}$";
    public static final String DEVELOPER_REGEX = "^[A-Za-z0-9&.,'\\-\\s]{1,150}$";


    // Dux Manager Regex
    public static final String GROUP_ID_REGEX = "[ \\wÀ-ú\\.:,;\\-\\[\\]()]{1,50}";
    public static final String ARTIFACT_ID_REGEX = "[ \\wÀ-ú\\.:,;\\-\\[\\]()]{1,50}";
    public static final String TYPE_REGEX = "[ \\wÀ-ú\\.:,;\\-\\[\\]()]{1,30}";
    public static final String ID_LIST_REGEX =
            "^([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})(," +
                    "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}){0," +
                    (MAX_LIMIT-1) + "}$";


    // Fields validation
    public static final String ID_INVALID_MSG = "'id' must match: " + ID_REGEX + ".";
    public static final String TITLE_INVALID_MSG = "'title' must match: " + TITLE_REGEX + ".";
    public static final String PLATFORM_INVALID_MSG = "'platform' must match: " + PLATFORM_REGEX + ".";
    public static final String GENRE_INVALID_MSG = "'genre' must match: " + GENRE_REGEX + ".";
    public static final String DEVELOPER_INVALID_MSG = "'developer' must match: " + DEVELOPER_REGEX + ".";
    public static final String OFFSET_INVALID_MSG = "'offset' must be positive.";
    public static final String LIMIT_INVALID_MSG = "'limit' must be in the range [" + MIN_LIMIT + ", " + MAX_LIMIT + "]";


    // Dux Manager fields validation
    public static final String GROUP_ID_INVALID_MSG = "'groupId' must match: " + GROUP_ID_REGEX + ".";
    public static final String ARTIFACT_ID_INVALID_MSG = "'artifactId' must match: " + ARTIFACT_ID_REGEX + ".";
    public static final String TYPE_INVALID_MSG = "'type' must match: " + TYPE_REGEX + ".";
    public static final String DIGITAL_USER_ID_INVALID_MSG = "'digitalUserId' must match: " + ID_REGEX + ".";
    public static final String IDS_INVALID_MSG = "'ids' must match: " + ID_LIST_REGEX + ".";
}
