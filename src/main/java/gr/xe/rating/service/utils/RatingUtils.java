package gr.xe.rating.service.utils;

import gr.xe.rating.service.exceptions.FieldFormatValidationException;
import gr.xe.rating.service.models.dto.ValidateDbFieldInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RatingUtils {

    public static void validateRating(String givenRating) {
        if (givenRating == null)  throw new FieldFormatValidationException("givenRating");
        if (isNotDouble(givenRating)) throw new FieldFormatValidationException("givenRating");

        double value = Double.parseDouble(givenRating);

        if (!isOnRange(0,5,value)) throw new FieldFormatValidationException("givenRating");
        if (!hasStep5(value)) throw new FieldFormatValidationException("givenRating");
    }

    public static boolean isOnRange(int min, int max, double value){
        if (value > max) return false;
        if (value < min) return false;

        return true;
    }

    public static boolean hasStep5(double value) {
        return (value % 0.5) == 0;
    }

    static boolean isNotDouble(String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
