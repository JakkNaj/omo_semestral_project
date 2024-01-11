package cz.fel.cvut.omo.json.deserializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import cz.fel.cvut.omo.vehicles.Bicycle;
import cz.fel.cvut.omo.vehicles.Car;
import cz.fel.cvut.omo.vehicles.Ski;
import cz.fel.cvut.omo.vehicles.Vehicle;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VehicleListDeserializer implements JsonDeserializer<List<Vehicle>> {
    @Override
    public List<Vehicle> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        List<Vehicle> vehicles = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            JsonElement typeElement = jsonObject.get("ObjectType");

            if (typeElement != null) {
                String type = typeElement.getAsString();

                switch (type) {
                    case "Ski":
                        vehicles.add(i, context.deserialize(jsonObject, Ski.class));
                        break;
                    case "Car":
                        vehicles.add(i, context.deserialize(jsonObject, Car.class));
                        break;
                    case "Bicycle":
                        vehicles.add(i, context.deserialize(jsonObject, Bicycle.class));
                        break;
                    default:
                        throw new JsonParseException("Unknown type: " + type);
                }
            }
        }

        return vehicles;
    }
}
