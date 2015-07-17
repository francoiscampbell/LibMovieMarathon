package io.github.francoiscampbell.gson;

import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

/**
 * Created by T904544 on 17/07/2015.
 */
public class DurationConverter implements JsonSerializer<Duration>, JsonDeserializer<Duration> {
    @Override
    public JsonElement serialize(Duration src, Type typeOfSrc, JsonSerializationContext context) {
        final DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        return new JsonPrimitive(fmt.print(src));
    }

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final DateTimeFormatter fmt = ISODateTimeFormat.dateTimeParser();
        return fmt.parseDateTime(json.getAsString());
    }
}
