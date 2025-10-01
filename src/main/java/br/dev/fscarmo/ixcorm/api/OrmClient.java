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
 * A classe 'OrmClient' manipula a query de busca, constrói o manipulador de requisições HTTP e fornece acesso aos
 * métodos de requisição de forma padronizada.
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.2.3
 * @since 2025-09-27
 */
public abstract class OrmClient {

    private static final HttpResponse.BodyHandler<String> BODY_HANDLER =
            HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8);

    private final List<Header> headers = new ArrayList<>();
    private final String table;
    private HttpRequest.BodyPublisher publisher;
    private String query;
    private URI uri;

    /**
     * @param table Aqui, representa o endpoint do IXC Provedor para o qual a requisição será enviada.
     */
    protected OrmClient(String table) {
        this.table = table;
        setupDefaultHeaders();
    }

    /**
     * <p>
     * Envia uma requisição HTTP para a API do IXC Provedor, para listar registros, filtrando-os pela query de busca
     * definida por <b>setQuery(String query).</b>
     * A requisição é do tipo POST, o que define que ela irá executar uma listagem de registros é a presença do header:
     * ["ixcsoft": "listar"].
     * </p>
     *
     * @return Um objeto {@link IxcResponse}.
     * @throws NetworkConnectionException Se ocorrer alguma falha na comunicação com o IXC Provedor.
     */
    public IxcResponse GET() throws NetworkConnectionException {
        setupUri();
        enableIxcListingHeader();
        setupPublisherWithBody(query);
        HttpResponse<String> response = sendCreatedRequest(Method.POST);
        return new IxcResponse(response);
    }

    /**
     * <p>
     * Envia uma requisição HTTP para a API do IXC Provedor, para inserir um novo registro no banco de dados, na tabela
     * definida pelo prâmetro <b>(String table)</b> no construtor.
     * </p>
     *
     * @param record O novo registro a ser inserido no banco de dados.
     * @return Um objeto {@link IxcResponse} contento o status e uma mensagem com a informação sobre o resultado da
     * requisição.
     * @throws NetworkConnectionException Se ocorrer alguma falha na comunicação com o IXC Provedor.
     */
    public IxcResponse POST(IxcRecord record) throws NetworkConnectionException {
        setupUri(record.getId());
        disableIxcListingHeader();
        setupPublisherWithBody(record.toJsonString());
        HttpResponse<String> response = sendCreatedRequest(Method.POST);
        return new IxcResponse(response);
    }

    /**
     * <p>
     * Envia uma requisição HTTP para a API do IXC Provedor, para atualizar um ou mais colunas de um registro no banco
     * de dados, na tabela definida pelo prâmetro <b>(String table)</b> no construtor.
     * </p>
     *
     * @param record O registro com os campos a serem atualizados no banco de dados.
     * @return Um objeto {@link IxcResponse} contento o status da requisição e uma mensagem que pode ser de sucesso ou
     * de erro, dependendo do status.
     * @throws NetworkConnectionException Se ocorrer alguma falha na comunicação com o IXC Provedor.
     */
    public IxcResponse PUT(IxcRecord record) throws NetworkConnectionException {
        setupUri(record.getId());
        disableIxcListingHeader();
        setupPublisherWithBody(record.toJsonString());
        HttpResponse<String> response = sendCreatedRequest(Method.PUT);
        return new IxcResponse(response);
    }

    /**
     * <p>
     *
     * </p>
     *
     * @param id Um {@link Long} com o id do ergistro a ser removido do banco de dados do IXC Provedor.
     * @return Um objeto {@link IxcResponse}.
     * @throws NetworkConnectionException Se ocorrer alguma falha na comunicação com o IXC Provedor.
     */
    public IxcResponse DELETE(int id) throws NetworkConnectionException {
        setupUri(id);
        disableIxcListingHeader();
        setupPublisherWithoutBody();
        HttpResponse<String> response = sendCreatedRequest(Method.DELETE);
        return new IxcResponse(response);
    }

    /**
     * @param query Uma {@link String} JSON com o corpo da query no formato exigido pela API do IXC Provedor.
     */
    protected void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return Uma {@link String} com o endpoint para o qual as requsições serão enviadas.
     */
    protected String getTable() {
        return table;
    }

    private HttpResponse<String> sendCreatedRequest(Method method) throws NetworkConnectionException {
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest.Builder builder = HttpRequest.newBuilder(uri);
            builder.method(method.value(), publisher);
            headers.forEach(h -> builder.setHeader(h.getName(), h.getValue()));

            return client.send(builder.build(), BODY_HANDLER);
        }
        catch (IllegalArgumentException | UncheckedIOException | InterruptedException | IOException e) {
            throw new NetworkConnectionException();
        }
    }

    private void setupUri() {
        String domain = IxcContext.INSTANCE.getEnv().getDomain();
        uri = URI.create("https://"+ domain +"/webservice/v1/"+ table);
    }

    private void setupUri(Integer id) {
        String domain = IxcContext.INSTANCE.getEnv().getDomain();
        uri = URI.create("https://"+ domain +"/webservice/v1/"+ table +"/"+ id);
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

    private void setupPublisherWithBody(String body) {
        publisher = HttpRequest.BodyPublishers.ofString(body);
    }

    private void setupPublisherWithoutBody() {
        publisher = HttpRequest.BodyPublishers.noBody();
    }

    private String getEncodedTokenFromContext() {
        String tokenFromEnv = IxcContext.INSTANCE.getEnv().getToken();
        byte[] bytes = tokenFromEnv.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
