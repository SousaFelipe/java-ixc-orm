package br.dev.fscarmo.ixcorm;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;


/**
 * <p>
 * A classe 'IxcResponse' manipula a resposta HTTP recebida no construtor pelo parâmetro <b>response</b>. Expões métodos
 * de acesso ao corpo e ao status da resposta.
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.2.0
 * @since 2025-09-28
 */
@SuppressWarnings("ClassCanBeRecord")
public class IxcResponse {


    private final HttpResponse<String> response;


    /**
     * @param response Um {@link HttpResponse} devolvido pelo método <b>send()</b> da classe {@link HttpClient}.
     */
    public IxcResponse(HttpResponse<String> response) {
        this.response = response;
    }

    /**
     * @return O código numérico do status da resposta obtida do IXC Provedor.
     */
    public int getStatusCode() {
        return response.statusCode();
    }

    /**
     * @return Um objeto {@link IxcResponse} contendo dodos os dados da resposta do IXC Provedor.
     */
    public IxcResponseBody getBody() {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        return new IxcResponseBody(jsonObject);
    }
}
