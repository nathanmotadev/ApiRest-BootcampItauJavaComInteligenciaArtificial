package dio.marketplace.ticketing.application;

import org.springframework.stereotype.Service;

import dio.marketplace.common.infrastructure.event.dto.CustomerCreated;
import dio.marketplace.ticketing.domain.Customer;
import dio.marketplace.ticketing.domain.CustomerRepository;

@Service
public class CreateCustomerUseCase {
    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void execute(CustomerCreated event) {
        var customer = new Customer(event.id(), event.name());
        customerRepository.save(customer);
    }
}
