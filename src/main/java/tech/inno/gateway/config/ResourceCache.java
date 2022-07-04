package tech.inno.gateway.config;

import org.springframework.stereotype.Component;
import tech.inno.gateway.domain.dto.ResourceDto;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResourceCache extends ConcurrentHashMap<UUID, ResourceDto> {
}
