package repository;

import domain.Car;
import domain.Reservation;

import java.sql.*;

public class DataBaseReservation extends DataBase<Integer,Reservation>{
    public DataBaseReservation(){fetchDataFromDataBase();}
    @Override
    public void fetchDataFromDataBase() {
        try(Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Reservation");) {
            while (resultSet.next()) {
            Reservation rev = new Reservation(resultSet.getInt("distance"),resultSet.getInt("numberOfPeople"),resultSet.getInt("rezervID"),resultSet.getInt("userID"),resultSet.getString("dateOfOrder"),resultSet.getString("TimeOfOrder"),resultSet.getInt("carID"));
            Repo.put(Repo.size()+1,rev);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeDataFromDB(Integer id) {
        String sql = "DELETE FROM Reservation WHERE rezervID=?";
        try(Connection connection = DriverManager.getConnection(URL);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateDataInDataBase() {
        String sql = "UPDATE Reservation SET distance=?, numberOfPeople=?, rezervID=?, userID=?, dateOfOrder=?, TimeOfOrder=?,carID=?";
        try(Connection connection = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            for(Reservation rev : Repo.values())
            {
                preparedStatement.setInt(1,rev.getDistance());
                preparedStatement.setInt(2,rev.getNumberOfPeople());
                preparedStatement.setInt(3,rev.getId());
                preparedStatement.setInt(4,rev.getUserid());
                preparedStatement.setString(5,rev.getDate_of_order());
                preparedStatement.setString(6,rev.getTimeOfOrder());
                preparedStatement.setInt(7,rev.getCarID());
                preparedStatement.executeUpdate();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void putDataInDataBase(Reservation rev) {
        String sql = " INSERT INTO Reservation (distance,numberOfPeople,userID,dateOfOrder,TimeOfOrder,carID) VALUES (?,?,?,?,?,?)";
        try(Connection connection = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {


                preparedStatement.setInt(1,rev.getDistance());
                preparedStatement.setInt(2,rev.getNumberOfPeople());
                preparedStatement.setInt(3,rev.getUserid());
                preparedStatement.setString(4,rev.getDate_of_order());
                preparedStatement.setString(5,rev.getTimeOfOrder());
                preparedStatement.setInt(6,rev.getCarID());
                preparedStatement.executeUpdate();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
