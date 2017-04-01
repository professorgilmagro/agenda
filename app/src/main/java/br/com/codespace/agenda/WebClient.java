package br.com.codespace.agenda;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by gilma on 01/04/2017.
 */

public class WebClient {
    public String post(String json) {
        try {
            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // determina o tipo de dados de envio
            connection.setRequestProperty("Content-Type", "application/json");

            // determina o tipo de dados esperado para retorno
            connection.setRequestProperty("Accept", "application/json");

            // define que será utilizado o method POST
            connection.setDoOutput(true);

            // prepara os dados para envio
            PrintStream output = new PrintStream(connection.getOutputStream());
            output.println(json);
            connection.connect();

            // recebe a resposta do servidor
            Scanner scanner = new Scanner(connection.getInputStream());
            return scanner.next();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

