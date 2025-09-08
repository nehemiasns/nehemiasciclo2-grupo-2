
package com.example.library.views;

import com.example.library.models.Loan;
import com.example.library.models.Book;
import com.example.library.services.LibraryService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;

public class StageLoanForm {
    private final LibraryService lib;
    private final LoansView parent;

    public StageLoanForm(LibraryService lib, LoansView parent) {
        this.lib = lib; this.parent = parent;
    }

    public void show() {
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        VBox root = new VBox(8);
        root.setPadding(new Insets(10));
        ComboBox<Book> books = new ComboBox<>();
        books.getItems().addAll(lib.getAllBooks());
        books.setCellFactory(cb -> new javafx.scene.control.ListCell<>() {
            @Override protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item==null ? null : item.getTitle() + " ("+item.getCopies()+")");
            }
        });
        books.setButtonCell(new javafx.scene.control.ListCell<>() { @Override protected void updateItem(Book item, boolean empty){ super.updateItem(item, empty); setText(empty||item==null?null:item.getTitle()); }});

        ComboBox<com.example.library.models.User> users = new ComboBox<>();
        users.getItems().addAll(lib.getAllUsers());
        users.setCellFactory(cb->new javafx.scene.control.ListCell<>(){ @Override protected void updateItem(com.example.library.models.User item, boolean empty){ super.updateItem(item, empty); setText(empty||item==null?null:item.getFullName()); }});
        users.setButtonCell(new javafx.scene.control.ListCell<>(){ @Override protected void updateItem(com.example.library.models.User item, boolean empty){ super.updateItem(item, empty); setText(empty||item==null?null:item.getFullName()); }});

        DatePicker due = new DatePicker(LocalDate.now().plusWeeks(2));
        Button save = new Button("Registrar");
        Label msg = new Label();
        save.setOnAction(e->{
            Book b = books.getSelectionModel().getSelectedItem();
            com.example.library.models.User u = users.getSelectionModel().getSelectedItem();
            if (b==null || u==null) { msg.setText("Seleccione libro y usuario"); return; }
            if (b.getCopies()<=0) { msg.setText("No hay copias disponibles"); return; }
            Loan loan = new Loan(b.getId(), u.getId(), LocalDate.now(), due.getValue());
            lib.addLoan(loan);
            parent.refresh();
            s.close();
        });

        root.getChildren().addAll(books, users, due, save, msg);
        s.setScene(new Scene(root, 360, 240));
        s.showAndWait();
    }
}
