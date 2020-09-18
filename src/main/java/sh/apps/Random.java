package sh.apps;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;


public class Random {

    private static final java.util.Random random = new java.util.Random();
    private static final int RANGE = 100;

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
        T instance = getInstance(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
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

    public static <T> T getInstance(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Can't create instance of class " + clazz.getName(), e);
        }
    }

    private static Object getRandomValueForField(Field field) {
        return getRandomValueForClass(field.getType());
    }

    // TODO add Collection and Map support
    private static Object getRandomValueForClass(Class<?> type) {
        // Note that we must handle the different types here! This is just an
        // example, so this list is not complete! Adapt this to your needs!
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
            int arrayLength = random.nextInt(RANGE);
            arrayLength = arrayLength == 0 ? 1 : arrayLength;
            Object array = Array.newInstance(type.getComponentType(), arrayLength);

            for (int i = 0; i < arrayLength; i++) {
                Array.set(array, i, getRandomValueForClass(type.getComponentType()));
            }
            return array;
        }
        return getInstanceAndRandomFill(type);
    }

    public static String randomString() {
        int size = random.nextInt(RANGE);
        size = size == 0 ? 1 : size;
        byte[] array = new byte[size];
        random.nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
