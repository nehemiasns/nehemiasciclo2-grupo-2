package app.service;

import app.dao.ProductoDAO;
import app.models.Producto;

import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService {
    private ProductoDAO dao = new ProductoDAO();

    @Override
    public List<Producto> listarTodos() {
        return dao.getAll();
    }

    @Override
    public void agregar(Producto p) {
        dao.add(p);
    }

    @Override
    public void actualizar(Producto p) {
        dao.update(p);
    }

    @Override
    public void eliminar(int id) {
        dao.delete(id);
    }

    @Override
    public Optional<Producto> buscarPorId(int id) {
        return dao.findById(id);
    }

    @Override
    public void reducirStock(int id, int cantidad) throws IllegalArgumentException {
        Optional<Producto> op = dao.findById(id);
        if (op.isEmpty()) throw new IllegalArgumentException("Producto no encontrado");
        Producto p = op.get();
        if (p.getStock() < cantidad) throw new IllegalArgumentException("Stock insuficiente");
        p.setStock(p.getStock() - cantidad);
        dao.update(p);
    }
}
