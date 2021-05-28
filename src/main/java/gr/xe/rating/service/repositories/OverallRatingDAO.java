package gr.xe.rating.service.repositories;

import gr.xe.rating.service.mappers.OverallRatingMapper;
import gr.xe.rating.service.models.dto.OverallRatingDto;
import gr.xe.rating.service.properties.SqlQueryProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class OverallRatingDAO implements IOverallRatingDAO {

    private final SqlQueryProperty sqlQueryProperty;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<OverallRatingDto> rowMapper;

    @Autowired
    public OverallRatingDAO(SqlQueryProperty sqlQueryProperty,
                            NamedParameterJdbcTemplate namedParameterJdbcTemplate){

        this.sqlQueryProperty = sqlQueryProperty;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = new OverallRatingMapper();

        if (log.isDebugEnabled()) log.debug("Component Initialized.");
    }

    @Override
    public long removeRatesOver100Days(){
        String sql = sqlQueryProperty.getDelete();
        Map<String, Object> parameters = new HashMap<>();
        return namedParameterJdbcTemplate.update(sql,parameters);
    }

    @Override
    public OverallRatingDto findOverallRating(String rated_entity) throws DataAccessException {
        String sql = sqlQueryProperty.getOverall();
        Map<String, Object> parameters = Collections.singletonMap("rated_entity", rated_entity);
        return namedParameterJdbcTemplate.query(sql, parameters, rowMapper).get(0);
    }
}
