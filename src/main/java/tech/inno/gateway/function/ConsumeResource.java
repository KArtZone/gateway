package tech.inno.gateway.function;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import tech.inno.gateway.config.ResourceCache;
import tech.inno.gateway.domain.dto.ResourceDto;

import java.util.UUID;
import java.util.function.Consumer;

import static tech.inno.gateway.config.Constants.REQUEST_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumeResource implements Consumer<Message<ResourceDto>> {

    private final ResourceCache cache;

    @Override
    public void accept(final Message<ResourceDto> message) {
        final String requestId = message.getHeaders().get(REQUEST_ID, String.class);
        log.info("RequestId: {}", requestId);
        final ResourceDto resourceDto = message.getPayload();
        log.info("Consumed resource: {}", resourceDto);
        cache.put(UUID.fromString(requestId), resourceDto);
    }
}
