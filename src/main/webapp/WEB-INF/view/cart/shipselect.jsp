<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dto.MarketDto" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b23fa9ea980f75e2aff0cab6c46496c0"></script>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css">

<%
ArrayList<MarketDto> marketlist = (ArrayList<MarketDto>)request.getAttribute("marketlist");
	MarketDto customerMarker = (MarketDto)request.getAttribute("customerMarker");
	String num = (String)request.getAttribute("num");
%>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section class="ship_sel">
		<div class="container">
		<div id="map" style="width:500px;height:400px;" class="mx-auto my-5"></div>
			<table class="table table-hover">
				<tr>
					<th class="col-2">상호명</th>
					<th class="col-8">주소</th>
					<th class="col-2">선택</th>
				</tr>
				<%
				if (marketlist != null && marketlist.size() != 0){
							for(int i = 0; i < marketlist.size(); i++){
								MarketDto dto = marketlist.get(i);
				%>
				<tr>
					<td><%=dto.getName()%></td>
					<td><a href="#" onclick="panTo<%=i%>()"><%=dto.getAddr()%></a></td>
					<td><input type="button" value="선택" onclick="shipsel<%=i%>()" class="btn btn-danger"></td>
					<input type="hidden" value="<%=dto.getId()%>" id="s_id<%=i%>">
				</tr>
				<script type="text/javascript">
					function shipsel<%=i%>() {
						var id = document.getElementById('s_id<%=i%>');
						opener.document.getElementById('ship<%=num%>').innerHTML = '<%=dto.getName()%>';
						opener.document.getElementById('food_sel_id<%=num%>').value = '<%=dto.getId()%>';
						window.close();
					}
					function panTo<%=i%>() {
					    // 이동할 위도 경도 위치를 생성합니다 
					    var moveLatLon = new kakao.maps.LatLng(<%=dto.getX()%>,<%=dto.getY()%>);
					    
					    // 지도 중심을 부드럽게 이동시킵니다
					    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
					    map.panTo(moveLatLon);            
					}        
				</script>
				<%
				}
						}
						else {
				%>
				<tr>
					<td></td>
					<td>고객님이 지정하신 배송지 근처 2km이내에 상점이 존재하지 않습니다.</td>
					<td></td>
				</tr>
				<%
				}
				%>
			</table>
		</div>
	</section>
</body>
<script>
	var container = document.getElementById('map');
	var options = {
		center : new kakao.maps.LatLng(<%=customerMarker.getX()%>,<%=customerMarker.getY()%>),
		level: 3
	};
	
	var map = new kakao.maps.Map(container, options);
	
	/* var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다 */

	var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 마커이미지의 주소입니다    
	    imageSize = new kakao.maps.Size(32, 35), // 마커이미지의 크기입니다
	    imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
	      
	// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
	var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
	    markerPosition = new kakao.maps.LatLng(<%=customerMarker.getX()%>,<%=customerMarker.getY()%>); // 마커가 표시될 위치입니다

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: markerPosition, 
	    image: markerImage // 마커이미지 설정 
	});

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);
	
	var positions = [
		<%if (marketlist != null) {
			for(int i = 0; i < marketlist.size(); i++) {
				MarketDto dto = marketlist.get(i);
				if(i == marketlist.size()) {%>
    	{
        	content: '<div style="text-align: center; width:150px;"><%=dto.getName()%></div>', 
        	latlng: new kakao.maps.LatLng(<%=dto.getX()%>,<%=dto.getY()%>)
	    }
    	<%
				}
				else {
		%>
	  	{
        	content: '<div style="text-align: center; width:150px;"><%=dto.getName()%></div>', 
        	latlng: new kakao.maps.LatLng(<%=dto.getX()%>,<%=dto.getY()%>)
	    },
		<%
				}
			}
		}
    	%>
	];
	for (var i = 0; i < positions.length; i ++) {
	    // 마커를 생성합니다
	    var marker = new kakao.maps.Marker({
	        map: map, // 마커를 표시할 지도
	        position: positions[i].latlng // 마커의 위치
	    });
	    // 마커에 표시할 인포윈도우를 생성합니다 
	    var infowindow = new kakao.maps.InfoWindow({
	        content: positions[i].content // 인포윈도우에 표시할 내용
	    });
	    infowindow.open(map, marker); 
	}
	
</script>
</html> 