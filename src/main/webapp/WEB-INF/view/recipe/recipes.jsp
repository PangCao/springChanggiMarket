<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dto.RecipelistDto" %>
<%@ page import="dto.FoodpriceDto" %>
<!DOCTYPE html>
<html>
<head>

<%
String c_id = (String)session.getAttribute("userid");
	String s_id = (String)session.getAttribute("seller");
	if (c_id != null || s_id != null) {
%>
	<script type="text/javascript">
		function addrecipe() {
			location.href="addrecipe";
		}
	</script>
<%
}
	else {
%>
	<script type="text/javascript">
		function addrecipe() {
			alert("로그인을 하신 후에 레시피를 등록하실 수 있습니다.");
		}
	</script>	
<%
	}
			String search_title = request.getParameter("search_title");
			String order = request.getParameter("order");
			ArrayList<RecipelistDto> rl = (ArrayList<RecipelistDto>)request.getAttribute("food");
			ArrayList<FoodpriceDto> fp = (ArrayList<FoodpriceDto>)request.getAttribute("foodprice");
			
			String ct = request.getParameter("r_category");
			int cnt = 0;
			int cupage = 1;
			if (request.getAttribute("page") != null) {
		cupage = Integer.parseInt((String)request.getAttribute("page"));
			}
			if(request.getAttribute("cnt")!=null){
		cnt = (Integer)request.getAttribute("cnt");
			}
			
			String chk = request.getParameter("chk");
			if (chk != null){
		if (chk.equals("1")) {
	%>
	<script type="text/javascript">
		alert("상품을 장바구니에 추가하였습니다.");
	</script>	
<%
	}
		else if (chk.equals("2")){
	%>
	<script type="text/javascript">
		alert("판매자 아이디로는 상품을 구매하실 수 없습니다.");
	</script>
<%
}
		else if (chk.equals("0")){
%>
	<script type="text/javascript">
		alert("장바구니에 담기 전에 로그인을 해주세요");
		location.href="login/login";
	</script>	
<%
	}
			}
	%>

<link rel="stylesheet" href="../resources/css/bootstrap.min.css">
<link rel="stylesheet" href="../resources/css/style.css?ver=1.4">
<script src="https://kit.fontawesome.com/42c64699fb.js" crossorigin="anonymous"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans+KR:wght@100;200;300;400;500;600&family=Noto+Sans+KR&display=swap" rel="stylesheet">
<meta charset="UTF-8">

