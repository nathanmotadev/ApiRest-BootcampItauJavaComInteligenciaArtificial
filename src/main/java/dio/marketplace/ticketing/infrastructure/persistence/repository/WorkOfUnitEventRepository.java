package dio.marketplace.ticketing.infrastructure.persistence.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Repository;

import dio.marketplace.ticketing.domain.CustomerId;
import dio.marketplace.ticketing.domain.Event;
import dio.marketplace.ticketing.domain.EventId;
import dio.marketplace.ticketing.domain.EventRepository;
import dio.marketplace.ticketing.domain.Seat;
import dio.marketplace.ticketing.domain.SeatId;
import dio.marketplace.ticketing.domain.Sector;
import dio.marketplace.ticketing.infrastructure.persistence.entity.SeatLock;

@Repository
public class WorkOfUnitEventRepository implements EventRepository {
        private final EventCrudRepository eventCrudRepository;
        private final RedisSeatLockRepository redisSeatLockRepository;

        public WorkOfUnitEventRepository(
                        EventCrudRepository eventCrudRepository,
                        RedisSeatLockRepository redisSeatLockRepository) {

                this.eventCrudRepository = eventCrudRepository;
                this.redisSeatLockRepository = redisSeatLockRepository;
        }

        @Override
        public void save(Event event) {
                var sectors = event.getSeats().entrySet().stream()
                                .map(entry -> {
                                        Sector domainSector = entry.getKey();
                                        List<Seat> domainSeats = entry.getValue();

                                        var seats = domainSeats.stream()
                                                        .map(s -> new dio.marketplace.ticketing.infrastructure.persistence.entity.Seat(
                                                                        s.getId(),
                                                                        s.getCorrelationId().id()))
                                                        .toList();

                                        return new dio.marketplace.ticketing.infrastructure.persistence.entity.Sector(
                                                        domainSector.getId(),
                                                        domainSector.getCorrelationId().id(),
                                                        domainSector.getPrice(),
                                                        seats);
                                }).toList();

                var entity = new dio.marketplace.ticketing.infrastructure.persistence.entity.Event(
                                event.getId(),
                                event.getCorrelationId().id(),
                                sectors);

                eventCrudRepository.save(entity);
        }

        @Override
        public boolean existsSeat(EventId eventId, SeatId seatId) {
                return eventCrudRepository.countByCorrelationIdAndSectors_Seats_CorrelationId(
                                eventId.id(),
                                seatId.id()) > 0;
        }

        @Override
        public boolean tryLockSeat(EventId eventId, SeatId seatId, CustomerId customerId) {
                String lockId = eventId.id().toString() + ":" + seatId.id();

                if (redisSeatLockRepository.existsById(lockId)) {
                        return false;
                }

                var lock = new SeatLock(
                                lockId,
                                customerId.id().toString(),
                                Instant.now());

                redisSeatLockRepository.save(lock);
                return true;
        }

}
