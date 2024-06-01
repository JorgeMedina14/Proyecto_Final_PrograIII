package com.example.interfaznegocio.interfaznegocio;

import com.example.interfaznegocio.models.Proveedor;
import com.example.interfaznegocio.models.Producto;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class ProveedorUI {
    private TextField txtNombre;
    private TextField txtDireccion;
    private TableView<Proveedor> tblProveedores;
    private Button btnCrear;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnRecargar;
    private HttpClient httpClient;

    public ProveedorUI() {
        httpClient = HttpClient.newHttpClient();
    }

    public Node getVista() {
        inicializarComponentes();
        configurarTabla();
        leerProveedores();

        btnCrear.setOnAction(event -> crearProveedor());
        btnActualizar.setOnAction(event -> actualizarProveedor());
        btnEliminar.setOnAction(event -> eliminarProveedor());
        btnRecargar.setOnAction(event -> leerProveedores());

        tblProveedores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtNombre.setText(newSelection.getNombre());
                txtDireccion.setText(newSelection.getDireccion());
            }
        });

        return new VBox(txtNombre, txtDireccion, btnCrear, btnActualizar, btnEliminar, btnRecargar, tblProveedores);
    }

    private void inicializarComponentes() {
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección");
        tblProveedores = new TableView<>();
        btnCrear = new Button("Crear");
        btnActualizar = new Button("Actualizar");
        btnEliminar = new Button("Eliminar");
        btnRecargar = new Button("Recargar");
    }

    private void configurarTabla() {
        TableColumn<Proveedor, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Proveedor, String> colDireccion = new TableColumn<>("Dirección");

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        tblProveedores.getColumns().addAll(colNombre, colDireccion);
    }

    private void crearProveedor() {
        if (txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            System.err.println("Todos los campos son obligatorios.");
            return;
        }
        Proveedor proveedor = new Proveedor(null, txtNombre.getText(), txtDireccion.getText(), null);
        Gson gson = new Gson();
        String json = gson.toJson(proveedor);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/proveedor"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        leerProveedores();
                        limpiarCampos();
                    } else {
                        System.err.println("Error al crear proveedor: " + response.body());
                    }
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private void leerProveedores() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/proveedor"))
                .GET()
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::actualizarTablaProveedores)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private void actualizarProveedor() {
        Proveedor proveedor = tblProveedores.getSelectionModel().getSelectedItem();
        if (proveedor == null) {
            System.err.println("Selecciona un proveedor para actualizar.");
            return;
        }
        proveedor.setNombre(txtNombre.getText());
        proveedor.setDireccion(txtDireccion.getText());
        Gson gson = new Gson();
        String json = gson.toJson(proveedor);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/proveedor/" + proveedor.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        leerProveedores();
                        limpiarCampos();
                    } else {
                        System.err.println("Error al actualizar proveedor: " + response.body());
                    }
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private void eliminarProveedor() {
        Proveedor proveedor = tblProveedores.getSelectionModel().getSelectedItem();
        if (proveedor == null) {
            System.err.println("Selecciona un proveedor para eliminar.");
            return;
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/proveedor/" + proveedor.getId()))
                .DELETE()
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        leerProveedores();
                        limpiarCampos();
                    } else {
                        System.err.println("Error al eliminar proveedor: " + response.body());
                    }
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private void actualizarTablaProveedores(String json) {
        Gson gson = new Gson();
        Proveedor[] proveedores = gson.fromJson(json, Proveedor[].class);
        javafx.application.Platform.runLater(() -> {
            tblProveedores.getItems().clear();
            tblProveedores.getItems().addAll(proveedores);
        });
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDireccion.clear();
    }
}
