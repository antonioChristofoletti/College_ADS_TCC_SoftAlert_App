package softalertv3.softalertv3.softalert.Retrofit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class JSONManager {

    public static String convertJSON(Object o) {
        ObjectMapper om = new ObjectMapper();

        try {
            return om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static ErrorMessageAPI convertJsonToErrorMessageAPI(String json) {
        try {
            ObjectMapper om = new ObjectMapper();

            return om.readValue(json, ErrorMessageAPI.class);
        } catch (IOException e) {
            return null;
        }
    }
}
