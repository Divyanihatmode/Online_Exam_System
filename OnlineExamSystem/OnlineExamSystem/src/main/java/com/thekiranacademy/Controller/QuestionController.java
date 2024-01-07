package com.thekiranacademy.Controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thekiranacademy.dao.QuestionDAO;
import com.thekiranacademy.entity.Answer;
import com.thekiranacademy.entity.Question;

@RestController
@CrossOrigin("http://localhost:4200")
public class QuestionController {
	@Autowired
	QuestionDAO dao;
	
	@RequestMapping("getFirstQuestion/{subject}")
	public ResponseEntity<Question> getFirstQuestion(@PathVariable String subject)
	{

		HttpSession httpsession=LoginController.httpSession;
		
		System.out.println(httpsession.getId());

		System.out.println("Subject from Angular is " + subject);

		List<Question> list =dao.getAllQuestions(subject);
				
		Question question=list.get(0);
		
		ResponseEntity<Question> responseEntity=new ResponseEntity(question,HttpStatus.OK);
		
		httpsession.setAttribute("list", list);
		
		return responseEntity;
		
	}

	// 0 1 2
		// 2=list.size()-1

		@RequestMapping("nextQuestion")
		public Question nextQuestion()
		{
		
			HttpSession httpsession=LoginController.httpSession;
			
			// Object getAttribute()
			
			List<Question> list=(List)httpsession.getAttribute("list");
			
			// index=index+1
			
			if((int)httpsession.getAttribute("questionIndex")<list.size()-1) 
			{
				httpsession.setAttribute("questionIndex",(Integer)httpsession.getAttribute("questionIndex")+1);
			
				return list.get((int)httpsession.getAttribute("questionIndex"));
			}
			else
			{
				return list.get(list.size()-1);
			}
		}
		
		@RequestMapping("previousQuestion")
		public Question previousQuestion()
		{
		
			HttpSession httpsession=LoginController.httpSession;
			
			// Object getAttribute()
			
			List<Question> list=(List)httpsession.getAttribute("list");
			
			// index=index+1
			
			if((int)httpsession.getAttribute("questionIndex")>0) 
			{
				httpsession.setAttribute("questionIndex",(Integer)httpsession.getAttribute("questionIndex")-1);
			
				return list.get((int)httpsession.getAttribute("questionIndex"));
			}
			else
			{
				return list.get(list.size()-1);
			}
		}
		
		@PostMapping("saveAnswer")
		public void saveAnswer(@RequestBody Answer answer)
		{
		  System.out.println(answer);
		  
		  HttpSession httpsession = LoginController.httpSession;
		  
		  //add/update user response in HashMap
		  
		  if(answer.getSubmittedAnswer()!=null)
		  {
			  HashMap<Integer,Answer> hashMap = (HashMap<Integer, Answer>)httpsession.getAttribute("submittedDetails");
			  hashMap.put(answer.getQno(), answer);
			  System.out.println(hashMap);
		  }
		  
		}
		
		@RequestMapping("endexam")
		public ResponseEntity<Integer> endexam()
		{
			HttpSession httpsession = LoginController.httpSession;
			
			HashMap<Integer,Answer> hashMap=(HashMap<Integer,Answer>) httpsession.getAttribute("submittedDetails");
//Collection values()
			Collection<Answer> collection=hashMap.values();	
		     
			System.out.println("values() gives object of class whose name is " + collection.getClass().getName());
			
			//Collection collection = new ArrayList();
			//reference of interface can refer to object of implementation class
			
			for (Answer ans:collection)
			{
				if(ans.getSubmittedAnswer().equals(ans.getOriginalAnswer()))
				{
					httpsession.setAttribute("score",(int)httpsession.getAttribute("score")+1);//2
					
					//httpsession.setAttribute("score",10);
				}
			}
			
			Integer score = (Integer)httpsession.getAttribute("score");
			
			System.out.println("Total Score at Server " + score);
			
			ResponseEntity<Integer> responseEntity = new ResponseEntity<Integer>(score,HttpStatus.OK);
			
			return responseEntity;
		}
	
		@RequestMapping("allAnswers")
		public ResponseEntity<Collection<Answer>> getAllAnswers()
		{

			HttpSession httpsession=LoginController.httpSession;

			HashMap<Integer,Answer> hashMap=(HashMap<Integer, Answer>) httpsession.getAttribute("submittedDetails");
			
			Collection<Answer> collection=hashMap.values();
			
			ResponseEntity<Collection<Answer>> responseEntity=new ResponseEntity<>(collection,HttpStatus.OK);

			return responseEntity;
				
			
		}
		
		@RequestMapping("getHashMap")
		public HashMap getHashMap()
		{
			HttpSession httpsession =LoginController.httpSession;
			
			HashMap<Integer,Answer> hashMap=(HashMap<Integer,Answer>) httpsession.getAttribute("submittedDetails");
			
			return hashMap;
		}
}

