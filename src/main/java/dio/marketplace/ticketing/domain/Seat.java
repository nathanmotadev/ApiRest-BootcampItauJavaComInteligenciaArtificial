package dio.marketplace.ticketing.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Seat {
    private UUID id;
    private SeatId correlationId;

    public Seat(String correlationID) {
        this.id = UUID.randomUUID();
        this.correlationId = new SeatId(correlationID);
    }

}
