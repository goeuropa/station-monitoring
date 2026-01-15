package pl.goeuropa.station.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.goeuropa.station.client.StopMonitoringClient;
import pl.goeuropa.station.dto.SiriDto;
import pl.goeuropa.station.repository.StationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StationService {

    private final StopMonitoringClient stopMonitoringClient;

    private final StationRepository repository = StationRepository.getInstance();

    @Value("${api.key}")
    private String key;


    public StationService(StopMonitoringClient stopMonitoringClient) {
        this.stopMonitoringClient = stopMonitoringClient;
    }

    public Map<String, SiriDto> getStationMonitoring(
            String obaKey,
            String unixTimestamp,
            String operatorRef,
            String stopId,
            String detailLevel,
            int minVisits) {

        if (obaKey.isBlank() || !obaKey.equals(key)) {
            log.debug("Unauthorized request with invalid key");
            throw new IllegalArgumentException("Unauthorized: The key is not valid. Check the key!");
        }

        return stopMonitoringClient.getStopMonitoringForStation(
                obaKey, unixTimestamp, operatorRef, stopId, detailLevel, minVisits
        );
    }


    public Map<String, List<String>> getStationIds(String obaKey) {
        Map<String, List<String>> response = new HashMap<>();
        if (!obaKey.equals(key) || obaKey.isBlank()) {
            response.put("Unauthorized", List.of("The key is not valid. Check the key!"));
            log.debug("The key is not valid. Check the key!");

            return response;
        }
        var ids = repository.getStopIds();
        if (ids == null || ids.isEmpty()) {
            log.debug("I/O error on GET stop IDs request: Check stations or the connection to OBA API");
            response.put("I/O error on GET stop IDs request", List.of("Check stations or the connection to OBA API"));
            return response;
        }
        return ids;
    }
}
