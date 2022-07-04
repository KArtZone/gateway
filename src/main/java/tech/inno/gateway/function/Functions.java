package tech.inno.gateway.function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.inno.gateway.domain.dto.OpenDto;

import java.util.UUID;
import java.util.function.Function;

@Configuration
public class Functions {

    @Bean
    public Function<UUID, OpenDto> getById() {
        return uuid -> {
            return new OpenDto(uuid);
        };
    }

}
