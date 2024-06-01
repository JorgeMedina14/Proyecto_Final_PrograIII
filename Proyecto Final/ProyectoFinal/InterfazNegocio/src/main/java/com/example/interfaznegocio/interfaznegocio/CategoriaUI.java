package com.example.interfaznegocio.interfaznegocio;

import com.example.interfaznegocio.models.Categoria;
import com.google.gson.Gson;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CategoriaUI {
    private TextField txtNombre;
    private TableView<Categoria> tblCategorias;
    private Button btnCrear;
    private Button btnEliminar;
    private Button btnRecargar;
    private HttpClient httpClient;

    public Node getVista() {
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de la categoría");
        tblCategorias = new TableView<>();
        btnCrear = new Button("Crear");
        btnEliminar = new Button("Eliminar");
        btnRecargar = new Button("Recargar");
        httpClient = HttpClient.newHttpClient();

        TableColumn<Categoria, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tblCategorias.getColumns().add(colNombre);

        btnCrear.setOnAction(event -> crearCategoria());
        btnEliminar.setOnAction(event -> eliminarCategoria());
        btnRecargar.setOnAction(event -> leerCategorias());

        leerCategorias();

        return new VBox(txtNombre, btnCrear, btnEliminar, btnRecargar, tblCategorias);
    }

    private void crearCategoria() {
        if (txtNombre.getText().isEmpty()) {
            showAlert("Error", "Campos vacíos", "Por favor, llena todos los campos.");
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(txtNombre.getText());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/producto/categorias"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(categoria)))
                .build();

        enviarSolicitud(request);
        limpiarCampos();
        leerCategorias();
    }

    private void leerCategorias() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/producto/categorias"))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::llenarCategorias)
                .join();
    }

    private void llenarCategorias(String categoriasJson) {
        Categoria[] categorias = new Gson().fromJson(categoriasJson, Categoria[].class);
        tblCategorias.getItems().clear();
        tblCategorias.getItems().addAll(categorias);
    }

    private void eliminarCategoria() {
        Categoria categoria = tblCategorias.getSelectionModel().getSelectedItem();
        if (categoria == null) {
            showAlert("Error", "Categoría no seleccionada", "Por favor, selecciona una categoría.");
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/producto/categorias/" + categoria.getId()))
                .DELETE()
                .build();

        enviarSolicitud(request);
        limpiarCampos();
        leerCategorias();
    }

    public void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void enviarSolicitud(HttpRequest request) {
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

    }

    private void limpiarCampos() {
        txtNombre.clear();
    }
}