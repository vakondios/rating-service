package gr.xe.rating.service.models.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OverallRatingDto extends AbstractDto{
    private double overallResult;
    private String ratedEntity;
}
