package app.ui;

import app.dao.ClienteDAO;
import app.models.Cliente;

import javax.swing.*;
import app.util.UIConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClientesPanel extends JPanel {
    private ClienteDAO clienteDAO = new ClienteDAO();
    private JTable tbl;
    private DefaultTableModel model;

    public ClientesPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.APP_WHITE);

        model = new DefaultTableModel(new Object[]{"ID","Nombre","Teléfono","Dirección"},0) {
            public boolean isCellEditable(int r, int c){ return false; }
        };
        tbl = new JTable(model);
        refresh();

        add(new JScrollPane(tbl), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setBackground(UIConstants.APP_WHITE);
        JButton add = new JButton("Agregar");
        JButton edit = new JButton("Editar");
        JButton del = new JButton("Eliminar");
        buttons.add(add); buttons.add(edit); buttons.add(del);
        add(buttons, BorderLayout.SOUTH);

        add.addActionListener(e -> openForm(null));
        edit.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if (r==-1) { JOptionPane.showMessageDialog(this, "Selecciona un cliente"); return; }
            int id = Integer.parseInt(tbl.getValueAt(r,0).toString());
            Cliente c = clienteDAO.findById(id).orElse(null);
            openForm(c);
        });
        del.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if (r==-1) { JOptionPane.showMessageDialog(this, "Selecciona un cliente"); return; }
            int id = Integer.parseInt(tbl.getValueAt(r,0).toString());
            if (JOptionPane.showConfirmDialog(this, "Eliminar cliente?", "Confirmar", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                clienteDAO.delete(id); refresh();
            }
        });
    }

    private void refresh() {
        model.setRowCount(0);
        for (Cliente c : clienteDAO.getAll()) {
            model.addRow(new Object[]{c.getId(), c.getNombre(), c.getTelefono(), c.getDireccion()});
        }
    }

    private void openForm(Cliente c) {
        JDialog dlg = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Cliente", true);
        dlg.setSize(360,260); dlg.setLocationRelativeTo(this);
        JPanel p = new JPanel(null);
        p.setBackground(UIConstants.APP_WHITE);

        JLabel l1 = new JLabel("Nombre:"); l1.setBounds(20,20,80,25); p.add(l1);
        JTextField tfNombre = new JTextField(); tfNombre.setBounds(110,20,200,25); p.add(tfNombre);

        JLabel l2 = new JLabel("Teléfono:"); l2.setBounds(20,60,80,25); p.add(l2);
        JTextField tfTel = new JTextField(); tfTel.setBounds(110,60,200,25); p.add(tfTel);

        JLabel l3 = new JLabel("Dirección:"); l3.setBounds(20,100,80,25); p.add(l3);
        JTextField tfDir = new JTextField(); tfDir.setBounds(110,100,200,25); p.add(tfDir);

        JButton save = new JButton("Guardar"); save.setBounds(110,150,100,30);
        save.setBackground(UIConstants.APP_BLUE); save.setForeground(Color.WHITE);
        p.add(save);

        if (c!=null) { tfNombre.setText(c.getNombre()); tfTel.setText(c.getTelefono()); tfDir.setText(c.getDireccion()); }

        save.addActionListener(e -> {
            String nombre = tfNombre.getText().trim(); String tel = tfTel.getText().trim(); String dir = tfDir.getText().trim();
            if (nombre.isEmpty()) { JOptionPane.showMessageDialog(dlg, "Nombre requerido"); return; }
            if (c==null) {
                clienteDAO.add(new Cliente(0,nombre,tel,dir));
            } else {
                c.setNombre(nombre); c.setTelefono(tel); c.setDireccion(dir); clienteDAO.update(c);
            }
            refresh(); dlg.dispose();
        });

        dlg.add(p); dlg.setVisible(true);
    }
}