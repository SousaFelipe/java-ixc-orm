package br.dev.fscarmo.ixcorm;


import br.dev.fscarmo.ixcorm.api.RecordMapperFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class IxcResponseBody {


    private final JsonObject jsonObject;
    private String type;
    private String message;
    private int page;
    private int total;


    public IxcResponseBody(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
        setupType();
        setupMessage();
        setupPage();
        setupTotal();
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public int getPage() {
        return page;
    }

    public int getTotal() {
        return total;
    }

    public <T extends IxcRecordMapper> List<T> getRegistros(Class<T> mapper) {
        JsonArray jsonElements = jsonObject.getAsJsonArray("registros");
        if (jsonElements != null) {
            RecordMapperFactory<T> factory = new RecordMapperFactory<>(mapper);
            return jsonElements.asList().stream().map(factory::newMapper).toList();
        }
        return new ArrayList<>();
    }

    private void setupType() {
        JsonElement element = jsonObject.get("type");
        type = (element == null) ? "" : element.getAsString();
    }

    private void setupMessage() {
        JsonElement element = jsonObject.get("message");
        message = (element == null) ? "" : element.getAsString();
    }

    private void setupPage() {
        JsonElement element = jsonObject.get("page");
        page = (element == null) ? 0 : Integer.parseInt(element.getAsString());
    }

    private void setupTotal() {
        JsonElement element = jsonObject.get("total");
        total = (element == null) ? 0 : element.getAsInt();
    }
}
