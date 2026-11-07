package clinica.vista;

import clinica.modelo.*;
import clinica.controlador.*;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import javax.swing.*;

/** HU-27 (REQ-52): pantalla de inicio con el dashboard según el rol (administrador, recepción o médico). */
public class IfInicio extends javax.swing.JPanel {

    // ASOCIACIÓN: la pantalla usa el Sistema que recibe en el constructor; no lo crea.
    private Sistema sistema;

    // Colores de las tarjetas y de las barras de estado
    private static final Color[] COLORES = {new Color(41, 128, 185), new Color(39, 174, 96), new Color(142, 68, 173),
        new Color(230, 126, 34), new Color(22, 160, 133), new Color(192, 57, 43)};
    private static final Color[] COLORES_ESTADO = {new Color(243, 156, 18), new Color(39, 174, 96), new Color(231, 76, 60)};

    /** Constructor: muestra el encabezado y arma las tarjetas y las barras del dashboard que le corresponden al rol. */
    public IfInicio(Sistema sistema) {
        initComponents();
        this.sistema = sistema;
        Usuario u = sistema.getUsuarioActual();
        setBackground(new Color(245, 247, 250));
        lblSaludo.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        lblFecha.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        lblSaludo.setText("Bienvenido, " + u.getNombreUsuario());
        lblFecha.setText(u.getRol() + "   |   " + Validaciones.nombreDia(LocalDate.now()) + " " + LocalDate.now());
        construirTarjetas(u);
        construirBarras(u);
    }

    /** Crea una tarjeta de color por cada dato del dashboard (hasta 3 por fila) con el número grande y su título. */
    private void construirTarjetas(Usuario u) {
        String[][] datos = sistema.getDashboard().tarjetas(u);
        for (int i = 0; i < datos.length; i++) {
            int x = 30 + (i % 3) * 237;
            int y = 115 + (i / 3) * 105;
            JPanel tarjeta = new JPanel();
            tarjeta.setLayout(null);
            tarjeta.setBackground(COLORES[i % COLORES.length]);
            JLabel valor = new JLabel(datos[i][1]);
            valor.setFont(new Font("Arial", Font.BOLD, 34));
            valor.setForeground(Color.WHITE);
            valor.setBounds(18, 10, 190, 45);
            JLabel titulo = new JLabel(datos[i][0]);
            titulo.setFont(new Font("Arial", Font.PLAIN, 14));
            titulo.setForeground(Color.WHITE);
            titulo.setBounds(18, 58, 190, 25);
            tarjeta.add(valor);
            tarjeta.add(titulo);
            add(tarjeta);
            tarjeta.setBounds(x, y, 225, 92);
        }
    }

    /** Dibuja una barra horizontal por estado de cita; el largo es proporcional a la cantidad. */
    private void construirBarras(Usuario u) {
        String[][] estados = sistema.getDashboard().citasPorEstado(u);
        int mayor = 1;
        for (String[] e : estados) {
            mayor = Math.max(mayor, Integer.parseInt(e[1]));
        }
        JLabel titulo = new JLabel(u.getRol().equals("MEDICO") ? "Mis citas por estado" : "Citas por estado");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(new Color(25, 55, 109));
        add(titulo);
        titulo.setBounds(30, 340, 400, 25);
        for (int i = 0; i < estados.length; i++) {
            int y = 380 + i * 45;
            int cantidad = Integer.parseInt(estados[i][1]);
            JLabel nombre = new JLabel(estados[i][0]);
            nombre.setFont(new Font("Arial", Font.PLAIN, 14));
            add(nombre);
            nombre.setBounds(30, y, 110, 28);
            JPanel barra = new JPanel();
            barra.setBackground(COLORES_ESTADO[i]);
            add(barra);
            barra.setBounds(145, y + 3, Math.max(6, cantidad * 480 / mayor), 22);
            JLabel numero = new JLabel("" + cantidad);
            numero.setFont(new Font("Arial", Font.BOLD, 14));
            add(numero);
            numero.setBounds(145 + Math.max(6, cantidad * 480 / mayor) + 10, y, 60, 28);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSaludo = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(780, 560));
        setLayout(null);

        lblSaludo.setBackground(new java.awt.Color(25, 55, 109));
        lblSaludo.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSaludo.setForeground(new java.awt.Color(255, 255, 255));
        lblSaludo.setText("Bienvenido");
        lblSaludo.setOpaque(true);
        add(lblSaludo);
        lblSaludo.setBounds(0, 0, 780, 55);

        lblFecha.setBackground(new java.awt.Color(25, 55, 109));
        lblFecha.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(200, 215, 240));
        lblFecha.setText("Rol | fecha");
        lblFecha.setOpaque(true);
        add(lblFecha);
        lblFecha.setBounds(0, 55, 780, 35);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblSaludo;
    // End of variables declaration//GEN-END:variables
}
