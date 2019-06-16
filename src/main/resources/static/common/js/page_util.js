/**
 * 判断页面的操作区是否有按钮
 * @param allOperations 所有权限按钮id字符串
 * @param thisPageOperations 当前页面权限按钮id数组
 * @returns 有按钮返回true 没有按钮返回false;
 */
function judgePageOperationArea(allOperations, thisPageOperations){
	  var result = false;		  
	  for (var i = 0; i < thisPageOperations.length; i++) {
		  if(allOperations.indexOf(thisPageOperations[i]) != -1){
			  result = true;
			  return result;
			  }
		}
	  return result;
}