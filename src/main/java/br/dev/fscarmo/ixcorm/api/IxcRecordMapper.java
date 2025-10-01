package br.dev.fscarmo.ixcorm.api;


import br.dev.fscarmo.ixcorm.IxcRecord;
import com.google.gson.JsonElement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * A classe 'IxcRecordMapper' fornece um método para mapear as proproedades de um {@link JsonElement}, passando os
 * respectivos valores para cada uma das propriedades correspondentes declaradas num objeto que herde de
 * {@link IxcRecord}b>.
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
     * Se forem declaradas propriedades na classe herdeira de {@link IxcRecord}, que não existam no
     * {@link JsonElement} de sua instância, tais propriedades permanecerão nulas. Do mesmo modo, a declaração de
     * todas as propriedades que, possivelmente, façam parte do {@link JsonElement} da instância, não é obrigatória.
     * </p>
     *
     * @param target Um objeto que herde de {@link IxcRecord}, contendo as propriedades cujo os identificadores
     *               correspondam aos mesmos identificadores do objeto {@link JsonElement} de sua instância.
     */
    public static void map(IxcRecord target) {
        List<Field> fields = getAllClassFields(target.getClass());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mapRecordFieldsByTypes(target, field);
            }
            catch (IllegalAccessException _) {}
        }
    }

    private static List<Field> getAllClassFields(Class<? extends IxcRecord> targetClass) {
        List<Field> fields = new ArrayList<>();

        Class<?> currentClass = targetClass;
        while (currentClass != null && currentClass != Object.class) {
            fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
            currentClass = currentClass.getSuperclass();
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
        JsonElement element = record.getJsonElement(field.getName());
        if (element != null) {
            field.set(record, element.getAsBigDecimal());
        };
    }

    private static void setRecordFieldValueAsBigInt(IxcRecord record, Field field) throws IllegalAccessException {
        JsonElement element = record.getJsonElement(field.getName());
        if (element != null) {
            field.set(record, element.getAsBigInteger());
        };
    }

    private static void setRecordFieldValueAsBoolean(IxcRecord record, Field field) throws IllegalAccessException {
        JsonElement element = record.getJsonElement(field.getName());
        if (element != null) {
            field.set(record, element.getAsBoolean());
        };
    }

    private static void setRecordFieldValueAsLong(IxcRecord record, Field field) throws IllegalAccessException {
        JsonElement element = record.getJsonElement(field.getName());
        if (element != null) {
            field.set(record, element.getAsLong());
        };
    }

    private static void setRecordFieldValueAsString(IxcRecord record, Field field) throws IllegalAccessException {
        JsonElement element = record.getJsonElement(field.getName());
        if (element != null) {
            field.set(record, element.getAsString());
        };
    }

    private static void setRecordFieldValueAsInt(IxcRecord record, Field field) throws IllegalAccessException {
        JsonElement element = record.getJsonElement(field.getName());
        if (element != null) {
            field.set(record, element.getAsInt());
        };
    }
}
