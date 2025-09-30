package br.dev.fscarmo.ixcorm.api;


import br.dev.fscarmo.ixcorm.IxcContext;
import br.dev.fscarmo.ixcorm.IxcRecord;
import br.dev.fscarmo.ixcorm.IxcResponse;
import br.dev.fscarmo.ixcorm.api.records.Header;
import br.dev.fscarmo.ixcorm.enums.Method;
import br.dev.fscarmo.ixcorm.exception.NetworkConnectionException;

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

    private final List<Header> headers = new ArrayList<>();
    private final String table;
    private String query;
    private URI uri;

    /**
     * @param table Aqui, representa endpoint para o qual a requisição será executada.
     */
    protected OrmClient(String table) {
        this.table = table;
        setupDefaultHeaders();
    }

    /**
     * <p>
     * Envia uma requisição HTTP para a API do IXC Provedor, para listar registros filtrados pela query de busca.
     * A requisição é do tipo POST, o que define que ela irá listar registros é a presença do header:
     * ["ixcsoft": "listar"].
     * </p>
     *
     * @return Um objeto {@link IxcResponse}.
     * @throws NetworkConnectionException Se ocorrer alguma falha na comunicação com o IXC Provedor.
     */
    public IxcResponse GET() throws NetworkConnectionException {
        setupUri();
        enableIxcListingHeader();
        HttpResponse<String> response = sendRequest(Method.POST, query);
        return new IxcResponse(response);
    }

    /**
     * <p>
     * Envia uma requisição HTTP para a API do IXC Provedor, para listar registros filtrados pela query de busca.
     * A requisição é do tipo POST, o que define que ela irá listar registros é a presença do header:
     * ["ixcsoft": "listar"].
     * </p>
     *
     * @return Um objeto {@link IxcResponse}.
     * @throws NetworkConnectionException Se ocorrer alguma falha na comunicação com o IXC Provedor.
     */
    public IxcResponse POST(IxcRecord record) throws NetworkConnectionException {
        setupUri(record.getId());
        disableIxcListingHeader();
        HttpResponse<String> response = sendRequest(Method.POST, record.toJsonString());
        return new IxcResponse(response);
    }

    /**
     * TODO: Documentar
     * @param record
     * @return
     * @throws NetworkConnectionException
     */
    public IxcResponse PUT(IxcRecord record) throws NetworkConnectionException {
        setupUri(record.getId());
        disableIxcListingHeader();
        HttpResponse<String> response = sendRequest(Method.PUT, record.toJsonString());
        return new IxcResponse(response);
    }

    /**
     * TODO: Documentar
     * @param id
     * @return
     * @throws NetworkConnectionException
     */
    public IxcResponse DELETE(Long id) throws NetworkConnectionException {
        setupUri(id);
        disableIxcListingHeader();
        HttpResponse<String> response = sendRequest(Method.DELETE);
        return new IxcResponse(response);
    }

    /**
     * TODO: Documentar
     * @param query
     */
    protected void setQuery(String query) {
        this.query = query;
    }

    /**
     * TODO: Documentar
     * @return
     */
    protected String getTable() {
        return table;
    }

    @SuppressWarnings("SameParameterValue")
    private HttpResponse<String> sendRequest(Method method) throws NetworkConnectionException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest.Builder builder = getCommonRequestBuilder(method, HttpRequest.BodyPublishers.noBody());
            return client.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        }
        catch (UncheckedIOException | InterruptedException | IOException e) {
            throw new NetworkConnectionException();
        }
    }

    private HttpResponse<String> sendRequest(Method method, String body) throws NetworkConnectionException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest.Builder builder = getCommonRequestBuilder(method, HttpRequest.BodyPublishers.ofString(body));
            return client.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        }
        catch (UncheckedIOException | InterruptedException | IOException e) {
            throw new NetworkConnectionException();
        }
    }

    private HttpRequest.Builder getCommonRequestBuilder(Method method, HttpRequest.BodyPublisher publisher) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(uri);
        requestBuilder.method(method.value(), publisher);
        headers.forEach(h -> requestBuilder.setHeader(h.getName(), h.getValue()));
        return requestBuilder;
    }

    private void setupUri() {
        String domain = IxcContext.INSTANCE.getEnv().getDomain();
        uri = URI.create("https://"+ domain +"/webservice/v1/"+ table);
    }

    private void setupUri(Long id) {
        String domain = IxcContext.INSTANCE.getEnv().getDomain();
        uri = URI.create("https://"+ domain +"/webservice/v1/"+ table +"/"+ id.toString());
    }

    private void setupDefaultHeaders() {
        String encodedToken = getEncodedTokenFromContext();
        headers.add(Header.of("Authorization", "Basic "+ encodedToken));
        headers.add(Header.of("Content-Type", "application/json"));
        headers.add(Header.of("ixcsoft", ""));
    }

    private void enableIxcListingHeader() {
        headers.stream()
                .filter(h -> h.hasName("ixcsoft"))
                .findFirst().ifPresent(h -> h.setValue("listar"));
    }

    private void disableIxcListingHeader() {
        headers.stream()
                .filter(h -> h.hasName("ixcsoft"))
                .findFirst().ifPresent(h -> h.setValue(""));
    }


    private String getEncodedTokenFromContext() {
        String tokenFromEnv = IxcContext.INSTANCE.getEnv().getToken();
        byte[] bytes = tokenFromEnv.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
