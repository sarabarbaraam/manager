package com.sarabarbara.manager.infrastructure.external.zerobounce;


import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ZeroBounceClient {

    private final ZeroBounceConfig zeroBounceConfig;
    private final RestTemplate restTemplate;

    // todo: mirar por qué me devuelve error 403 forbidden 1020
    public EmailValidationResult emailIsReal(String email) {

        log.info("Checking if the email {} is real, disposable or spam trap...", email);

        String apiKey = zeroBounceConfig.getApiKey();
        String url = ZERO_BOUNCE_URL + "?email=" + email + "&api_key=" + apiKey;

        try {
            EmailValidationResponse response =
                    restTemplate.getForObject(url, EmailValidationResponse.class);

            if (response == null) {
                log.warn("ZeroBounce returned null. Falling back to unverified registration.");
                return EmailValidationResult.unverified();
            }

            if ("valid".equals(response.status())
                    && !"disposable".equals(response.subStatus())
                    && !"spam trap".equals(response.subStatus())) {

                log.info("Email {} is valid.", email);
                return EmailValidationResult.success();
            }

            log.warn("Email {} is not acceptable: {}", email, response.subStatus());
            return EmailValidationResult.invalid(response.subStatus());

        } catch (Exception e) {
            log.error("ZeroBounce API failed: {}. Falling back to unverified registration.", e.getMessage());
            return EmailValidationResult.unverified();
        }
    }
}
