/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcamqr;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.ImageIcon;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

/**
 *
 * @author Marto Nieto G
 */
public class WebCamQR extends javax.swing.JFrame implements Runnable, ThreadFactory{
    
    
	private static final long serialVersionUID = 6441489157408381878L;
	private Executor executor = Executors.newSingleThreadExecutor(this);
	private Webcam webcam = null;
	private WebcamPanel panel = null;
        public static Boolean estado = false;
        public static int id_aula;
        //loadImage cargarImg = new loadImage();
        //ImageIcon img = null;
    /**
     * Creates new form WebCamQRExample3
     */
    public WebCamQR() {
        initComponents();
        //this.setLocation(100, 50);
        this.setResizable(false);
        this.setLocationRelativeTo(null); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Boton_Iniciar = new javax.swing.JButton();
        Texto_QR = new javax.swing.JTextField();
        codig_qr = new javax.swing.JLabel();
        config = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QR - Webcam");
        setIconImage(getIconImage());
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );

        Boton_Iniciar.setText("Iniciar");
        Boton_Iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_IniciarActionPerformed(evt);
            }
        });

        Texto_QR.setEnabled(false);

        codig_qr.setText("Codigo QR");

        config.setText("Configuracion");
        config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(codig_qr)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Texto_QR, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Boton_Iniciar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(config)
                        .addGap(24, 24, 24))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Texto_QR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codig_qr))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Boton_Iniciar)
                    .addComponent(config))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Boton_IniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_IniciarActionPerformed
        
        if (this.estado != false){
            
        Dimension size = WebcamResolution.QVGA.getSize();     
        webcam = Webcam.getWebcams().get(0);
	webcam.setViewSize(size);
        
        panel = new WebcamPanel(webcam);
	panel.setPreferredSize(size);
	panel.setFPSDisplayed(true);
       
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
        );    
	executor.execute(this);
        Boton_Iniciar.setEnabled(false);
        
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, "Debe configurar un aula para continuar!");
        }
        				
    }//GEN-LAST:event_Boton_IniciarActionPerformed

    private void configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configActionPerformed
        
        Aula va = new Aula();
        va.show();
        
    }//GEN-LAST:event_configActionPerformed
       /* @Override
        public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("resources/icon.png"));


        return retValue;
    }
    */
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WebCamQR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WebCamQR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WebCamQR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WebCamQR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new WebCamQR().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Boton_Iniciar;
    private javax.swing.JTextField Texto_QR;
    private javax.swing.JLabel codig_qr;
    private javax.swing.JButton config;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    @Override
   public void run() {
		do {
                    try {
                        Thread.sleep(2000);    
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Result result = null;
			BufferedImage image = null;
			if (webcam.isOpen()) {
                            if ((image = webcam.getImage()) == null) {
                                continue;
                            }
                            LuminanceSource source = new BufferedImageLuminanceSource(image);
                            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                            try {
                                result = new MultiFormatReader().decode(bitmap);
                            } catch (NotFoundException e) {
					// fall thru, it means there is no QR code in image
                            }
			}
                        if (result != null) {
                      
                            String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                            String hora = dateTime.substring(dateTime.indexOf(" "));
                            String dia = DateTimeFormatter.ofPattern("e").format(LocalDateTime.now());

                            String apellido,consulta,id_asignatura_a, id_asignatura_p,id_usuario;                           
                            Statement sentencia;
                            ResultSet resultado;
                            int resultado2;
                            id_usuario = result.getText();
                            
                        consulta = "SELECT apellido,id_asignatura_a,id_asignatura_p FROM usuarios WHERE id_usuario='"+id_usuario+"'";
  
                        try {
                             
                            sentencia = Conexion.obtener().createStatement();
                            resultado = sentencia.executeQuery(consulta);
                            
                            if (resultado.next()){
                       
                            apellido = resultado.getString(1);
                            id_asignatura_a = resultado.getString(2);
                            id_asignatura_p = resultado.getString(3);
                            
                            if (id_asignatura_a != null || id_asignatura_p != null){
                                
                                consulta = "SELECT id_asignatura FROM asignaturas WHERE ((id_aula='"+id_aula+"') AND (FIND_IN_SET(id_asignatura,'"+id_asignatura_a+"')) AND (FIND_IN_SET("+dia+",dias)) AND ('"+hora+"' BETWEEN hora_inicio AND hora_fin))";
                                resultado = sentencia.executeQuery(consulta);
                                if (resultado.next()){ 
                                    id_asignatura_a = "'"+resultado.getString(1)+"'";
                                    id_asignatura_p = "null";
                                    
                                    consulta = "SELECT asistencias.id_asistencia FROM asistencias,asignaturas WHERE ((asignaturas.id_asignatura=asistencias.id_asignatura_a) AND (asistencias.id_asignatura_a="+id_asignatura_a+") AND (DATE_FORMAT(asistencias.fecha,'%H:%i') BETWEEN asignaturas.hora_inicio AND asignaturas.hora_fin))";
                                    resultado = sentencia.executeQuery(consulta);
                                    
                                    if (resultado.next()){
                                        Texto_QR.setText("Asistencia ya existente para el usuario "+apellido); 
                                    }
                                    else
                                    {
                                        consulta = "INSERT INTO asistencias VALUES (null,'"+id_usuario+"',"+id_asignatura_a+","+id_asignatura_p+",'"+dateTime+"')";
                                        sentencia.executeUpdate(consulta);
                                        Texto_QR.setText("Asistencia cargada para el usuario "+apellido); 
                                    }
                                    
                                }
                                else
                                {
                                    consulta = "SELECT id_asignatura FROM asignaturas WHERE ((id_aula='"+id_aula+"') AND (FIND_IN_SET(id_asignatura,'"+id_asignatura_p+"')) AND (FIND_IN_SET("+dia+",dias)) AND ('"+hora+"' BETWEEN hora_inicio AND hora_fin))";
                                    resultado = sentencia.executeQuery(consulta);
                                    if (resultado.next()){ 
                                        id_asignatura_p = "'"+resultado.getString(1)+"'"; 
                                        id_asignatura_a = "null";
                                        
                                        consulta = "SELECT asistencias.id_asistencia FROM asistencias,asignaturas WHERE ((asignaturas.id_asignatura=asistencias.id_asignatura_p) AND (asistencias.id_asignatura_p="+id_asignatura_p+") AND (DATE_FORMAT(asistencias.fecha,'%H:%i') BETWEEN asignaturas.hora_inicio AND asignaturas.hora_fin))";
                                        resultado = sentencia.executeQuery(consulta);
                                        
                                        if (resultado.next()){                                       
                                        Texto_QR.setText("Asistencia ya existente para el usuario "+apellido);
                                        }
                                        else
                                        {
                                            consulta = "INSERT INTO asistencias VALUES (null,'"+id_usuario+"',"+id_asignatura_a+","+id_asignatura_p+",'"+dateTime+"')";
                                            sentencia.executeUpdate(consulta);
                                            Texto_QR.setText("Asistencia cargada para el usuario "+apellido); 
                                        }
                                        
                                    }
                                    else
                                    {
                                        Texto_QR.setText("No se encontro una materia en este horario para el usuario "+apellido); 
                                    }
                                }
                                    
                            }
                            else
                            {   
                                consulta = "INSERT INTO asistencias VALUES (null,'"+id_usuario+"',"+id_asignatura_a+","+id_asignatura_p+",'"+dateTime+"')";
                                sentencia.executeUpdate(consulta);
                                Texto_QR.setText("Asistencia cargada para el usuario "+apellido);
                            }

                            }
                            else
                            {
                                Texto_QR.setText("Usuario no encontrado");
                            }
   
                         
                        } catch (ClassNotFoundException | SQLException e) {
                            
                        e.printStackTrace();
                        
                        }

                            //img = new ImageIcon("imgQR/"+result.getText()+".png");
                            //cargarImg.loadImageQR(img);
                            //cargarImg.show();
			}else{
                            Texto_QR.setText("Esperando..");
                            //cargarImg.setVisible(false);
                        }

		} while (true);
	}

    @Override
   public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "example-runner");
		t.setDaemon(true);
		return t;
	}  
}