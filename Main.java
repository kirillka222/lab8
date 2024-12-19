import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Main {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface DataProcessor {
        String value() default "";
    }
}