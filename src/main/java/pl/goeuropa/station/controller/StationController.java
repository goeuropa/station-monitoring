package pl.goeuropa.station.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.goeuropa.station.service.StationService;

import java.util.List;
import java.util.Map;

@Slf4j
@SecurityRequirement(name = "basicAuth")
@RestController
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/station-monitoring")
    @Operation(summary = "Return stop monitoring for specific station ID")
    public Map<String, String> getStopMonitoring(
            @RequestParam String key,
            @RequestParam(name = "_", required = false, defaultValue = "") String unixTimestamp,
            @RequestParam String OperatorRef,
            @RequestParam String MonitoringRef,
            @RequestParam(defaultValue = "") String StopMonitoringDetailLevel,
            @RequestParam int MinimumStopVisitsPerLine) {

        return stationService.getStationMonitoring(
                key,
                unixTimestamp,
                OperatorRef,
                MonitoringRef,
                StopMonitoringDetailLevel,
                MinimumStopVisitsPerLine
        );
    }

    @GetMapping("/stations")
    @Operation(summary = "Return available stations with stop IDs")
    public Map<String, List<String>> getStationIds(@RequestParam(name = "key") String key
    ) {
        log.info("Get station IDs with stop IDs: {}", stationService.getStationIds(key));
        return stationService.getStationIds(key);
    }
}