<title>ChanggiFood-Recipes</title>
</head>
<body>
	<jsp:include page="../menu.jsp"/>
	<section class="recipes">
        <div class="container">
            <div>
                <div class="col-8">
                    <input type="button" class="btn btn-secondary col-3" value="레시피 등록" onclick="addrecipe()">
                    <h4 class="col-6"><%=ct%></h4>
                </div>
                <p><i class="fa-solid fa-house"></i>&nbsp;HOME > 레시피 > <%=ct%></p>
            </div>
            <hr>
            <div>
                <p>
                    <a href="recipes?r_category=<%=ct%>&page=<%=cupage%>&order=new" id="new">신상품순</a> | <a href="recipes?r_category=<%=ct%>&page=<%=cupage%>&order=sell" id="sell">판매량순</a> | <a href="recipes?r_category=<%=ct%>&page=<%=cupage%>&order=rowprice" id="rowprice">낮은가격순</a> | <a href="recipes?r_category=<%=ct%>&page=<%=cupage%>&order=highprice" id="highprice">높은가격순</a>
                </p>
                <form action="recipes?r_category=<%=ct%>&page=<%=cupage%>" method="post" class="col-4">
                    <input type="text" placeholder="검색어를 입력해주세요." class="form-control" name="search_title">
                    <a><i class="fa-solid fa-magnifying-glass"></i></a>
                </form>
            </div>
            <div class="row">
           	<%
           	for (int i = 0; i <rl.size(); i++) {
           	           	           	           	           			int price = 0;
           	           	           	           	           			RecipelistDto rp = rl.get(i);           			
           	           	           	           	           			if(rp.getR_category().equals(ct)){
           	           	           	           	           				String[] foods = rp.getR_product().split(",");
           	           	           	           	           				String[] food_unit = rp.getR_unit().split(",");
           	           	           	           	           				for (int j = 0; j < foods.length; j++) {
           	           	           	           	           					for (int x = 0; x < fp.size(); x++) {
           	           	           	           	           						FoodpriceDto f_price = fp.get(x);           						
           	           	           	           	           						if (foods[j].equals(f_price.getF_name())){           							
           	           	           	           	           							price+= (f_price.getF_price()*Integer.parseInt(food_unit[j]));
           	           	           	           	           							break;
           	           	           	           	           						}
           	           	           	           	           					}
           	           	           	           	           				}
           	%>
           		<div class="col-3">
           			<div>
           				<a href="recipe?id=<%=rp.getR_id()%>"><img src="../resources/images/<%=rp.getR_img() %>" alt="" class="col-12"></a>
                        <a href="addCartIcon?id=<%=rp.getR_id()%>&r_category=<%=ct%>&page=<%=cupage%>&order=<%=order%>"><i class="fa-solid fa-cart-shopping"></i></a>           				
           			</div>
           			<a href="recipe?id=<%=rp.getR_id()%>">
	                    <h5><%=rp.getR_name()%></h5>
	                    <p>0% <small> <%=price %>원</small></p>
	                    <h5><%=price%>원</h5>
	                    <p><%=rp.getR_desc() %></p>
                    </a>
           		</div>
           	<%
           			}
           		}
           	%>
            </div>
            <div>
            	<%
            		int pagenum = (cnt/20)+1;
            		int minpage;
            		int maxpage;
            		if (cupage < 3) {
            			minpage = 0;
            			maxpage = 5;
            		}
            		else {
            			minpage = cupage - 3;
            			maxpage = cupage + 2;
            		}
            		if (maxpage > pagenum) {
            			maxpage = pagenum;
            		}
            		if (cnt % 20 == 0) {
            			maxpage -= 1;
            		}
            		if (cnt == 0) {
            			maxpage = 1;
            		}
            		if (maxpage < 5) {
            			minpage = 0;
            		}

            		if (cupage == 1){
            	%>
                <p><b>&lt;&nbsp;&nbsp;</b>
                <%
            		}
            		else {
           		%>
   		                 <a href="./recipes?r_category=<%=ct %>&page=<%=cupage-1%>&search_title=<%=search_title%>&order=<%=order%>"><b>&lt;</b></a></p>&nbsp;&nbsp;
           		
           		<%
            			}
                	for (int a = minpage; a < maxpage; a++) {
                		if (a == cupage - 1) {
                %>
                	<a href="./recipes?r_category=<%=ct %>&page=<%=a+1%>&search_title=<%=search_title%>&order=<%=order%>" style="color: red;"><%=a+1%></a>&nbsp;&nbsp;
                <%
                		}
                		else{
                %>
                 	<a href="./recipes?r_category=<%=ct %>&page=<%=a+1%>&search_title=<%=search_title%>&order=<%=order%>"><%=a+1%></a>&nbsp;&nbsp;
               	<%
                		}
                	}
                	if (pagenum == cupage) { 
               	%>
               		<b>&gt;</b></p>
               	<%
                	}
                	else {
               	%>
                <a href="./recipes?r_category=<%=ct %>&page=<%=cupage+1%>&search_title=<%=search_title%>&order=<%=order%>"><b>&gt;</b></a></p>
                <%
                	}
                %>
            </div>
        </div>
        <input type="checkbox" id="recipe_menu">
        <div class="col-2">
            <label for="recipe_menu"><i class="fa-solid fa-angles-left"></i><br><i class="fa-solid fa-angles-right"></i></label>
            <ul><h3>카테고리</h3>
                <hr>
                <%
                	if (ct.equals("밥·죽")){
                %>
                <a href="./recipes?r_category=밥·죽&page=1" class="cupage"><li>밥 · 죽</li></a>
                <%
                	}
                	else {
               	%>
                <a href="./recipes?r_category=밥·죽&page=1"><li>밥 · 죽</li></a>
                <%
                	}
                	if (ct.equals("국·탕·찌개·전골")){
                %>
                <a href="./recipes?r_category=국·탕·찌개·전골&page=1" class="cupage"><li>국 · 탕 · 찌개 · 전골</li></a>
                <%
                	}
                	else {
               	%>
                <a href="./recipes?r_category=국·탕·찌개·전골&page=1"><li>국 · 탕 · 찌개 · 전골</li></a>
                <%
                	}
                	if (ct.equals("면")){
                %>
                <a href="./recipes?r_category=면&page=1" class="cupage"><li>면</li></a>
                <%
                	}
                	else {
               	%>
                <a href="./recipes?r_category=면&page=1"><li>면</li></a>
                <%
                	}
                	if (ct.equals("찜·조림")){
                %>
                <a href="./recipes?r_category=찜·조림&page=1" class="cupage"><li>찜 · 조림</li></a>
                <%
                	}
                	else {
               	%>
                <a href="./recipes?r_category=찜·조림&page=1"><li>찜 · 조림</li></a>
                <%
                	}
                	if (ct.equals("구이")){
                %>
                <a href="./recipes?r_category=구이&page=1" class="cupage"><li>구이</li></a>
                <%
                	}
                	else {
               	%>
                <a href="./recipes?r_category=구이&page=1"><li>구이</li></a>
                <%
                	}
                	if (ct.equals("볶음")){
                %>
                <a href="./recipes?r_category=볶음&page=1" class="cupage"><li>볶음</li></a>
                <%
                	}
                	else {
               	%>
                <a href="./recipes?r_category=볶음&page=1"><li>볶음</li></a>
                <%
                	}
                	if (ct.equals("튀김")){
                %>
                <a href="./recipes?r_category=튀김&page=1" class="cupage"><li>튀김</li></a>
                <%
                	}
                	else {
               	%>
                <a href="./recipes?r_category=튀김&page=1"><li>튀김</li></a>
                <%
                	}
                	if (ct.equals("나물")){
                %>
                <a href="./recipes?r_category=나물&page=1" class="cupage"><li>나물</li></a>
                <%
                	}
                	else {
               	%>
                <a href="./recipes?r_category=나물&page=1"><li>나물</li></a>
                <%
                	}
                	if (ct.equals("기타")){
                %>
                <a href="./recipes?r_category=기타&page=1" class="cupage"><li>기타</li></a>
                <%
                	}
                	else {
               	%>
                <a href="./recipes?r_category=기타&page=1"><li>기타</li></a>
                <%
                	}
                %>
            </ul>
        </div>
    </section>
	<jsp:include page="../footer.jsp"/>
</body>
<%
	if (order != null && !order.equals("")) {
%>
<script type="text/javascript">
	document.getElementById("<%=order%>").style.color = "red";
</script>
<%
	}
%>
</html>