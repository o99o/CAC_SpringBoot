var bufferList= new Array();
var errorPlateBufferList= new Array();
var liCount=0;
var tempSaveData=0;
var errorTempSaveData=0;
var liCountConstant=60;
var layer;
layui.use(["form","table"], function(){
	layer = layui.layer;
	//loadVehiclePassRecord();
	setInterval("loadVehiclePassRecord()",1000);
	setInterval("loadErrorVehiclePlate()",3000);
	
});
function detailPassRecord(obj){
	window.sessionStorage.setItem('cphm',obj.id);
	layer.open({
		  type: 2,
		  title:["实时过车轨迹查询","padding-left:40px;background: url("+appRootPath+"/images/index/p.png) no-repeat;background-position:10px 10px;font-size: 18px;font-weight: bold;color: #1C1C1C;color: #01EEFE;background-color:#000c17;border-bottom:1px solid 01EEFE"],
		  area: ['1360px', '700px'], //宽高
		  content:[appRootPath+'/trajectory/trajectoryPage', 'no']
		});
}
/*加载左侧过车记录*/
function loadVehiclePassRecord(){
	 $.ajax({
	        type:'POST',
	        dataType: "json",
	        url: appRootPath+"/indexPage/vehiclePassRecord",
	        success:function(list){
	        	loadAllVehiclePassRecord(list);
	        	loadSingleVehiclePassRecord(list);
	        }
	    })
}
function loadAllVehiclePassRecord(list){
	if(list==null||list.length == 0){
		return;
	}
	//下面操作是为了从缓存中取到重复的数据不在界面上显示
	if(bufferList.length==0){
		bufferList = list;
	}else{
		for(var i=0;i<bufferList.length;i++){
			for(var j=0;j<list.length;j++){
				if(bufferList[i].cphm==list[j].cphm&&bufferList[i].dmmc==list[j].dmmc){
					list.splice(j,1); 
				}
			}
		}
		//数组合并
		bufferList=bufferList.concat(list);
		if(bufferList.length>10){
			bufferList.splice(0,bufferList.length-10);
		}
	}
	var str = "";
	for (var i = list.length - 1; i >= 0; i--) {
		str += "<li  class='content_vehicleNum' id='"+liCount+"'><span ondblclick=detailPassRecord(this) id="+list[i].cphm+">" + list[i].cphm + "</span></li>";
		liCount++;
		str += "<li  class='content_sectionInfo' id='"+liCount+"'><span ondblclick=detailPassRecord(this) id="+list[i].cphm+">" + list[i].dmmc+"</span></li>";
		liCount++;
	}
	//在被选中元素的开头插入制定内容
	$(".passRecordSliderDetial").prepend(str);	
	if(liCount>liCountConstant){
		for(var i=tempSaveData;i<liCount-liCountConstant;i++){
			$("#"+i+"").remove();
		}
		tempSaveData=liCount-liCountConstant;
	}
	window.sessionStorage.setItem("bufferList",JSON.stringify(bufferList));
}
var errorPlateLiCount=0;
/*加载右侧假套牌记录*/
function loadErrorVehiclePlate(){
	 $.ajax({
	        type:'POST',
	        dataType: "json",
	        url: appRootPath+"/indexPage/errorVehiclePlate",
	        success:function(list){
	        	loadAllErrorPlate(list);
	        }
	    })
}
function loadAllErrorPlate(list){
	if(list==null||list.length == 0){
		return;
	}
	if(errorPlateBufferList.length==0){
		errorPlateBufferList = list;
	}else{
		for(var i=0;i<errorPlateBufferList.length;i++){
			for(var j=0;j<list.length;j++){
				if(errorPlateBufferList[i]==list[j]){
					list.splice(j,1); 
				}
			}
		}
		//数组合并
		errorPlateBufferList=errorPlateBufferList.concat(list);
		if(errorPlateBufferList.length>10){
			errorPlateBufferList.splice(0,errorPlateBufferList.length-10);
		}
	}
	var str = "";
	for (var i = list.length-1; i >= 0; i--) {
		str += "<li  class='errorVehiclePlateLi' id='error"+errorPlateLiCount+"' style='background:url("+appRootPath+"/images/index/errorPlate.png) no-repeat;' ><div class='errorPlateDiv'><span>假套牌：" + list[i] + "</span></div></li>";
		errorPlateLiCount++;
	}
	//在被选中元素的开头插入制定内容
	$("#errorPlate").prepend(str);	
	if(errorPlateLiCount>singleLiCountConstant){
		for(var i=errorTempSaveData;i<errorPlateLiCount-singleLiCountConstant;i++){
			$("#error"+i+"").remove();
		}
		errorTempSaveData=errorPlateLiCount-singleLiCountConstant;
	}
}
var singleBufferList= new Array();
var singleLiCountMiddle=0;
var singleLiCountLeftOne=0;
var singleLiCountLeftTwo=0;
var singleLiCountRightOne=0;
var singleLiCountRightTwo=0;
var singleLiCountDoorleft=0;
var singleLiCountDoorRight=0;

