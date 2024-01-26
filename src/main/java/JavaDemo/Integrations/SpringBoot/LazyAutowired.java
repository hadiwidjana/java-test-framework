package JavaDemo.Integrations.SpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.lang.annotation.*;

@Lazy
@Autowired
@Scope("prototype") // to create new instances of bean instead of sharing; helps during parallel runs
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface LazyAutowired {
}
