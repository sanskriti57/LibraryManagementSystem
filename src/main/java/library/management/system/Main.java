//package library.management.system;
//
//public class Main {
//
//	public static void main(String[] args) {
//		LibraryManager manager = new LibraryManager();
//        manager.start();
//
//	}
//
//}

package library.management.system;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
				new LoginFrame();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // launches login/register screen
        });
    }
}
