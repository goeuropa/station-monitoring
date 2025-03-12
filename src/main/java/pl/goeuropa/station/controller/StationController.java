package pl.goeuropa.station.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.goeuropa.station.service.StationService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/station-monitoring")
    @Operation(summary = "Return stop monitoring for specific station ID")
    public Map<String, String> getStopMonitoring(
            @RequestParam(name = "key") String key,
            @Parameter(description = "Unix timestamp")
            @RequestParam(name = "_", required = false) String unixTimestamp,
            @Parameter(description = "Agency ID")
            @RequestParam(name = "OperatorRef") String operatorRef,
            @Parameter(description = "Stop ID ‘1_339-1’ or Station ID ‘1_339’; both are valid and can be used to retrieve data for the entire station.")
            @RequestParam(name = "MonitoringRef") String monitoringRef,
            @RequestParam(name = "StopMonitoringDetailLevel", required = false) String stopMonitoringDetailLevel,
            @RequestParam(name = "MinimumStopVisitsPerLine") int minimumStopVisitsPerLine) {
        try {
            String uri = String.format("/siri/stop-monitoring?key=%s&_=%sOperatorRef=%s&MonitoringRef={stopId}&StopMonitoringDetailLevel=%s&MinimumStopVisitsPerLine=%d&type=json",
                    key, unixTimestamp, operatorRef, stopMonitoringDetailLevel, minimumStopVisitsPerLine);
            var response = stationService.getStationMonitoring(uri, key, monitoringRef);
            log.info("Get info of {} platforms", response.size());
            return response;
        } catch (Exception e) {
            log.warn("I/O error on GET request: " + e.getMessage());
            throw e;
        }
    }

    @GetMapping("/stations")
    @Operation(summary = "Return available stations with stop IDs")
    public Map<String, List<String>> getStationIds(@RequestParam(name = "key") String key
    ) {
        log.info("Get station IDs with stop IDs: {}", stationService.getStationIds(key));
        return stationService.getStationIds(key);
    }
}
