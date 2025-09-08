
package com.example.library.views;

import com.example.library.services.LibraryService;
import com.example.library.models.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

public class ReportsView {
    private final VBox view = new VBox(8);
    public ReportsView(LibraryService lib, com.example.library.UIRouter router) {
        view.setPadding(new Insets(10));
        Label title = new Label("Reportes");
        title.getStyleClass().add("title");
        Label tbooks = new Label("Total libros (copias): " + lib.totalBooks());
        Label tloans = new Label("Préstamos activos: " + lib.totalActiveLoans());
        Button export = new Button("Exportar usuarios top a CSV");
        export.setOnAction(e-> {
            try {
                List<User> top = lib.topUsersByLoans();
                java.nio.file.Path out = Paths.get(System.getProperty("user.home"), "library_top_users.csv");
                try (PrintWriter pw = new PrintWriter(out.toFile())) {
                    pw.println("username,fullName");
                    for (User u: top) pw.println(u.getUsername()+","+u.getFullName());
                }
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Exportado: " + out.toString());
                a.showAndWait();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        view.getChildren().addAll(title, tbooks, tloans, export);
    }
    public VBox getView() { return view; }
}
