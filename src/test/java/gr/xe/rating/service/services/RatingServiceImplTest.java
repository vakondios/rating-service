package gr.xe.rating.service.services;

import gr.xe.rating.service.models.dto.OverallRatingDto;
import gr.xe.rating.service.models.dto.RatingDto;
import gr.xe.rating.service.models.dto.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
class RatingServiceImplTest {

    private final RatingService ratingService;
    private final ResponseInfo responseInfo = new ResponseInfo("test","test","test");

    @Autowired
    public RatingServiceImplTest(RatingService ratingService){
        this.ratingService = ratingService;
    }


    @Test
	void createRate() {
        RatingDto ratingDto = new RatingDto();
        //ratingDto.setRater("");
        ratingDto.setRatedEntity("36f553ed-720b-4fb5-85d9-5349018c899e");
        ratingDto.setGivenRatingValue("5");
        ratingService.createRating(responseInfo,ratingDto);

        assertEquals(201,responseInfo.getStatus());
    }

    @Test
    void overallRating() {
        String rated_entity = "36f553ed-720b-4fb5-85d9-5349018c899e";
        ratingService.overallRating(responseInfo, rated_entity);
        OverallRatingDto dto = (OverallRatingDto) responseInfo.getData();
        log.info("Rating {}", dto.getOverallResult());
        assertEquals(rated_entity, dto.getRatedEntity());
        assertEquals(200,responseInfo.getStatus());
    }
}