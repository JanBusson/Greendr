
public class CurrentQuizModel {
	public int index;
	public double score;
	
	public CurrentQuizModel() {
		this.index = 0;
		this.score = 0.0;
	}

	
	public  int getIndex() {
		return index;
		
	}
	
	public void incrementIndex() {
		index++;
	}
	
	public double getScore() {
		return score;
	}
	
	public void IncrementScore(double points) {
		 score+= points;
	}
}
