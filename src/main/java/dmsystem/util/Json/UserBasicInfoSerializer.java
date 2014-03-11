package dmsystem.util.Json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dmsystem.entity.Document;
import dmsystem.entity.User;

import java.lang.reflect.Type;

/**
 * Created by justinyang on 14-3-11.
 */
public class UserBasicInfoSerializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", user.getUsername());
        jsonObject.addProperty("password", user.getPassword());
        jsonObject.addProperty("name", user.getName());
        jsonObject.addProperty("authority", user.getAuthority());

        return jsonObject;
    }
}
