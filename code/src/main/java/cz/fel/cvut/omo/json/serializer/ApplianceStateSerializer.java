package cz.fel.cvut.omo.json.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import cz.fel.cvut.omo.appliances.states.ApplianceState;

import java.lang.reflect.Type;

public class ApplianceStateSerializer implements JsonSerializer<ApplianceState> {
    @Override
    public JsonElement serialize(ApplianceState applianceState, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("stateType", applianceState.getClass().getSimpleName());
        return jsonObject;
    }
}
