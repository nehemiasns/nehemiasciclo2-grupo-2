package app.ui;

import app.dao.VentaDAO;
import app.models.Venta;

import javax.swing.*;
import app.util.UIConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class VentasPanel extends JPanel {
    private VentaDAO dao = new VentaDAO();
    private JTable tbl;
    private DefaultTableModel model;

    public VentasPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.APP_WHITE);

        model = new DefaultTableModel(new Object[]{"ID","Cliente","Total","Fecha"},0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };
        tbl = new JTable(model);
        refresh();

        add(new JScrollPane(tbl), BorderLayout.CENTER);

        JPanel buttons = new JPanel(); buttons.setBackground(UIConstants.APP_WHITE);
        JButton view = new JButton("Ver ticket");
        buttons.add(view);
        add(buttons, BorderLayout.SOUTH);

        view.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if (r==-1) { JOptionPane.showMessageDialog(this, "Selecciona una venta"); return; }
            int id = Integer.parseInt(tbl.getValueAt(r,0).toString());
            Venta v = dao.findById(id).orElse(null);
            if (v!=null) {
                String ticket = generarTexto(v);
                try (FileWriter fw = new FileWriter("venta_"+id+".txt")) { fw.write(ticket); }
                catch (Exception ex) { /* ignore */ }
                JTextArea ta = new JTextArea(ticket);
                ta.setEditable(false);
                JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Ticket venta " + id, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void refresh() {
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Venta v : dao.getAll()) {
            model.addRow(new Object[]{v.getId(), v.getCliente(), String.format("S/ %.2f", v.getTotal()), sdf.format(v.getFecha())});
        }
    }

    private String generarTexto(Venta v) {
        StringBuilder sb = new StringBuilder();
        sb.append("VENTA ID: ").append(v.getId()).append("\n");
        sb.append("Cliente: ").append(v.getCliente()).append("\n");
        sb.append("Fecha: ").append(v.getFecha()).append("\n\n");
        sb.append(v.getItems()).append("\n\n");
        sb.append(String.format("TOTAL: S/ %.2f\n", v.getTotal()));
        return sb.toString();
    }
}