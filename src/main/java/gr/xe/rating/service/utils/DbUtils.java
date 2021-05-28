package gr.xe.rating.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.xe.rating.service.exceptions.GenericMappingException;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class DbUtils {

    public static void genericMapper(Object from, Object to) {
        try {
            Map<String,Object> fromProperties  = new ObjectMapper().convertValue(from, Map.class);

            for (Map.Entry<String, Object> property : fromProperties.entrySet()) {
                //Check if the field exists
                if (doesObjectContainField(to,property.getKey()) && property.getValue() != null) {
                    Field toField = to.getClass().getDeclaredField(property.getKey());
                    Field fromField = from.getClass().getDeclaredField(property.getKey());
                    toField.setAccessible(true);
                    fromField.setAccessible(true);
                    toField.set(to, fromField.get(from));
                }
            }
        } catch (Exception ex) {
            throw new GenericMappingException(from.getClass().getSimpleName(), to.getClass().getSimpleName());
        }
    }

    private static boolean doesObjectContainField(Object object, String fieldName) {
        Field[] fld = object.getClass().getDeclaredFields();
        return Arrays.stream(fld).anyMatch(f -> f.getName().equals(fieldName));
    }

}
