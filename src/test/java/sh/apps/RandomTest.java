package sh.apps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sh.apps.testdata.ClassOfAllTypes;
import sh.apps.testdata.enums.OneFieldsEnum;
import sh.apps.testdata.enums.ThreeFieldsEnum;
import sh.apps.testdata.enums.WithoutFieldsEnum;

import java.util.Collection;
import java.util.Map;

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

    @Test
    public void randomString() {
        String str = Random.randomString();
        Assertions.assertNotNull(str);
        Assertions.assertFalse(str.isEmpty());
    }

    @Test
    public void randomClass() {
        Assertions.assertNull(Random.getInstanceAndRandomFill(null));

        ClassOfAllTypes instance = Random.getInstanceAndRandomFill(ClassOfAllTypes.class);
        Assertions.assertNotNull(instance);
        Assertions.assertNotNull(instance.f_byte_class);
        Assertions.assertNotNull(instance.f_short_class);
        Assertions.assertNotNull(instance.f_int_class);
        Assertions.assertNotNull(instance.f_long_class);
        Assertions.assertNotNull(instance.f_float_class);
        Assertions.assertNotNull(instance.f_double_class);
        Assertions.assertNotNull(instance.f_boolean_class);
        Assertions.assertNotNull(instance.f_char_class);
        Assertions.assertNotNull(instance.f_string);
        Assertions.assertNotNull(instance.f_enum);
        Assertions.assertNotNull(instance.f_bigInteger);
        Assertions.assertNotNull(instance.f_bigDecimal);

        assertArray(instance.f_array_obj);
        assertArray(instance.f_array_byte);
        assertArray(instance.f_array_short);
        assertArray(instance.f_array_int);
        assertArray(instance.f_array_long);
        assertArray(instance.f_array_float);
        assertArray(instance.f_array_double);
        assertArray(instance.f_array_bool);
        assertArray(instance.f_array_char);
        assertArray(instance.f_array_srt);
        assertArray(instance.f_array_big_int);
        assertArray(instance.f_array_big_dec);
        assertArray(instance.f_array_enum);

        Assertions.assertNotNull(instance.f_object);
        Assertions.assertNull(instance.f_object.f_object_of_ClassWithPrimitives);

        Assertions.assertNotNull(instance.f_date);
        Assertions.assertNotNull(instance.f_localDate);
        Assertions.assertNotNull(instance.f_localDateTime);
        Assertions.assertNotNull(instance.f_zonedDateTime);

        assertCollection(instance.f_collection);
        assertCollection(instance.f_arrayList);
        assertCollection(instance.f_linkedList);
//        assertCollection(instance.f_collection_of_collection);

        assertMap(instance.f_map);
        assertMap(instance.f_linkedHashMap);

        Assertions.assertNotNull(instance.f_uuid);
    }

    private void assertArray(Object[] array) {
        Assertions.assertNotNull(array);
        Assertions.assertTrue(array.length > 0);
        Assertions.assertNotNull(array[0]);
    }

    private void assertCollection(Collection<?> collection) {
        Assertions.assertNotNull(collection);
        Assertions.assertTrue(collection.size() > 0);
        Assertions.assertNotNull(collection.iterator().next());
    }

    private void assertMap(Map<?, ?> map) {
        Assertions.assertNotNull(map);
        Assertions.assertTrue(map.size() > 0);
        Assertions.assertNotNull(map.entrySet().iterator().next());
    }
}
