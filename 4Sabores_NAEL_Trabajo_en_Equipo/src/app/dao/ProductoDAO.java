package app.dao;

import app.models.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAO {
    private static final String FILE = "productos.dat";
    private ArrayList<Producto> list;

    public ProductoDAO() {
        list = Storage.loadList(FILE);
        if (list == null) list = new ArrayList<>();
        if (list.isEmpty()) seed();
    }

    private void seed() {
        // seed con imágenes (ajusta las rutas si tus imágenes están en otra carpeta)
        list.add(new Producto(1, "Combo Clásico", "Combos", 21.90, 10, "img/combo_clasico.png"));
        list.add(new Producto(2, "Mega Combo Familiar", "Combos", 49.90, 5, "img/mega_combo.png"));
        list.add(new Producto(3, "Twister BBQ", "Hamburguesas", 12.50, 8, "img/twister_bbq.png"));
        list.add(new Producto(4, "Hamburguesa Crispy", "Hamburguesas", 10.00, 15, "img/hamburguesa_crispy.png"));
        // puedes agregar más items aquí...
        save();
    }

    private void save() {
        Storage.save(FILE, list);
    }

    public List<Producto> getAll() {
        return list;
    }

    public void add(Producto p) {
        int next = list.stream().mapToInt(Producto::getId).max().orElse(0) + 1;
        p.setId(next);
        list.add(p);
        save();
    }

    public void update(Producto p) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == p.getId()) {
                list.set(i, p);
                save();
                return;
            }
        }
    }

    public void delete(int id) {
        list.removeIf(p -> p.getId() == id);
        save();
    }

    public Optional<Producto> findById(int id) {
        return list.stream().filter(p -> p.getId() == id).findFirst();
    }

    /**
     * Método conservado para compatibilidad con código existente que llama a
     * productoDAO.findByIdByName(String). Realiza búsqueda case-insensitive por nombre.
     */
    public Producto findByIdByName(String name) {
        if (name == null) return null;
        return list.stream()
                .filter(p -> p.getNombre() != null && p.getNombre().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método más claro: devuelve Optional<Producto> buscando por nombre (case-insensitive).
     */
    public Optional<Producto> findByName(String name) {
        if (name == null) return Optional.empty();
        return list.stream()
                .filter(p -> p.getNombre() != null && p.getNombre().equalsIgnoreCase(name))
                .findFirst();
    }
}
