
package com.example.library.views;

import com.example.library.UIRouter;
import com.example.library.services.AuthService;
import com.example.library.models.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Optional;

public class LoginView {
    private final UIRouter router;
    private final AuthService auth;
    private final VBox view = new VBox(10);

    public LoginView(UIRouter router, AuthService auth) {
        this.router = router;
        this.auth = auth;
        build();
    }

    private void build() {
        view.setPadding(new Insets(30));
        Label title = new Label("Library Manager FX");
        title.getStyleClass().add("title");
        TextField username = new TextField();
        username.setPromptText("Usuario");
        PasswordField password = new PasswordField();
        password.setPromptText("Contraseña");
        Button btn = new Button("Iniciar sesión");
        Label msg = new Label();
        btn.setOnAction(e->{
            Optional<User> u = auth.authenticate(username.getText().trim(), password.getText());
            if (u.isPresent()) {
                msg.setText("Bienvenido, " + u.get().getFullName());
                router.showDashboard();
            } else {
                msg.setText("Credenciales inválidas");
            }
        });
        view.getChildren().addAll(title, username, password, btn, msg);
    }

    public VBox getView() { return view; }
}
