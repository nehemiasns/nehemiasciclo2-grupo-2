package app.service;

import app.models.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listarTodos();
    void agregar(Producto p);
    void actualizar(Producto p);
    void eliminar(int id);
    Optional<Producto> buscarPorId(int id);
    void reducirStock(int id, int cantidad) throws IllegalArgumentException;
}
