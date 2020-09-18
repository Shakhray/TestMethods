package sh.apps;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Random {

    private static final java.util.Random random = new java.util.Random();

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

    public <T> T getInstanceAndRandomFill(Class<T> clazz) throws Exception {
        if (clazz == null) {
            return null;
        }
        T instance = getInstance(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = getRandomValueForField(field);
            field.set(instance, value);
        }
        return instance;
    }

    public <T> T getInstance(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Can't create instance of class " + clazz.getName(), e);
        }
    }

    private Object getRandomValueForField(Field field) throws Exception {
        Class<?> type = field.getType();

        // Note that we must handle the different types here! This is just an
        // example, so this list is not complete! Adapt this to your needs!
        if (type.isEnum()) {
            return randomEnumNotParametrized(type);
        } else if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
            return random.nextBoolean();
        } else if (type.equals(Byte.TYPE) || type.equals(Byte.class)) {
            return random.nextInt(Byte.MAX_VALUE);
        } else if (type.equals(Short.TYPE) || type.equals(Short.class)) {
            return random.nextInt(Short.MAX_VALUE);
        } else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return random.nextInt();
        } else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            return random.nextLong();
        } else if (type.equals(Double.TYPE) || type.equals(Double.class)) {
            return random.nextDouble();
        } else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
            return random.nextFloat();
        } else if (type.equals(String.class)) {
            return randomString();
        } else if (type.equals(BigInteger.class)) {
            return BigInteger.valueOf(random.nextInt());
        } else if (type.equals(BigDecimal.class)) {
            return BigDecimal.valueOf(random.nextDouble());
//        } else if (type.isArray()) {
//            List<?> values = new ArrayList<>();
//
//            ArrayList<Model> modelArray = t.getModelArray(((JsonResponseParam)an).Name());
////            ArrayList<Model> values = new ArrayList<Model>();
//
//            for (Model model : modelArray) {
//
//                Class<? extends Model> arrayType = field.getType().getComponentType().asSubclass(Model.class);
//                Model m = arrayType.newInstance();
//                m.jsonObject = model.jsonObject;
//                model.getModel(m);
//                values.add(m);
//            }
//
//            return values.toArray(Array.newInstance(type.getComponentType(), values.size()));
        }
        return getInstanceAndRandomFill(type);
    }

    public String randomString() {
        byte[] array = new byte[random.nextInt()];
        random.nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
