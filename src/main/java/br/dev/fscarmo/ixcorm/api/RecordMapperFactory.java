package br.dev.fscarmo.ixcorm.api;


import br.dev.fscarmo.ixcorm.IxcRecordMapper;
import com.google.gson.JsonElement;

import java.lang.reflect.InvocationTargetException;


@SuppressWarnings("ClassCanBeRecord")
public class RecordMapperFactory<T extends IxcRecordMapper> {

    private final Class<T> mapperClass;

    public RecordMapperFactory(Class<T> mapperClass) {
        this.mapperClass = mapperClass;
    }

    public T newMapper(JsonElement data) {
        try {
            return mapperClass.getDeclaredConstructor(JsonElement.class).newInstance(data);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
