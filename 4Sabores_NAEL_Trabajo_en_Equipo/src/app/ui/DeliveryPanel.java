package app.ui;

import app.dao.DeliveryDAO;
import app.dao.VentaDAO;
import app.models.Delivery;
import app.models.Venta;

import javax.swing.*;
import app.util.UIConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DeliveryPanel extends JPanel {
    private DeliveryDAO dao = new DeliveryDAO();
    private VentaDAO ventaDAO = new VentaDAO();
    private JTable tbl;
    private DefaultTableModel model;

    public DeliveryPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.APP_WHITE);

        model = new DefaultTableModel(new Object[]{"ID","VentaID","Cliente","Dirección","Estado"},0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };
        tbl = new JTable(model);
        refresh();
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        JPanel buttons = new JPanel(); buttons.setBackground(UIConstants.APP_WHITE);
        JButton add = new JButton("Crear Delivery");
        JButton upd = new JButton("Marcar entregado");
        buttons.add(add); buttons.add(upd);
        add(buttons, BorderLayout.SOUTH);

        add.addActionListener(e -> {
            String vid = JOptionPane.showInputDialog(this, "ID de venta para delivery:");
            if (vid==null || vid.trim().isEmpty()) return;
            try {
                int id = Integer.parseInt(vid.trim());
                Venta v = ventaDAO.findById(id).orElse(null);
                if (v==null) { JOptionPane.showMessageDialog(this, "Venta no encontrada"); return; }
                String dir = JOptionPane.showInputDialog(this, "Dirección de entrega:");
                if (dir==null || dir.trim().isEmpty()) return;
                dao.add(new Delivery(0, id, dir, "Pendiente"));
                refresh();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID inválido"); }
        });

        upd.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if (r==-1) { JOptionPane.showMessageDialog(this, "Selecciona un delivery"); return; }
            int id = Integer.parseInt(tbl.getValueAt(r,0).toString());
            Delivery d = dao.findById(id).orElse(null);
            if (d!=null) {
                d.setEstado("Entregado");
                dao.update(d);
                refresh();
            }
        });
    }

    private void refresh() {
        model.setRowCount(0);
        for (var d : dao.getAll()) {
            String cliente = ventaDAO.findById(d.getVentaId()).map(Venta::getCliente).orElse("-");
            model.addRow(new Object[]{d.getId(), d.getVentaId(), cliente, d.getDireccion(), d.getEstado()});
        }
    }
}