var singleTempSaveDataMiddle=0;
var singleTempSaveDataLeftOne=0;
var singleTempSaveDataLeftTwo=0;
var singleTempSaveDataRightOne=0;
var singleTempSaveDataRightTwo=0;
var singleTempSaveDataDoorleft=0;
var singleTempSaveDataDoorlRight=0;
var singleLiCountConstant=4;
function loadSingleVehiclePassRecord(list){
	if(list==null||list.length == 0){
		return;
	}
	if(singleBufferList.length==0){
		singleBufferList = list;
	}else{
		for(var i=0;i<singleBufferList.length;i++){
			for(var j=0;j<list.length;j++){
				if(singleBufferList[i].cphm==list[j].cphm){
					list.splice(j,1); 
				}
			}
		}
		//数组合并
		singleBufferList=singleBufferList.concat(list);
		if(singleBufferList.length>20){
			singleBufferList.splice(singleBufferList.length-4,singleBufferList.length);
		}
	}
	var middleStr="";
	var leftOneStr="";
	var leftTwoStr="";
	var rightOneStr="";
	var rightTwoStr="";
	var doorleftStr="";
	var doorRightStr="";
	for(var i = list.length - 1; i >= 0; i--){
		if(list[i].dmmc=="断面名称1"){
			middleStr += "<li  class='singlePointLiCommon' style='background:url("+appRootPath+"/images/index/inputNormal.png) no-repeat;' id='middle"+singleLiCountMiddle+"'><div style='margin-top:4px;'>" + list[i].cphm + "</div></li>";
			singleLiCountMiddle++;
		}else if(list[i].dmmc=="断面名称2"){
			leftOneStr += "<li  class='singlePointLiCommon' style='background:url("+appRootPath+"/images/index/inputNormal.png) no-repeat;' id='leftOne"+singleLiCountLeftOne+"'><div style='margin-top:4px;'>" + list[i].cphm + "</div></li>";
			singleLiCountLeftOne++;
		}else if(list[i].dmmc=="断面名称3"){
			leftTwoStr += "<li  class='singlePointLiCommon' style='background:url("+appRootPath+"/images/index/inputNormal.png) no-repeat;' id='leftTwo"+singleLiCountLeftTwo+"'><div style='margin-top:4px;'>" + list[i].cphm + "</div></li>";
			singleLiCountLeftTwo++;
		}else if(list[i].dmmc=="断面名称4"){
			rightOneStr += "<li  class='singlePointLiCommon' style='background:url("+appRootPath+"/images/index/inputNormal.png) no-repeat;' id='rightOne"+singleLiCountRightOne+"'><div style='margin-top:4px;'>" + list[i].cphm + "</div></li>";
			singleLiCountRightOne++;
		}else if(list[i].dmmc=="断面名称5"){
			rightTwoStr += "<li  class='singlePointLiCommon' style='background:url("+appRootPath+"/images/index/inputNormal.png) no-repeat;' id='rightTwo"+singleLiCountRightTwo+"'><div style='margin-top:4px;'>" + list[i].cphm + "</div></li>";
			singleLiCountRightTwo++;
		}else if(list[i].dmmc=="断面名称6"){
			doorleftStr += "<li  class='singlePointLiCommon' style='background:url("+appRootPath+"/images/index/inputNormal.png) no-repeat;' id='doorleft"+singleLiCountDoorleft+"'><div style='margin-top:4px;'>" + list[i].cphm + "</div></li>";
			singleLiCountDoorleft++;
		}else if(list[i].dmmc=="断面名称7"){
			doorRightStr += "<li  class='singlePointLiCommon' style='background:url("+appRootPath+"/images/index/inputNormal.png) no-repeat;' id='doorRight"+singleLiCountDoorRight+"'><div style='margin-top:4px;'>" + list[i].cphm + "</div></li>";
			singleLiCountDoorRight++;
		}
	}
	//在被选中元素的开头插入制定内容
	$("#middle").prepend(middleStr);
	$("#leftOne").prepend(leftOneStr);
	$("#leftTwo").prepend(leftTwoStr);
	$("#rightOne").prepend(rightOneStr);
	$("#rightTwo").prepend(rightTwoStr);
	$("#doorleft").prepend(doorleftStr);
	$("#doorRight").prepend(doorRightStr);
	

	if(singleLiCountMiddle==2){
		$("#middle").css("margin-top","74px");
		$("#middle").css("height","68px");
	}else if(singleLiCountMiddle==3){
		$("#middle").css("margin-top","40px");
		$("#middle").css("height","102px");
	}else if(singleLiCountMiddle>=4){
		$("#middle").css("margin-top","6px");
		$("#middle").css("height","136px");
	}
	if(singleLiCountLeftOne==2){
		$("#leftOne").css("margin-top","74px");
		$("#leftOne").css("height","68px");
	}else if(singleLiCountLeftOne==3){
		$("#leftOne").css("margin-top","40px");
		$("#leftOne").css("height","102px");
	}else if(singleLiCountLeftOne>=4){
		$("#leftOne").css("margin-top","6px");
		$("#leftOne").css("height","136px");
	}
	if(singleLiCountLeftTwo==2){
		$("#leftTwo").css("margin-top","74px");
		$("#leftTwo").css("height","68px");
	}else if(singleLiCountLeftTwo==3){
		$("#leftTwo").css("margin-top","40px");
		$("#leftTwo").css("height","102px");
	}else if(singleLiCountLeftTwo>=4){
		$("#leftTwo").css("margin-top","6px");
		$("#leftTwo").css("height","136px");
	}
	if(singleLiCountRightOne==2){
		$("#rightOne").css("margin-top","74px");
		$("#rightOne").css("height","68px");
	}else if(singleLiCountRightOne==3){
		$("#rightOne").css("margin-top","40px");
		$("#rightOne").css("height","102px");
	}else if(singleLiCountRightOne>=4){
		$("#rightOne").css("margin-top","6px");
		$("#rightOne").css("height","136px");
	}
	if(singleLiCountRightTwo==2){
		$("#rightTwo").css("margin-top","74px");
		$("#rightTwo").css("height","68px");
	}else if(singleLiCountRightTwo==3){
		$("#rightTwo").css("margin-top","40px");
		$("#rightTwo").css("height","102px");
	}else if(singleLiCountRightTwo>=4){
		$("#rightTwo").css("margin-top","6px");
		$("#rightTwo").css("height","136px");
	}
	
	if(singleLiCountDoorleft==2){
		$("#doorleft").css("margin-top","74px");
		$("#doorleft").css("height","68px");
	}else if(singleLiCountDoorleft==3){
		$("#doorleft").css("margin-top","40px");
		$("#doorleft").css("height","102px");
	}else if(singleLiCountDoorleft>=4){
		$("#doorleft").css("margin-top","6px");
		$("#doorleft").css("height","136px");
	}
	if(singleLiCountDoorRight==2){
		$("#doorRight").css("margin-top","74px");
		$("#doorRight").css("height","68px");
	}else if(singleLiCountDoorRight==3){
		$("#doorRight").css("margin-top","40px");
		$("#doorRight").css("height","102px");
	}else if(singleLiCountDoorRight>=4){
		$("#doorRight").css("margin-top","6px");
		$("#doorRight").css("height","136px");
	}
	if(singleLiCountMiddle>singleLiCountConstant){
		for(var i=singleTempSaveDataMiddle;i<singleLiCountMiddle-singleLiCountConstant;i++){
			$("#middle"+i+"").remove();
		}
		singleTempSaveDataMiddle=singleLiCountMiddle-singleLiCountConstant;
	}
	if(singleLiCountLeftOne>singleLiCountConstant){
		for(var i=singleTempSaveDataLeftOne;i<singleLiCountLeftOne-singleLiCountConstant;i++){
			$("#leftOne"+i+"").remove();
		}
		singleTempSaveDataLeftOne=singleLiCountLeftOne-singleLiCountConstant;
	}
	if(singleLiCountLeftTwo>singleLiCountConstant){
		for(var i=singleTempSaveDataLeftTwo;i<singleLiCountLeftTwo-singleLiCountConstant;i++){
			$("#leftTwo"+i+"").remove();
		}
		singleTempSaveDataLeftTwo=singleLiCountLeftTwo-singleLiCountConstant;
	}
	if(singleLiCountRightOne>singleLiCountConstant){
		for(var i=singleTempSaveDataRightOne;i<singleLiCountRightOne-singleLiCountConstant;i++){
			$("#rightOne"+i+"").remove();
		}
		singleTempSaveDataRightOne=singleLiCountRightOne-singleLiCountConstant;
	}
	if(singleLiCountRightTwo>singleLiCountConstant){
		for(var i=singleTempSaveDataRightTwo;i<singleLiCountRightTwo-singleLiCountConstant;i++){
			$("#rightTwo"+i+"").remove();
		}
		singleTempSaveDataRightTwo=singleLiCountRightTwo-singleLiCountConstant;
	}
	if(singleLiCountDoorleft>singleLiCountConstant){
		for(var i=singleTempSaveDataDoorleft;i<singleLiCountDoorleft-singleLiCountConstant;i++){
			$("#doorleft"+i+"").remove();
		}
		singleTempSaveDataDoorleft=singleLiCountDoorleft-singleLiCountConstant;
	}
	if(singleLiCountDoorRight>singleLiCountConstant){
		for(var i=singleTempSaveDataDoorlRight;i<singleLiCountDoorRight-singleLiCountConstant;i++){
			$("#doorRight"+i+"").remove();
		}
		singleTempSaveDataDoorlRight=singleLiCountDoorRight-singleLiCountConstant;
	}
	
}

