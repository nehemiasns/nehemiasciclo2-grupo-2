
package com.example.library.views;

import com.example.library.services.LibraryService;
import com.example.library.UIRouter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ConfigView {
    private final VBox view = new VBox(8);
    public ConfigView(LibraryService lib, UIRouter router) {
        view.setPadding(new Insets(10));
        Label title = new Label("Configuración");
        title.getStyleClass().add("title");
        Button backup = new Button("Hacer backup (copia de users.json)");
        backup.setOnAction(e-> {
            lib.backup(System.getProperty("user.home") + "/library_backup");
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Backup realizado en " + System.getProperty("user.home"));
            a.showAndWait();
        });
        view.getChildren().addAll(title, backup);
    }
    public VBox getView() { return view; }
}
