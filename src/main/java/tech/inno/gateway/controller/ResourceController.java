package tech.inno.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import tech.inno.gateway.config.ResourceCache;
import tech.inno.gateway.domain.dto.OpenDto;
import tech.inno.gateway.domain.dto.ResourceDto;

import java.util.UUID;

import static java.lang.System.currentTimeMillis;
import static java.util.UUID.randomUUID;
import static tech.inno.gateway.config.Constants.REQUEST_ID;
import static tech.inno.gateway.config.Constants.USER_ID;

@Slf4j
@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {

    private final StreamBridge bridge;

    private final ResourceCache cache;

    @GetMapping("/{id}/{userId}")
    public ResourceDto getById(
            @PathVariable UUID id,
            @PathVariable UUID userId
    ) {
        log.info("Requesting for resource with id: {} by user with id: {}", id, userId);
        final long before = currentTimeMillis();
        final UUID requestId = randomUUID();
        final OpenDto openDto = new OpenDto(id);
        cache.put(requestId, new ResourceDto());
        final Message<OpenDto> message = MessageBuilder.withPayload(openDto)
                .setHeader(USER_ID, userId)
                .setHeader(REQUEST_ID, requestId)
                .build();
        bridge.send("openResource-out-0", message);
        long after = waitForResult(requestId);
        log.info("{}ms", after - before);
        return cache.get(requestId);
    }

    @PostMapping("/{userId}")
    public ResourceDto save(
            @RequestBody ResourceDto resourceDto,
            @PathVariable UUID userId
    ) {
        log.info("Requesting for save resource: {} by user with id: {}", resourceDto, userId);
        final long before = currentTimeMillis();
        final UUID requestId = randomUUID();
        cache.put(requestId, new ResourceDto());
        final Message<ResourceDto> message = MessageBuilder.withPayload(resourceDto)
                .setHeader(USER_ID, userId)
                .setHeader(REQUEST_ID, requestId)
                .build();
        bridge.send("saveResource-out-0", message);
        long after = waitForResult(requestId);
        log.info("{}ms", after - before);
        return cache.get(requestId);
    }

    @PostMapping("/register/{userId}")
    public ResourceDto register(
            @RequestBody ResourceDto resourceDto,
            @PathVariable UUID userId
    ) {
        log.info("Requesting for registration resource: {} by user with id: {}", resourceDto, userId);
        final long before = currentTimeMillis();
        final UUID requestId = randomUUID();
        cache.put(requestId, new ResourceDto());
        final Message<ResourceDto> message = MessageBuilder.withPayload(resourceDto)
                .setHeader(USER_ID, userId)
                .setHeader(REQUEST_ID, requestId)
                .build();
        bridge.send("registerResource-out-0", message);
        long after = waitForResult(requestId);
        log.info("{}ms", after - before);
        return cache.get(requestId);
    }

    private long waitForResult(UUID requestId) {
        UUID fromCache = cache.get(requestId).getId();
        while (fromCache == null) {
            fromCache = cache.get(requestId).getId();
        }
        return currentTimeMillis();
    }
}
