package repository;

import domain.Car;

import java.sql.*;

public class DataBaseCAR extends DataBase<Integer,Car>{

    public DataBaseCAR(){
        fetchDataFromDataBase();
    }

    @Override
    public void fetchDataFromDataBase() {
        try(Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Car ");) {
                while (resultSet.next()) {
                 Car car = new Car(resultSet.getInt("carID"),resultSet.getString("model"),type(resultSet.getString("fuelType")));
                 if(resultSet.getInt("activeStatus")==1)
                     car.set_status(true);
                 else
                     car.set_status(false);
                 Repo.put(car.getId(),car);
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }

    }

    @Override
    public void updateDataInDataBase() {
         try(Connection connection = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Car SET fuelType=?, activeStatus=?, model=? WHERE carID=?");){
             for(Car car : getAllItems()) {
                 preparedStatement.setInt(1, car.getId());
                 preparedStatement.setString(2,reversed_type(car.getFuel_type()));
                 if(car.get_status())
                     preparedStatement.setInt(3,1);
                 else
                     preparedStatement.setInt(3,0);
                 preparedStatement.setString(4, car.get_model());

                 preparedStatement.executeUpdate();
             }
         }catch (SQLException e){
             e.printStackTrace();
         }
    }

    @Override
    public void removeDataFromDB(Integer id) {
        String sql = "DELETE FROM Car WHERE carID=?";
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
    public void putDataInDataBase(Car car) {
        String sql = "INSERT INTO Car (fuelType,activeStatus,model) VALUES (?,?,?)";
        try(Connection connection = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {

                preparedStatement.setString(1,reversed_type(car.getFuel_type()));
                if(car.get_status())
                    preparedStatement.setInt(2,1);
                else
                    preparedStatement.setInt(2,0);
                preparedStatement.setString(3, car.get_model());

                preparedStatement.executeUpdate();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
