package Core.Utilities.Messages;

public class SqlCommand {

	
	public static String SearchCountryCode="SELECT * FROM country WHERE Code=?";
	public static String LikeCountryCode="SELECT * FROM country Code LIKE ?";
	public static String SearchCountryName="SELECT * FROM country WHERE Name=?";
	public static String SearchCountry="SELECT * FROM country";
	public static String CountryAdd="insert into country (Code,Name,Population,Region) values(?,?,?,?)";
	public static String CountryDelete="delete from country where code = ?";
	public static String CountryUpdate="update country set Name=?,Population=?,Region=? where Code = ?";
	public static String SelectCountryDeleteSql="delete from country where Code=?";
	
	public static String getLikeCodeSql(String key) {
		return "SELECT * FROM country WHERE Code LIKE '%" + key + "%'";
		
	}
	
}
