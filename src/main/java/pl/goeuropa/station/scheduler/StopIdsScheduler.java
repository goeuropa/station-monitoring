package pl.goeuropa.station.scheduler;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pl.goeuropa.station.service.StopScheduleService;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class StopIdsScheduler {

    private final RestClient restClient;
    private final StopScheduleService service;

    @Value("${api.key}")
    private String key;
    @Value("${api.agency}")
    private String agency;

    public StopIdsScheduler(RestClient restClient, StopScheduleService service) {
        this.restClient = restClient;
        this.service = service;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 4 * * *")
    public void getStopIds() {
        final String URI = String.format("/api/where/stop-ids-for-agency/%s.json?key=%s", agency, key);

        try {
            var response = restClient.get()
                    .uri(URI)
                    .retrieve()
                    .body(Map.class);
            assert response != null;

            Map<String, ?> data = (Map<String, ?>) response.get("data");
            service.save((List<String>) data.get("list"));
            log.info("Succeed fetched stop IDs from OBA API");
        } catch (Exception e) {
            log.error("Something went wrong while fetching data from remote OBA API: {}", e.getMessage());
        }
    }
}

