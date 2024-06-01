module com.example.interfaznegocio.interfaznegocio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.gson;
    requires java.net.http;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires kafka.clients;
    opens com.example.interfaznegocio.models to com.google.gson, javafx.base;

    opens com.example.interfaznegocio.interfaznegocio to javafx.fxml;
    exports com.example.interfaznegocio.interfaznegocio;
}