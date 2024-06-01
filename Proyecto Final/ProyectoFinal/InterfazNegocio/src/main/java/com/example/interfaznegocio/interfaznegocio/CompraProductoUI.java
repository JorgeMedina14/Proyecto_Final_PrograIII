package com.example.interfaznegocio.interfaznegocio;

import com.example.interfaznegocio.models.CompraProducto;
import com.example.interfaznegocio.models.Producto;
import com.example.interfaznegocio.models.Proveedor;
import com.google.gson.Gson;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CompraProductoUI {
    private ComboBox<String> cbxProducto;
    private ComboBox<String> cbxProveedor;
    private TextField txtCantidad;
    private TableView<CompraProducto> tblCompraProductos;
    private Button btnCrear;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnRecargar;
    private HttpClient httpClient;

    public Node getVista() {
        cbxProducto = new ComboBox<>();
        cbxProducto.setPromptText("Nombre del producto");
        cbxProveedor = new ComboBox<>();
        cbxProveedor.setPromptText("Nombre del proveedor");
        txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");
        tblCompraProductos = new TableView<>();
        btnCrear = new Button("Crear");
        btnActualizar = new Button("Actualizar");
        btnEliminar = new Button("Eliminar");
        btnRecargar = new Button("Recargar");
        httpClient = HttpClient.newHttpClient();

        TableColumn<CompraProducto, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        TableColumn<CompraProducto, String> colProveedor = new TableColumn<>("Proveedor");
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        TableColumn<CompraProducto, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        tblCompraProductos.getColumns().addAll(colProducto, colProveedor, colCantidad);

        obtenerProductos();
        obtenerProveedores();
        leerCompraProductos();

        tblCompraProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cbxProducto.setValue(newSelection.getProducto());
                cbxProveedor.setValue(newSelection.getProveedor());
                txtCantidad.setText(newSelection.getCantidad().toString());
            }
        });

        btnCrear.setOnAction(event -> crearCompraProducto());
        btnActualizar.setOnAction(event -> actualizarCompraProducto());
        btnEliminar.setOnAction(event -> eliminarCompraProducto());
        btnRecargar.setOnAction(event -> leerCompraProductos());

        return new VBox(cbxProducto, cbxProveedor, txtCantidad, btnCrear, btnActualizar, btnEliminar, btnRecargar, tblCompraProductos);
    }

    private void obtenerProductos() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/producto"))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::llenarProductos)
                .join();

    }

    private void llenarProductos(String productosJson) {
        Producto[] productos = new Gson().fromJson(productosJson, Producto[].class);
        for (Producto producto : productos) {
            cbxProducto.getItems().add(producto.getNombre());
        }
    }

    private void obtenerProveedores() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/proveedor"))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::llenarProveedores)
                .join();
    }

    private void llenarProveedores(String proveedoresJson) {
        Proveedor[] proveedores = new Gson().fromJson(proveedoresJson, Proveedor[].class);
        for (Proveedor proveedor : proveedores) {
            cbxProveedor.getItems().add(proveedor.getNombre());
        }
    }

    private void crearCompraProducto() {
        // Si los campos están vacíos mostrar una alerta
        if (cbxProducto.getValue() == null || cbxProveedor.getValue() == null || txtCantidad.getText().isEmpty()) {
            showAlert("Error", "Campos vacíos", "Por favor, llene todos los campos.");
            return;
        }

        // Crear un objeto CompraProducto con los datos de los campos
        CompraProducto compraProducto = new CompraProducto();
        compraProducto.setId(0L);
        compraProducto.setProducto(cbxProducto.getValue());
        compraProducto.setProveedor(cbxProveedor.getValue());
        compraProducto.setCantidad(Integer.parseInt(txtCantidad.getText()));

        // Crear una solicitud HTTP POST con el objeto CompraProducto
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/compra_producto"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(compraProducto)))
                .build();

        // Enviar la solicitud
        enviarSolicitud(request);
        // Limpiar los campos
        leerCompraProductos();
        limpiarCampos();
    }

    private void leerCompraProductos() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/compra_producto"))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::llenarCompraProductos)
                .join();
    }

    private void actualizarCompraProducto() {
        // Código para actualizar CompraProducto...
        if (cbxProducto.getValue() == null || cbxProveedor.getValue() == null || txtCantidad.getText().isEmpty()) {
            showAlert("Error", "Campos vacíos", "Por favor, llene todos los campos.");
            return;
        }

        CompraProducto compraProducto = tblCompraProductos.getSelectionModel().getSelectedItem();
        if (compraProducto == null) {
            showAlert("Error", "No se seleccionó ningún producto para actualizar", "Por favor seleccione un producto de la tabla.");
            return;
        }

        compraProducto.setProducto(cbxProducto.getValue());
        compraProducto.setProveedor(cbxProveedor.getValue());
        compraProducto.setCantidad(Integer.parseInt(txtCantidad.getText()));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/compra_producto/" + compraProducto.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(new Gson().toJson(compraProducto)))
                .build();

        enviarSolicitud(request);
        leerCompraProductos();
        limpiarCampos();
    }

    private void eliminarCompraProducto() {
        // Código para eliminar CompraProducto...
        CompraProducto compraProducto = tblCompraProductos.getSelectionModel().getSelectedItem();
        if (compraProducto == null) {
            showAlert("Error", "No se seleccionó ningún producto para eliminar", "Por favor seleccione un producto de la tabla.");
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/compra_producto/" + compraProducto.getId()))
                .DELETE()
                .build();

        enviarSolicitud(request);
        leerCompraProductos();
        limpiarCampos();
    }

    private void enviarSolicitud(HttpRequest request) {
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }

    private void llenarCompraProductos(String compraProductosJson) {
        CompraProducto[] compraProductos = new Gson().fromJson(compraProductosJson, CompraProducto[].class);
        tblCompraProductos.getItems().clear();
        tblCompraProductos.getItems().addAll(compraProductos);
    }

    public void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        cbxProducto.setValue(null);
        cbxProveedor.setValue(null);
        txtCantidad.clear();
    }
}