package web.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import services.QuestionnaireCreationService;

@WebFilter
public class EJBCreationFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		
		QuestionnaireCreationService qcs = (QuestionnaireCreationService) session.getAttribute("QuestionnaireCreationService");
		
		if(qcs!=null) {
			qcs.remove();
			System.out.print("REMOVING EJB");
		}
		session.removeAttribute("QuestionnaireCreationService");
		chain.doFilter(request,response);
	}
}
