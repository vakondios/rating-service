package gr.xe.rating.service.models.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ratings",
    indexes = {
            @Index(name = "ratings_entity_idx", columnList = "rated_entity", unique = false),
            @Index(name = "ratings_created_at_idx", columnList = "created_at", unique = false),
            @Index(name = "ratings_rater_idx", columnList = "rater", unique = false)
    }
)
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "given_rating", nullable = false)
    private double givenRating;

    @Column(name = "rated_entity", nullable = false)
    private String ratedEntity;

    @Column(name = "created_at", nullable = false)
    private long createdAt;

    @Column(name = "rater", nullable = true)
    private String rater;
}
