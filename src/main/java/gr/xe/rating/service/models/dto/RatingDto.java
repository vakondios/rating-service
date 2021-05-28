package gr.xe.rating.service.models.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private String givenRatingValue;
    private String ratedEntity;
    private String rater;
}
