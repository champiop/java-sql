
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;
import javax.swing.JFrame;
import state.MainMenuState;
import state.State;

public class BDD {

    private Connection connection;
    private final JFrame window;
    private final Stack<State> states;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            BDD bdd = new BDD();
        });
    }

    public BDD() {
        this.connectToDatabase();

        this.window = new JFrame("BDD");
        this.window.setBounds(100, 100, 800, 600);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setVisible(true);

        this.states = new Stack<>();
        State.pushState(this.states, new MainMenuState(this.window, this.states));
    }

    private void connectToDatabase() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du driver oracle");
        }

        try {
            String url = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
            String user = "pya";
            String password = "pya";
            this.connection = DriverManager.getConnection(url, user, password);
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données");
        }
    }
}
