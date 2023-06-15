package entities;

public class Category {
	private int id;
        private int order;
	private String name,
		description;
        private Boolean isActive;
	
	public Category() {}
	
	public Category(int id, String name, String description,int order, boolean isActive) {
		this.id = id;
		this.name = name;
		this.description = description;
                this.order=order;
                this.isActive = isActive;

	}

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Category(int id, String name, String description, Boolean isActive,int order) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
         this.order=order;
    }

    public int getOrder() {
		return order;
	}
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setOrder(int order) {
		this.order = order;
	}
        public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
