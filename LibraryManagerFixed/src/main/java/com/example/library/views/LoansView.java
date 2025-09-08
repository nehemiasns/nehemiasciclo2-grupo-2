
package com.example.library.views;

import com.example.library.models.Loan;
import com.example.library.models.Book;
import com.example.library.services.LibraryService;
import com.example.library.UIRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.util.List;

public class LoansView {
    private final BorderPane view = new BorderPane();
    private final LibraryService lib;
    private final UIRouter router;
    private final TableView<Loan> table = new TableView<>();

    public LoansView(LibraryService lib, UIRouter router) {
        this.lib = lib; this.router = router;
        build();
    }

    private void build() {
        view.setPadding(new Insets(10));
        Label title = new Label("Préstamos");
        title.getStyleClass().add("title");

        TableColumn<Loan,String> bcol = new TableColumn<>("Libro");
        bcol.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(lib.findBookById(data.getValue().getBookId()).getTitle()));
        TableColumn<Loan,String> ucol = new TableColumn<>("Usuario");
        ucol.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(lib.findUserById(data.getValue().getUserId()).getFullName()));
        TableColumn<Loan,LocalDate> lcol = new TableColumn<>("Fecha préstamo");
        lcol.setCellValueFactory(data-> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getLoanDate()));
        TableColumn<Loan,LocalDate> dcol = new TableColumn<>("Fecha devolución");
        dcol.setCellValueFactory(data-> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDueDate()));
        TableColumn<Loan,LocalDate> rcol = new TableColumn<>("Devuelto");
        rcol.setCellValueFactory(data-> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getReturnDate()));

        table.getColumns().addAll(bcol, ucol, lcol, dcol, rcol);
        refresh();

        Button add = new Button("Registrar préstamo");
        add.setOnAction(e-> showLoanForm());
        Button ret = new Button("Registrar devolución");
        ret.setOnAction(e-> {
            Loan sel = table.getSelectionModel().getSelectedItem();
            if (sel!=null && sel.getReturnDate()==null) {
                lib.returnLoan(sel.getId(), LocalDate.now());
                refresh();
            }
        });

        HBox controls = new HBox(8, add, ret);
        VBox top = new VBox(6, title, controls);
        view.setTop(top);
        view.setCenter(table);
    }

    private void showLoanForm() {
        StageLoanForm f = new StageLoanForm(lib, this);
        f.show();
    }

    public void refresh() {
        ObservableList<Loan> items = FXCollections.observableArrayList(lib.getAllLoans());
        table.setItems(items);
    }

    public BorderPane getView() { return view; }
}
