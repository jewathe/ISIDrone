package entities;

public class Order extends Cart{
	private static final long serialVersionUID = 1L;
	
	int id;
	int userId;
	String date;
	boolean isShipped;

	public Order() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public boolean getIsShipped() {
            return isShipped;
        }

        public void setIsShipped(boolean isShipped) {
            this.isShipped = isShipped;
        }
        
         public void toogleIsShipped() {
            this.isShipped = !this.isShipped;
        }
        
       
}
