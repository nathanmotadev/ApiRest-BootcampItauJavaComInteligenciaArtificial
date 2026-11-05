package dio.marketplace.ticketing.application;

import org.springframework.stereotype.Service;

import dio.marketplace.ticketing.domain.CustomerId;
import dio.marketplace.ticketing.domain.EventId;
import dio.marketplace.ticketing.domain.EventRepository;
import dio.marketplace.ticketing.domain.SeatAlreadyReservedException;
import dio.marketplace.ticketing.domain.SeatId;
import dio.marketplace.ticketing.domain.SeatNotFoundException;

@Service
public class SelectSeatUseCase {

    private final EventRepository eventRepository;

    public SelectSeatUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void execute(EventId eventId, SeatId seatId, CustomerId customerId) {
        if (!eventRepository.existsSeat(eventId, seatId)) {
            throw new SeatNotFoundException(eventId, seatId);
        }

        boolean lock = eventRepository.tryLockSeat(eventId, seatId, customerId);

        if(!lock) {
            throw new SeatAlreadyReservedException();
        }
    }
}
