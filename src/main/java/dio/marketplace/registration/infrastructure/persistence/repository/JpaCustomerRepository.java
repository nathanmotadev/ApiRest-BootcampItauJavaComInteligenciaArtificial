package dio.marketplace.registration.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import dio.marketplace.common.infrastructure.event.dto.CustomerCreated;
import dio.marketplace.registration.domain.CustomerRepository;
import dio.marketplace.registration.domain.Customer;
import dio.marketplace.registration.domain.CustomerId;

@Repository
public class JpaCustomerRepository implements CustomerRepository {
    private final CustomerEntityRepository customerEntityRepository;
    private final ApplicationEventPublisher publisher;

    public JpaCustomerRepository(
            CustomerEntityRepository customerEntityRepository,
            ApplicationEventPublisher publisher) {

        this.customerEntityRepository = customerEntityRepository;
        this.publisher = publisher;
    }

    @Override
    public Customer save(Customer customer) {
        var entity = mapper(customer);
        customerEntityRepository.save(entity);

        publisher.publishEvent(new CustomerCreated(
                customer.getId().toString(),
                customer.getName()));

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        var iterable = customerEntityRepository.findAll();

        return StreamSupport
                .stream(iterable.spliterator(), false)
                .map(JpaCustomerRepository::mapper)
                .toList();
    }

    private static dio.marketplace.registration.domain.entity.Customer mapper(Customer customer) {
        var entity = new dio.marketplace.registration.domain.entity.Customer();

        entity.setId(customer.getId().id());
        entity.setFirstName(customer.getName());
        entity.setEmail(customer.getEmail());

        return entity;
    }

    private static Customer mapper(dio.marketplace.registration.domain.entity.Customer entity) {
        String fullName = Optional
                .ofNullable(entity.getLastName())
                .map(lastName -> entity.getFirstName() + " " + lastName)
                .orElseGet(entity::getFirstName);

        return new Customer(
                new CustomerId(entity.getId()),
                fullName,
                entity.getEmail());
    }

}