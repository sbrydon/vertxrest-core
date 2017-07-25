package space.sausage.vertxrest.core.handler.mapping;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import space.sausage.vertxrest.core.annotation.MediaType;

@Singleton
public class RequestMapperFactory {
    private final BodyMapperFactory factory;

    @Inject
    public RequestMapperFactory(BodyMapperFactory factory) {
        this.factory = factory;
    }

    public RequestMapper create(MediaType readType, MediaType writeType) {
        BodyMapper readMapper = readType == null ? null : factory.create(readType);
        BodyMapper writeMapper = writeType == null ? null : factory.create(writeType);

        return new RequestMapper(readMapper, writeMapper);
    }
}
