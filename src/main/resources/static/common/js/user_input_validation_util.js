/**
 * 非空验证
 * @param str
 * @returns 空返回true 非空返回false
 */
function NpeValid(str){
	if(""==str || null==str){
		return true;
	}
	return false;
}


/**
 * 字符串长度验证
 * @param str 需要验证的字符串
 * @param length 允许的最大长度
 * @returns
 */
function strLengthValid(str, length){
	
	if(str.length>length){
		
		return true;
	}
	return false;
}
/**
 * 图片名称格式验证
 * @param photoStr 图片名称字符串
 * @returns 格式正确返回false 错误返回true
 */
function photoFormatValid(photoStr){
	var photoPostfix = photoStr.toLowerCase().substring(photoStr.lastIndexOf('.')+1);
	if(photoPostfix == 'png' ||photoPostfix == "jpeg" || photoPostfix == "bmp" || photoPostfix == "jpg" || photoPostfix == "gif"){
		return false;		
	}
	return true;
}
/**
 * 请求地址uri验证
 * @param uriStr
 * @returns 格式正确返回false 错误返回true
 */
function requestUri(uriStr){
	if(/^[a-zA-Z0-9\./]*$/g.test(uriStr)){
		return false;	
	}
	return true;
}
/**
 * 数字格式验证
 * @param num
 * @returns 格式正确放回false 错误返回true
 */
function numberValid(num){
	if(/^[0-9]*$/.test(num)){
		return false;
	}
	return true;
}