
package com.example.library.views;

import com.example.library.models.Book;
import com.example.library.services.LibraryService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BookForm {
    private final LibraryService lib;
    private final Book book;
    private final BooksView parent;

    public BookForm(LibraryService lib, Book book, BooksView parent) {
        this.lib = lib; this.book = book; this.parent = parent;
    }

    public void show() {
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        VBox root = new VBox(8);
        root.setPadding(new Insets(10));
        TextField title = new TextField();
        title.setPromptText("Título");
        TextField author = new TextField();
        author.setPromptText("Autor");
        TextField category = new TextField();
        category.setPromptText("Categoría");
        TextField year = new TextField();
        year.setPromptText("Año");
        TextField copies = new TextField();
        copies.setPromptText("Copias");

        if (book!=null) {
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            category.setText(book.getCategory());
            year.setText(String.valueOf(book.getYear()));
            copies.setText(String.valueOf(book.getCopies()));
        }

        Button save = new Button("Guardar");
        Label msg = new Label();
        save.setOnAction(e->{
            try {
                String t = title.getText().trim();
                String a = author.getText().trim();
                String c = category.getText().trim();
                int y = Integer.parseInt(year.getText().trim());
                int cp = Integer.parseInt(copies.getText().trim());
                if (book==null) {
                    lib.addBook(new Book(t,a,c,y,cp));
                } else {
                    book.setTitle(t); book.setAuthor(a); book.setCategory(c); book.setYear(y); book.setCopies(cp);
                    lib.updateBook(book);
                }
                parent.refresh();
                s.close();
            } catch (Exception ex) {
                msg.setText("Error: " + ex.getMessage());
            }
        });

        root.getChildren().addAll(title, author, category, year, copies, save, msg);
        s.setScene(new Scene(root, 320, 360));
        s.showAndWait();
    }
}
