package JavaDemo.Integrations.SpringBoot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.lang.annotation.*;

@Lazy
@Configuration
@Scope("prototype") // to create new instances of bean instead of sharing; helps during parallel runs
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LazyConfiguration {
}
