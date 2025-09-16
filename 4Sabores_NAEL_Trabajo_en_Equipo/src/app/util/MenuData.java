package app.util;

import app.dao.ProductoDAO;
import app.models.Producto;

import java.util.LinkedHashMap;
import java.util.Map;

public class MenuData {
    private ProductoDAO productoDAO = new ProductoDAO();

    public Map<String, Double> cargarMenu(Map<String, String> images) {
        Map<String, Double> menu = new LinkedHashMap<>();
        for (Producto p : productoDAO.getAll()) {
            menu.put(p.getNombre(), p.getPrecio());
            images.put(p.getNombre(),
                "img/" + p.getNombre().toLowerCase().replaceAll(" ", "_") + ".png");
        }
        if (menu.isEmpty()) {
            menu.put("Combo Clásico", 21.90);
            menu.put("Hamburguesa Crispy", 10.00);
            images.put("Combo Clásico", "img/combo_clasico.png");
            images.put("Hamburguesa Crispy", "img/crispy.png");
        }
        return menu;
    }
}
