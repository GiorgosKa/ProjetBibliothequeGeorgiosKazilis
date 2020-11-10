import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Vector;

public class LibraryUserPage extends JFrame {
    private JPanel panel;
    private JTable table3;
    private JTable table1;
    public JTable table;
    private javax.swing.JTabbedPane JTabbedPane;
    private JTable table2;
    private JPanel welcome;
    private JLabel welcomeLabel;
    public JScrollPane history;
    private static DatabaseConnector DatabaseConnector;
    LibraryUserPage(String user){
        super("Library User's Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);
        setSize(800,600);
        DatabaseConnector = new DatabaseConnector();
        DatabaseConnector.connect("jdbc:sqlite:C:\\Users\\Giorgos\\Desktop\\Bibliotheque");
        welcomeLabel.setText("Welcome "+application.user+" in your personal account");
        int State = JTabbedPane.getSelectedIndex();
        // Vector donnee = DatabaseConnector.donneeVector("select distinct\"Oeuvre\".Titre,\"Auteur\".Nom as\"author name\",\"Edition\".\"Editeur\" from\"Oeuvre\",\"Edition\",\"Auteur\",\"A.Ecrit\",\"Livre\" where\"A.Ecrit\".\"IdOeuvre\"=\"Oeuvre\".\"IdOeuvre\" and\"Edition\".\"IdOeuvre\"=\"Oeuvre\".\"IdOeuvre\" and\"Auteur\".\"IdAuteur\"=\"A.Ecrit\".\"IdAuteur\" and\"Livre\".ISBN =\"Edition\".ISBN ;");
        Vector donnee = DatabaseConnector.donneeVector("select distinct\"Oeuvre\".Titre,\"Auteur\".Nom as\"author name\",\"Edition\".\"Editeur\" from\"Oeuvre\",\"Edition\",\"Auteur\",\"A.Ecrit\",\"Livre\" where\"A.Ecrit\".\"IdOeuvre\"=\"Oeuvre\".\"IdOeuvre\" and\"Edition\".\"IdOeuvre\"=\"Oeuvre\".\"IdOeuvre\" and\"Auteur\".\"IdAuteur\"=\"A.Ecrit\".\"IdAuteur\" and\"Livre\".ISBN =\"Edition\".ISBN ;");
        Vector titre = DatabaseConnector.getcolumnsVector("select distinct\"Oeuvre\".Titre,\"Auteur\".Nom as\"author name\",\"Edition\".\"Editeur\" from\"Oeuvre\",\"Edition\",\"Auteur\",\"A.Ecrit\",\"Livre\" where\"A.Ecrit\".\"IdOeuvre\"=\"Oeuvre\".\"IdOeuvre\" and\"Edition\".\"IdOeuvre\"=\"Oeuvre\".\"IdOeuvre\" and\"Auteur\".\"IdAuteur\"=\"A.Ecrit\".\"IdAuteur\" and\"Livre\".ISBN =\"Edition\".ISBN ;");
        DefaultTableModel model = new DefaultTableModel(donnee, new Vector<Object>(Arrays.asList("Title", "Author's Last Name", "Publisher")));
        table1.setModel(model);


        donnee = DatabaseConnector.donneeVector(String.format("select O.Titre,e.ISBN,E.'Date Debut',E.'Date Fin' \n" +
                "from Emprunt E \n" +
                "join Utilisateur U on E.IdUtilisateur =U.IdUtilisateur \n" +
                "join Livre L on L.IdLivre=E.IdLivre \n" +
                "join Edition e on e.ISBN=L.ISBN \n" +
                "join Oeuvre O on O.IdOeuvre=e.IdOeuvre where E.idUtilisateur =%s",user));
        model = new DefaultTableModel(donnee, new Vector<Object>(Arrays.asList("Title", "ISBN", "Borrowing Date", "Returning Date")));
        table.setModel(model);

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
        table2.setModel(model);

    }
}