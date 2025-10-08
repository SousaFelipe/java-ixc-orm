package br.dev.fscarmo.ixcorm.api;


import br.dev.fscarmo.ixcorm.IxcRecord;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * A classe 'IxcRecordMapper' fornece um método para mapear as propriedades de um {@link JsonElement}, passando os
 * respectivos valores para cada uma das propriedades correspondentes declaradas num objeto que herde de
 * {@link IxcRecord}.
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.0.0
 * @since 2025-10-01
 */
public abstract class IxcRecordMapper {

    /**
     * <p>
     * Mapeia as propriedades declaradas em uma classe que herde de {@link IxcRecord}, baseando-se nas propriedades do
     * objeto {@link JsonElement} da instância.
     * </p>
     *
     * <p>
     * Se forem declaradas propriedades na classe filha de {@link IxcRecord}, que não existam no {@link JsonElement}
     * de sua instância, tais propriedades permanecerão nulas. Do mesmo modo, a declaração de todas as propriedades
     * que, possívelmente, façam parte do {@link JsonElement} da instância, não é obrigatória.
     * </p>
     *
     * @param target Um objeto que herde de {@link IxcRecord}, contendo as propriedades cujo os identificadores
     *               correspondam a identificadores de propriedades encontradas no objeto {@link JsonElement} de sua
     *               instância.
     */
    public static void map(IxcRecord target) {
        List<Field> fields = getAllClassFields(target.getClass());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mapRecordFieldsByTypes(target, field);
            }
            catch (IllegalAccessException e) {
                System.out.println(e.getCause().getMessage());
            }
        }
    }

    private static List<Field> getAllClassFields(Class<? extends IxcRecord> targetClass) {
        List<Field> fields = new ArrayList<>();

        Field[] currentFields;
        Class<?> currentClass = targetClass;

        while (currentClass != null && currentClass != Object.class) {
            currentFields = currentClass.getDeclaredFields();
            currentClass = currentClass.getSuperclass();
            fields.addAll(Arrays.asList(currentFields));
        }

        return fields;
    }

    private static void mapRecordFieldsByTypes(IxcRecord record, Field field) throws IllegalAccessException {
        switch (field.getType().getName()) {
            case "java.math.BigDecimal":
                setRecordFieldValueAsBigDecimal(record, field);
                break;

            case "java.math.BigInteger":
                setRecordFieldValueAsBigInt(record, field);
                break;

            case "java.lang.Boolean", "boolean":
                setRecordFieldValueAsBoolean(record, field);
                break;

            case "java.lang.Integer", "int":
                setRecordFieldValueAsInt(record, field);
                break;

            case "java.lang.Long":
                setRecordFieldValueAsLong(record, field);
                break;

            case "java.lang.String":
                setRecordFieldValueAsString(record, field);
                break;
        }
    }

    private static void setRecordFieldValueAsBigDecimal(IxcRecord record, Field field) throws IllegalAccessException {
        String identifier = getRealFieldIdentifier(field);
        JsonElement element = record.getJsonElement(identifier);
        if (element != null) {
            field.set(record, element.getAsBigDecimal());
        };
    }

    private static void setRecordFieldValueAsBigInt(IxcRecord record, Field field) throws IllegalAccessException {
        String identifier = getRealFieldIdentifier(field);
        JsonElement element = record.getJsonElement(identifier);
        if (element != null) {
            field.set(record, element.getAsBigInteger());
        };
    }

    private static void setRecordFieldValueAsBoolean(IxcRecord record, Field field) throws IllegalAccessException {
        String identifier = getRealFieldIdentifier(field);
        JsonElement element = record.getJsonElement(identifier);
        if (element != null) {
            field.set(record, element.getAsBoolean());
        };
    }

    private static void setRecordFieldValueAsLong(IxcRecord record, Field field) throws IllegalAccessException {
        String identifier = getRealFieldIdentifier(field);
        JsonElement element = record.getJsonElement(identifier);
        if (element != null) {
            field.set(record, element.getAsLong());
        };
    }

    private static void setRecordFieldValueAsString(IxcRecord record, Field field) throws IllegalAccessException {
        String identifier = getRealFieldIdentifier(field);
        JsonElement element = record.getJsonElement(identifier);
        if (element != null) {
            field.set(record, element.getAsString());
        };
    }

    private static void setRecordFieldValueAsInt(IxcRecord record, Field field) throws IllegalAccessException {
        String identifier = getRealFieldIdentifier(field);
        JsonElement element = record.getJsonElement(identifier);
        if (element != null) {
            field.set(record, element.getAsInt());
        };
    }

    private static String getRealFieldIdentifier(Field field) {
        if (field.isAnnotationPresent(SerializedName.class)) {
            return fieldNameByGsonSerializedName(field);
        }
        return field.getName();
    }

    private static String fieldNameByGsonSerializedName(Field field) {
        SerializedName serializedName = field.getAnnotation(SerializedName.class);
        return serializedName.value();
    }
}
