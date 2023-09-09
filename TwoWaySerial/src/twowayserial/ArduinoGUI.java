package twowayserial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ArduinoGUI extends javax.swing.JFrame {

    boolean isRedPressed = false;
    boolean isGreenPressed = false;
    boolean isBluePressed = false;
     
    static private OutputStream out;
     
    private int[] baudRate = {110, 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 38400, 57600, 115200, 128000, 256000};
    
    String imgPath = "C:\\Images\\";
    
    public ArduinoGUI() {
        initComponents();
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) 
        {
            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) 
            {
                cboxPort.addItem(portId.getName());
            }
        }
        
        for(int b : baudRate)
        {
            cboxBaud.addItem(Integer.toString(b));
        }
        
       	btnRed.setEnabled(false);
	btnGreen.setEnabled(false);
        btnBlue.setEnabled(false);
	jSlider1.setEnabled(false);
	jTextField1.setEnabled(false);
	btnSend.setEnabled(false);
	btnClear.setEnabled(false);
    }  
    private void sendData(JButton btn,  String msg, String img)
    {
        try
        {
            ImageIcon newIcon = new ImageIcon(imgPath+img);
            btn.setIcon(newIcon);
            out.write(msg.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception ex)
        {
            
        }
    }
    static boolean connect(String portName, int baudRate) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort comPort = portIdentifier.open("", 2000);
           
            SerialPort serialPort = (SerialPort) comPort;
            serialPort.setSerialPortParams(baudRate,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

            InputStream in = serialPort.getInputStream();
            out = serialPort.getOutputStream();

            SerialReader reader = new SerialReader(in);
            
            Thread readerThread = new Thread(reader);

            readerThread.start();
            
        }
        return true;
    }
    public static class SerialReader implements Runnable {
        
    InputStream in;
    StringBuilder stringBuilder;
    
    public SerialReader(InputStream in) 
    {
        this.in = in;
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public void run() 
    {
        byte[] buffer = new byte[1024];
        int len = -1;
        try
        {
            while ((len = in.read(buffer)) > -1)
            {
                String str = new String(buffer, 0, len);
                
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c == '\n' || c == '\r') {
                        if (stringBuilder.length() > 0) {
                            try {
                                int number = Integer.parseInt(stringBuilder.toString());
                                sensorProgressBar.update(number);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input: " + stringBuilder.toString());
                            }
                            stringBuilder.setLength(0);
                        }
                    } else {
                        stringBuilder.append(c);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }    
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        btnRed = new javax.swing.JButton();
        btnGreen = new javax.swing.JButton();
        btnBlue = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        labelServoValue = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        sensorProgressBar = new twowayserial.CircularProgressBar();
        jSeparator1 = new javax.swing.JSeparator();
        cboxPort = new javax.swing.JComboBox<>();
        cboxBaud = new javax.swing.JComboBox<>();
        btnOpen = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Arduino GUI");

        btnRed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/twowayserial/ledRedOff.png"))); // NOI18N
        btnRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedActionPerformed(evt);
            }
        });

        btnGreen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/twowayserial/ledGreenOff.png"))); // NOI18N
        btnGreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGreenActionPerformed(evt);
            }
        });

        btnBlue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/twowayserial/ledBlueOff.png"))); // NOI18N
        btnBlue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBlueActionPerformed(evt);
            }
        });

        jSlider1.setMaximum(180);
        jSlider1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jSlider1MouseDragged(evt);
            }
        });

        labelServoValue.setText("0");

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sensorProgressBarLayout = new javax.swing.GroupLayout(sensorProgressBar);
        sensorProgressBar.setLayout(sensorProgressBarLayout);
        sensorProgressBarLayout.setHorizontalGroup(
            sensorProgressBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        sensorProgressBarLayout.setVerticalGroup(
            sensorProgressBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnOpen.setText("Open");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        jLabel1.setText("Port");

        jLabel3.setText("Baud Rate");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboxPort, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboxBaud, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOpen)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnRed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnGreen, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnBlue, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(165, 165, 165)
                                    .addComponent(labelServoValue))
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sensorProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cboxPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboxBaud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)))
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnBlue, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGreen, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRed, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelServoValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(sensorProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        try 
        {
            if (connect(String.valueOf(cboxPort.getSelectedItem()), Integer.valueOf((String) cboxBaud.getSelectedItem()))) 
            {
                btnRed.setEnabled(true);
                btnGreen.setEnabled(true);
                btnBlue.setEnabled(true);
                jSlider1.setEnabled(true);
                jTextField1.setEnabled(true);
                btnSend.setEnabled(true);
                btnClear.setEnabled(true);
            }
            else
            {
                System.out.println("NOK");
            }

        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnRedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedActionPerformed
        if(isRedPressed)
        {
            sendData(btnRed, "ROFF", "ledRedOff.png");
        }
        else
        {
            sendData(btnRed, "RON", "ledRedOn.png");
        }       
       isRedPressed = !isRedPressed;
    }//GEN-LAST:event_btnRedActionPerformed

    private void btnGreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGreenActionPerformed
        if(isGreenPressed)
        {
            sendData(btnGreen, "GOFF", "ledGreenOff.png");
        }
        else
        {
            sendData(btnGreen, "GON", "ledGreenOn.png");
        }       
       isGreenPressed = !isGreenPressed;
    }//GEN-LAST:event_btnGreenActionPerformed

    private void btnBlueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBlueActionPerformed
        if(isBluePressed)
        {
            sendData(btnBlue, "BOFF", "ledBlueOff.png");
        }
        else
        {
            sendData(btnBlue, "BON", "ledBlueOn.png");
        }       
       isBluePressed = !isBluePressed;
    }//GEN-LAST:event_btnBlueActionPerformed

    private void jSlider1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider1MouseDragged
        String value = String.valueOf(jSlider1.getValue());
        labelServoValue.setText(value);
         try 
         {
            out.write(value.getBytes(StandardCharsets.UTF_8));
            out.write("\n".getBytes(StandardCharsets.UTF_8));
         } 
         catch (Exception ex) 
         {
            
         }
    }//GEN-LAST:event_jSlider1MouseDragged

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        try 
        {
            out.write(jTextField1.getText().getBytes(StandardCharsets.UTF_8));
            jTextField1.setText("");
         } 
        catch (Exception ex)
        {
             
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        try 
        {
            out.write("clr".getBytes(StandardCharsets.UTF_8));
         } 
        catch (Exception ex)
        {
             
        }
    }//GEN-LAST:event_btnClearActionPerformed
    public static void main(String args[]) {
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ArduinoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArduinoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArduinoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArduinoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ArduinoGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBlue;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnGreen;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnRed;
    private javax.swing.JButton btnSend;
    private javax.swing.JComboBox<String> cboxBaud;
    private javax.swing.JComboBox<String> cboxPort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelServoValue;
    private static twowayserial.CircularProgressBar sensorProgressBar;
    // End of variables declaration//GEN-END:variables
}
