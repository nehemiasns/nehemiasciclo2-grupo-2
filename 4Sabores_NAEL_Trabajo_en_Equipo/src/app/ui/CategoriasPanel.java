package app.ui;

import app.dao.CategoriaDAO;
import app.models.Categoria;

import javax.swing.*;
import app.util.UIConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategoriasPanel extends JPanel {
    private CategoriaDAO dao = new CategoriaDAO();
    private JTable tbl;
    private DefaultTableModel model;

    public CategoriasPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.APP_WHITE);

        model = new DefaultTableModel(new Object[]{"ID","Nombre","Descripcion"},0) {
            public boolean isCellEditable(int r, int c){ return false; }
        };
        tbl = new JTable(model);
        refresh();
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        JPanel buttons = new JPanel(); buttons.setBackground(UIConstants.APP_WHITE);
        JButton add = new JButton("Agregar"), edit = new JButton("Editar"), del = new JButton("Eliminar");
        buttons.add(add); buttons.add(edit); buttons.add(del);
        add(buttons, BorderLayout.SOUTH);

        add.addActionListener(e -> openForm(null));
        edit.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if (r==-1) { JOptionPane.showMessageDialog(this, "Selecciona una categoria"); return; }
            int id = Integer.parseInt(tbl.getValueAt(r,0).toString());
            Categoria c = dao.findById(id).orElse(null);
            openForm(c);
        });
        del.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if (r==-1) { JOptionPane.showMessageDialog(this, "Selecciona una categoria"); return; }
            int id = Integer.parseInt(tbl.getValueAt(r,0).toString());
            if (JOptionPane.showConfirmDialog(this, "Eliminar categoria?","Confirmar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                dao.delete(id); refresh();
            }
        });
    }

    private void refresh() {
        model.setRowCount(0);
        for (Categoria c : dao.getAll()) model.addRow(new Object[]{c.getId(), c.getNombre(), c.getDescripcion()});
    }

    private void openForm(Categoria c) {
        JDialog dlg = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Categoria", true);
        dlg.setSize(360,240); dlg.setLocationRelativeTo(this);
        JPanel p = new JPanel(null); p.setBackground(UIConstants.APP_WHITE);

        JLabel l1 = new JLabel("Nombre:"); l1.setBounds(20,20,80,25); p.add(l1);
        JTextField tfNom = new JTextField(); tfNom.setBounds(110,20,200,25); p.add(tfNom);

        JLabel l2 = new JLabel("Descripcion:"); l2.setBounds(20,60,80,25); p.add(l2);
        JTextField tfDesc = new JTextField(); tfDesc.setBounds(110,60,200,25); p.add(tfDesc);

        JButton save = new JButton("Guardar"); save.setBounds(110,110,100,30);
        save.setBackground(UIConstants.APP_BLUE); save.setForeground(Color.WHITE); p.add(save);

        if (c!=null) { tfNom.setText(c.getNombre()); tfDesc.setText(c.getDescripcion()); }

        save.addActionListener(e -> {
            String nom = tfNom.getText().trim(); String desc = tfDesc.getText().trim();
            if (nom.isEmpty()) { JOptionPane.showMessageDialog(dlg, "Nombre requerido"); return; }
            if (c==null) dao.add(new Categoria(0,nom,desc)); else { c.setNombre(nom); c.setDescripcion(desc); dao.update(c); }
            refresh(); dlg.dispose();
        });

        dlg.add(p); dlg.setVisible(true);
    }
}