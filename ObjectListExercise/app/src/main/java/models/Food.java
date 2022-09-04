package models;

public class Food {
	private String name;
	private double rating;
	private int eta;

	public Food() {
	}

	public Food(String name, double rating, int eta) {
		this.name = name;
		this.rating = rating;
		this.eta = eta;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		this.eta = eta;
	}
}
