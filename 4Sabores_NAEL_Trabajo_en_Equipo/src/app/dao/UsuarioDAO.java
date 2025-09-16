package app.dao;

import app.models.Usuario;

import java.util.ArrayList;
import java.util.Optional;

public class UsuarioDAO {
    private static final String FILE = "usuarios.dat";
    private ArrayList<Usuario> list;

    public UsuarioDAO() {
        list = Storage.loadList(FILE);
        if (list.isEmpty()) seed();
    }

    private void seed() {
        list.add(new Usuario(1, "Nehemias", "Nehemias", "admin"));
        list.add(new Usuario(2, "Anderson", "Anderson", "user"));
        list.add(new Usuario(3, "Erick", "Erick", "user"));
        save();
    }

    private void save() {
        Storage.save(FILE, list);
    }

    public ArrayList<Usuario> getAll() { return list; }

    public Optional<Usuario> findByUsername(String username) {
        return list.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    public void add(Usuario u) {
        int next = list.stream().mapToInt(Usuario::getId).max().orElse(0) + 1;
        u.setId(next);
        list.add(u);
        save();
    }

    public void update(Usuario u) {
        for (int i=0;i<list.size();i++) {
            if (list.get(i).getId() == u.getId()) {
                list.set(i, u);
                save();
                return;
            }
        }
    }

    public void delete(int id) {
        list.removeIf(u -> u.getId() == id);
        save();
    }

    public boolean validate(String username, String password) {
        return list.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
    }
}
