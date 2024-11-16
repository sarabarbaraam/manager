package com.sarabarbara.manager.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * UsersUtils class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 18/10/2024
 */

@Component
public class UsersUtils {

    private UsersUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(UsersUtils.class);

    /**
     * Validates if the password's format is correct
     *
     * @param password password
     *
     * @return true or false
     */

    public static boolean isFormatPasswordCorrect(String password) {

        logger.info("isValidPassword started");
        final Pattern pattern = Pattern.compile(
                "^(?=.*?[A-Z].*?)(?=.*?[a-z].*?)(?=.*?\\d.*?)(?=.*?[!?/@#$%^&*()_+=-].*?)[A-Za-z\\d!?/@#$%^&*()" +
                        "_+=-]{8,70}$"
        );

        if (!pattern.matcher(password).matches()) {

            logger.info("Password doesn't match pattern");
            return false;
        }

        logger.info("The password's format is correct");
        logger.info("isValidPassword finished");
        return true;

    }

}
