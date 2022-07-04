package tech.inno.gateway.domain.dto;

import lombok.Data;
import tech.inno.gateway.domain.enums.ResourceState;

import java.util.UUID;

@Data
public class ResourceDto {

    private UUID id;

    private String title;

    private String description;

    private ResourceState state;

    private String createdByName;

}
