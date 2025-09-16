package app.dao;

import app.models.Delivery;
import java.util.ArrayList;
import java.util.Optional;

public class DeliveryDAO {
    private static final String FILE = "deliverys.dat";
    private ArrayList<Delivery> list;

    public DeliveryDAO() {
        list = Storage.loadList(FILE);
    }

    private void save() { Storage.save(FILE, list); }

    public ArrayList<Delivery> getAll() { return list; }

    public void add(Delivery d) {
        int next = list.stream().mapToInt(Delivery::getId).max().orElse(0) + 1;
        d.setId(next); list.add(d); save();
    }

    public void update(Delivery d) {
        for (int i=0;i<list.size();i++) {
            if (list.get(i).getId() == d.getId()) { list.set(i,d); save(); return; }
        }
    }

    public void delete(int id) { list.removeIf(d->d.getId()==id); save(); }

    public Optional<Delivery> findById(int id) { return list.stream().filter(d->d.getId()==id).findFirst(); }
}
