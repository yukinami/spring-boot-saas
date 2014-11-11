package yukinami.example.saas.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * specify that the resource should be accessed by root
 *
 */
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface RootResource {
}
