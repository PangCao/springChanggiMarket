<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="dto.foodmanage" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css">
<link rel="stylesheet" href="../resources/css/style.css?ver=1.7">
<script src="https://kit.fontawesome.com/42c64699fb.js" crossorigin="anonymous"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans+KR:wght@100;200;300;400;500;600&family=Noto+Sans+KR&display=swap" rel="stylesheet">
<meta charset="utf-8">
<script type="text/javascript">
	function food_search(){
		document.searchfoodForm.submit();
	}
	
	
	function food_code_search() {
		var popupwidth = 800;
		var popupheight = 500;
		var popx = (window.screen.width / 2) - (popupwidth / 2);
		var popy = (window.screen.height / 2) - (popupheight / 2);
		window.open("foodsearch?search="+document.getElementById('foodserach').value ,"selectPop","status=no, width="+popupwidth+", height="+popupheight+",left="+popx+",top="+popy);
	}
	
	
	document.addEventListener('keydown', function(event) {
		if (event.keyCode === 13) {
			event.preventDefault();
		};
	}, true);
</script>
<title>ChanggiFood-상품관리</title>
<%
	String addchk = request.getParameter("addchk");
	ArrayList<foodmanage> alf = (ArrayList<foodmanage>)request.getAttribute("foodmanage");
	int cupage = 1;
	if (request.getAttribute("page") != null) { 
		cupage = Integer.valueOf((String)request.getAttribute("page"));
	}
	int min = 0;	
	int max = 5;
	int totalpage = 0;
	if (request.getAttribute("totalpage") != null){
		totalpage = (Integer)request.getAttribute("totalpage");
	}
	
	if (cupage > 3) {
		min = cupage - 3;
		max = cupage + 2;
	}
	if (max > (totalpage/10+1)) {
		max = totalpage/10+1;
	}
	if (totalpage % 10 == 0) {
		max -= 1;
	}
	
	if (addchk != null && addchk.equals("1")){
%>
	<script type="text/javascript">
		alert("이미 존재하는 상품입니다.");
	</script>
<%
	}
%>
<script type="text/javascript">
	function addfoodbtn(){
		var foodcate = document.getElementById('foodcate');
		var name = document.getElementById('name');
		var price = document.getElementById('price');
		var unit = document.getElementById('unit');
		
		if(foodcate.length == 0 || foodcate.value=="") {
			alert("상품코드를 검색해주세요");
			return false;
		}
		if(name.length == 0 || name.value == "") {
			alert("상품명을 입력해주세요.");
			return false;
		}
		if (price.length == 0 || price.value == ""){
			alert("가격을 입력해주세요.");
			return false;
		}
		else if (isNaN(price.value)) {
			alert("가격은 숫자만 입력해주세요");
			return false;
		}
		if (unit.length == 0 || unit.value == ""){
			alert("수량을 입력해주세요.");
			return false;
		}
		else if (isNaN(unit.value)) {
			alert("수량은 숫자만 입력해주세요.");
			return false;
		}
		
		document.addfoodForm.submit();
		
	}
