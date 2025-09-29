package br.dev.fscarmo.ixcorm.api;


import com.google.gson.JsonElement;


public abstract class Mapper {


    private final JsonElement jsonElement;


    public Mapper(JsonElement jsonElement) {
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
