package sh.apps;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static void assertNotNullByAllFields(Object obj, String... excludeFields) throws IllegalAccessException {
        List<String> excludes = Arrays.asList(excludeFields);
        Assertions.assertNotNull(obj);
        for (Field field : ClassUtils.getAllFields(obj)) {
            field.setAccessible(true);
            if (!excludes.contains(field.getName())) {
                try {
                    Assertions.assertNotNull(field.get(obj));
                } catch (AssertionError e) {
                    String message = "Field " + obj.getClass().getCanonicalName() + "." + field.getName() + " is null.";
                    throw new IllegalArgumentException(message, e);
                }
            }
        }
    }

}
