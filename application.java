import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class application extends JFrame {
    static String Utilisateur;
    static ArrayList names = new ArrayList();
    static ArrayList Ids = new ArrayList();
    private JPasswordField passn;
    private JTextField usern;
    private JRadioButton librarianRadioButton;
    private JRadioButton LibrarianRadioButton;
    private JButton button1;
    private JPanel Panel;
    private JLabel Username;
    private JPanel panel;
    static String user;
    static String pass;
    static int m = 0;

    application() {
        super("application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);
        setSize(800,600);
        ButtonGroup group = new ButtonGroup();
        group.add(librarianRadioButton); group.add(LibrarianRadioButton);
        try {
            DatabaseConnector.connect("jdbc:sqlite:C:\\Users\\Giorgos\\Desktop\\Bibliotheque");
        } catch (Exception e){
            System.err.println("Error");
            System.err.println(e.getMessage());
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pass = passn.getText();
                user = usern.getText();
                if(librarianRadioButton.isSelected()) {
                    try {
                        names = DatabaseConnector.list("select Nom from Utilisateur where NomCatégorie=\"Student\"", "Nom");
                        Ids = DatabaseConnector.list("select IdUtilisateur from Utilisateur where NomCatégorie=\"Student\"", "IdUtilisateur");
                    } catch (Exception err){
                        System.err.println("Error");
                        System.err.println(err.getMessage());
                    }         System.out.println(names); System.out.println(Ids);

                    for (int k = 0; k < names.size(); k++) {
                        if (names.get(k).equals(user) && Ids.get(k).equals(pass)) {
                            new LibraryUserPage(pass); dispose();
                        }
                    }
                } else if ( LibrarianRadioButton.isSelected() ) {
                    try {
                        names = DatabaseConnector.list("select Nom from Utilisateur where NomCatégorie=\"Librarian\"", "Nom");
                        Ids = DatabaseConnector.list("select IdUtilisateur from Utilisateur where NomCatégorie=\"Librarian\"", "IdUtilisateur");
                    } catch (Exception err) {
                        System.err.println("Error");
                        System.err.println(err.getMessage());
                    }        System.out.println(names); System.out.println(Ids);


                    for (int k = 0; k < names.size(); k++) {
                        if (names.get(k).equals(user) && Ids.get(k).equals(pass)) {
                            new LibrarianPage();
                            dispose();
                        }
                    }
                }
            }
        });
    }

    public application(String title){
    }


}