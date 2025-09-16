package app.dao;

import app.models.Venta;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class VentaDAO {
    private static final String FILE = "ventas.dat";
    private ArrayList<Venta> list;

    public VentaDAO() {
        list = Storage.loadList(FILE);
    }

    private void save() { Storage.save(FILE, list); }

    public ArrayList<Venta> getAll() { return list; }

    public void add(Venta v) {
        int next = list.stream().mapToInt(Venta::getId).max().orElse(0) + 1;
        v.setId(next);
        if (v.getFecha() == null) v.setFecha(new Date());
        list.add(v);
        save();
    }

    public Optional<Venta> findById(int id) { return list.stream().filter(v->v.getId()==id).findFirst(); }
}
