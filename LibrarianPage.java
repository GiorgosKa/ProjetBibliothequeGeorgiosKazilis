import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;
import java.util.Vector;

public class LibrarianPage extends JFrame {
    private JPanel panel;
    private JTable table1;
    private JLabel welcome;
    private JTable table2;
    private JLabel textf;
    private JButton addUserButton;
    private JButton addANewEditionButton;
    private JPanel LibraryUsers;
    private JPanel RedList;
    private JPanel AvailableBooks;
    private JTable table3;
    private JTable table4;
    private JTable table5;
    private JButton redListButton;
    private JButton removeFromRedListButton;
    private static DatabaseConnector DatabaseConnector;
    LibrarianPage(){
        super("Librarian's Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);
        setSize(800,600);
        welcome.setText("Welcome "+application.user+" in your personal account");
        DatabaseConnector = new DatabaseConnector();
        DatabaseConnector.connect("jdbc:sqlite:C:\\Users\\Giorgos\\Desktop\\Bibliotheque");

        Vector donnee = DatabaseConnector.donneeVector("select\"IdUtilisateur\",\"Nom\",\"Prenom\",\"Email\" from\"Utilisateur\"");
        Vector titre = DatabaseConnector.getcolumnsVector("select\"IdUtilisateur\",\"Nom\",\"Prenom\",\"Email\" from\"Utilisateur\"");
        DefaultTableModel model = new DefaultTableModel(donnee, new Vector<Object>(Arrays.asList("User's ID", "Last Name", "First Name", "E-mail")));
        table4.setModel(model);

        donnee = DatabaseConnector.donneeVector("select\"Appartient\".\"IdUtilisateur\",\"Utilisateur\".\"Nom\",\"Utilisateur\".\"Prenom\" from\"Appartient\",\"Utilisateur\" WHERE\"Appartient\".\"IdUtilisateur\"=\"Utilisateur\".\"IdUtilisateur\"");
        titre = DatabaseConnector.getcolumnsVector("select\"Appartient\".\"IdUtilisateur\",\"Utilisateur\".\"Nom\",\"Utilisateur\".\"Prenom\" from\"Appartient\",\"Utilisateur\" WHERE\"Appartient\".\"IdUtilisateur\"=\"Utilisateur\".\"IdUtilisateur\"");
        model = new DefaultTableModel(donnee, new Vector<Object>(Arrays.asList("User's ID", "Last Name", "First Name")));
        table5.setModel(model);
        table5.setBackground(Color.RED);

         donnee = DatabaseConnector.donneeVector("select distinct\"Oeuvre\".Titre,\"Auteur\".Nom as\"author name\",\"Edition\".ISBN,\"Edition\".\"Editeur\" from\"Oeuvre\",\"Edition\",\"Auteur\",\"A.Ecrit\",\"Livre\" where\"A.Ecrit\".\"IdOeuvre\"=\"Oeuvre\".\"IdOeuvre\" and\"Edition\".\"IdOeuvre\"=\"Oeuvre\".\"IdOeuvre\" and\"Auteur\".\"IdAuteur\"=\"A.Ecrit\".\"IdAuteur\" and\"Livre\".ISBN =\"Edition\".ISBN ;");
         titre = DatabaseConnector.getcolumnsVector("select distinct\"Oeuvre\".Titre,\"Auteur\".Nom as\"author name\",\"Edition\".ISBN,\"Edition\".\"Editeur\" from\"Oeuvre\",\"Edition\",\"Auteur\",\"A.Ecrit\",\"Livre\" where\"A.Ecrit\".\"IdOeuvre\"=\"Oeuvre\".\"IdOeuvre\" and\"Edition\".\"IdOeuvre\"=\"Oeuvre\".\"IdOeuvre\" and\"Auteur\".\"IdAuteur\"=\"A.Ecrit\".\"IdAuteur\" and\"Livre\".ISBN =\"Edition\".ISBN ;");
         model = new DefaultTableModel(donnee, new Vector<Object>(Arrays.asList("Title", "Author's Last Name", "ISBN", "Publisher")));
        table1.setModel(model);
        donnee = DatabaseConnector.donneeVector("select O.Titre,e.ISBN,E.'Date Debut',E.'Date Fin' \n" +
                "from Emprunt E \n" +
                "join Utilisateur U on E.IdUtilisateur =U.IdUtilisateur \n" +
                "join Livre L on L.IdLivre=E.IdLivre \n" +
                "join Edition e on e.ISBN=L.ISBN \n" +
                "join Oeuvre O on O.IdOeuvre=e.IdOeuvre");
        titre = DatabaseConnector.getcolumnsVector("select O.Titre,e.ISBN,E.'Date Debut',E.'Date Fin' \n" +
                "from Emprunt E \n" +
                "join Utilisateur U on E.IdUtilisateur =U.IdUtilisateur \n" +
                "join Livre L on L.IdLivre=E.IdLivre \n" +
                "join Edition e on e.ISBN=L.ISBN \n" +
                "join Oeuvre O on O.IdOeuvre=e.IdOeuvre");
        model = new DefaultTableModel(donnee, new Vector<Object>(Arrays.asList("Title", "ISBN", "Borrowing Date", "Returning Date")));
        table2.setModel(model);

        donnee = DatabaseConnector.donneeVector("select O.Titre,Aa.Nom,e.ISBN\n" +
                "from Livre L\n" +
                "join Edition e on e.ISBN=L.ISBN \n" +
                "join Oeuvre O on O.IdOeuvre=e.IdOeuvre\n" +
                "join 'A.Ecrit' A on A.IdOeuvre = O.IdOeuvre\n" +
                "join Auteur Aa on Aa.IdAuteur=A.IdAuteur\n" +
                "where L.IdLivre not In (\n" +
                "select IdLivre from Emprunt where Emprunt.'Date Fin' = 'NULL')");
        titre = DatabaseConnector.getcolumnsVector("select O.Titre,Aa.Nom,e.ISBN\n" +
                "from Livre L\n" +
                "join Edition e on e.ISBN=L.ISBN \n" +
                "join Oeuvre O on O.IdOeuvre=e.IdOeuvre\n" +
                "join 'A.Ecrit' A on A.IdOeuvre = O.IdOeuvre\n" +
                "join Auteur Aa on Aa.IdAuteur=A.IdAuteur\n" +
                "where L.IdLivre not In (\n" +
                "select IdLivre from Emprunt where Emprunt.'Date Fin' = 'NULL')");
        model = new DefaultTableModel(donnee, new Vector<Object>(Arrays.asList("Title", "Author's Last Name", "ISBN")));
        table3.setModel(model);


        LibrarianPage self = this;
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserOption(self);
            }
        });

        addANewEditionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddBookOption();
            }
        });
        redListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table4.getSelectedRow();
                if (row == -1)return;

                row = table4.convertRowIndexToModel(row);
                DefaultTableModel model = (DefaultTableModel) table4.getModel();
                Object[] dataRow = new Object[]{
                    model.getValueAt(row, 0),
                    model.getValueAt(row, 1),
                    model.getValueAt(row, 2),
                    model.getValueAt(row, 3)
                };

                if ( DatabaseConnector.addToRedlist((int)dataRow[0]) > 0) {
                    model = (DefaultTableModel) table5.getModel();
                    model.addRow(
                            dataRow
                    );
                }
            }
        });

        removeFromRedListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table5.getSelectedRow();
                if (row == -1)return;

                row = table4.convertRowIndexToModel(row);
                DefaultTableModel model = (DefaultTableModel) table5.getModel();
                if ( DatabaseConnector.deleteFromRedlist((int)model.getValueAt(row, 0)) > 0) {
                    model.removeRow(row);
                }
            }
        });
    }
    }


