package common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getEmpListS")
public class GetEmpListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetEmpListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		EmpDAO dao = new EmpDAO();
		List<EmployeeVO> list = dao.getEmpList();
		String json = "[";
		int cnt = 1;
		for(EmployeeVO em : list) {
			json += "{";
			json += "\"fisrtName\":\"" + em.getFirstName() + "\"";
			json += ",\"lastName\":\"" + em.getLastName() + "\"";
			json += ",\"email\":\"" + em.getEmail() + "\"";
			json += ",\"hireDate\":\"" + em.getHireDate() + "\"";
			json += ",\"jobId\":\"" + em.jobId + "\"";
			json += ",\"salary\":"+ em.getSalary();
			json += "}";
			if(list.size() != cnt++) {
				json += ",";
			}
		}
		json += "]";
		
		response.getWriter().append(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
