package gr.xe.rating.service.caches;

import gr.xe.rating.service.models.dto.OverallRatingDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
class RatingResultsCacheTest {

    private RatingResultsCache ratingResultsCache;

    @Autowired
    public RatingResultsCacheTest(RatingResultsCache ratingResultsCache){
        this.ratingResultsCache = ratingResultsCache;
    }

    @Test
    void insert(){

        String id = "12345";
        OverallRatingDto overallRatingDto = new OverallRatingDto();
        overallRatingDto.setRatedEntity(id);
        overallRatingDto.setOverallResult(10);
        ratingResultsCache.newRateResults(overallRatingDto);

        OverallRatingDto getOverallRatingDTO = ratingResultsCache.getRateResults(id);

        assertEquals(getOverallRatingDTO.getOverallResult(), overallRatingDto.getOverallResult());
    }

    @Test
    void get(){
        String id = "12345";
        OverallRatingDto getOverallRatingDTO = ratingResultsCache.getRateResults(id);

        assertEquals(getOverallRatingDTO.getRatedEntity(), id);
    }



}