package cz.fel.cvut.omo.json.deserializer;

import com.google.gson.*;
import cz.fel.cvut.omo.activities.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ActivityDeserializer implements JsonDeserializer<List<Activity>> {

    @Override
    public List<Activity> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        List<Activity> activities = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            JsonElement typeElement = jsonObject.get("ObjectType");

            if (typeElement != null) {
                String type = typeElement.getAsString();

                switch (type) {
                    case "ApplianceActivity":
                        activities.add(i, context.deserialize(jsonObject, ApplianceActivity.class));
                        break;
                    case "VehicleActivity":
                        activities.add(i, context.deserialize(jsonObject, VehicleActivity.class));
                        break;
                    case "WaitingActivity":
                        activities.add(i, context.deserialize(jsonObject, WaitingActivity.class));
                        break;
                    case "Fix":
                        activities.add(i, context.deserialize(jsonObject, Fix.class));
                        break;

                    default:
                        throw new JsonParseException("Unknown type: " + type);
                }
            }
        }

        return activities;
    }
}
