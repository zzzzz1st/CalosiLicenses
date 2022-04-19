package sample;
import java.sql.*;

public class MySQLConn {
    private final Connection connection = establishConnection ();

    public Connection establishConnection (){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://bngskvjcteosbt8lowsa-mysql.services.clever-cloud.com:3306/bngskvjcteosbt8lowsa","uvs3ueqaxl0rkmwb","EQ0ja5dFFQRQ5SrGM99O");
            String query = "select * from Dispositivo";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String hostid = rs.getString("HostID");
                System.out.println(hostid);
                return conn;
            }
        }catch (SQLException ex) {
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }
        return conn;
    }

    public void insertDispositivo(String hostid, String sn, String model, String puntoV){
        try {

            String query = "insert into Dispositivo (HostID, SerialN, Model, PuntoV) " +
                            "values (?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostid);
            stmt.setString(2, sn);
            stmt.setString(3, model);
            stmt.setString(4, puntoV);
            System.out.println(query);
            stmt.execute();
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }

    }

    public void insertLicenza(String hostid, String codeL, String nOrdine){
        try {

            String query = "insert into Licenza (HostID, CodL, NOrdine) " +
                            "values (?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostid);
            stmt.setString(2, codeL);
            stmt.setString(3, nOrdine);
            System.out.println(query);
            stmt.execute();
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }

    }

    public void deleteLicenza(String hostid, String codeL) {
        try {

            String query = "delete from Licenza where hostID = ? and CodL = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostid);
            stmt.setString(2, codeL);
            System.out.println(query);
            stmt.execute();
        } catch (SQLException ex) {
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }
    }

        public void deleteDispositivo(String hostid){
            try {
                String query = "delete from Dispositivo where hostID = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, hostid);
                System.out.println(query);
                stmt.execute();
            } catch (SQLException ex) {
                ErrorsController errorsController = new ErrorsController();
                errorsController.display(ex.getMessage());
            }
        }

    public void updateDispositivo(String setwhat, String setthis, String setwhere){
        try {
            String query = "update Dispositivo set " + setwhat + " = ?  where HostID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, setthis);
            stmt.setString(2, setwhere);
            System.out.println(query);
            stmt.execute();
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }

    }

    public ResultSet selectByPV(String pv){
        try {
            String query = "select Dispositivo.HostID, CodL, NOrdine from Dispositivo left join Licenza on Dispositivo.HostID = Licenza.HostID where Dispositivo.PuntoV = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, pv);
            System.out.println(query);
            return stmt.executeQuery();
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }
        return null;
    }

    public ResultSet selectByHostID(String hostid){
        try {
            String query = "SELECT SerialN, Model, PuntoV FROM Dispositivo where Dispositivo.HostID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostid);
            System.out.println(query);
            return stmt.executeQuery();
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }
        return null;
    }

    public ResultSet selectByHostIDLicenses(String hostid){
        try {
            String query = "SELECT CodL, NOrdine FROM Licenza where Licenza.HostID= ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostid);
            System.out.println(query);
            return stmt.executeQuery();
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }
        return null;
    }

    public ResultSet selectDistinctPV(){
        try {
            Statement stmt = connection.createStatement();
            String query = "select distinct PuntoV from Dispositivo";
            System.out.println(query);
            return stmt.executeQuery(query);
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }
        return null;
    }
    public ResultSet selectAllHostIDs(){
        try {
            Statement stmt = connection.createStatement();
            String query = "select HostID from Dispositivo";
            System.out.println(query);
            return stmt.executeQuery(query);
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }
        return null;
    }
}
