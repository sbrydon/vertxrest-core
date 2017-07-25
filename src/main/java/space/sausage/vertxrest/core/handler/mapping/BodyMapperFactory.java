package space.sausage.vertxrest.core.handler.mapping;

import com.google.inject.Singleton;
import io.vertx.core.json.Json;
import space.sausage.vertxrest.core.annotation.MediaType;

/**
 * A factory for creating a {@link BodyMapper} for a {@link MediaType}
 */
@Singleton
class BodyMapperFactory {
    /**
     * @param mediaType the media type
     * @return the mapper
     * @throws IllegalArgumentException if a mapper can't be found for the media type
     */
    BodyMapper create(MediaType mediaType) {
        switch (mediaType) {
            case JSON:
                return new JsonBodyMapper(Json.mapper);
            case PLAIN_TEXT:
                return new TextBodyMapper();
            default:
                throw new IllegalArgumentException("No mapper found for media type");
        }
    }
}
