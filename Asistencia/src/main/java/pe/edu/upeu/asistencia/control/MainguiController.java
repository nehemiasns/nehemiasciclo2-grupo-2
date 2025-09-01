package pe.edu.upeu.asistencia.control;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Controller;

@Controller
public class MainguiController {

    @FXML
    private BorderPane bp;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TabPane tabPane;

}
