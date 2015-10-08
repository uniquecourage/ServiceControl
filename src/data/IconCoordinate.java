package data;

public class IconCoordinate {

	public int id;
	public float coordinate_x;
	public float coordinate_y;
	
	public IconCoordinate() {
		
	}
	
	public IconCoordinate(int id, float coordinate_x, float coordinate_y) {
		this.id = id;
		this.coordinate_x = coordinate_x;
		this.coordinate_y = coordinate_y;
	}
	
	public void set_id(int id) {
		this.id = id;
	}
	
	public int get_id() {
		return id;
	}
	
	public void set_coordinate_x(float coordinate_x) {
		this.coordinate_x = coordinate_x;
	}
	
	public float get_coordinate_x() {
		return coordinate_x;
	}
	
	public void set_coordinate_y(float coordinate_y) {
		this.coordinate_y = coordinate_y;
	}
	
	public float get_coordinate_y() {
		return coordinate_y;
	}
}
