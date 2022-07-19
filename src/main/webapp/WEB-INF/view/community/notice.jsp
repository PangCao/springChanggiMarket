<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dto.Boardlist"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css">
<link rel="stylesheet" href="../resources/css/style.css?ver=1.2">
<script src="https://kit.fontawesome.com/42c64699fb.js" crossorigin="anonymous"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans+KR:wght@100;200;300;400;500;600&family=Noto+Sans+KR&display=swap" rel="stylesheet">
<meta charset="UTF-8">
<%
	request.setCharacterEncoding("utf-8");
	String search_title = (String)request.getAttribute("search_title");
	String category = (String)request.getAttribute("category");
	if (category.equals("notice")) {
		category = "공지사항";
	}
	else if (category.equals("bulletin")) {
		category = "게시판";
	}
	ArrayList<Boardlist> al = (ArrayList<Boardlist>)request.getAttribute("notice");

	int cupage = Integer.valueOf((String)request.getAttribute("page"));
	
	int totalpage = 0;
	if (request.getAttribute("totalpage") != null){
		totalpage = (Integer)request.getAttribute("totalpage");
	}
	
%>


<title>ChanggiFood-Board</title>
</head>
<body>
	<jsp:include page="../menu.jsp"/>
	<section class="notice">
        <div>
            <img src="../resources/images/main01.jpg" alt="">
        </div>
        <div class="container">
            <div>
                <h3><%=category %></h3>
                <p><i class="fa-solid fa-house"></i>&nbsp;HOME > 커뮤니티 > <%=category %></p>
            </div>
            <table class="table text-center">
                <tr>
                    <th class="col-1">번호</th>
                    <th class="col-5">제목</th>
                    <th class="col-2">작성자</th>
                    <th class="col-2">작성일</th>
                    <th class="col-2">조회수</th>
                </tr>
                <%
                	for (int i = 0; i < al.size(); i++) {
                		Boardlist bl = al.get(i);
                %>
                	<tr>
                		<%
                			if (category.equals("공지사항")){
                		%>
                		<td><a href="notice_view?id=<%=bl.getId()%>&page=<%=cupage%>&category=<%=category%>"><%=bl.getId()%></a></td>
                		<td><a href="notice_view?id=<%=bl.getId()%>&page=<%=cupage%>&category=<%=category%>"><%=bl.getTitle() %></a></td>
                		<%
                			}
                			else {
                		%>
                		<td><a href="bulletin_view?id=<%=bl.getId()%>&page=<%=cupage%>&category=<%=category%>"><%=bl.getId()%></a></td>
                		<td><a href="bulletin_view?id=<%=bl.getId()%>&page=<%=cupage%>&category=<%=category%>"><%=bl.getTitle() %></a></td>
                		<%
                			}
                		%>
                		<td><%=bl.getWriter()%></td>
                		<td><%=bl.getDate()%></td>
                		<td><%=bl.getHit()%></td>
                	</tr>
                <%
                	}
                %>
            </table>
            <div>
                <a href="board_write?category=<%=category %>" class="btn btn-secondary col-2">글쓰기</a>
                <%
                	if (category.equals("공지사항")){
                %>
                <form action="notice" method="post" class="col-4" name="searchForm">
                    <input type="text" placeholder="검색어를 입력해주세요." class="form-control" name="search_title" id="search_title">
                    <a><i class="fa-solid fa-magnifying-glass"></i></a>
                </form>
                <%
                	}
                	else {
                %>
                <form action="bulletin" method="post" class="col-4" name="searchForm">
                    <input type="text" placeholder="검색어를 입력해주세요." class="form-control" name="search_title" id="search_title">
                    <a><i class="fa-solid fa-magnifying-glass"></i></a>
                </form>
                <%
                	}
                %>
            </div>
            <div class="col-12">
                <%
	                if (category.equals("공지사항")){
	            		if (cupage == 1){
            	%>
                <p><b>&lt;</b>&nbsp;&nbsp;
                <%
            			}
            			else {
           		%>
   		                 <p><a href="notice?page=<%=cupage-1%>&search_title=<%=search_title%>"><b>&lt;</b></a>&nbsp;&nbsp;
           		
           		<%
            			}
                		int pagenum = (totalpage/10)+1;
                		int minpage;
                		int maxpage;
                		if (cupage < 3) {
                			minpage = 0;
                			maxpage = 5;
                		}
                		else {
                			minpage = cupage-3;
                			maxpage = cupage+2;
                		}
                		if (maxpage > pagenum) {
                			maxpage = pagenum;
                		}
                		if (totalpage == 0) {
                			maxpage = 1;
                		}
                		
                		for (int a = minpage; a < maxpage; a++) {
                			if (a == cupage-1){
                %>
                	<a href="notice?page=<%=a+1%>&search_title=<%=search_title%>" class="pagenum" style="color: red;"><%=a+1%></a>&nbsp;&nbsp;
                <%
                			}
                			else{
                %>
                
                 	<a href="notice?page=<%=a+1%>&search_title=<%=search_title%>" class="pagenum"><%=a+1%></a>&nbsp;&nbsp;
               	<%			}
                		}
	                		if (maxpage == cupage) { 
               	%>
               		<b>&gt;</b></p>
               	<%
	                		}
	                		else {
               	%>
                <a href="notice?page=<%=cupage+1%>&search_title=<%=search_title%>" class="pagenum"><b>&gt;</b></a></p>
                <%
                			}
               			}
	                else {
     					if (cupage == 1){
            	%>
                <p><b>&lt;</b>&nbsp;&nbsp;
                <%
            			}
            			else {
           		%>
   		                 <p><a href="bulletin?page=<%=cupage-1%>&search_title=<%=search_title%>"><b>&lt;</b></a>&nbsp;&nbsp;
           		
           		<%
            			}
                		int pagenum = (totalpage/10)+1;
                		int minpage;
                		int maxpage;
                		if (cupage < 3) {
                			minpage = 0;
                			maxpage = 5;
                		}
                		else {
                			minpage = cupage-3;
                			maxpage = cupage+2;
                		}
                		if (maxpage > pagenum) {
                			maxpage = pagenum;
                		}
                		if (totalpage == 0) {
                			maxpage = 1;
                		}
                		for (int a = minpage; a < maxpage; a++) {
                			if(a == cupage-1) {
                %>
                 	<a href="bulletin?page=<%=a+1%>&search_title=<%=search_title%>" class="pagenum" style="color: red;"><%=a+1%></a>&nbsp;&nbsp;
               	<%
                			}
                			else {
                %>
                	<a href="bulletin?page=<%=a+1%>&search_title=<%=search_title%>" class="pagenum"><%=a+1%></a>&nbsp;&nbsp;
                <%
                			}		
                		}
	                	if (maxpage == cupage) { 
               	%>
               		<b>&gt;</b></p>
               	<%
	                	}
	                	else {
               	%>
                <a href="bulletin?page=<%=cupage+1%>&search_title=<%=search_title%>" class="pagenum"><b>&gt;</b></a></p>
                <%
               			}
                	}
                %>
           	</div>
        </div>
    </section>
    <jsp:include page="../footer.jsp"/>
</body>
</html>