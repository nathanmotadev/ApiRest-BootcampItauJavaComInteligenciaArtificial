package dio.marketplace.ticketing.infrastructure.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dio.marketplace.ticketing.application.SelectSeatUseCase;
import dio.marketplace.ticketing.domain.CustomerId;
import dio.marketplace.ticketing.domain.EventId;
import dio.marketplace.ticketing.infrastructure.http.request.SeatSelectionRequest;

@RestController
@RequestMapping("/ticketing/events/{eventId}/seats")
public class SeatSelectionController {
    private final SelectSeatUseCase selectSeatUseCase;

    public SeatSelectionController(SelectSeatUseCase selectSeatUseCase) {
        this.selectSeatUseCase = selectSeatUseCase;
    }

    @PostMapping("/select")
    @ResponseStatus(HttpStatus.CREATED)
    public void selectSeat(
            @PathVariable String eventId,
            @RequestBody SeatSelectionRequest request,
            @RequestHeader("X-CUSTOMER-ID") String customerId) {

        selectSeatUseCase.execute(
                new EventId(eventId),
                request.toInput(),
                new CustomerId(customerId));
    }

}
