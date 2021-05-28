package gr.xe.rating.service.controllers;

import gr.xe.rating.service.models.dto.RatingDto;
import gr.xe.rating.service.models.dto.RequestHelperInfo;
import gr.xe.rating.service.models.dto.ResponseInfo;
import gr.xe.rating.service.properties.AppProperties;
import gr.xe.rating.service.services.RatingService;
import gr.xe.rating.service.utils.PrintUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class RatingsController {
    private final AppProperties appProperties;
    private final RatingService ratingService;

    @Autowired
    public RatingsController (AppProperties appProperties,
                              RatingService ratingService) {

        this.appProperties = appProperties;
        this.ratingService = ratingService;
        if (log.isDebugEnabled()) log.debug("Controller Initialized.");
    }

    @Operation(security = @SecurityRequirement(name = "controllerBasicAuth")) //Swagger Configuration
    @RequestMapping(path = "/ratings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseInfo> createRating (
            @RequestBody RatingDto ratingDto,
            @RequestAttribute("requestHelper") RequestHelperInfo requestHelperInfo) {

        PrintUtils.onController(requestHelperInfo);
        ResponseInfo responseInfo = new ResponseInfo(appProperties.getName(), appProperties.getVersion(), requestHelperInfo.getRequestURI());
        ratingService.createRating(responseInfo, ratingDto);
        return new ResponseEntity<>(responseInfo, HttpStatus.valueOf(responseInfo.getStatus()));
    }

    @Operation(security = @SecurityRequirement(name = "controllerBasicAuth")) //Swagger Configuration
    @RequestMapping(value = "/ratings/{rated_entity}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseInfo> getOverallRating(
            @PathVariable String rated_entity,
            @RequestAttribute("requestHelper") RequestHelperInfo requestHelperInfo) {

        PrintUtils.onController(requestHelperInfo);
        ResponseInfo responseInfo = new ResponseInfo(appProperties.getName(), appProperties.getVersion(), requestHelperInfo.getRequestURI());
        ratingService.overallRating(responseInfo,rated_entity);
        return new ResponseEntity<>(responseInfo, HttpStatus.valueOf(responseInfo.getStatus()));
    }

}
