package app.service;

import app.models.Venta;
import java.util.List;
import java.util.Optional;

public interface VentaService {
    List<Venta> listarVentas();
    void registrarVenta(Venta v);
    Optional<Venta> buscarVenta(int id);
}
