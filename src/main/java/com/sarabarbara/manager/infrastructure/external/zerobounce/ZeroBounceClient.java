package com.sarabarbara.manager.infrastructure.external.zerobounce;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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

    private final WebClient zeroBounceWebClient;
    private final ZeroBounceConfig zeroBounceConfig;


    public EmailValidationResult emailIsReal(String email) {

        log.info("Checking if the email {} is real, disposable or spam trap...", email);

        EmailValidationResponse response = zeroBounceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.zerobounce.net")
                        .path("/v2/validate")
                        .queryParam("email", email)
                        .queryParam("api_key", zeroBounceConfig.getApiKey())
                        .build())
                .retrieve()
                .bodyToMono(EmailValidationResponse.class)
                .block();

        if (response == null) {
            return EmailValidationResult.unverified();
        }

        if ("valid".equalsIgnoreCase(response.status())) {
            return EmailValidationResult.success();
        }

        return EmailValidationResult.invalid(response.subStatus());

    }
}
