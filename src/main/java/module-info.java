module livraria.livrariafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens livraria.livrariafx.application to javafx.fxml;
    exports livraria.livrariafx.application;

    opens livraria.livrariafx.controller to javafx.fxml;
    exports livraria.livrariafx.controller;

    opens livraria.livrariafx.model.entities to javafx.fxml;
    exports livraria.livrariafx.model.entities;

    opens livraria.livrariafx.model.services to javafx.fxml;
    exports livraria.livrariafx.model.services;

}