package io.github.francoiscampbell.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

/**
 * Created by T904544 on 14/07/2015.
 */
public class DateTimeConverter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
        final DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        return new JsonPrimitive(fmt.print(src));
    }

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final DateTimeFormatter fmt = ISODateTimeFormat.dateTimeParser();
        return fmt.parseDateTime(json.getAsString());
    }
}
