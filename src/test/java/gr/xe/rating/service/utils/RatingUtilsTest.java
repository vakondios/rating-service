package gr.xe.rating.service.utils;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import javax.validation.constraints.AssertFalse;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
class RatingUtilsTest {

    @Test
    void isOnRange() {
        assertTrue(RatingUtils.isOnRange(0,5, 4.8));
        assertTrue(RatingUtils.isOnRange(0,5, 0.00));
        assertTrue(RatingUtils.isOnRange(0,5, 5.00));
        assertTrue(RatingUtils.isOnRange(0,5, 5));
        assertTrue(RatingUtils.isOnRange(0,5, 0));
        assertFalse(RatingUtils.isOnRange(0,5, 5.01));
        assertFalse(RatingUtils.isOnRange(0,5, -0.01));
        assertFalse(RatingUtils.isOnRange(0,5, 10.50));

    }

    @Test
    void hasStep5() {
        assertTrue(RatingUtils.hasStep5(5));
        assertTrue(RatingUtils.hasStep5(0));
        assertFalse(RatingUtils.hasStep5(0.3));
        assertFalse(RatingUtils.hasStep5(3.3));
        assertFalse(RatingUtils.hasStep5(-3.3));
    }
}