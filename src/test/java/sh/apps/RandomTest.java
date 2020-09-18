package sh.apps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomTest {

    @Test
    @SuppressWarnings("unchecked")
    public void randomEnumTest() {
        Assertions.assertNotNull(Random.randomEnum(ThreeFieldsEnum.class));
        Assertions.assertNotNull(Random.randomEnum(OneFieldsEnum.class));
        Assertions.assertNull(Random.randomEnum(WithoutFieldsEnum.class));
        Assertions.assertNull(Random.randomEnum(Enum.class));
        Assertions.assertNull(Random.randomEnum(null));
    }

    private enum ThreeFieldsEnum {
        FIRST,
        SECOND,
        THIRD
    }

    public enum OneFieldsEnum {
        FIRST
    }

    public enum WithoutFieldsEnum {
    }

}
