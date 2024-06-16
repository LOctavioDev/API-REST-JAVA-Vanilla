import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        // Crear el servidor HTTP y asignar la direccion y el puerto
        HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);

        // Crear un contexto para la ruta raiz y asignarel un manejador
        server.createContext("/", new MyHandler());

        // Inicar el servidro
        server.setExecutor(null); // Usa el ejecutor por defecto
        server.start();
        System.out.println("SERVER RUNNING AT http://localhost:3000");
    }

    // Clase para manejar las solicitudes HTTP
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Verificar si el metodo es GET y si la ruta es "/"
            if ("GET".equals(exchange.getRequestMethod()) && "/".equals(exchange.getRequestURI().getPath())) {
                // Creaar la respuesta en formato JSON
                String response = "{\"message\":\"Hola desde: Java\", \"framework\": false}";

                // Establecer el codigo de estado y el tipo de contenido de la respuesta
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseHeaders().set("Content-Type", "application/json");

                // Enviar la respuesta
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                // En el caso de que no enviamo sun 404 (Not Found)
                exchange.sendResponseHeaders(404, -1); // -1 Indica que no hya cuerpo
            }
        }
    }

}