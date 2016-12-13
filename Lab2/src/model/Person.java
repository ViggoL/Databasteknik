package src.model;

public abstract class Person {

	private String id;
	protected String name;

	public Person(String id, String name) {
		this(name);
		this.id = id;
	}

	public Person(String name) {
		this.name = name;
		this.id = "-1";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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