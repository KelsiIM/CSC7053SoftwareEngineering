package project;
 
public class Area {
 
    private String name;
   
    
    public Area() {
    	
    }
 
	/**
	 * @param name
	 */
	public Area(String name) {
		this.name = name;
	}
 
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
 
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public Environment getEnvironment() {
        return Environment.DEFAULT;
    }
 
}