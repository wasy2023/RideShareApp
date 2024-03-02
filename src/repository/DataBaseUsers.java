package repository;

import domain.Reservation;
import domain.User;

import java.sql.*;
import java.util.List;

public class DataBaseUsers extends DataBase<Integer,User> {
    @Override
    public void fetchDataFromDataBase() {
        try(Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");) {
            while (resultSet.next()) {
               User user = new User(resultSet.getInt("userID"),resultSet.getString("username"),resultSet.getString("password"));
               Repo.put(user.getId(),user);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void putDataInDataBase(User user) {
        String sql = " INSERT INTO Users (userID,username,password) VALUES (?,?,?)";
        try(Connection connection = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
                preparedStatement.setInt(1,user.getId());
                preparedStatement.setString(2,user.getName());
                preparedStatement.setString(3,user.getPassword());
                preparedStatement.executeUpdate();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void removeDataFromDB(Integer id) {
        String sql = "DELETE FROM Users WHERE userID=?";
        try(Connection connection = DriverManager.getConnection(URL);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateDataInDataBase() {
        String sql = "UPDATE Users SET userID=?, username=?, password=?";
        try(Connection connection = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            for(User user : Repo.values())
            {
                preparedStatement.setInt(1,user.getId());
                preparedStatement.setString(2,user.getName());
                preparedStatement.setString(3,user.getPassword());
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
