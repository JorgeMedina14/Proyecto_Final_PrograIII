package com.example.interfaznegocio.interfaznegocio;

import com.example.interfaznegocio.models.Cliente;
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

public class ClienteUI {
    private TextField txtCodigo;
    private TextField txtNombre;
    private TextField txtEmail;
    private TextField txtDireccion;
    private TableView<Cliente> tblClientes;
    private Button btnCrear;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnRecargar;
    private HttpClient httpClient;

    public Node getVista() {
        txtCodigo = new TextField();
        txtCodigo.setPromptText("Código");
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección");
        tblClientes = new TableView<>();
        btnCrear = new Button("Crear");
        btnActualizar = new Button("Actualizar");
        btnEliminar = new Button("Eliminar");
        btnRecargar = new Button("Recargar");
        httpClient = HttpClient.newHttpClient();

        TableColumn<Cliente, String> colCodigo = new TableColumn<>("Código");
        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        TableColumn<Cliente, String> colDireccion = new TableColumn<>("Direccion");

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        tblClientes.getColumns().add(colCodigo);
        tblClientes.getColumns().add(colNombre);
        tblClientes.getColumns().add(colEmail);
        tblClientes.getColumns().add(colDireccion);
        leerClientes();

        btnCrear.setOnAction(event -> crearCliente());
        btnActualizar.setOnAction(event -> actualizarCliente());
        btnEliminar.setOnAction(event -> eliminarCliente());
        btnRecargar.setOnAction(event -> leerClientes());

        httpClient = HttpClient.newHttpClient();

        tblClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtCodigo.setText(newSelection.getCodigo());
                txtNombre.setText(newSelection.getNombre());
                txtEmail.setText(newSelection.getEmail());
                txtDireccion.setText(newSelection.getDireccion());
            }
        });

        return new VBox(txtCodigo, txtNombre, txtEmail, txtDireccion, btnCrear, btnActualizar, btnEliminar, btnRecargar, tblClientes);
    }

    // Métodos para crear, leer, actualizar y eliminar clientes

    private void crearCliente() {
        Cliente cliente = new Cliente(null, txtCodigo.getText(), txtNombre.getText(), txtEmail.getText(), txtDireccion.getText());
        Gson gson = new Gson();
        String json = gson.toJson(cliente);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/cliente"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> leerClientes());

        limpiarCampos();
    }

    private void leerClientes() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/cliente"))
                .GET()
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::actualizarTablaClientes);
    }

    private void actualizarCliente() {
        Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
        cliente.setCodigo(txtCodigo.getText());
        cliente.setNombre(txtNombre.getText());
        cliente.setEmail(txtEmail.getText());
        cliente.setDireccion(txtDireccion.getText());
        Gson gson = new Gson();
        String json = gson.toJson(cliente);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/cliente/" + cliente.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> leerClientes());

        limpiarCampos();
    }

    private void eliminarCliente() {
        Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/cliente/" + cliente.getId()))
                .DELETE()
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> leerClientes());

        limpiarCampos();
    }

    private void actualizarTablaClientes(String json) {
        Gson gson = new Gson();
        Cliente[] clientes = gson.fromJson(json, Cliente[].class);
        tblClientes.getItems().clear();
        tblClientes.getItems().addAll(clientes);
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtEmail.clear();
        txtDireccion.clear();
    }
}