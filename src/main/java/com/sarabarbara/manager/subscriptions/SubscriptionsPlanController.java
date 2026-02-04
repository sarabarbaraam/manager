package com.sarabarbara.manager.subscriptions;


import com.sarabarbara.manager.shared.BaseResponse;
import com.sarabarbara.manager.subscriptions.dtos.CreateSubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.dtos.SubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.requestes.SubscriptionsPlanRequest;
import com.sarabarbara.manager.subscriptions.requestes.UpdateSubscriptionsPlanRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sarabarbara.manager.shared.constants.APIConstants.*;
import static com.sarabarbara.manager.shared.constants.SwaggerSubscriptionsPlanExamplesConstants.*;

/**
 * SubscriptionsPlanController class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 08/01/2026
 */

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/subscriptions-plans")
public class SubscriptionsPlanController {

    private final SubscriptionsPlanService subscriptionsPlanService;

    @Operation(
            summary = "Register a subscriptions plan",
            description = "Register a subscriptions plan for their name, price, duration and features. " +
                    "Only ADMIN users can access this endpoint.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS,
                                    summary = SUCCESS_SUMMARY,
                                    value = REGISTER_SUBSCRIPTION_PLAN_SUCCESSFUL_RESPONSE
                            ))),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = BAD_REQUEST,
                                    summary = BAD_REQUEST_SUMMARY,
                                    value = REGISTER_SUBSCRIPTION_PLAN_BAD_REQUEST_RESPONSE
                            ))),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = UNAUTHORIZED,
                                    summary = UNAUTHORIZED_SUMMARY,
                                    value = REGISTER_SUBSCRIPTION_PLAN_UNAUTHORIZED_RESPONSE
                            ))),
            @ApiResponse(responseCode = "403", description = FORBIDDEN,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = FORBIDDEN,
                                    summary = FORBIDDEN_SUMMARY,
                                    value = REGISTER_SUBSCRIPTION_PLAN_FORBIDDEN_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR,
                                    summary = INTERNAL_SERVER_ERROR_SUMMARY,
                                    value = REGISTER_SUBSCRIPTION_PLAN_INTERNAL_SERVER_ERROR_RESPONSE
                            )))
    })
    @PostMapping("/register")
    public BaseResponse<CreateSubscriptionsPlanDTO> registerSubscriptionPlan(@Valid @RequestBody SubscriptionsPlanRequest request) {

        log.info("SubscriptionsPlanController - registerSubscriptionPlan called");
        log.info("SubscriptionsPlanController - registerSubscriptionPlan finished with data: {}", request);
        return BaseResponse
                .<CreateSubscriptionsPlanDTO>builder()
                .success(true)
                .data(subscriptionsPlanService.createSubscriptionPlan(request))
                .message("Subscription plan created successfully")
                .build();
    }

    @Operation(summary = "Search all subscription plans",
            description = "Search all subscription plans with pagination support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_SUMMARY,
                                    summary = SUCCESS,
                                    value = SEARCH_SUBSCRIPTION_PLAN_SUCCESSFUL_RESPONSE
                            ))),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = BAD_REQUEST_SUMMARY,
                                    summary = BAD_REQUEST,
                                    value = SEARCH_SUBSCRIPTION_PLAN_BAD_REQUEST_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_SUMMARY,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = SEARCH_SUBSCRIPTION_PLAN_INTERNAL_SERVER_ERROR_RESPONSE
                            )))
    })
    @GetMapping
    public BaseResponse<List<SubscriptionsPlanDTO>> getSubscriptionsPlan(@RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {

        log.info("SubscriptionsPlanController - getSubscriptionPlan called");
        log.info("SubscriptionsPlanController - getSubscriptionPlan finished");
        return BaseResponse
                .<List<SubscriptionsPlanDTO>>builder()
                .success(true)
                .data(subscriptionsPlanService.getSubscriptionPlan(page - 1, size))
                .message("Subscriptions plans retrieved successfully")
                .build();

    }

    @Operation(summary = "Searches a subscription plan by id",
            description = "Searches an subscription plan by id. Supports pagination and partial matches.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_SUMMARY,
                                    summary = SUCCESS,
                                    value = SEARCH_SUBSCRIPTION_PLAN_SUCCESSFUL_RESPONSE
                            ))),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = BAD_REQUEST_SUMMARY,
                                    summary = BAD_REQUEST,
                                    value = SEARCH_SUBSCRIPTION_PLAN_BAD_REQUEST_RESPONSE
                            ))),
            @ApiResponse(responseCode = "404", description = NOT_FOUND,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = NOT_FOUND_SUMMARY,
                                    summary = NOT_FOUND,
                                    value = SEARCH_SUBSCRIPTION_PLAN_NOT_FOUND_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_SUMMARY,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = SEARCH_SUBSCRIPTION_PLAN_INTERNAL_SERVER_ERROR_RESPONSE
                            )))
    })
    @GetMapping("/search/{id}")
    public BaseResponse<List<SubscriptionsPlanDTO>> searchSubscriptionPlan(@PathVariable Long id,
                                                                           @RequestParam(defaultValue = "1") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {

        log.info("SubscriptionsPlanController - searchSubscriptionPlan called");
        log.info("SubscriptionsPlanController - searchSubscriptionPlan finished");
        return BaseResponse
                .<List<SubscriptionsPlanDTO>>builder()
                .success(true)
                .data(subscriptionsPlanService.getSubscriptionPlanById(id, page - 1, size))
                .message("Search completed successfully")
                .build();
    }

    @Operation(summary = "Updates a subscription plan by id",
            description = "Updates a subscription plan by the id with the new data provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_SUMMARY,
                                    summary = SUCCESS,
                                    value = UPDATE_SUBSCRIPTION_PLAN_SUCCESSFUL_RESPONSE
                            ))),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = BAD_REQUEST_SUMMARY,
                                    summary = BAD_REQUEST,
                                    value = UPDATE_SUBSCRIPTION_PLAN_BAD_REQUEST_RESPONSE
                            ))),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = UNAUTHORIZED,
                                    summary = UNAUTHORIZED_SUMMARY,
                                    value = REGISTER_SUBSCRIPTION_PLAN_UNAUTHORIZED_RESPONSE
                            ))),
            @ApiResponse(responseCode = "403", description = FORBIDDEN,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = FORBIDDEN,
                                    summary = FORBIDDEN_SUMMARY,
                                    value = REGISTER_SUBSCRIPTION_PLAN_FORBIDDEN_RESPONSE
                            ))),
            @ApiResponse(responseCode = "404", description = NOT_FOUND,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = NOT_FOUND_SUMMARY,
                                    summary = NOT_FOUND,
                                    value = UPDATE_SUBSCRIPTION_PLAN_NOT_FOUND_RESPONSE))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_SUMMARY,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = UPDATE_SUBSCRIPTION_PLAN_INTERNAL_SERVER_ERROR_RESPONSE
                            )))
    })
    @PatchMapping("/update/{id}")
    public BaseResponse<SubscriptionsPlanDTO> updateSubscriptionPlan(@PathVariable Long id,
                                                                     @Valid @RequestBody UpdateSubscriptionsPlanRequest request) {

        log.info("SubscriptionsPlanController - updateSubscriptionPlan called");
        log.info("SubscriptionsPlanController - updateSubscriptionPlan finished with data: {}", request);
        return BaseResponse
                .<SubscriptionsPlanDTO>builder()
                .success(true)
                .data(subscriptionsPlanService.updateSubscriptionPlan(id, request))
                .message("Subscription plan updated successfully")
                .build();
    }

    @Operation(summary = "Delete a subscription plan",
            description = "Delete a subscription plan by the id provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_SUMMARY,
                                    summary = SUCCESS,
                                    value = DELETE_SUBSCRIPTION_PLAN_SUCCESSFUL_RESPONSE
                            ))),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = UNAUTHORIZED,
                                    summary = UNAUTHORIZED_SUMMARY,
                                    value = REGISTER_SUBSCRIPTION_PLAN_UNAUTHORIZED_RESPONSE
                            ))),
            @ApiResponse(responseCode = "403", description = FORBIDDEN,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = FORBIDDEN,
                                    summary = FORBIDDEN_SUMMARY,
                                    value = REGISTER_SUBSCRIPTION_PLAN_FORBIDDEN_RESPONSE
                            ))),
            @ApiResponse(responseCode = "404", description = NOT_FOUND,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = NOT_FOUND_SUMMARY,
                                    summary = NOT_FOUND,
                                    value = DELETE_SUBSCRIPTION_PLAN_NOT_FOUND_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_SUMMARY,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = DELETE_SUBSCRIPTION_PLAN_INTERNAL_SERVER_ERROR_RESPONSE
                            )))
    })
    @DeleteMapping("/delete/{id}")
    public BaseResponse<String> deleteSubscriptionPlan(@PathVariable Long id) {

        log.info("SubscriptionsPlanController - deleteSubscriptionPlan called");
        subscriptionsPlanService.deleteSubscriptionPlan(id);

        log.info("SubscriptionsPlanController - deleteSubscriptionPlan finished");
        return BaseResponse
                .<String>builder()
                .success(true)
                .message("Subscription plan deleted successfully")
                .build();
    }

}
