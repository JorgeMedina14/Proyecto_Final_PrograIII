module com.final.interfazecommerce.interfazecommerce {
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

    opens com.final.interfazecommerce.interfazecommerce to javafx.fxml;
    exports com.final.interfazecommerce.interfazecommerce;
}