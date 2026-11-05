package dio.marketplace.catalog.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
public record EventMetadata(String eventDescription, Map<String, Object> technicalRequirements,
                            Map<Sector, List<Seat>> seats) {
}
