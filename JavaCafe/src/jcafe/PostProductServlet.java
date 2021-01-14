package jcafe;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PostProductServlet")
public class PostProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PostProductServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String itemNo = request.getParameter("itemNo");
		String item = request.getParameter("item");
		String category = request.getParameter("category");
		String price = request.getParameter("price");
		int pric = Integer.parseInt(price);
		String content = request.getParameter("content");
		String likeIt = request.getParameter("likeIt");
		int likeI = Integer.parseInt(likeIt);
		String image = request.getParameter("image");
		
		System.out.println(itemNo);
		System.out.println(item);
		System.out.println(category);
		System.out.println(price);
		System.out.println(content);
		System.out.println(likeIt);
		System.out.println(image);
		
		ProductVO pro = new ProductVO();
		pro.setItemNo(itemNo);
		pro.setItem(item);
		pro.setCategory(category);
		pro.setPrice(pric);
		pro.setContent(content);
		pro.setLikeIt(likeI);
		pro.setImage(image);
		
		ProductDAO dao = new ProductDAO();
		dao.insertProduct(pro);
		
		String script = "<script>location.href='jcafe/cafeList.html'</script>";
		response.getWriter().append(script);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
