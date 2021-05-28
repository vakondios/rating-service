package gr.xe.rating.service.services;

import gr.xe.rating.service.models.dto.RatingDto;
import gr.xe.rating.service.models.dto.ResponseInfo;

public interface RatingService {
    Object createRating(ResponseInfo responseInfo, RatingDto ratingDto);
    Object overallRating(ResponseInfo responseInfo, String incRatedEntity);
    Object removeRatesOver100Days();
}
