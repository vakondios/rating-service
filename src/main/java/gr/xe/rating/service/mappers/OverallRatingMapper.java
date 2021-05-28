package gr.xe.rating.service.mappers;

import gr.xe.rating.service.models.dto.OverallRatingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class OverallRatingMapper implements RowMapper<OverallRatingDto> {

    @Override
    public OverallRatingDto mapRow(ResultSet resultSet, int i) throws SQLException {
        OverallRatingDto overallRatingDto = new OverallRatingDto();

        overallRatingDto.setOverallResult(resultSet.getDouble("overall_result"));
        overallRatingDto.setRatedEntity(resultSet.getString("rated_entity"));

        return overallRatingDto;
    }
}
