package web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

import database.Product;
import database.Question;
import database.Questionnaire;
import services.QuestionnaireOfTheDayService;

//@Contributor(s): Etion

@WebServlet("/inspection")
public class GetInspection extends HttpThymeleafServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB(name = "QuestionnaireOfTheDayService")
	private QuestionnaireOfTheDayService QDS;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String questionnaireId = req.getParameter("idQuestionnaire");
		
		String publicationDate = req.getParameter("publicationDate");
		

		
		//if it is his first visit and the user has not input any number yet write answer and 
		if( questionnaireId == null && publicationDate == null) {
			wrongFormat(req, resp);
			return;
		};

		// selector is "1" if you are choosing id and "2" if you are choosing "publicationDate"
		int selector = Integer.parseInt(req.getParameter("selector"));
		
		if(selector == 1) {
			getQuestionnaireById(questionnaireId, req, resp);
		} else if (selector == 2) {
			getQuestionnaireByDate(publicationDate, req, resp);
		}
		
		
		
	}
	
//	@SuppressWarnings("deprecation")
	private void getQuestionnaireByDate(String publicationDate, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Questionnaire questionnaire = null;
		List<Question> questions = null;

		try {
			questionnaire =QDS.getQuestionnaireByDate(publicationDate);
			questions = QDS.getQuestions(questionnaire.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//guard check if query is right
		if(questionnaire == null || questions == null) {
			wrongFormat(req, resp);
			return;
		}
		
		List<String> questionsString = new ArrayList<String>();
		for(Question q : questions) {
			questionsString.add(q.getQuestion());
		}
		
		String creatorName = questionnaire.getCreator().getNickname();
		Product product = questionnaire.getProduct();
		Date creationDate = questionnaire.getDate();
		Date presentationDate = questionnaire.getPresDate();

		String path = "QuestionnaireInspection";
		ServletContext servletContext = getServletContext();
		
		final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
		ctx.setVariable("questionnaire", questionnaire.getId());
		ctx.setVariable("questionnaireName", questionnaire.getName());
		ctx.setVariable("questionsString", questionsString);
		ctx.setVariable("creatorName", creatorName);
		ctx.setVariable("product", product.getImage());
		ctx.setVariable("creationDate", creationDate);
		ctx.setVariable("presentationDate", presentationDate);
		
		thymeleaf.process(path, ctx, resp.getWriter());	
		
	}

	/**
	 * Creates a thymeleaf object that dynamically writes on the page if the questionnaire is found
	 * @param s questionnaire id
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void getQuestionnaireById(String s, HttpServletRequest req, HttpServletResponse resp ) throws IOException {
		Integer idQuestionnaire = Integer.parseInt(s);
		Questionnaire questionnaire = null;
		List<Question> questions = null;
		
		try {
			questionnaire =QDS.getQuestionnaire(idQuestionnaire);
			questions = QDS.getQuestions(idQuestionnaire);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//guard check if query is right
		if(questionnaire == null || questions == null) {
			wrongFormat(req, resp);
			return;
		}
		
		List<String> questionsString = new ArrayList<String>();
		for(Question q : questions) {
			questionsString.add(q.getQuestion());
		}
		
		String creatorName = questionnaire.getCreator().getNickname();
		Product product = questionnaire.getProduct();
		Date creationDate = questionnaire.getDate();
		Date presentationDate = questionnaire.getPresDate();

		String path = "QuestionnaireInspection";
		ServletContext servletContext = getServletContext();
		
		final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
		ctx.setVariable("questionnaire", questionnaire.getId());
		ctx.setVariable("questionnaireName", questionnaire.getName());
		ctx.setVariable("questionsString", questionsString);
		ctx.setVariable("creatorName", creatorName);
		ctx.setVariable("product", product.getImage());
		ctx.setVariable("creationDate", creationDate);
		ctx.setVariable("presentationDate", presentationDate);
		
		thymeleaf.process(path, ctx, resp.getWriter());	
	}
	
	/**
	 * checks if string s is null, aka no input has been put in
	 * @param s
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	private boolean wrongFormat( HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String path = "QuestionnaireInspection";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
		thymeleaf.process(path, ctx, resp.getWriter());	
		return true;
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
