package dio.marketplace.ticketing.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Customer {
    private UUID id;
    private CustomerId correlationId;
    private String name;

    public Customer(String correlationId, String name) {
        this.id = UUID.randomUUID();
        this.correlationId = new CustomerId(correlationId);
        this.name = name;
    }
}
