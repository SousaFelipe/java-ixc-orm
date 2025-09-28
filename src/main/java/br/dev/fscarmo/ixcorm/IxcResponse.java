package br.dev.fscarmo.ixcorm;


import java.lang.reflect.Field;
import java.net.http.HttpResponse;


/**
 * <p>
 * A classe 'IxcResponse' manipula a resposta HTTP recebida no construtor pelo parâmetro <b>response</b>, baseando-se
 * na classe que define o formato dos dados no corpo da resposta, fornecida pelo parâmetro <b>responseFormat.</b>
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.0.0
 * @since 2025-09-27
 */
public class IxcResponse {

    private final HttpResponse<String> response;
    private final Class<?> responseFormat;

    public IxcResponse(HttpResponse<String> response, Class<?> responseFormat) {
        this.response = response;
        this.responseFormat = responseFormat;
    }

    public int getStatusCode() {
        return response.statusCode();
    }

    public String getBody() {
        return response.body();
    }
}
