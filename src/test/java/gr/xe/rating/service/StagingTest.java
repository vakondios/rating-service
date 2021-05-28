package gr.xe.rating.service;

import gr.xe.rating.service.models.dto.RatingDto;
import gr.xe.rating.service.models.dto.ResponseInfo;
import gr.xe.rating.service.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.UUID;

@ActiveProfiles("staging")
@SpringBootTest
public class StagingTest {
    private final RatingService ratingService;
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public StagingTest(RatingService ratingService,EntityManagerFactory entityManagerFactory){
        this.ratingService = ratingService;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Test
    void createCreate() {
        cleanup();
        createData();
    }

    void cleanup() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("truncate table ratings").executeUpdate();
        em.getTransaction().commit();
    }

    void createData() {

        ResponseInfo responseInfo = new ResponseInfo("test","test","test");
        double value = 0.0;
        String entity = UUID.randomUUID().toString();
        String rater = UUID.randomUUID().toString();

        for (int i=1;i<1001;i++) {
            RatingDto ratingDto = new RatingDto();
            entity = ratedEntityValue(ratingDto, entity, i);
            value = ratingValue(ratingDto, value);
            rater = raterValue(ratingDto, rater, i);

            ratingService.createRating(responseInfo,ratingDto);

        }

    }

    String  raterValue(RatingDto ratingDto, String rater, int i){
        if ((i % 100) == 0) { rater = UUID.randomUUID().toString(); }

        if ((i % 2) == 0) {
            ratingDto.setRater(rater);
        } else {
            ratingDto.setRater("");
        }

        return rater;
    }

    String  ratedEntityValue(RatingDto ratingDto, String entity, int i){
        if ((i % 100) == 0) { entity = UUID.randomUUID().toString(); }
        ratingDto.setRatedEntity(entity);

        return entity;
    }

    double ratingValue(RatingDto ratingDto, double value){
        value = value + 0.5;
        if (value>5) value=0;
        ratingDto.setGivenRatingValue(String.valueOf(value));

        return value;
    }

}
