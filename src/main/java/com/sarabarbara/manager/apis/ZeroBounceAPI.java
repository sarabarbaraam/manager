package com.sarabarbara.manager.apis;

import com.sarabarbara.manager.config.ZeroBounceConfig;
import com.sarabarbara.manager.responses.users.EmailValidationResponse;
import com.sarabarbara.manager.exceptions.users.UserValidateException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.sarabarbara.manager.constants.ZeroBounceConstants.ZERO_BOUNCE_URL;

/**
 * ZeroBounceAPI class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 03/11/2024
 */

@Component
@AllArgsConstructor
public class ZeroBounceAPI {

    private static final Logger logger = LoggerFactory.getLogger(ZeroBounceAPI.class);

    private final ZeroBounceConfig zeroBounceConfig;
    private final RestTemplate restTemplate;

    /**
     * Validate if the email is real and not a temporal or spam trap
     *
     * @param email the email
     */

    public void emailIsReal(String email) {

        logger.info("checking if the email {} is real, disposable or spam trap...", email);

        String apiKey = zeroBounceConfig.getApiKey();
        String url = ZERO_BOUNCE_URL + "?email=" + email + "&api_key=" + apiKey;

        EmailValidationResponse response = restTemplate.getForObject(url, EmailValidationResponse.class);

        if (response != null) {

            if ("valid".equals(response.getStatus())
                    && !"disposable".equals(response.getSubStatus())
                    && !"spam trap".equals(response.getSubStatus())) {

                logger.info("It's a real email. Proceeding with registration...");

            } else {

                throw new UserValidateException("The email " + email + " is not acceptable: " + response.getSubStatus());
            }
        } else {

            throw new UserValidateException("Email validation service returned a null response");
        }
    }

}
