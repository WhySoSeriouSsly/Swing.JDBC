package Entities;

public class Country {
	   private String code;
	   public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	private String name;
	   private int population;
	   private String region;

	   public Country(String code, String name, int population, String region){
	       this.code = code;
	       this.name = name;
	       this.population = population;
	       this.region = region;
	      

}
	   
}
