import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class DatabaseConnector {
    public static Connection DataConnect;

    static public boolean connect(String url) {
        try {
            DataConnect = DriverManager.getConnection(url);
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }
    static public ResultSet executequery(String sqlQuery) throws SQLException {
        Statement statement = DataConnect.createStatement();
        return statement.executeQuery(sqlQuery);
    }
    static public ArrayList<String> list(String sqlQuery, String column) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet resultset = executequery(sqlQuery);
        if ( resultset == null )
            return list;

        while ( resultset.next() )
            list.add(resultset.getString(column));

        return list;
    }

    static public  Vector donneeVector(String sqlQuery){
        ArrayList data = new ArrayList();
        try {
            Connection DataConnect = DatabaseConnector.DataConnect;
            ResultSet rs = DatabaseConnector.executequery(sqlQuery);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            while (rs.next()) {
                ArrayList row = new ArrayList(columns);
                for (int i = 1; i <= columns; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }

        Vector dataVector = new Vector();
        for (int i = 0; i < data.size(); i++) {
            ArrayList subArray = (ArrayList) data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++) {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }
        return dataVector;
    }
    static public  Vector getcolumnsVector(String sqlQuery) {
        ArrayList columnNames = new ArrayList();
        try {
            Connection con = DatabaseConnector.DataConnect;
            ResultSet rs = DatabaseConnector.executequery(sqlQuery);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                columnNames.add(md.getColumnName(i));
            }
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        Vector columnNamesVector = new Vector();
        for (int i = 0; i < columnNames.size(); i++)
            columnNamesVector.add(columnNames.get(i));
        return columnNamesVector;
    }

    static public int addUser(String nom, String prenom, String email, String categorie) {
        try {
            ResultSet r = executequery("select IdUtilisateur from Utilisateur where Email=\""+email+"\"");
            if ( r.next() )
                return 0;
            Statement statement = DataConnect.createStatement();
            return statement.executeUpdate("insert into Utilisateur (Nom, Prenom, Email, NomCatégorie) values (\""+nom+"\", \""+prenom+"\", \""+email+"\", \""+categorie+"\")");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    static public int addEdition(String ISBN, String Editeur, String Annee, int IdOeuvre) {
        try {
            Statement statement = DataConnect.createStatement();
            return statement.executeUpdate("insert into Edition (ISBN, Editeur, Annee, IdOeuvre) values (\""+ISBN+"\", \""+Editeur+"\", \""+Annee+"\", \""+IdOeuvre+"\")");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    static public int addToRedlist(int IdUtilisateur){
        try {
            ResultSet r = executequery("select IdUtilisateur from Appartient where IdUtilisateur="+IdUtilisateur);
            if ( r.next() )
                return 0;
            Statement statement = DataConnect.createStatement();
            return statement.executeUpdate("insert into Appartient values (" + IdUtilisateur + ")");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    static public int deleteFromRedlist(int IdUtilisateur){
        try {
            Statement statement = DataConnect.createStatement();
            return statement.executeUpdate("delete from Appartient where IdUtilisateur="+IdUtilisateur);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }


    static public int insertKeyQuery(String sqlQuery) {
        try {
            PreparedStatement statement = DataConnect.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet r = statement.getGeneratedKeys();
            if ( r != null && r.next() )
                return r.getInt(1);
            return 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    static public int insertQuery(String sqlQuery) {
        try {
            Statement statement = DataConnect.createStatement();
            return statement.executeUpdate(sqlQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    static public int deleteQuery(String sqlQuery) {
        try {
            Statement statement = DataConnect.createStatement();
            return statement.executeUpdate(sqlQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

}