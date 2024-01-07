package com.thekiranacademy.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.thekiranacademy.entity.User;

@Repository
public class LoginDAO {

	@Autowired
	SessionFactory factory;
	
	public Boolean validate(User user)
	{
		Session session = factory.openSession();
		
		User userfromDatabase = session.get(User.class, user.username);
		
		if(userfromDatabase == null)
			return null;
		
		return userfromDatabase.password.equals(user.password);
		}
}
