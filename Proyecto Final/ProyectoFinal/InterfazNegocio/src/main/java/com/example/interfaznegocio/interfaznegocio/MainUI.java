package com.example.interfaznegocio.interfaznegocio;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainUI extends Application {
    private ProductoUI productoUI;
    private ClienteUI clienteUI;
    private CategoriaUI categoriaUI;
    private CompraProductoUI compraProductoUI;
    private ProveedorUI proveedorUI;
    private ReporteUI reporteUI;
    private VentaUI ventaUI;

    @Override
    public void start(Stage primaryStage) {
        productoUI = new ProductoUI();
        clienteUI = new ClienteUI();
        categoriaUI = new CategoriaUI();
        compraProductoUI = new CompraProductoUI();
        proveedorUI = new ProveedorUI();
        reporteUI = new ReporteUI();
        ventaUI = new VentaUI();

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Navegación");
        MenuItem productoMenuItem = new MenuItem("Productos");
        MenuItem clienteMenuItem = new MenuItem("Clientes");
        MenuItem categoriaMenuItem = new MenuItem("Categorías");
        MenuItem compraProductoMenuItem = new MenuItem("Compras de productos");
        MenuItem proveedorMenuItem = new MenuItem("Proveedores");
        MenuItem reporteMenuItem = new MenuItem("Reportes");
        MenuItem ventaMenuItem = new MenuItem("Ventas");
        menu.getItems().addAll(productoMenuItem, clienteMenuItem, categoriaMenuItem, compraProductoMenuItem, proveedorMenuItem, reporteMenuItem, ventaMenuItem);
        menuBar.getMenus().add(menu);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        productoMenuItem.setOnAction(e -> root.setCenter(productoUI.getVista()));
        clienteMenuItem.setOnAction(e -> root.setCenter(clienteUI.getVista()));
        categoriaMenuItem.setOnAction(e -> root.setCenter(categoriaUI.getVista()));
        compraProductoMenuItem.setOnAction(e -> root.setCenter(compraProductoUI.getVista()));
        proveedorMenuItem.setOnAction(e -> root.setCenter(proveedorUI.getVista()));
        reporteMenuItem.setOnAction(e -> root.setCenter(reporteUI.getVista()));
        ventaMenuItem.setOnAction(e -> root.setCenter(ventaUI.getVista()));

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}