//Jason Hanna
//300266085
public class HighScore {
	private int time;
	private String name;
	private int lvl;
	
	//High Score object to keep name, time and lvl
	HighScore(String name, int time, int lvl) {
		
		this.time = time;
		this.name = name;
		this.lvl = lvl;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		
		switch(lvl) {
			case 0:
				return "Beginner: " + time + " seconds " + name;
			case 1:
				return "Intermediate: " + time + " seconds " + name;
			case 2:
				return "Expert: " + time + " seconds " + name;
			default:
				return "";
		}
	}
}
