package net.praqma.util.test.junit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author cwolfgang
 */
@Retention( RUNTIME )
@Target( { METHOD } )
public @interface TestDescription {
    String title();
    String text() default "";
    String[] outcome() default {};
    String[] configurations() default {};
}
