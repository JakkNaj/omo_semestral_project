package cz.fel.cvut.omo.json.deserializer;

import com.google.gson.*;
import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.coffeeMachine.CoffeeMachineImpl;
import cz.fel.cvut.omo.appliances.fridge.FridgeImpl;
import cz.fel.cvut.omo.appliances.heater.HeaterImpl;
import cz.fel.cvut.omo.appliances.light.LightingImpl;
import cz.fel.cvut.omo.appliances.playstation.PlaystationImpl;
import cz.fel.cvut.omo.appliances.treadmill.TreadmillImpl;
import cz.fel.cvut.omo.appliances.tv.TelevisionImpl;
import cz.fel.cvut.omo.appliances.washingMachine.WashingMachineImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ApplianceListDeserializer implements JsonDeserializer<List<Appliance>> {
    @Override
    public List<Appliance> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        List<Appliance> appliances = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            JsonElement typeElement = jsonObject.get("ObjectType");

            if (typeElement != null) {
                String type = typeElement.getAsString();

                switch (type) {
                    case "WashingMachineImpl":
                        appliances.add(i, context.deserialize(jsonObject, WashingMachineImpl.class));
                        break;
                    case "TelevisionImpl":
                        appliances.add(i, context.deserialize(jsonObject, TelevisionImpl.class));
                        break;
                    case "TreadmillImpl":
                        appliances.add(i, context.deserialize(jsonObject, TreadmillImpl.class));
                        break;
                    case "PlaystationImpl":
                        appliances.add(i, context.deserialize(jsonObject, PlaystationImpl.class));
                        break;
                    case "LightingImpl":
                        appliances.add(i, context.deserialize(jsonObject, LightingImpl.class));
                        break;
                    case "HeaterImpl":
                        appliances.add(i, context.deserialize(jsonObject, HeaterImpl.class));
                        break;
                    case "FridgeImpl":
                        appliances.add(i, context.deserialize(jsonObject, FridgeImpl.class));
                        break;
                    case "CoffeeMachineImpl":
                        appliances.add(i, context.deserialize(jsonObject, CoffeeMachineImpl.class));
                        break;
                    default:
                        throw new JsonParseException("Unknown type: " + type);
                }
            }
        }

        return appliances;
    }
}
