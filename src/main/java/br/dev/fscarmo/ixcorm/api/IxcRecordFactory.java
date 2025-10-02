package br.dev.fscarmo.ixcorm.api;


import br.dev.fscarmo.ixcorm.IxcRecord;
import com.google.gson.JsonElement;

import java.lang.reflect.InvocationTargetException;


/**
 * <p>
 * A classe 'IxcRecordFactory' concentra a lógica de instanciação dinâmica dos tipos derivados de {@link IxcRecord}.
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.0.0
 * @since 2025-09-28
 */
@SuppressWarnings("ClassCanBeRecord")
public class IxcRecordFactory<T extends IxcRecord> {

    private final Class<T> mapperClass;

    /**
     * @param mapperClass Um Class< T >. Onde T é o tipo da classe que se deseja mapear. < T > deve herdar de
     *                    {@link IxcRecord}.
     */
    public IxcRecordFactory(Class<T> mapperClass) {
        this.mapperClass = mapperClass;
    }

    /**
     * @param data Um {@link JsonElement} com as propriedades que se deseja mapear do parâmetro
     *             <b>(JsonElement data).</b>
     * @return Um < T >, onde < T > é o tipo da classe que será mapeada.
     */
    public T newRecord(JsonElement data) {
        try {
            return mapperClass.getDeclaredConstructor(JsonElement.class).newInstance(data);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            IO.println(e.getCause().getMessage());
            return null;
        }
    }
}
