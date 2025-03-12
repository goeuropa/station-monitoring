package pl.goeuropa.station.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${api.base-url}")
    private String basePath;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(basePath)
                .defaultHeaders(
                        httpHeaders -> {
                            httpHeaders.set(HttpHeaders.CONTENT_TYPE,
                                    MediaType.APPLICATION_JSON_VALUE
                            );
                        })
                .build();
    }
}
