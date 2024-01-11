package cz.fel.cvut.omo.json.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import cz.fel.cvut.omo.appliances.Appliance;

import java.lang.reflect.Type;
import java.util.List;

public class ApplianceListSerializer extends ListSerializer<List<Appliance>>{
}
