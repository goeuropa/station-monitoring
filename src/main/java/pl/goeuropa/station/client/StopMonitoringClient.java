package pl.goeuropa.station.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pl.goeuropa.station.repository.StationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class StopMonitoringClient {

    private final RestClient restClient;

    private final StationRepository repository = StationRepository
            .getInstance();

    public StopMonitoringClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public Map<String, String> getStopMonitoringForStation(String uri, String stopId) {
        var startTimer = System.currentTimeMillis() / 1000;

        List<String> ids = getValidIds(stopId);
        if (ids == null || ids.isEmpty()) return getResponseMessage();

        Map<String, String> station = new HashMap<>(ids.size());
        for (String id : ids)
            try {
                var response = restClient.get()
                        .uri(uri, id)
                        .retrieve()
                        .body(Map.class);
                assert response != null;

                station.put(id.split("-")[1], response.get("Siri").toString());
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        var stopTimer = System.currentTimeMillis() / 1000;
        log.debug("Get stop monitoring for station {} which contains {} platforms. Took {} sec",
                stopId.split("-")[0],
                ids.size(),
                stopTimer - startTimer
        );
        return station;
    }

    private static Map<String, String> getResponseMessage() {
        Map<String, String> response = new HashMap<>();
        log.debug("I/O error on GET stop IDs request: Check stations or the connection to OBA API");
        response.put("I/O error on GET stop IDs request", "Check stations or the connection to OBA API");
        return response;
    }

    private List<String> getValidIds(String stopId) {
        if (stopId != null && stopId.contains("-")) {
            var stationId = stopId.split("-")[0];
            return repository.getStopIds().get(stationId);
        } else
            return repository.getStopIds().get(stopId);
    }
}

