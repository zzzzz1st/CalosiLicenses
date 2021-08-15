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
            Statement stmt = connection.createStatement();
            String query = "insert into Dispositivo values (" + "'" + hostid + "'" + "," + "'" + sn + "'" + ","
                                                                + "'" + model + "'" + "," + "'" + puntoV + "'" + ")";
            System.out.println(query);
            stmt.executeUpdate(query);
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }

    }

    public void insertLicenza(String hostid, String codeL, String nOrdine){
        try {
            Statement stmt = connection.createStatement();
            String query = "insert into Licenza values (" + "'" + hostid + "'" + "," + "'" + codeL + "'" + "," + "'" + nOrdine + "'" + ")";
            System.out.println(query);
            stmt.executeUpdate(query);
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }

    }

    public void deleteLicenza(String hostid, String codeL) {
        try {
            Statement stmt = connection.createStatement();
            String query = "delete from Licenza where hostID = " + "'" + hostid + "'" + " and CodL = " + "'" + codeL + "'";
            System.out.println(query);
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }
    }

        public void deleteDispositivo(String hostid){
            try {
                Statement stmt = connection.createStatement();
                String query = "delete from Dispositivo where HostID= " + "'" + hostid + "'";
                System.out.println(query);
                stmt.executeUpdate(query);
            } catch (SQLException ex) {
                ErrorsController errorsController = new ErrorsController();
                errorsController.display(ex.getMessage());
            }
        }

    public void updateDispositivo(String setwhat, String setthis, String setwhere){
        try {
            Statement stmt = connection.createStatement();
            String query = "update Dispositivo set " + setwhat + " =" + "'" + setthis + "' " + "where HostID = " + "'" + setwhere + "'";
            System.out.println(query);
            stmt.executeUpdate(query);
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }

    }

    public ResultSet selectByPV(String pv){
        try {
            Statement stmt = connection.createStatement();
            String query = "select Dispositivo.HostID, CodL, NOrdine from Dispositivo left join Licenza on Dispositivo.HostID = Licenza.HostID where Dispositivo.PuntoV = " + "'" + pv + "'";
            System.out.println(query);
            return stmt.executeQuery(query);
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }
        return null;
    }

    public ResultSet selectByHostID(String hostid){
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT SerialN, Model, PuntoV FROM Dispositivo where Dispositivo.HostID = " + "'" +hostid + "'";
            System.out.println(query);
            return stmt.executeQuery(query);
        }catch (SQLException ex){
            ErrorsController errorsController = new ErrorsController();
            errorsController.display(ex.getMessage());
        }
        return null;
    }

    public ResultSet selectByHostIDLicenses(String hostid){
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT CodL, NOrdine FROM Licenza where Licenza.HostID= " + "'" + hostid + "'";
            System.out.println(query);
            return stmt.executeQuery(query);
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
