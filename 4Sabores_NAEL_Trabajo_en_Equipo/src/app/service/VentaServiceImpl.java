package app.service;

import app.dao.VentaDAO;
import app.models.Venta;

import java.util.List;
import java.util.Optional;

public class VentaServiceImpl implements VentaService {
    private VentaDAO dao = new VentaDAO();

    @Override
    public List<Venta> listarVentas() {
        return dao.getAll();
    }

    @Override
    public void registrarVenta(Venta v) {
        dao.add(v);
    }

    @Override
    public Optional<Venta> buscarVenta(int id) {
        return dao.findById(id);
    }
}
