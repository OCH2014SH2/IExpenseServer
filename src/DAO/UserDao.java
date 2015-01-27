package DAO;

import model.User;


public interface UserDao {
	
	public User queryUser(String empId);
	
	public int updateUser(User user);
	
}
