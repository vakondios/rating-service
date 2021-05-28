package gr.xe.rating.service.repositories;

import gr.xe.rating.service.models.dto.OverallRatingDto;
import java.util.Optional;

public interface IOverallRatingDAO {
    OverallRatingDto findOverallRating(String rated_entity);
    long removeRatesOver100Days();
}
