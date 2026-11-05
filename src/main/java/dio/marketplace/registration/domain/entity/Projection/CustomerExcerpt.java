package dio.marketplace.registration.domain.entity.Projection;

import dio.marketplace.registration.domain.entity.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "excerpt", types = Customer.class)
public interface CustomerExcerpt {

    String getFirstName();

    String getLastName();

    @Value("#{target.address?.toString()}")
    String getAddress();

}