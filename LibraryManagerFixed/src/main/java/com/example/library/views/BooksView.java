
package com.example.library.views;

import com.example.library.services.LibraryService;
import com.example.library.models.Book;
import com.example.library.UIRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class BooksView {
    private final BorderPane view = new BorderPane();
    private final LibraryService lib;
    private final UIRouter router;
    private final TableView<Book> table = new TableView<>();

    public BooksView(LibraryService lib, UIRouter router) {
        this.lib = lib; this.router = router;
        build();
    }

    private void build() {
        view.setPadding(new Insets(10));
        Label title = new Label("Gestión de Libros");
        title.getStyleClass().add("title");

        TableColumn<Book,String> tcol = new TableColumn<>("Título");
        tcol.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        TableColumn<Book,String> acol = new TableColumn<>("Autor");
        acol.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(data.getValue().getAuthor()));
        TableColumn<Book,String> ccol = new TableColumn<>("Categoría");
        ccol.setCellValueFactory(data-> new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));
        TableColumn<Book,Integer> copies = new TableColumn<>("Copias");
        copies.setCellValueFactory(data-> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getCopies()));

        table.getColumns().addAll(tcol, acol, ccol, copies);
        refresh();

        Button add = new Button("Agregar");
        add.setOnAction(e->{
            BookForm form = new BookForm(lib, null, this);
            form.show();
        });
        Button edit = new Button("Editar");
        edit.setOnAction(e->{
            Book sel = table.getSelectionModel().getSelectedItem();
            if (sel!=null) new BookForm(lib, sel, this).show();
        });
        Button del = new Button("Eliminar");
        del.setOnAction(e->{
            Book sel = table.getSelectionModel().getSelectedItem();
            if (sel!=null) { lib.removeBook(sel.getId()); refresh(); }
        });

        HBox controls = new HBox(8, add, edit, del);
        VBox top = new VBox(6, title, controls);
        view.setTop(top);
        view.setCenter(table);
    }

    public void refresh() {
        ObservableList<Book> items = FXCollections.observableArrayList(lib.getAllBooks());
        table.setItems(items);
    }

    public BorderPane getView() { return view; }
}
