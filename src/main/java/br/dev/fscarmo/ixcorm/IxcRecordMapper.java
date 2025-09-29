package br.dev.fscarmo.ixcorm;


import com.google.gson.JsonElement;


public abstract class IxcRecordMapper {

    private final JsonElement jsonElement;

    public IxcRecordMapper(JsonElement jsonElement) {
        this.jsonElement = jsonElement;
        map();
    }

    protected JsonElement get(String property) {
        return jsonElement.getAsJsonObject().get(property);
    }

    protected boolean elementHasProperty(String property) {
        return jsonElement.getAsJsonObject().has(property);
    }

    protected abstract void map();
}
