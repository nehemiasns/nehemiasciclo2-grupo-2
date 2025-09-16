package app.dao;

import app.models.Cliente;
import java.util.ArrayList;
import java.util.Optional;

public class ClienteDAO {
    private static final String FILE = "clientes.dat";
    private ArrayList<Cliente> list;

    public ClienteDAO() {
        list = Storage.loadList(FILE);
    }

    private void save() { Storage.save(FILE, list); }

    public ArrayList<Cliente> getAll() { return list; }

    public void add(Cliente c) {
        int next = list.stream().mapToInt(Cliente::getId).max().orElse(0) + 1;
        c.setId(next);
        list.add(c);
        save();
    }

    public void update(Cliente c) {
        for (int i=0;i<list.size();i++) {
            if (list.get(i).getId() == c.getId()) {
                list.set(i, c); save(); return;
            }
        }
    }

    public void delete(int id) { list.removeIf(c -> c.getId() == id); save(); }

    public Optional<Cliente> findById(int id) { return list.stream().filter(c->c.getId()==id).findFirst(); }
}
