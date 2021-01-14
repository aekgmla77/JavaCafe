<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<%
	String itemNo = request.getParameter("itemNo");
%>
<script>
	$(function() {
		$.ajax({
			url: '../GetProductServlet',
			data: {item_no: "<%=itemNo%>"},
			dataType: 'json',
			success: function(result) {
				console.log(result);
				$('#item_no').val(result.item_no);
				$('#item').val(result.item);
				$('#content').val(result.content);
				$('#price').val(result.price);
				$('#link').val(result.link);
				$('#image').val(result.image);
				$('#like_it').val(result.like_it);
			},
			error: function(reject) {
				console.log(reject);
			}
		});
		
		$('#btnchange').on('click', function(){
			
		})
	});
</script>
</head>
<body>
    <form>
      <input type="text" id="item_no" name="item_no"> 
      <input type="text" id="item" name="item"> 
      <input type="text" id="content" name="content"> 
      <input type="text" id="price" name="price"> 
      <input type="text" id="link" name="link"> 
      <input type="text" id="image" name="image">
      <input type="text" id="like_it" name="like_it">
      <input type='submit' id='btnchange' value="수정">
    </form>
</body>
</html>