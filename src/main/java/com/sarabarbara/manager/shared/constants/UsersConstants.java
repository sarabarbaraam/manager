package com.sarabarbara.manager.shared.constants;


/**
 * Constants class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

public class UsersConstants {

    private UsersConstants() {
    }

    public static final String NAME_CHARACTERS_LIMIT = "The name must be between 3 and 45 characters";

    public static final String USERNAME_REGEX = "^\\w{3,20}$";

    public static final String USERNAME_CHARACTERS_LIMIT = "The name must be between 3 and 20 characters";

    public static final String USERNAME_PATTERN = "The user name must not have two consecutive underscores or periods, " +
            "ensure that the string does not end in a period or underscore.";

    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#._-])[A-Za-z\\d@$!%*?&#._-]{8,70}$\n";

    public static final String PASSWORD_CHARACTERS_LIMIT = "The password must be between 8 and 70 characters";

    public static final String PASSWORD_PATTERN = "The password must contain at least one uppercase letter, " +
            "one lowercase letter, one number, and one special character. Special characters allowed: !?/@#$%^&*()_+=-";

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static final String EMAIL_MUST_BE_VALID = "The email must be valid";
}
