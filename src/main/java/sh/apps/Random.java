package sh.apps;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

import static sh.apps.ClassUtils.isModifierSet;


public class Random {

    private static final java.util.Random random = new java.util.Random();
    private static final int ARRAY_RANGE = 100;
//    private static final int RANGE = 100;

    private Random() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T randomEnum(Class<T> clazz) {
        return (T) randomEnumNotParametrized(clazz);
    }

    private static Object randomEnumNotParametrized(Class<?> clazz) {
        if (clazz == null || clazz.getEnumConstants() == null) {
            return null;
        }
        int fieldsQuantity = clazz.getEnumConstants().length;
        return fieldsQuantity == 0 ? null : clazz.getEnumConstants()[random.nextInt(fieldsQuantity)];
    }

    public static <T> T getInstanceAndRandomFill(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        T instance = ClassUtils.getInstance(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (isModifierSet(field.getModifiers(), Modifier.FINAL)
                    || isModifierSet(field.getModifiers(), Modifier.STATIC)) {
                continue;
            }
            // if field has type as its class it will be infinite recursion
            Object value = field.getType().equals(clazz) ? null : getRandomValueForField(field);
            try {
                field.set(instance, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e); // TODO
            }
        }
        return instance;
    }

    private static Object getRandomValueForField(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            Type fieldType = field.getGenericType();
            Class<?> generic = (Class<?>) (((ParameterizedType) fieldType).getActualTypeArguments()[0]);
            return getRandomCollection(field.getType(), generic);
        } else if (Map.class.isAssignableFrom(field.getType())) {
            Type fieldType = field.getGenericType();
            Class<?> key = (Class<?>) (((ParameterizedType) fieldType).getActualTypeArguments()[0]);
            Class<?> value = (Class<?>) (((ParameterizedType) fieldType).getActualTypeArguments()[1]);
            return getRandomMap(field.getType(), key, value);
        }
        return getRandomValueForClass(field.getType());
    }

    // TODO add Collection of collection support
    private static Object getRandomValueForClass(Class<?> type) {
        if (type.isEnum()) {
            return randomEnumNotParametrized(type);
        } else if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
            return random.nextBoolean();
        } else if (type.equals(Byte.TYPE) || type.equals(Byte.class)) {
            return (byte) random.nextInt(Byte.MAX_VALUE);
        } else if (type.equals(Short.TYPE) || type.equals(Short.class)) {
            return (short) random.nextInt(Short.MAX_VALUE);
        } else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return random.nextInt();
        } else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            return random.nextLong();
        } else if (type.equals(Double.TYPE) || type.equals(Double.class)) {
            return random.nextDouble();
        } else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
            return random.nextFloat();
        } else if (type.equals(Character.TYPE) || type.equals(Character.class)) {
            return (char) random.nextInt(Byte.MAX_VALUE);
        } else if (type.equals(String.class)) {
            return randomString();
        } else if (type.equals(BigInteger.class)) {
            return BigInteger.valueOf(random.nextInt());
        } else if (type.equals(BigDecimal.class)) {
            return BigDecimal.valueOf(random.nextDouble());
        } else if (type.isArray()) {
            int arrayLength = randomPositiveInt(ARRAY_RANGE);
            Object array = Array.newInstance(type.getComponentType(), arrayLength);

            for (int i = 0; i < arrayLength; i++) {
                Array.set(array, i, getRandomValueForClass(type.getComponentType()));
            }
            return array;
        } else if (type.equals(Date.class)) {
            return randomDate();
        } else if (type.equals(LocalDate.class)) {
            return Converter.convertDateToLocalDate(randomDate());
        } else if (type.equals(LocalDateTime.class)) {
            return Converter.convertDateToLocalDateTime(randomDate());
        } else if (type.equals(ZonedDateTime.class)) {
            return Converter.convertDateToZonedDateTime(randomDate());
        } else if (type.equals(UUID.class)) {
            return UUID.randomUUID();
        }
        return getInstanceAndRandomFill(type);
    }

    @SuppressWarnings("unchecked")
    public static <CollectionType, Generic> Collection<Generic> getRandomCollection(
            Class<CollectionType> collectionType, Class<Generic> generic) {

        Collection<Generic> collection;
        if (collectionType.isInterface()) {
            collection = new ArrayList<>();
        } else {
            collection = (Collection<Generic>) ClassUtils.getInstance(collectionType);
        }

        for (int i = 0; i < randomPositiveInt(ARRAY_RANGE); i++) {
            collection.add((Generic) getRandomValueForClass(generic));
        }
        return collection;
    }

    @SuppressWarnings("unchecked")
    public static <MapType, Key, Value> Map<Key, Value> getRandomMap(
            Class<MapType> mapType, Class<Key> keyClass, Class<Value> valueClass) {

        Map<Key, Value> map;
        if (mapType.isInterface()) {
            map = new HashMap<>();
        } else {
            map = (Map<Key, Value>) ClassUtils.getInstance(mapType);
        }

        for (int i = 0; i < randomPositiveInt(ARRAY_RANGE); i++) {
            Key key = (Key) getRandomValueForClass(keyClass);
            Value value = (Value) getRandomValueForClass(valueClass);
            map.put(key, value);
        }
        return map;
    }

    public static Date randomDate() {
        return new Date(random.nextLong());
    }

    public static String randomString() {
        byte[] array = new byte[randomPositiveInt(ARRAY_RANGE)];
        random.nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private static int randomPositiveInt(int range) {
        int value = random.nextInt(range);
        return value == 0 ? 1 : value;
    }
}
