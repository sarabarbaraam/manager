package com.sarabarbara.manager.apis;

import com.sarabarbara.manager.config.ZeroBounceConfig;
import com.sarabarbara.manager.dto.EmailValidationResponse;
import com.sarabarbara.manager.exceptions.UserValidateException;
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

    private ZeroBounceConfig zeroBounceConfig;
    private final RestTemplate restTemplate;

    public void emailIsReal(String email) {

        logger.info("checking if the email {} is real, disposable or spamtrap...", email);

        String apiKey = zeroBounceConfig.getApiKey();
        String url = ZERO_BOUNCE_URL + "?email=" + email + "&api_key=" + apiKey;

        EmailValidationResponse response = restTemplate.getForObject(url, EmailValidationResponse.class);

        if (response != null) {

            if ("valid".equals(response.getStatus())
                    && !"disposable".equals(response.getSubStatus())
                    && !"spamtrap".equals(response.getSubStatus())) {

                logger.info("It's a real email. Proceeding with registration...");

            } else {

                throw new UserValidateException("The email " + email + " is not acceptable: " + response.getSubStatus());
            }
        }
    }

}
