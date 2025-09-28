package br.dev.fscarmo.ixcorm.api;


import br.dev.fscarmo.ixcorm.IxcContext;
import br.dev.fscarmo.ixcorm.IxcResponse;
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
 * A classe 'OrmClient' manipula a query de busca, constrói o objeto HTTP que executa a requisição e expões métodos que
 * retornam a resposta.
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.2.0
 * @since 2025-09-27
 */
public abstract class OrmClient {


    private String body;
    private final List<Header> headers = new ArrayList<>();
    private final String table;
    private URI uri;


    protected OrmClient(String table) {
        this.table = table;
        setupDefaultHeaders();
        setupUri();
    }


    public IxcResponse GET(Class<?> responseFormat) {
        enableListingHeader();
        HttpResponse<String> response = sendRequestAndGetResponse("POST");
        return new IxcResponse(response, responseFormat);
    }

    public IxcResponse POST(Class<?> responseFormat) {
        disableListingHeader();
        HttpResponse<String> response = sendRequestAndGetResponse("POST");
        return new IxcResponse(response, responseFormat);
    }

    public IxcResponse PUT(Class<?> responseFormat) {
        disableListingHeader();
        HttpResponse<String> response = sendRequestAndGetResponse("PUT");
        return new IxcResponse(response, responseFormat);
    }

    public IxcResponse DELETE(Class<?> responseFormat) {
        disableListingHeader();
        HttpResponse<String> response = sendRequestAndGetResponse("DELETE");
        return new IxcResponse(response, responseFormat);
    }


    protected void setBody(String body) {
        this.body = body;
    }

    protected String getTable() {
        return table;
    }


    private HttpResponse<String> sendRequestAndGetResponse(String method) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(uri);

            // OBS: Definindo o (method) de forma dinâmica
            requestBuilder.method(method, HttpRequest.BodyPublishers.ofString(body));

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
