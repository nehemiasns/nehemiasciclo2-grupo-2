package app.ui;

import app.dao.ProductoDAO;
import app.dao.UsuarioDAO;
import app.models.Producto;
import app.ui.PedidosPanel;
import app.ui.CarritoPanel;
import app.util.UIConstants;
import app.ui.ClientesPanel;
import app.ui.CategoriasPanel;
import app.ui.VentasPanel;
import app.ui.DeliveryPanel;
import app.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class MainFrame extends JFrame {
    private String currentUser;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ProductoDAO productoDAO = new ProductoDAO();

    private JTable tblUsuarios;
    private DefaultTableModel usuariosModel;

    private JTable tblProductos;
    private DefaultTableModel productosModel;

    public MainFrame(String currentUser) {
        this.currentUser = currentUser;
        setTitle("4Sabores_NAEL - Panel | Usuario: " + currentUser);
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }

    private void init() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(UIConstants.APP_WHITE);

        // Usuarios tab
        JPanel pUsuarios = new JPanel(new BorderLayout());
        usuariosModel = new DefaultTableModel(new Object[]{"ID","Usuario","Rol"},0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblUsuarios = new JTable(usuariosModel);
        refreshUsuariosTable();
        pUsuarios.add(new JScrollPane(tblUsuarios), BorderLayout.CENTER);
        JPanel upanel = new JPanel();
        JButton btnAddU = new JButton("Agregar");
        JButton btnEditU = new JButton("Editar");
        JButton btnDelU = new JButton("Eliminar");
        upanel.add(btnAddU); upanel.add(btnEditU); upanel.add(btnDelU);
        pUsuarios.add(upanel, BorderLayout.SOUTH);

        btnAddU.addActionListener(e -> openUsuarioForm(null));
        btnEditU.addActionListener(e -> {
            int r = tblUsuarios.getSelectedRow();
            if (r==-1) { JOptionPane.showMessageDialog(this, "Selecciona un usuario"); return; }
            int id = Integer.parseInt(tblUsuarios.getValueAt(r,0).toString());
            Usuario u = usuarioDAO.getAll().stream().filter(x->x.getId()==id).findFirst().orElse(null);
            openUsuarioForm(u);
        });
        btnDelU.addActionListener(e -> {
            int r = tblUsuarios.getSelectedRow();
            if (r==-1) { JOptionPane.showMessageDialog(this, "Selecciona un usuario"); return; }
            int id = Integer.parseInt(tblUsuarios.getValueAt(r,0).toString());
            int opt = JOptionPane.showConfirmDialog(this, "Eliminar usuario ID="+id+"?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opt==JOptionPane.YES_OPTION) {
                usuarioDAO.delete(id);
                refreshUsuariosTable();
            }
        });

        // Productos tab
        JPanel pProductos = new JPanel(new BorderLayout());
        productosModel = new DefaultTableModel(new Object[]{"ID","Nombre","Categoria","Precio","Stock"},0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblProductos = new JTable(productosModel);
        refreshProductosTable();
        pProductos.add(new JScrollPane(tblProductos), BorderLayout.CENTER);
        JPanel pprodButtons = new JPanel();
        JButton btnAddP = new JButton("Agregar");
        JButton btnEditP = new JButton("Editar");
        JButton btnDelP = new JButton("Eliminar");
        pprodButtons.add(btnAddP); pprodButtons.add(btnEditP); pprodButtons.add(btnDelP);
        pProductos.add(pprodButtons, BorderLayout.SOUTH);

        btnAddP.addActionListener(e -> openProductoForm(null));
        btnEditP.addActionListener(e -> {
            int r = tblProductos.getSelectedRow();
            if (r==-1) { JOptionPane.showMessageDialog(this, "Selecciona un producto"); return; }
            int id = Integer.parseInt(tblProductos.getValueAt(r,0).toString());
            Producto p = productoDAO.findById(id).orElse(null);
            openProductoForm(p);
        });
        btnDelP.addActionListener(e -> {
            int r = tblProductos.getSelectedRow();
            if (r==-1) { JOptionPane.showMessageDialog(this, "Selecciona un producto"); return; }
            int id = Integer.parseInt(tblProductos.getValueAt(r,0).toString());
            int opt = JOptionPane.showConfirmDialog(this, "Eliminar producto ID="+id+"?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opt==JOptionPane.YES_OPTION) {
                productoDAO.delete(id);
                refreshProductosTable();
            }
        });

        // Other tabs (skeleton)
        JPanel pClientes = new JPanel(); pClientes.add(new JLabel("CRUD Cliente - Pendiente implementar (skeleton).")); 
        JPanel pCategorias = new JPanel(); pCategorias.add(new JLabel("CRUD Categoria - Pendiente implementar (skeleton).")); 
        JPanel pVentas = new JPanel(); pVentas.add(new JLabel("Venta y Venta_Detalle - Pendiente implementar (skeleton).")); 
        JPanel pCarrito = new JPanel(); pCarrito.add(new JLabel("Carrito_Venta - Pendiente implementar (skeleton).")); 
        JPanel pDelivery = new JPanel(); pDelivery.add(new JLabel("Delivery - Pendiente implementar (skeleton).")); 

        tabs.addTab("Usuarios", pUsuarios);
        tabs.addTab("Clientes", new ClientesPanel());
        tabs.addTab("Productos", pProductos);
        tabs.addTab("Pedidos", new PedidosPanel());
        tabs.addTab("Categorias", new CategoriasPanel());
        tabs.addTab("Ventas", new VentasPanel());
        tabs.addTab("Carrito", new CarritoPanel());
        tabs.addTab("Delivery", new DeliveryPanel());

        add(tabs);
    }

    private void refreshUsuariosTable() {
        usuariosModel.setRowCount(0);
        for (Usuario u : usuarioDAO.getAll()) {
            usuariosModel.addRow(new Object[]{u.getId(), u.getUsername(), u.getRole()});
        }
    }

    private void refreshProductosTable() {
        productosModel.setRowCount(0);
        for (Producto p : productoDAO.getAll()) {
            productosModel.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
        }
    }

    private void openUsuarioForm(Usuario u) {
        JDialog dlg = new JDialog(this, "Usuario", true);
        dlg.setSize(360,260);
        dlg.setLocationRelativeTo(this);
        JPanel p = new JPanel(null);
        p.setBackground(UIConstants.APP_WHITE);

        JLabel l1 = new JLabel("Usuario:"); l1.setBounds(20,20,80,25); p.add(l1);
        JTextField tfUser = new JTextField(); tfUser.setBounds(110,20,200,25); p.add(tfUser);

        JLabel l2 = new JLabel("Contraseña:"); l2.setBounds(20,60,80,25); p.add(l2);
        JTextField tfPass = new JTextField(); tfPass.setBounds(110,60,200,25); p.add(tfPass);

        JLabel l3 = new JLabel("Rol:"); l3.setBounds(20,100,80,25); p.add(l3);
        JTextField tfRole = new JTextField(); tfRole.setBounds(110,100,200,25); p.add(tfRole);

        JButton btnSave = new JButton("Guardar");
        btnSave.setBounds(110,150,100,30);
        btnSave.setBackground(UIConstants.APP_BLUE);
        btnSave.setForeground(Color.WHITE);
        p.add(btnSave);

        if (u!=null) {
            tfUser.setText(u.getUsername());
            tfPass.setText(u.getPassword());
            tfRole.setText(u.getRole());
        }

        btnSave.addActionListener(e -> {
            String user = tfUser.getText().trim();
            String pass = tfPass.getText().trim();
            String role = tfRole.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(dlg, "Usuario y contraseña requeridos");
                return;
            }
            if (u==null) {
                Usuario nu = new Usuario(0, user, pass, role);
                usuarioDAO.add(nu);
            } else {
                u.setUsername(user); u.setPassword(pass); u.setRole(role);
                usuarioDAO.update(u);
            }
            refreshUsuariosTable();
            dlg.dispose();
        });

        dlg.add(p);
        dlg.setVisible(true);
    }

    private void openProductoForm(Producto p) {
        JDialog dlg = new JDialog(this, "Producto", true);
        dlg.setSize(420,320);
        dlg.setLocationRelativeTo(this);
        JPanel panel = new JPanel(null);
        panel.setBackground(UIConstants.APP_WHITE);

        JLabel l1 = new JLabel("Nombre:"); l1.setBounds(20,20,100,25); panel.add(l1);
        JTextField tfNombre = new JTextField(); tfNombre.setBounds(130,20,240,25); panel.add(tfNombre);

        JLabel l2 = new JLabel("Categoria:"); l2.setBounds(20,60,100,25); panel.add(l2);
        JTextField tfCat = new JTextField(); tfCat.setBounds(130,60,240,25); panel.add(tfCat);

        JLabel l3 = new JLabel("Precio:"); l3.setBounds(20,100,100,25); panel.add(l3);
        JTextField tfPrecio = new JTextField(); tfPrecio.setBounds(130,100,240,25); panel.add(tfPrecio);

        JLabel l4 = new JLabel("Stock:"); l4.setBounds(20,140,100,25); panel.add(l4);
        JTextField tfStock = new JTextField(); tfStock.setBounds(130,140,240,25); panel.add(tfStock);

        JButton btnSave = new JButton("Guardar");
        btnSave.setBounds(140,200,120,35);
        btnSave.setBackground(UIConstants.APP_BLUE);
        btnSave.setForeground(Color.WHITE);
        panel.add(btnSave);

        if (p!=null) {
            tfNombre.setText(p.getNombre());
            tfCat.setText(p.getCategoria());
            tfPrecio.setText(String.valueOf(p.getPrecio()));
            tfStock.setText(String.valueOf(p.getStock()));
        }

        btnSave.addActionListener(e -> {
            try {
                String nombre = tfNombre.getText().trim();
                String cat = tfCat.getText().trim();
                double precio = Double.parseDouble(tfPrecio.getText().trim());
                int stock = Integer.parseInt(tfStock.getText().trim());
                if (nombre.isEmpty()) { JOptionPane.showMessageDialog(dlg, "Nombre requerido"); return; }
                if (p==null) {
                    Producto np = new Producto(0, nombre, cat, precio, stock);
                    productoDAO.add(np);
                } else {
                    p.setNombre(nombre); p.setCategoria(cat); p.setPrecio(precio); p.setStock(stock);
                    productoDAO.update(p);
                }
                refreshProductosTable();
                dlg.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Error en datos: " + ex.getMessage());
            }
        });

        dlg.add(panel);
        dlg.setVisible(true);
    }
}