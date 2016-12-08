package src.model;

public abstract class Person {

	private int id;
	private String name;

	public Person(int id, String name) {
		this(name);
		this.id = id;
	}

	public Person(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}