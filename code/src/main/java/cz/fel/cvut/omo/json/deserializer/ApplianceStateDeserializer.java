package cz.fel.cvut.omo.json.deserializer;

import com.google.gson.*;
import cz.fel.cvut.omo.appliances.states.*;

import java.lang.reflect.Type;

public class ApplianceStateDeserializer implements JsonDeserializer<ApplianceState> {
    @Override
    public ApplianceState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject != null) {
            String type = jsonObject.get("stateType").getAsString();

            return switch (type) {
                case "BrokenState" -> context.deserialize(jsonObject, BrokenState.class);
                case "IdleState" -> context.deserialize(jsonObject, IdleState.class);
                case "OffState" -> context.deserialize(jsonObject, OffState.class);
                case "OnState" -> context.deserialize(jsonObject, OnState.class);
                default -> throw new JsonParseException("Unknown type: " + type);
            };
        }

        return null;
    }
}
