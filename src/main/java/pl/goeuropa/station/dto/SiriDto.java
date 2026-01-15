package pl.goeuropa.station.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiriDto {

    @JsonProperty("Siri")
    private Siri siri;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Siri {

        @JsonProperty("ServiceDelivery")
        private ServiceDelivery serviceDelivery;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ServiceDelivery {

        @JsonProperty("ResponseTimestamp")
        private Date responseTimestamp;

        @JsonProperty("StopMonitoringDelivery")
        private List<StopMonitoringDelivery> stopMonitoringDelivery;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StopMonitoringDelivery {

        @JsonProperty("MonitoredStopVisit")
        private List<MonitoredStopVisit> monitoredStopVisit;

        @JsonProperty("ResponseTimestamp")
        private Date responseTimestamp;

        @JsonProperty("ValidUntil")
        private Date validUntil;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MonitoredStopVisit {

        @JsonProperty("RecordedAtTime")
        private Date recordedAtTime;

        @JsonProperty("MonitoredVehicleJourney")
        private MonitoredVehicleJourney monitoredVehicleJourney;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MonitoredVehicleJourney {

        @JsonProperty("LineRef")
        private String lineRef;

        @JsonProperty("DirectionRef")
        private String directionRef;

        @JsonProperty("JourneyPatternRef")
        private String journeyPatternRef;

        @JsonProperty("VehicleMode")
        private List<String> vehicleMode;

        @JsonProperty("PublishedLineName")
        private String publishedLineName;

        @JsonProperty("OperatorRef")
        private String operatorRef;

        @JsonProperty("OriginRef")
        private String originRef;

        @JsonProperty("DestinationRef")
        private String destinationRef;

        @JsonProperty("DestinationName")
        private String destinationName;

        @JsonProperty("Monitored")
        private boolean monitored;

        @JsonProperty("VehicleLocation")
        private VehicleLocation vehicleLocation;

        @JsonProperty("Bearing")
        private double bearing;

        @JsonProperty("ProgressRate")
        private String progressRate;

        @JsonProperty("BlockRef")
        private String blockRef;

        @JsonProperty("VehicleRef")
        private String vehicleRef;

        @JsonProperty("FramedVehicleJourneyRef")
        private FramedVehicleJourneyRef framedVehicleJourneyRef;

        @JsonProperty("MonitoredCall")
        private MonitoredCall monitoredCall;

        @JsonProperty("OnwardCalls")
        private OnwardCalls onwardCalls;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FramedVehicleJourneyRef {

        @JsonProperty("DataFrameRef")
        private LocalDate dataFrameRef;

        @JsonProperty("DatedVehicleJourneyRef")
        private String datedVehicleJourneyRef;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MonitoredCall {

        @JsonProperty("StopPointRef")
        private String stopPointRef;

        @JsonProperty("AimedArrivalTime")
        private Date aimedArrivalTime;

        @JsonProperty("ExpectedArrivalTime")
        private Date expectedArrivalTime;

        @JsonProperty("ExpectedDepartureTime")
        private Date expectedDepartureTime;

        @JsonProperty("VisitNumber")
        private long visitNumber;

        @JsonProperty("StopPointName")
        private String stopPointName;

        @JsonProperty("Extensions")
        private Extensions extensions;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Extensions {

        @JsonProperty("Distances")
        private Distances distances;

        @JsonProperty("Deviation")
        private String deviation;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Distances {

        @JsonProperty("PresentableDistance")
        private String presentableDistance;

        @JsonProperty("DistanceFromCall")
        private double distanceFromCall;

        @JsonProperty("StopsFromCall")
        private long stopsFromCall;

        @JsonProperty("CallDistanceAlongRoute")
        private double callDistanceAlongRoute;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VehicleLocation {

        @JsonProperty("Longitude")
        private double longitude;

        @JsonProperty("Latitude")
        private double latitude;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OnwardCalls {
    }
}
