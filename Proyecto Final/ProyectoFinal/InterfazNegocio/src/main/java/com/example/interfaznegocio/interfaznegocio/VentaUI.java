package com.example.interfaznegocio.interfaznegocio;

import com.example.interfaznegocio.models.Cliente;
import com.example.interfaznegocio.models.Producto;
import com.example.interfaznegocio.models.Venta;
import com.example.interfaznegocio.models.VentaProducto;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.kafka.common.protocol.types.Field;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VentaUI {
    private TextField txtClienteId;
    private TextField txtProductoId;
    private TextField txtCantidad;
    private TableView<VentaProducto> tblProductos;
    private Label lblTotal;
    private Label lblClienteNombre;
    private Label lblClienteEmail;
    private Button btnBuscarCliente;
    private Button btnAgregarProducto;
    private Button btnRealizarVenta;
    private HttpClient httpClient;
    private List<VentaProducto> productos;
    private double totalVenta;

    public Node getVista() {
        httpClient = HttpClient.newHttpClient();
        productos = new ArrayList<>();
        totalVenta = 0.0;

        inicializarComponentes();

        return new VBox(
                new HBox(new Label("ID Cliente:"), txtClienteId, btnBuscarCliente, lblClienteNombre, lblClienteEmail),
                new HBox(new Label("ID Producto:"), txtProductoId, new Label("Cantidad:"), txtCantidad, btnAgregarProducto),
                tblProductos,
                new HBox(new Label("Total Venta:"), lblTotal),
                btnRealizarVenta
        );
    }

    private void inicializarComponentes() {
        txtClienteId = new TextField();
        txtProductoId = new TextField();
        txtCantidad = new TextField();
        tblProductos = new TableView<>();
        lblTotal = new Label("0.0");
        lblClienteNombre = new Label();
        lblClienteEmail = new Label();
        btnBuscarCliente = new Button("Buscar Cliente");
        btnAgregarProducto = new Button("Agregar Producto");
        btnRealizarVenta = new Button("Realizar Venta");

        configurarTabla();

        btnBuscarCliente.setOnAction(event -> buscarCliente());
        btnAgregarProducto.setOnAction(event -> agregarProducto());
        btnRealizarVenta.setOnAction(event -> realizarVenta());
    }

    private void buscarCliente() {
        Long clienteId = Long.parseLong(txtClienteId.getText());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/cliente/" + clienteId))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    Gson gson = new Gson();
                    Cliente cliente = gson.fromJson(json, Cliente.class);
                    Platform.runLater(() -> {
                        lblClienteNombre.setText(cliente.getNombre());
                        lblClienteEmail.setText(cliente.getEmail());
                    });
                });
    }

    private void configurarTabla() {
        TableColumn<VentaProducto, String> colProducto = new TableColumn<>("Producto");
        TableColumn<VentaProducto, Integer> colCantidad = new TableColumn<>("Cantidad");
        TableColumn<VentaProducto, Double> colPrecio = new TableColumn<>("Precio");
        TableColumn<VentaProducto, Double> colSubtotal = new TableColumn<>("Subtotal");

        colProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProducto().getNombre()));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProducto().getPrecio()).asObject());
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        tblProductos.getColumns().addAll(colProducto, colCantidad, colPrecio, colSubtotal);
    }

    private void agregarProducto() {
        long productoId = Long.parseLong(txtProductoId.getText());
        Integer cantidad = Integer.parseInt(txtCantidad.getText());

        // Obtener el producto del servidor
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/producto/" + productoId))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    Gson gson = new Gson();
                    Producto producto = gson.fromJson(json, Producto.class);
                    Platform.runLater(() -> {
                        agregarProductoATabla(producto, cantidad);
                        actualizarTotalVenta();
                    });
                });
    }

    private void agregarProductoATabla(Producto producto, Integer cantidad) {
        double subtotal = producto.getPrecio() * cantidad;
        String proveedor = producto.getProveedor().getNombre();
        VentaProducto ventaProducto = new VentaProducto(producto, proveedor, cantidad, subtotal);

        productos.add(ventaProducto);
        tblProductos.getItems().add(ventaProducto);
        actualizarTotalVenta();
    }

    private void actualizarTotalVenta() {
        totalVenta = productos.stream()
                .mapToDouble(VentaProducto::getSubtotal)
                .sum();
        lblTotal.setText(String.valueOf(totalVenta));
    }

    private void realizarVenta() {
        Long clienteId = Long.parseLong(txtClienteId.getText());
        String clienteEmail = lblClienteEmail.getText();
        String fechaVenta = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        Venta venta = new Venta(productos, clienteId, clienteEmail, totalVenta, fechaVenta, "recibido");
        Gson gson = new Gson();
        String json = gson.toJson(venta);

        System.out.println(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/venta"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        Platform.runLater(() -> {
                            productos.clear();
                            tblProductos.getItems().clear();
                            lblTotal.setText("0.0");
                            txtClienteId.clear();
                            txtProductoId.clear();
                            txtCantidad.clear();
                        });
                    }
                });
    }
}
