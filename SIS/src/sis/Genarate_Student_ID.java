
package sis;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import static sis.IDCardGenerator.generateIDCard;
import java.sql.*;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.table.DefaultTableModel;
import static sis.DBConnection.getConnection;


/**
 *
 * @author USER
 */
public class Genarate_Student_ID extends javax.swing.JFrame {
    
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Genarate_Student_ID.class.getName());

    public Genarate_Student_ID() {
        initComponents();
         loadStudents();
        loadStudentIDs(); 
    }
    private void loadStudents() {
    // Define column names
    String[] columns = {
        "ID", "Full Name", "Date of Birth", "Address", 
        "Gender", "Gmail", "Phone", "Registered", "Course"
    };

    // Create a new table model and set it to jTable1
    DefaultTableModel model = new DefaultTableModel(columns, 0);
    jTable1.setModel(model);

    // SQL query: join students with courses to get course name
    String sql = "SELECT s.student_id, s.full_name, s.date_of_birth, s.address, "
               + "s.gender, s.gmail, s.mobile_number, s.date_registered, c.course_name "
               + "FROM students s "
               + "JOIN courses c ON s.course_id = c.course_id";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        // Loop through the result set and add rows to the table model
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("student_id"),
                rs.getString("full_name"),
                rs.getDate("date_of_birth"),
                rs.getString("address"),
                rs.getString("gender"),
                rs.getString("gmail"),
                rs.getString("mobile_number"),
                rs.getTimestamp("date_registered"),
                rs.getString("course_name")
            });
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        e.printStackTrace();
    }
}
    private void loadStudentIDs() {
        try {
            String sql = "SELECT student_id FROM students";
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            studentID.removeAllItems();

            while (rs.next()) {
                studentID.addItem(rs.getString("student_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        studentID = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(10, 183, 186));

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Genarate Student ID");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton7.setBackground(new java.awt.Color(10, 183, 186));
        jButton7.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Genarate ID");
        jButton7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        studentID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        studentID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        studentID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentIDActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(10, 183, 186));
        jButton8.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Back");
        jButton8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(studentID, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        studentID.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
    String enteredID = studentID.getSelectedItem().toString();
    Student st = StudentDAO.getStudentByID(enteredID);

    // 2. Generate PNG ID Card
    generateIDCard(st.name, st.studentId, st.course_id, st.photoPath);

    // 3. Send ID Card to Gmail
    sendIDEmail(st.email, st.studentId);

    JOptionPane.showMessageDialog(this, "ID Generated & Sent Successfully!");
    
    
    
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void studentIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentIDActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_studentIDActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> new Genarate_Student_ID().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> studentID;
    // End of variables declaration//GEN-END:variables

    public void sendIDEmail(String receiverEmail, String studentId) {

    final String sender = "janinduac@gmail.com";
    final String appPassword = "glqxhedfgmtjbtqz"; // Gmail App Password

    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(sender, appPassword);
        }
    });

    try {

        // Path to attachment
        File idCardFile = new File("C:\\Users\\USER\\Documents\\NetBeansProjects\\SIS\\Student_ID_" + studentId + ".png");
        if (!idCardFile.exists()) {
            System.out.println("ERROR: ID Card image not found: " + idCardFile.getAbsolutePath());
            return;
        }

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
        message.setSubject("Your Student ID Card");

        // Message text
        MimeBodyPart textBody = new MimeBodyPart();
        textBody.setText("Dear Student,\n\nPlease find your Student ID Card attached.\n\nThank you.");

        // Attachment
        MimeBodyPart attachmentBody = new MimeBodyPart();
        attachmentBody.attachFile(idCardFile);

        // Combine
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textBody);
        multipart.addBodyPart(attachmentBody);

        message.setContent(multipart);

        Transport.send(message);
        System.out.println("Email sent successfully!");

    } catch (IOException | MessagingException e) {
        System.out.println("EMAIL ERROR: " + e.getMessage());
    }
}

    
}
