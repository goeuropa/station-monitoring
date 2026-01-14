package pl.goeuropa.station.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pl.goeuropa.station.repository.StationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class StopMonitoringClient {

    private final RestClient restClient;

    private final StationRepository repository = StationRepository.getInstance();

    public StopMonitoringClient(RestClient restClient) {
        this.restClient = restClient;
    }

    private static Map<String, String> getResponseMessage() {
        Map<String, String> response = new HashMap<>();
        log.debug("I/O error on GET stop IDs request: Check stations or the connection to OBA API");
        response.put("I/O error on GET stop IDs request", "Check stations or the connection to OBA API");
        return response;
    }

    public Map<String, String> getStopMonitoringForStation(
            String key,
            String unixTimestamp,
            String operatorRef,
            String stopId,
            String detailLevel,
            int minVisits) {

        long startTimer = System.currentTimeMillis();

        List<String> ids = getValidIds(stopId);
        if (ids == null || ids.isEmpty()) { return getResponseMessage(); }

        Map<String, String> station = new ConcurrentHashMap<>();

        ids.parallelStream().forEach(id -> {
            try {
                Map<String, Object> response = restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/siri/stop-monitoring.json")
                                .queryParam("key", key)
                                .queryParam("_", unixTimestamp)
                                .queryParam("OperatorRef", operatorRef)
                                .queryParam("MonitoringRef", id)
                                .queryParam("StopMonitoringDetailLevel", detailLevel)
                                .queryParam("MinimumStopVisitsPerLine", minVisits)
                                .queryParam("type", "json")
                                .build())
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::is3xxRedirection,
                                (req, res) -> {
                                    log.error("302 Location = {}", res.getHeaders().getLocation());
                                    throw new IllegalStateException("Redirect blocked");
                                }
                        )
                        .body(new ParameterizedTypeReference<>() {});

                if (response != null && response.containsKey("Siri")) {
                    station.put(id.split("-")[1], response.get("Siri").toString());
                }
            } catch (Exception ex) {
                log.warn("Failed fetching monitoring for id {}: {}", id, ex.getMessage());
            }
        });

        long elapsed = (System.currentTimeMillis() - startTimer) / 1000;
        log.debug(
                "Get stop monitoring for station {} with {} platforms. Took {} sec",
                stopId.split("-")[0],
                ids.size(),
                elapsed
        );

        return station;
    }

    private List<String> getValidIds(String stopId) {
        if (stopId != null && stopId.contains("-")) {
            var stationId = stopId.split("-")[0];
            return repository.getStopIds().get(stationId);
        } else
            return repository.getStopIds().get(stopId);
    }
}

