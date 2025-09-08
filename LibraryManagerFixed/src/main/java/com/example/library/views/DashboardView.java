
package com.example.library.views;

import com.example.library.services.LibraryService;
import com.example.library.UIRouter;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardView {
    private final VBox view = new VBox(10);
    public DashboardView(LibraryService lib, UIRouter router) {
        view.setPadding(new Insets(20));
        Label title = new Label("Dashboard");
        title.getStyleClass().add("title");
        Label stats = new Label("Total libros: " + lib.totalBooks() + " | Préstamos activos: " + lib.totalActiveLoans());
        Button b1 = new Button("Libros");
        b1.setOnAction(e->router.showBooks());
        Button b2 = new Button("Préstamos");
        b2.setOnAction(e->router.showLoans());
        Button b3 = new Button("Reportes");
        b3.setOnAction(e->router.showReports());
        view.getChildren().addAll(title, stats, b1, b2, b3);
    }
    public VBox getView() { return view; }
}
