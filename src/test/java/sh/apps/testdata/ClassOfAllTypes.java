package sh.apps.testdata;

import sh.apps.testdata.enums.ThreeFieldsEnum;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

public class ClassOfAllTypes {

    public byte f_byte;
    public Byte f_byte_class;
    public short f_short;
    public Short f_short_class;
    public int f_int;
    public Integer f_int_class;
    public long f_long;
    public Long f_long_class;
    public float f_float;
    public Float f_float_class;
    public double f_double;
    public Double f_double_class;
    public boolean f_boolean;
    public Boolean f_boolean_class;
    public char f_char;
    public Character f_char_class;
    public String f_string;
    public BigInteger f_bigInteger;
    public BigDecimal f_bigDecimal;
    public ThreeFieldsEnum f_enum;

    public ClassWithPrimitives[] f_array_obj;
    public Byte[] f_array_byte;
    public Short[] f_array_short;
    public Integer[] f_array_int;
    public Long[] f_array_long;
    public Float[] f_array_float;
    public Double[] f_array_double;
    public Boolean[] f_array_bool;
    public Character[] f_array_char;
    public String[] f_array_srt;
    public BigInteger[] f_array_big_int;
    public BigDecimal[] f_array_big_dec;
    public ThreeFieldsEnum[] f_array_enum;

    public ClassWithPrimitives f_object;

    public Date f_date;
    public LocalDate f_localDate;
    public LocalDateTime f_localDateTime;
    public ZonedDateTime f_zonedDateTime;

    public Collection<ClassWithPrimitives> f_collection;
    //    public Collection<Collection<ClassWithPrimitives>> f_collection_of_collection;
    public ArrayList<ClassWithPrimitives> f_arrayList;
    public LinkedList<ClassWithPrimitives> f_linkedList;

    public Map<String, ClassWithPrimitives> f_map;
    public LinkedHashMap<String, ClassWithPrimitives> f_linkedHashMap;

    public UUID f_uuid;
}

