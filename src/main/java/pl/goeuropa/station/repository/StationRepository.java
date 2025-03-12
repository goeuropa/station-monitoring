package pl.goeuropa.station.repository;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class StationRepository {

    private static final StationRepository singleton = new StationRepository();

    private Map<String, List<String>> stopIds = new ConcurrentHashMap<>();

    private StationRepository() {
    }

    public static StationRepository getInstance() {
        return singleton;
    }
}