</script>
</head>
<body>
	<jsp:include page="../menu.jsp"/>
	<section class="a_product">
                <div class="container">
            <div class="row">
                <h3>상품 관리</h3>
                <p><i class="fa-solid fa-house"></i>&nbsp;HOME > 상품관리</p>
            </div>
            <div>
                <form action="productmanage" method="post" class="col-4" name="searchfoodForm">
                    <input type="text" placeholder="찾으실 상품을 입력해주세요" class="form-control" name="search_food" id="search_food">
                    <a onclick="food_search()"><i class="fa-solid fa-magnifying-glass"></i></a>
                </form>
            </div>
            <form action="addfood?page=<%=cupage %>" method="post" name="addfoodForm">
                <table class="table text-center">
                    <tr>
                        <th class="col-1 align-bottom">번호</th>
                        <th class="col-3 foodsearch">
                            <div class="row">
                                <input type="text" placeholder="상품코드 검색" class="form-control col-12 my-2" id="foodserach" name="foodserach">
                                <a onclick="food_code_search()"><i class="fa-solid fa-magnifying-glass"></i></a>
                                <input type="text" placeholder="상품코드" readonly class="form-control col-12" name="foodcate" id="foodcate">
                            </div>
                        </th>
                        <th class="col-3 align-bottom">
                            <div class="row">
                                <input type="text" name="name" id="name" class="col-10 ml-3 form-control" placeholder="상품명 입력">
                            </div>
                        </th>
                        <th class="col-2 align-bottom">
                            <div class="row">
                                <input type="text" name="price" id="price" class="col-10 ml-1 form-control" placeholder="가격 입력">
                            </div>
                        </th>
                        <th class="col-2 align-bottom">
                            <div class="row">
                                <input type="text" name="unit" id="unit" class="col-10 form-control" placeholder="수량 입력">
                            </div>
                        </th>
                        <th class="col-1 align-bottom" colspan="2">
                            <input type="button" name="addfood" id="addfood" value="추가" class="btn btn-success col-12" onclick="addfoodbtn()">
                        </th>
                    </tr>
                    <%
                    	for (int i = 0; i < alf.size(); i++) {
                    		foodmanage fm = alf.get(i);
                    %>
	                    <tr>
	                        <td style="vertical-align: middle;"><p class="m-0 text-center"><%=((cupage-1)*10)+i+1%></p></td>
	                        <td style="vertical-align: middle;"><p class="m-0 text-center"><%=fm.getF_code() %></p></td>
	                        <td style="vertical-align: middle;"><p class="m-0 text-center"><%=fm.getF_name() %></p></td>
	                        <td><div class="row"><input type="number" class="form-control col-8" value="<%=fm.getF_price() %>" min="0" name="foodprice<%=i%>"><p class="col-2 m-0 d-flex align-items-center">원</p></div> </td>
	                        <td><div class="row"><input type="number" class="form-control col-8" value="<%=fm.getF_unit() %>" min="0" name="foodunit<%=i%>"><p class="col-2 m-0 d-flex align-items-center">pcs</p></div></td>
	                        <td>
	                            <input type="button" value="삭제" class="btn btn-danger" onclick="fooddelete<%=i%>()">
	                            <input type="hidden" name="fooddel<%=i %>" id="fooddel<%=i %>" value="<%=fm.getF_id()%>">
	                        </td>
	                        <td>
   	                            <input type="button" value="수정" class="btn btn-success" onclick="foodmodify<%=i%>()">
	                        </td>
	                    </tr>
	                    <script type="text/javascript">
							function fooddelete<%=i%>() {
								document.addfoodForm.action = "delfood?num=<%=i%>";
								document.addfoodForm.submit();
							}
							function foodmodify<%=i%>() {
								document.addfoodForm.action = "modifood?num=<%=i%>";
								document.addfoodForm.submit();
							}
						</script>
                    <%
                    	}
                    %>
                </table>
            </form>
            <div class="col-12">
                <%
	            		if (cupage == 1){
            	%>
                <p><b>&lt;</b>&nbsp;&nbsp;
                <%
            			}
            			else {
           		%>
   		                 <p><a href="productmanage?page=<%=cupage-1%>"><b>&lt;</b></a>&nbsp;&nbsp;
           		
           		<%
            			}
                		for (int a = min; a < max; a++) {
                			if (a == cupage - 1) {
                %>
                 	<a href="productmanage?page=<%=a+1%>" class="pagenum" style="color:red;"><%=a+1%></a>&nbsp;&nbsp;
               	<%
                			}
                			else {
   				%>
   				 	<a href="productmanage?page=<%=a+1%>" class="pagenum"><%=a+1%></a>&nbsp;&nbsp;
                <%
                			}
	                	}
	                	if (cupage == max) { 
               	%>
               		<b>&gt;</b></p>
               	<%
	                	}
	                	else {
               	%>
                <a href="productmanage?page=<%=cupage+1%>" class="pagenum"><b>&gt;</b></a></p>
                <%
                		}
				%>
           	</div>
        </div>
    </section>
    <jsp:include page="../footer.jsp"/>
</body>
</html>