package com.example.interfaznegocio.interfaznegocio;


import com.example.interfaznegocio.models.Categoria;
import com.example.interfaznegocio.models.Producto;
import com.example.interfaznegocio.models.Proveedor;
import com.google.gson.Gson;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProductoUI {
    private TextField txtNombre;
    private TextField txtPrecio;
    private ComboBox<String> cbxCategorias;
    private ComboBox<Proveedor> cbxProveedores;
    private TextField txtStock;
    private TableView<Producto> tblProductos;
    private Button btnCrear;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnRecargar;
    private HttpClient httpClient;

    private void obtenerCategorias() {
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
        for (Categoria categoria : categorias) {
            cbxCategorias.getItems().add(categoria.getNombre());
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
            cbxProveedores.getItems().add(proveedor);
        }
    }

    public Node getVista() {
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");
        cbxCategorias = new ComboBox<>();
        cbxCategorias.setPromptText("Categoría");
        cbxProveedores = new ComboBox<>();
        cbxProveedores.setPromptText("Proveedor");
        txtStock = new TextField();
        txtStock.setPromptText("Stock");
        tblProductos = new TableView<>();
        btnCrear = new Button("Crear");
        btnActualizar = new Button("Actualizar");
        btnEliminar = new Button("Eliminar");
        btnRecargar = new Button("Recargar");
        httpClient = HttpClient.newHttpClient();

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        TableColumn<Producto, Categoria> colCategoria = new TableColumn<>("Categoría");
        TableColumn<Producto, Proveedor> colProveedor = new TableColumn<>("Proveedor");
        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");


        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        cbxProveedores.setCellFactory(new Callback<ListView<Proveedor>, ListCell<Proveedor>>() {
            @Override
            public ListCell<Proveedor> call(ListView<Proveedor> param) {
                return new ListCell<Proveedor>() {
                    @Override
                    protected void updateItem(Proveedor item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getNombre());
                        }
                    }
                };
            }
        });

        cbxProveedores.setButtonCell(new ListCell<Proveedor>() {
            @Override
            protected void updateItem(Proveedor item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getNombre());
                }
            }
        });
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tblProductos.getColumns().add(colNombre);
        tblProductos.getColumns().add(colPrecio);
        tblProductos.getColumns().add(colCategoria);
        tblProductos.getColumns().add(colProveedor);
        tblProductos.getColumns().add(colStock);

        tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Producto producto = tblProductos.getSelectionModel().getSelectedItem();
                txtNombre.setText(producto.getNombre());
                txtPrecio.setText(String.valueOf(producto.getPrecio()));
                cbxCategorias.setValue(producto.getCategoria());
                cbxProveedores.setValue(producto.getProveedor());
                txtStock.setText(String.valueOf(producto.getStock()));
            }
        });

        obtenerCategorias();
        obtenerProveedores();
        leerProductos();

        btnCrear.setOnAction(event -> crearProducto());
        btnActualizar.setOnAction(event -> actualizarProducto());
        btnEliminar.setOnAction(event -> eliminarProducto());
        btnRecargar.setOnAction(event -> leerProductos());

        httpClient = HttpClient.newHttpClient();

        return new VBox(txtNombre, txtPrecio, cbxCategorias, cbxProveedores, txtStock, btnCrear, btnActualizar, btnEliminar, btnRecargar, tblProductos);
    }

    private void crearProducto() {
        if (txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty() || cbxCategorias.getValue() == null || cbxProveedores.getValue() == null || txtStock.getText().isEmpty()) {
            System.out.println("Faltan campos por llenar.");
            showAlert(Alert.AlertType.INFORMATION.toString(), "Faltan campos por llenar", "Por favor llene todos los campos.");
            return;
        }

        Producto producto = new Producto();
        producto.setId(0L);
        producto.setNombre(txtNombre.getText());
        producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
        producto.setCategoria(cbxCategorias.getValue());
        producto.setProveedor(cbxProveedores.getValue());
        producto.setStock(Integer.parseInt(txtStock.getText()));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/producto"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(producto)))
                .build();

        System.out.println(new Gson().toJson(producto));
        enviarSolicitud(request);
        limpiarCampos();
        leerProductos();
    }

    private void leerProductos() {
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
        tblProductos.getItems().clear();

        Producto[] productos = new Gson().fromJson(productosJson, Producto[].class);
        tblProductos.getItems().addAll(productos);
    }

    private void actualizarProducto() {
        Producto producto = tblProductos.getSelectionModel().getSelectedItem();
        if (producto == null) {
            System.out.println("No se seleccionó ningún producto para actualizar.");
            // Enviar mensaje de error al usuario en globo emergente
            showAlert(Alert.AlertType.ERROR.toString(), "No se seleccionó ningún producto para actualizar", "Por favor seleccione un producto de la tabla.");
            return;
        }

        producto.setNombre(txtNombre.getText());
        producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
        producto.setCategoria(cbxCategorias.getValue());
        producto.setProveedor(cbxProveedores.getValue());
        producto.setStock(Integer.parseInt(txtStock.getText()));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/producto/" + producto.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(new Gson().toJson(producto)))
                .build();

        enviarSolicitud(request);
        limpiarCampos();
        leerProductos();
    }

    private void eliminarProducto() {
        Producto producto = tblProductos.getSelectionModel().getSelectedItem();
        if (producto == null) {
            System.out.println("No se seleccionó ningún producto para eliminar.");
            // Enviar mensaje de error al usuario en globo emergente
            showAlert(Alert.AlertType.ERROR.toString(), "No se seleccionó ningún producto para eliminar", "Por favor seleccione un producto de la tabla.");
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/producto/" + producto.getId()))
                .DELETE()
                .build();

        enviarSolicitud(request);
        limpiarCampos();
        leerProductos();
    }

    private void enviarSolicitud(HttpRequest request) {
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

    }

    public void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtPrecio.clear();
        cbxCategorias.setValue(null);
        cbxProveedores.setValue(null);
        txtStock.clear();
    }
}
