package br.dev.fscarmo.ixcorm;


import java.net.http.HttpResponse;


public class IxcResponse {

    HttpResponse<String> response;

    public IxcResponse(HttpResponse<String> response) {
        this.response = response;
    }

    public void print() {
        IO.println(response.statusCode());
        IO.println(response.body());
    }
}
