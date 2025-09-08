
package com.example.library;

import com.example.library.services.AuthService;
import com.example.library.services.LibraryService;
import com.example.library.views.*;
import javafx.scene.layout.BorderPane;

public class UIRouter {
    private final BorderPane root;
    private final LibraryService libraryService;
    private final AuthService authService;

    public UIRouter(BorderPane root, LibraryService libraryService, AuthService authService) {
        this.root = root;
        this.libraryService = libraryService;
        this.authService = authService;
    }

    public void showDashboard() {
        DashboardView d = new DashboardView(libraryService, this);
        root.setCenter(d.getView());
    }

    public void showBooks() {
        BooksView b = new BooksView(libraryService, this);
        root.setCenter(b.getView());
    }

    public void showLoans() {
        LoansView l = new LoansView(libraryService, this);
        root.setCenter(l.getView());
    }

    public void showReports() {
        ReportsView r = new ReportsView(libraryService, this);
        root.setCenter(r.getView());
    }

    public void showConfig() {
        ConfigView c = new ConfigView(libraryService, this);
        root.setCenter(c.getView());
    }

    public void showLogin() {
        LoginView v = new LoginView(this, authService);
        root.setCenter(v.getView());
    }
}
