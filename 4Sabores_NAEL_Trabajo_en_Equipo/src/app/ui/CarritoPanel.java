package app.ui;

import app.dao.ProductoDAO;
import app.models.Producto;
import app.service.VentaService;
import app.service.VentaServiceImpl;
import app.models.Venta;
import app.util.Cart;
import app.util.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.Date;
import java.util.Map;

public class CarritoPanel extends JPanel {
    private DefaultListModel<String> cartModel;
    private JList<String> cartList;
    private JLabel subtotalLbl, igvLbl, totalLbl;
    private JButton checkoutBtn, clearBtn;
    private ProductoDAO productoDAO = new ProductoDAO();
    private VentaService ventaService = new VentaServiceImpl();

    private static final double IGV = 0.18;

    public CarritoPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.APP_WHITE);

        JLabel title = new JLabel("Carrito de compras");
        title.setFont(UIConstants.TITLE_FONT);
        title.setForeground(UIConstants.APP_BLUE);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        cartModel = new DefaultListModel<>();
        cartList = new JList<>(cartModel);
        add(new JScrollPane(cartList), BorderLayout.CENTER);

        JPanel right = new JPanel(new GridLayout(6,1,5,5));
        right.setBackground(UIConstants.APP_WHITE);
        subtotalLbl = new JLabel("Subtotal: S/ 0.00");
        igvLbl = new JLabel("IGV (18%): S/ 0.00");
        totalLbl = new JLabel("Total: S/ 0.00");
        right.add(subtotalLbl); right.add(igvLbl); right.add(totalLbl);

        clearBtn = new JButton("Vaciar carrito");
        clearBtn.setBackground(UIConstants.APP_BLUE);
        clearBtn.setForeground(UIConstants.APP_WHITE);
        clearBtn.addActionListener(e -> { Cart.getInstance().clear(); refresh(); });
        right.add(clearBtn);

        checkoutBtn = new JButton("Finalizar compra");
        checkoutBtn.setBackground(UIConstants.APP_DARK);
        checkoutBtn.setForeground(UIConstants.APP_WHITE);
        checkoutBtn.addActionListener(e -> onCheckout());
        right.add(checkoutBtn);

        add(right, BorderLayout.EAST);
        refresh();
    }

    public void refresh() {
        cartModel.clear();
        double subtotal = 0.0;
        for (Map.Entry<String,Integer> e : Cart.getInstance().getItems().entrySet()) {
            String name = e.getKey();
            int qty = e.getValue();
            Producto p = productoDAO.findByIdByName(name);
            double price = p != null ? p.getPrecio() : 0.0;
            double itemTotal = price * qty;
            cartModel.addElement(name + " x" + qty + " — S/ " + String.format("%.2f", itemTotal));
            subtotal += itemTotal;
        }
        double igv = subtotal * IGV;
        double total = subtotal + igv;
        subtotalLbl.setText(String.format("Subtotal: S/ %.2f", subtotal));
        igvLbl.setText(String.format("IGV (18%%): S/ %.2f", igv));
        totalLbl.setText(String.format("Total: S/ %.2f", total));
        checkoutBtn.setEnabled(!Cart.getInstance().isEmpty());
    }

    private void onCheckout() {
        if (Cart.getInstance().isEmpty()) return;
        String cliente = JOptionPane.showInputDialog(this, "Nombre del cliente:", "Consumidor Final");
        if (cliente == null) return;
        StringBuilder items = new StringBuilder();
        double subtotal = 0.0;
        for (Map.Entry<String,Integer> e : Cart.getInstance().getItems().entrySet()) {
            String name = e.getKey();
            int qty = e.getValue();
            Producto p = productoDAO.findByIdByName(name);
            double price = p != null ? p.getPrecio() : 0.0;
            double itemTotal = price * qty;
            items.append(String.format("%s x%d — S/ %.2f\n", name, qty, itemTotal));
            subtotal += itemTotal;
        }
        double igv = subtotal * IGV; double total = subtotal + igv;
        Venta v = new Venta(0, cliente, items.toString(), total, new Date());
        ventaService.registrarVenta(v);
        // write ticket into data/
        try (FileWriter fw = new FileWriter("data/ticket_venta_"+v.getId()+".txt")) { fw.write(items.toString() + "\nTOTAL: S/ " + total); } catch (Exception ex) { /* ignore */ }
        JOptionPane.showMessageDialog(this, "Compra registrada. Ticket: ticket_venta_" + v.getId() + ".txt");
        Cart.getInstance().clear();
        refresh();
    }
}