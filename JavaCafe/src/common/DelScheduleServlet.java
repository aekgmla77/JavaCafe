package common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteSchedule")
public class DelScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DelScheduleServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String title = request.getParameter("title");
		title = title == null ? "0" : title;
		
		Schedule vo = new Schedule();
		vo.setTitle(title);
		
		EmpDAO dao = new EmpDAO();
		if(dao.deleteSchedule(vo)) {
			response.getWriter().append("<h1>OK</h1>");
		}else {
			response.getWriter().append("<h1>NG</h1>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
