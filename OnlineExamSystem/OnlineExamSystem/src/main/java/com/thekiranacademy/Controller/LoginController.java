package com.thekiranacademy.Controller;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thekiranacademy.entity.Answer;
import com.thekiranacademy.entity.User;



@RestController
@CrossOrigin("http://localhost:4200")
public class LoginController {
	@Autowired
	SessionFactory factory;
	
	static HttpSession httpSession;
			
		// {"username":"bhushan","password":"123"}
		
	@PostMapping("validate")

		boolean validate(@RequestBody User userfromAngular,HttpServletRequest request)
		{		
			httpSession=request.getSession();
				
			Session session=factory.openSession();
									
			User userfromDatabase=session.get(User.class,userfromAngular.username);
			
			if(userfromDatabase!=null)
			{
					boolean answer=userfromDatabase.password.equals(userfromAngular.password);// false if password are not matching	
					if(answer)
					{
						httpSession.setAttribute("questionIndex",0);
						
						httpSession.setAttribute("score",0);
						
						HashMap<Integer,Answer> hashmap = new HashMap<Integer, Answer>();
						httpSession.setAttribute("submittedDetails",hashmap);
					}			
					
				return answer;		
			}
			else
				return false;// username is wrong
		}
	
	@PostMapping("saveUser")
	public boolean saveUser(@RequestBody User userFromAngular) 
	{
		Session session=factory.openSession();
					
		Transaction tx=session.beginTransaction();
		
					session.save(userFromAngular);
		tx.commit();
		
		return true;
	}
	
	@RequestMapping("checkUsername")
	
	public boolean checkUsername(@RequestBody String username)
	{
		Session session = factory.openSession();
		
		User userfromDatabase = session.get(User.class,username);
		
		if(userfromDatabase!=null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

