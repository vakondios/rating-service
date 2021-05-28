package gr.xe.rating.service.services;

import gr.xe.rating.service.caches.RatingResultsCache;
import gr.xe.rating.service.configurations.Constant;
import gr.xe.rating.service.exceptions.FieldFormatValidationException;
import gr.xe.rating.service.models.dto.OverallRatingDto;
import gr.xe.rating.service.models.dto.RatingDto;
import gr.xe.rating.service.models.dto.ResponseInfo;
import gr.xe.rating.service.models.dto.ValidateDbFieldInfo;
import gr.xe.rating.service.models.entity.RatingEntity;
import gr.xe.rating.service.repositories.OverallRatingDAO;
import gr.xe.rating.service.repositories.RatingsRepository;
import gr.xe.rating.service.utils.CommonLib;
import gr.xe.rating.service.utils.DbUtils;
import gr.xe.rating.service.utils.RatingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for Rating
 *
 * @author avacondios-xps
 * @since v.0.0.0
 */
@Slf4j
@Service
public class RatingServiceImpl implements RatingService{

    private final RatingsRepository ratingsRepository;
    private final OverallRatingDAO overallRatingDAO;
    private final RatingResultsCache ratingResultsCache;

    @Autowired
    public RatingServiceImpl(RatingsRepository ratingsRepository,
                             OverallRatingDAO overallRatingDAO,
                             RatingResultsCache ratingResultsCache){

        this.ratingsRepository = ratingsRepository;
        this.overallRatingDAO = overallRatingDAO;
        this.ratingResultsCache = ratingResultsCache;

        if (log.isDebugEnabled()) log.debug("Service Initialized");
    }

    public Object removeRatesOver100Days() {
        try{
            long num = overallRatingDAO.removeRatesOver100Days();
            if (log.isInfoEnabled()) log.info("System removed ratings older than 100 days from the database. Number of records: {}",num);
        } catch (Exception exception) {
            log.error(exception.getMessage(),exception);
        }
        return null;
    }

    @Override
    public Object overallRating(ResponseInfo responseInfo, String incRatedEntity) {
        try {
            boolean NotExists = false;
            OverallRatingDto overallRatingDto = ratingResultsCache.getRateResults(incRatedEntity.trim());

            if (overallRatingDto == null) {
                if (log.isInfoEnabled()) log.info("Retrieve data from Database");
                overallRatingDto = overallRatingDAO.findOverallRating(incRatedEntity.trim());
                NotExists =true;
            } else {
                if (log.isInfoEnabled()) log.info("Retrieved data from cache");
            }

            if (NotExists) {
                ratingResultsCache.newRateResults(overallRatingDto);
            }

            responseInfo.setStatus(200);
            responseInfo.setData(overallRatingDto);

        } catch ( DataAccessException | IndexOutOfBoundsException ex) {
            responseInfo.setError(ex.getMessage());
            responseInfo.setMessage(Constant.RATEDENTITYNOTFOUND_ERROR);
            responseInfo.setStatus(404);
            if (log.isWarnEnabled()) log.warn("Overall Rating not found in database for id " + incRatedEntity + ". Reason: " + ex.getMessage());
        } catch (Exception exception) {
            CatchException(exception, HttpStatus.INTERNAL_SERVER_ERROR.value(), responseInfo);
        }

        return null;
    }

    @Override
    public Object createRating(ResponseInfo responseInfo, RatingDto ratingDto) {

        try{
            // Input Validation -Returns error type "Invalid_" + key + "_format"
            Map<String, ValidateDbFieldInfo> check = new HashMap<>();
            check.put("ratedEntity", new ValidateDbFieldInfo(true, 255, false, false));
            check.put("rater", new ValidateDbFieldInfo(false, 255, false, false));

            CommonLib.validationObjectField(ratingDto, check);
            RatingUtils.validateRating(ratingDto.getGivenRatingValue());

            //Store to db
            RatingEntity ratingEntity = new RatingEntity();
            DbUtils.genericMapper(ratingDto,ratingEntity);
            ratingEntity.setCreatedAt(System.currentTimeMillis());
            ratingEntity.setGivenRating(Double.parseDouble(ratingDto.getGivenRatingValue()));
            ratingsRepository.save(ratingEntity);
            DbUtils.genericMapper(ratingEntity, ratingDto);

            responseInfo.setStatus(HttpStatus.CREATED.value());
            responseInfo.setData(ratingEntity);

        } catch (NoSuchFieldException | IllegalAccessException | FieldFormatValidationException | DataIntegrityViolationException ex) {
            responseInfo.setError(ex.getMessage());
            responseInfo.setMessage(Constant.CREATERATING_ERROR);
            responseInfo.setStatus(HttpStatus.BAD_REQUEST.value());
            if (log.isWarnEnabled()) log.warn(ex.getMessage());
        } catch (Exception exception) {
            CatchException(exception, HttpStatus.INTERNAL_SERVER_ERROR.value(), responseInfo);
        }

        return null;
    }

    private void CatchException( Exception ex, int httpStatus, ResponseInfo responseInfo ) {
        responseInfo.setErrors(CommonLib.getStackTraceErrors("gr.xe.rating", ex.getStackTrace()));
        responseInfo.setError(ex.getMessage());
        responseInfo.setMessage(Constant.UNEXPECTED_ERROR);
        responseInfo.setStatus(httpStatus);
        log.error(ex.getMessage());
    }
}
