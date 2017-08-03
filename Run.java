public class Run {
	public String distance;
	public String pace;
	public String time;
	public String day;

	public Run (String distance, String pace, String time, String day) {
		this.distance = distance;
		this.pace = pace;
		this.time = time;
		this.day = day;
	}

	public String getDistance() {
		return this.distance;
	}

	public String getPace() {
		return this.pace;
	}

	public String getTime(){
		return this.time;
	}

	public String getDay(){
		return this.day;
	}

	public String toString(){
		return "distance: " + this.distance + " miles\npace: " + this.pace + " min per mile\ntime: " + this.time + "\nday: " + this.day;
	}
}