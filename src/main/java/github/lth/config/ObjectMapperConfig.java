package github.lth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfig {

    public static final String YML_MAPPER = "ymlMapper";

    @Bean
    @Primary
    public ObjectMapper jsonMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    @Bean(name = YML_MAPPER)
    public ObjectMapper ymlMapper() {
        var mapper = new ObjectMapper(new YAMLFactory());
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }


}
