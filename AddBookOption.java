import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookOption extends JFrame {
    private JPanel mainView;
    private JTextField titleField;
    private JTextField releaseDateField;
    private JTextField author1;
    private JTextField author2;
    private JTextField author3;
    private JTextField author4;
    private JTextField author5;
    private JTextField ISBNField;
    private JTextField editorField;
    private JTextField editYearField;
    private JButton confirm;
    private JLabel note;

    AddBookOption() {
        super("Add a Book");
        this.setContentPane(mainView);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setSize(800,600);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                note.setForeground(new Color(187, 0, 7));
                if ( titleField.getText().equals("") )
                    note.setText("Invalid Title");
                else if ( releaseDateField.getText().equals("") ) //checking
                    note.setText("Invalid Date");
                else if ( author1.getText().equals("") )
                    note.setText("auteur 1 obligatoire.");
                else if ( ISBNField.getText().equals("") )
                    note.setText("Invalid ISBN");
                else if ( editorField.getText().equals("") )
                    note.setText("Invalid Publisher");
                else if ( editYearField.getText().equals("") )
                    note.setText("Invalid Publishing Date");
                else {
                    int id = DatabaseConnector.insertKeyQuery("insert into Oeuvre (Titre, Année) values (\"" + titleField.getText() + "\", \"" + Integer.parseInt(releaseDateField.getText()) + "\")");
                    int ide = id;
                    if (id > 0)
                        id = DatabaseConnector.addEdition(ISBNField.getText(), editorField.getText(), editYearField.getText(), id);
                    if (id > 0)
                        id = DatabaseConnector.insertKeyQuery("insert into Livre (ISBN) values (" + ISBNField.getText() + ")");
                    if ( id > 0 ) {
                        note.setForeground(new Color(7, 187, 0));;
                        note.setText("Book was Added Successfully");
                    }

                    String[] name = getName(author1.getText());
                    id = DatabaseConnector.insertKeyQuery("insert into Auteur (Nom, Prenom) values (\""+name[0]+"\",\""+name[1]+"\")");
                    if (id > 0)
                        DatabaseConnector.insertQuery("insert into \"A.Ecrit\" values ("+ide+", " + id + ")");

                    if ( !author2.getText().equals("") ) {
                        name = getName(author2.getText());
                        id = DatabaseConnector.insertKeyQuery("insert into Auteur (Nom, Prenom) values (\""+name[0]+"\",\""+name[1]+"\")");
                        if (id > 0)
                            DatabaseConnector.insertQuery("insert into \"A.Ecrit\" values ("+ide+", " + id + ")");
                    }

                    if ( !author3.getText().equals("") ) {
                        name = getName(author3.getText());
                        id = DatabaseConnector.insertKeyQuery("insert into Auteur (Nom, Prenom) values (\""+name[0]+"\",\""+name[1]+"\")");
                        if (id > 0)
                            DatabaseConnector.insertQuery("insert into \"A.Ecrit\" values ("+ide+", " + id + ")");
                    }

                    if ( !author4.getText().equals("") ) {
                        name = getName(author4.getText());
                        id = DatabaseConnector.insertKeyQuery("insert into Auteur (Nom, Prenom) values (\""+name[0]+"\",\""+name[1]+"\")");
                        if (id > 0)
                            DatabaseConnector.insertQuery("insert into \"A.Ecrit\" values ("+ide+", " + id + ")");
                    }

                    if ( !author5.getText().equals("") ) {
                        name = getName(author5.getText());
                        id = DatabaseConnector.insertKeyQuery("insert into Auteur (Nom, Prenom) values (\""+name[0]+"\",\""+name[1]+"\")");
                        if (id > 0)
                            DatabaseConnector.insertQuery("insert into \"A.Ecrit\" values ("+ide+", " + id + ")");
                    }
                }
            }
        });

    }

    public String[] getName(String name) {
        String[] parts = name.split(" ");
        String noun = parts[parts.length - 1];
        String pronoun = "";
        for ( int i = 0; i < parts.length - 1; i++ )
            pronoun += parts[i] + " ";
        pronoun = pronoun.substring(0, Math.max(pronoun.length()-1, 0));
        return new String[]{pronoun, noun};
    }
}
