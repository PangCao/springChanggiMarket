<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dto.Boardlist" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css">
<link rel="stylesheet" href="../resources/css/style.css?ver=1.3">
<script src="https://kit.fontawesome.com/42c64699fb.js" crossorigin="anonymous"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans+KR:wght@100;200;300;400;500;600&family=Noto+Sans+KR&display=swap" rel="stylesheet">
<meta charset="UTF-8">
<title>ChanggiFood-자주하는 질문</title>
</head>
<%
	String search_title = request.getParameter("search_title");
	ArrayList<Boardlist> al = (ArrayList<Boardlist>)request.getAttribute("faqlist");
	int cupage = Integer.valueOf((String)request.getAttribute("page"));
	int min = 0;
	int max = 5;
	if (cupage > 3) {
		min = cupage - 3;
		max = cupage + 2;
	}
	int totalpage = 0;
	if (request.getAttribute("totalpage") != null){
		totalpage = (Integer)request.getAttribute("totalpage");
	}
	if (max > totalpage/10 + 1) {
		max = totalpage/10 + 1;
	}
	if (totalpage % 10 == 0) {
		max -= 1;
	}
	if (totalpage == 0) {
		max = 1;
	}
%>

<body>
	<jsp:include page="../menu.jsp"/>
	<section class="faq">
        <div>
            <img src="../resources/images/main02.jpg" alt="">
        </div>
        <div class="container">
            <div class="row">
                <div class="col-3">
                    <div>
                        <img src="../resources/images/logo_green.png" alt="" style="width: 35%;">
                    </div>
                    <ul>
                        <a href="faq?page=1"><li>자주하는 질문 <span>&gt;</span></li></a>
                        <a href="one_qna?page=1"><li>1:1 문의<span>&gt;</span></li></a>
                    </ul>
                </div>
                <div class="col-9">
	                <div>
		                <h3>자주하는 질문</h3>
		                <p><i class="fa-solid fa-house"></i>&nbsp;HOME > 커뮤니티 > 고객센터 > 자주하는 질문</p>
		            </div>
		            <%
		            	for(int i = 0; i < al.size(); i++) {
		            		Boardlist bl = al.get(i);
		            %>
                    <div>
                        <label for="faq<%=i %>" class="col-12">
                            <div>
                                <p><i class="fa-solid fa-q"></i>&nbsp;<%=bl.getTitle() %></p>
                                <i class="fa-solid fa-angle-down"></i>
                            </div>
                        </label>
                        <input type="checkbox" id="faq<%=i %>">
                        <div class="col-12">
                            <p><i class="fa-solid fa-a"></i>&nbsp;<%=bl.getContent() %>.</p>
                        </div>
                    </div>
                    <%
		            	}
                    %>
                    <div>
                        <form action="faq" method="post" class="col-5">
                            <input type="text" placeholder="검색어를 입력해주세요." class="form-control" id="search_title" name="search_title">
                            <a onclick="submit()"><i class="fa-solid fa-magnifying-glass"></i></a>
                        </form>
                    </div>
                    <div class="col-12">
		                
		                <%
		     					if (cupage == 1){
		            	%>
		                <p><b>&lt;</b>&nbsp;&nbsp;
		                <%
		            			}
		            			else {
		           		%>
		   		                 <p><a href="faq?page=<%=cupage-1%>&search_title=<%=search_title%>"><b>&lt;</b></a>&nbsp;&nbsp;
		           		
		           		<%
		            			}
		                		for (int a = min; a < max; a++) {
		                			if (a == cupage-1) {
		                %>
		                 	<a href="faq?page=<%=a+1%>&search_title=<%=search_title%>" class="pagenum" style="color: red"><%=a+1%></a>&nbsp;&nbsp;
		               	<%
		                			}
		                			else {
		                %>
		                 	<a href="faq?page=<%=a+1%>&search_title=<%=search_title%>" class="pagenum"><%=a+1%></a>&nbsp;&nbsp;
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
		                <a href="faq?page=<%=cupage+1%>&search_title=<%=search_title%>" class="pagenum"><b>&gt;</b></a></p>
		                <%
		                		}
		                %>
		            </div>
                </div>
            </div>
            
        </div>
    </section>    
    <jsp:include page="../footer.jsp"/>
</body>
</html>