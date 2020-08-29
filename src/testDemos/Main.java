package testDemos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import DataAccess.DbHelper;
import Entities.Country;

public class Main {

	public static void main(String[] args) throws SQLException {
		  Connection connection = null;
	        DbHelper helper = new DbHelper();
	        Statement statement = null;
	        ResultSet resultSet;
	        try {
	            connection = helper.getConnection();
	            statement = connection.createStatement();
	            resultSet = statement.executeQuery("select Code,Name,Population,Region from country ");
	            ArrayList<Country> countries = new ArrayList<Country>();
	            while (resultSet.next()) {
	                countries.add(new Country(resultSet.getString("Code"),
	                        resultSet.getString("Name"),
	                        resultSet.getInt("Population"),
	                        resultSet.getString("Region")));
	            }
	            System.out.println(countries.size());
	            
	            for (Country country : countries) {
	                System.out.println(country.getName()+country.getPopulation());
	              }

	        } catch (SQLException exception) {
	            helper.showErrorMessage(exception);
	        } finally {
	            connection.close();
	        }
	}

}
