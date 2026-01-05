package com.sarabarbara.manager.infrastructure.external.zerobounce;


import com.sarabarbara.manager.shared.EmailValidationResponse;
import com.sarabarbara.manager.users.exceptions.UserValidateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.sarabarbara.manager.infrastructure.external.zerobounce.ZeroBounceConstants.ZERO_BOUNCE_URL;

/**
 * ZeroBounceAPI class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 01/01/2026
 */

@Slf4j
@Component
@AllArgsConstructor
public class ZeroBounceClient {

    private final ZeroBounceConfig zeroBounceConfig;
    private final RestTemplate restTemplate;

    public void emailIsReal(String email) {

        log.info("Checking if the email {} is real, disposable or spam trap...", email);

        String apiKey = zeroBounceConfig.getApiKey();
        String url = ZERO_BOUNCE_URL + "?email=" + email + "&api_key=" + apiKey;

        EmailValidationResponse response = restTemplate.getForObject(url, EmailValidationResponse.class);

        if (response != null) {

            if ("valid".equals(response.status())
                    && !"disposable".equals(response.subStatus())
                    && !"spam trap".equals(response.subStatus())) {

                log.info("It's a real email. Proceeding with registration...");

            } else {

                throw new UserValidateException("The email " + email + " is not acceptable: " + response.subStatus());
            }

        }

        throw new UserValidateException("Email validation service returned a null response");
    }

}
