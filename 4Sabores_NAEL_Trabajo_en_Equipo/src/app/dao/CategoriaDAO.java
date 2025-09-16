package app.dao;


import app.models.Categoria;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CategoriaDAO {
private static final String FILE = "categorias.dat";
private ArrayList<Categoria> list;


public CategoriaDAO() {
list = Storage.loadList(FILE);
if (list == null) list = new ArrayList<>();
if (list.isEmpty()) seed();
}


private void seed() {
list.add(new Categoria(1, "Combos", "Combos y promociones"));
list.add(new Categoria(2, "Hamburguesas", "Hamburguesas variadas"));
list.add(new Categoria(3, "Bebidas", "Bebidas frías y calientes"));
list.add(new Categoria(4, "Postres", "Dulces y helados"));
list.add(new Categoria(5, "Acompañamientos", "Papas, ensaladas y más"));
save();
}


private void save() { Storage.save(FILE, list); }


public List<Categoria> getAll() { return list; }


public void add(Categoria c) {
int next = list.stream().mapToInt(Categoria::getId).max().orElse(0) + 1;
c.setId(next);
list.add(c);
save();
}


public void update(Categoria c) {
for (int i = 0; i < list.size(); i++) {
if (list.get(i).getId() == c.getId()) {
list.set(i, c);
save();
return;
}
}
}


public void delete(int id) {
list.removeIf(c -> c.getId() == id);
save();
}


public Optional<Categoria> findById(int id) {
return list.stream().filter(c -> c.getId() == id).findFirst();
}


public Optional<Categoria> findByName(String nombre) {
return list.stream().filter(c -> c.getNombre() != null && c.getNombre().equalsIgnoreCase(nombre)).findFirst();
}
}