package domain;

public class User {

	private int id;
	private String username;
	private String password;
	private Boolean blocked;
	private Permission permission;
	
	public enum Permission{
		NOT_AUTHENTICATED(0),AUTHENTICATED(1),MODERATOR(2),ADMINISTRATOR(3);
		
		private int permission;
		
		Permission(int permission){
			this.permission = permission;
		}
		
		public int getPermission(){
			return this.permission;
		}
		
		public static Permission createPermission(int i){
			switch (i) {
				case 0:
					return Permission.NOT_AUTHENTICATED;
			
				case 1:
					return Permission.AUTHENTICATED;
					
				case 2:
					return Permission.MODERATOR;
					
				case 3:
					return Permission.ADMINISTRATOR;
					
				default:
				return Permission.NOT_AUTHENTICATED;
			}
		}
	}
	
	
	
	public User(int id, String username, String password, Boolean blocked, Permission permission) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.blocked = blocked;
		this.permission = permission;
	}


	public User(String username, String password, Permission permission) {
		super();
		this.username = username;
		this.password = password;
		this.permission = permission;
	}
	
	public User(int id, Permission permission) {
		super();
		this.id = id;
		this.permission = permission;
	}
	
	
	public User(int id, Boolean blocked) {
		super();
		this.id = id;
		this.blocked = blocked;
	}
	
	
	public User(Permission permission) {
		super();
		this.permission = permission;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	

}
