package io.github.francoiscampbell.gson;

import com.google.gson.*;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.lang.reflect.Type;

/**
 * Created by T904544 on 17/07/2015.
 */
public class DurationConverter implements JsonSerializer<Duration>, JsonDeserializer<Duration> {
    @Override
    public JsonElement serialize(Duration src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(getPeriodFormatter().print(src.toPeriod()));
    }

    @Override
    public Duration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Period p = getPeriodFormatter().parsePeriod(json.getAsString());
        return p.toStandardDuration();
    }

    private PeriodFormatter getPeriodFormatter() {
        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();
        builder.appendPrefix("PT")
               .appendHours()
               .appendSuffix("H")
               .appendMinutes()
               .appendSuffix("M");
        return builder.toFormatter();
    }
}