function slideOn(obj){ 
	$("."+$(obj)[0].className+"").css("background-image","url("+appRootPath+"/images/index/buttonSelected.png)");
	$("."+$(obj)[0].className+"").css("font-weight","bold");
}
function slideOver(obj){
	$("."+$(obj)[0].className+"").css("background-image","url("+appRootPath+"/images/index/buttonNoSelect.png)");
	$("."+$(obj)[0].className+"").css("font-weight","normal");
}
function layerShow(obj){
	var path;
	if($(obj)[0].className=='gpsVehicleFlow'){
		path = '';//framePage/page/huoche_staticstics.html
	}else if($(obj)[0].className=='vehiclePassRecord'){
		path = '';
	}else if($(obj)[0].className=='falseDeck'){
		path = '';
	}else if($(obj)[0].className=='searchVehicle'){
		path = '';
	}	
	layer.open({
		type :2,
		title : false,
	//	skin:'layui-layer-nobg',
		content: [path, 'no'],//这样写可以去掉滚动条
		area : [ "1385px", "542px" ],
		fixed : true,
		shadeClose : true,  //shadeClose : true,
		scrollbar: false,
		closeBtn : 2,
		shade: [0.8, 'black'],//阴影颜色
	//	anim: 1,
		isOutAnim: false,
		idInAnim:false,
	});
}
