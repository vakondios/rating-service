package gr.xe.rating.service.repositories;

import gr.xe.rating.service.models.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface RatingsRepository extends JpaRepository<RatingEntity, Integer> {
}
