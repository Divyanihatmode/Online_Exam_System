package com.thekiranacademy.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.thekiranacademy.entity.Question;


@Repository
public class QuestionDAO {

	@Autowired
	SessionFactory factory;
	
		public List<Question> getAllQuestions(String subject)
		{
			//return factory.openSession().createCriteria(Question.class).add(Restrictions.eq("subject", subject)).list();	
			// from Question where subject=:subject
			Session session=factory.openSession();
			
			Query query = session.createQuery("from Question where subject=:subject");
			
			query.setParameter("subject",subject);
			
			return query.list();
			}
}
