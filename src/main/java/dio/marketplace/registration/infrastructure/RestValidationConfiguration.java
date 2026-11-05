package dio.marketplace.registration.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration(proxyBeanMethods = false)
public class RestValidationConfiguration implements RepositoryRestConfigurer {

    private final LocalValidatorFactoryBean validator;

    public RestValidationConfiguration(LocalValidatorFactoryBean validator) {
        this.validator = validator;
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener listener) {
        listener.addValidator("beforeCreate", validator);
        listener.addValidator("beforeSave", validator);
    }
}
