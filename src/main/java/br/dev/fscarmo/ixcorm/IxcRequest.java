package br.dev.fscarmo.ixcorm;


import br.dev.fscarmo.ixcorm.api.ORM;
import br.dev.fscarmo.ixcorm.api.records.Header;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/**
 * <p>
 * A classe 'IxcRequest' manipula a query de busca, constrói o objeto HTTP que executa a requisição e expões métodos que
 * retornam a resposta.
 * </p>
 *
 * <p>
 * Essa classe não pode ser instanciada, pois ela existe apenas com a finalizada de encapsular toda a lógica da
 * requisição e resposta HTTP. A maneira correta de utilizá-la é através de herança, como no exemplo a seguir:
 * </p>
 *
 * {@snippet lang=java:
 *
 * class Cliente extends IxcRequest {
 *
 *     private Cliente() {
 *         super("cliente");
 *     }
 *
 *     public static Cliente newBuilder() {
 *         return new Cliente();
 *     }
 * }
 *
 * Cliente cliente = Cliente.newBuilder()
 *         .where("razao").exactly("João")
 *         .where("data_nascimento").greaterThan("1999-01-01")
 *
 * }
 *
 * @author Felipe S. Carmo
 * @version 1.0.2
 * @since 2025-09-27
 */
public abstract class IxcRequest extends ORM {


    private final List<Header> headers = new ArrayList<>();
    private URI uri;


    protected IxcRequest(String table) {
        super(table);
        setupDefaultHeaders();
        setupUri();
    }


    protected IxcResponse GET() {
        enableListingHeader();
        HttpResponse<String> response = sendRequestAndGetResponse("POST");
        return new IxcResponse(response);
    }


    protected IxcResponse POST() {
        disableListingHeader();
        HttpResponse<String> response = sendRequestAndGetResponse("POST");
        return new IxcResponse(response);
    }


    protected IxcResponse PUT() {
        disableListingHeader();
        HttpResponse<String> response = sendRequestAndGetResponse("PUT");
        return new IxcResponse(response);
    }


    protected IxcResponse DELETE() {
        disableListingHeader();
        HttpResponse<String> response = sendRequestAndGetResponse("DELETE");
        return new IxcResponse(response);
    }


    private HttpResponse<String> sendRequestAndGetResponse(String method) {
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(uri);
            String jsonBody = super.getQueryAsJSON();

            // OBS: Definindo o (method) de forma dinâmica
            requestBuilder.method(method, HttpRequest.BodyPublishers.ofString(jsonBody));

            // ATENÇÃO: (Side Effect) no "requestBuilder"
            includeHeadersOnRequestBuilder(requestBuilder);

            return client.send(
                    requestBuilder.build(),
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            );
        }
        catch (UncheckedIOException | InterruptedException | IOException e) {
            IO.println(e);
            throw new RuntimeException(e);
        }
    }


    private void includeHeadersOnRequestBuilder(HttpRequest.Builder builder) {
        for (Header header : headers) {
            builder.setHeader(header.name(), header.value());
        }
    }


    private void setupDefaultHeaders() {
        headers.add(Header.of("Authorization", "Basic " + this.getEncodedTokenFromContext()));
        headers.add(Header.of("Content-Type", "application/json"));
    }


    private void setupUri() {
        String domain = IxcContext.INSTANCE.getEnv().getDomain();
        String table = getTable();
        uri = URI.create("https://"+ domain + "/webservice/v1/"+ table);
    }


    private void enableListingHeader() {
        if (isListingHeaderDisabled()) {
            Header header = Header.of("ixcsoft", "listar");
            headers.add(header);
        }
    }


    private void disableListingHeader() {
        headers.removeIf(header -> header.hasName("ixcsoft"));
    }


    private boolean isListingHeaderDisabled() {
        return headers.stream()
                .filter(header -> header.hasName("ixcsoft"))
                .findFirst()
                .isEmpty();
    }


    private String getEncodedTokenFromContext() {
        String tokenFromEnv = IxcContext.INSTANCE.getEnv().getToken();
        byte[] bytes = tokenFromEnv.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
