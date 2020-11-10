

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserOption extends JFrame {
    private JPanel mainView;
    private JTextField pronounField;
    private JTextField nounField;
    private JTextField emailField;
    private JCheckBox isAdmin;
    private JButton confirm;
    private JLabel note;
    LibrarianPage parent;

    AddUserOption(LibrarianPage parent) {
        super("Add a User");
        this.setContentPane(mainView);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.parent = parent;
        this.setVisible(true);
        this.setSize(800,600);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                note.setForeground(new Color(187, 0, 7));
                if ( pronounField.getText().equals("") )
                    note.setText("Invalid First Name");
                else if ( nounField.getText().equals("") )
                    note.setText("Invalid Last Name");
                else if ( emailField.getText().equals("") )
                    note.setText("Invalid E-mail");
                else {
                    if ( DatabaseConnector.addUser(nounField.getText(), pronounField.getText(), emailField.getText(), isAdmin.isSelected() ? "Librarian" : "Student") <= 0 )
                        note.setText("Creation of a New User Failed.");
                    else {
                        note.setForeground(new Color(7, 187, 0));
                        note.setText("The New User was created Successfully!");
                    }

                }
            }
        });
    }
}
