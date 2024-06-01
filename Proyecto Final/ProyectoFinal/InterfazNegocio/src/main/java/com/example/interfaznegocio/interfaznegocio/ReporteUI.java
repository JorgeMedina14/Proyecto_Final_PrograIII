package com.example.interfaznegocio.interfaznegocio;

import com.example.interfaznegocio.models.Reporte;
import com.example.interfaznegocio.models.Venta;
import com.google.gson.Gson;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ReporteUI {
    private DatePicker dpInicio;
    private DatePicker dpFin;
    private TextField txtProductoId;
    private TextField txtClienteId;
    private TextField txtProveedorId;
    private TableView<Venta> tblVentas;
    private Label lblTotalVentas;
    private Button btnGenerarPorTiempo;
    private Button btnGenerarPorProducto;
    private Button btnGenerarPorCliente;
    private Button btnGenerarPorProveedor;
    private HttpClient httpClient;

    public ReporteUI() {
        httpClient = HttpClient.newHttpClient();
    }

    public Node getVista() {
        inicializarComponentes();
        configurarTabla();

        btnGenerarPorTiempo.setOnAction(event -> generarReportePorTiempo());
        btnGenerarPorProducto.setOnAction(event -> generarReportePorProducto());
        btnGenerarPorCliente.setOnAction(event -> generarReportePorCliente());
        btnGenerarPorProveedor.setOnAction(event -> generarReportePorProveedor());

        return new VBox(
                dpInicio, dpFin, btnGenerarPorTiempo,
                txtProductoId, btnGenerarPorProducto,
                txtClienteId, btnGenerarPorCliente,
                txtProveedorId, btnGenerarPorProveedor,
                tblVentas, lblTotalVentas
        );
    }

    private void inicializarComponentes() {
        dpInicio = new DatePicker();
        dpInicio.setPromptText("Fecha Inicio");
        dpFin = new DatePicker();
        dpFin.setPromptText("Fecha Fin");
        txtProductoId = new TextField();
        txtProductoId.setPromptText("ID Producto");
        txtClienteId = new TextField();
        txtClienteId.setPromptText("ID Cliente");
        txtProveedorId = new TextField();
        txtProveedorId.setPromptText("ID Proveedor");
        tblVentas = new TableView<>();
        lblTotalVentas = new Label("Total Ventas: 0.0");
        btnGenerarPorTiempo = new Button("Generar Reporte por Tiempo");
        btnGenerarPorProducto = new Button("Generar Reporte por Producto");
        btnGenerarPorCliente = new Button("Generar Reporte por Cliente");
        btnGenerarPorProveedor = new Button("Generar Reporte por Proveedor");
    }

    private void configurarTabla() {
        TableColumn<Venta, Long> colId = new TableColumn<>("ID");
        TableColumn<Venta, Double> colTotalVenta = new TableColumn<>("Total Venta");
        TableColumn<Venta, Date> colFechaVenta = new TableColumn<>("Fecha Venta");
        TableColumn<Venta, String> colEstado = new TableColumn<>("Estado");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTotalVenta.setCellValueFactory(new PropertyValueFactory<>("totalVenta"));
        colFechaVenta.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblVentas.getColumns().addAll(colId, colTotalVenta, colFechaVenta, colEstado);
    }

    private void generarReportePorTiempo() {
        LocalDate inicio = dpInicio.getValue();
        LocalDate fin = dpFin.getValue();
        if (inicio == null || fin == null) {
            System.err.println("Fechas son obligatorias.");
            return;
        }
        Date inicioDate = Date.from(inicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date finDate = Date.from(fin.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String url = String.format("http://localhost:8080/reportes/tiempo/%s/%s", inicioDate.getTime(), finDate.getTime());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::actualizarTablaVentas)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private void generarReportePorProducto() {
        String productoId = txtProductoId.getText();
        if (productoId.isEmpty()) {
            System.err.println("ID del producto es obligatorio.");
            return;
        }
        String url = String.format("http://localhost:8080/reportes/producto/%s", productoId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::actualizarTablaVentas)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private void generarReportePorCliente() {
        String clienteId = txtClienteId.getText();
        if (clienteId.isEmpty()) {
            System.err.println("ID del cliente es obligatorio.");
            return;
        }
        String url = String.format("http://localhost:8080/reportes/cliente/%s", clienteId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::actualizarTablaVentas)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private void generarReportePorProveedor() {
        String proveedorId = txtProveedorId.getText();
        if (proveedorId.isEmpty()) {
            System.err.println("ID del proveedor es obligatorio.");
            return;
        }
        String url = String.format("http://localhost:8080/reportes/proveedor/%s", proveedorId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::actualizarTablaVentas)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private void actualizarTablaVentas(String json) {
        Gson gson = new Gson();
        Reporte reporte = gson.fromJson(json, Reporte.class);
        if (reporte == null) {
            System.err.println("No se pudo obtener el reporte.");
            return;
        }
        var ventas = reporte.getVentas();
        double totalVentas = reporte.getTotalVentas();

        javafx.application.Platform.runLater(() -> {
            tblVentas.getItems().clear();
            tblVentas.getItems().addAll(ventas);
            lblTotalVentas.setText("Total Ventas: " + totalVentas);
        });
    }
}
