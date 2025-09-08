
package com.example.library;

import com.example.library.services.LibraryService;
import com.example.library.services.AuthService;
import com.example.library.views.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static LibraryService libraryService;
    private static AuthService authService;
    private static UIRouter router;
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        libraryService = new LibraryService();
        authService = new AuthService(libraryService);

        root = new BorderPane();
        router = new UIRouter(root, libraryService, authService);

        // Show login
        LoginView login = new LoginView(router, authService);
        root.setCenter(login.getView());

        Scene scene = new Scene(root, 1100, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Library Manager FX - Biblioteca Universitaria");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static UIRouter getRouter() { return router; }
    public static LibraryService getLibraryService() { return libraryService; }

    public static void main(String[] args) {
        launch(args);
    }
}
