package app.ui;

import app.dao.UsuarioDAO;

import javax.swing.*;
import app.util.UIConstants;
import java.awt.*;

public class LoginFrame extends JFrame {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public LoginFrame() {
        setTitle("4Sabores_NAEL - Login");
        setSize(360,220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }

    private void init() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240,240,240));
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("4Sabores_NAEL - Iniciar sesión");
        lblTitle.setBounds(60,10,240,30);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitle);

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setBounds(30,50,80,25);
        panel.add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(120,50,180,25);
        panel.add(txtUser);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(30,85,80,25);
        panel.add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(120,85,180,25);
        panel.add(txtPass);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBounds(120,125,100,30);
        btnLogin.setBackground(new Color(30, 144, 255)); // azul
        btnLogin.setForeground(Color.WHITE);
        panel.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();
            if (usuarioDAO.validate(user, pass)) {
                SwingUtilities.invokeLater(() -> {
                    MainFrame mf = new MainFrame(user);
                    mf.setVisible(true);
                    dispose();
                });
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);
    }
}