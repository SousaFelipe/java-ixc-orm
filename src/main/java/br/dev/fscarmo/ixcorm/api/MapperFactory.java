package br.dev.fscarmo.ixcorm.api;


import com.google.gson.JsonElement;

import java.lang.reflect.InvocationTargetException;


@SuppressWarnings("ClassCanBeRecord")
public class MapperFactory<T extends Mapper> {


    private final Class<T> mapperClass;


    public MapperFactory(Class<T> mapperClass) {
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
