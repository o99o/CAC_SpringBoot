function checkTel(tel) {
	if (/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(tel)) {
		return true;
	}
	return false;
}

function checkPhone(phone) {
	if (/^1(3|4|5|6|7|8|9)\d{9}$/.test(phone)) {
		return true;
	}
	return false;
}

// 验证正整数,小于1000000
function checkNum(value) {

	var reg = /^([1-9][0-9]*)$/;
	if (reg.test(value) && value < 1000000) {
		return false;
	} else {
		return true;
	}
};

//验证正整数,小于100
function checkNum100(value) {

	var reg = /^([1-9][0-9]*)$/;
	if (reg.test(value) && value < 100) {
		return false;
	} else {
		return true;
	}
};

// 校验经度是否符合规范
// 校验经度
function checkLong(lng) {
	var longrg = /^(\-|\+)?(((\d|[1-9]\d|1[0-7]\d|0{1,3})\.\d{0,6})|(\d|[1-9]\d|1[0-7]\d|0{1,3})|180\.0{0,6}|180)$/;
	if (!longrg.test(lng)) {
		return true;
	}
	return false;
}

// 校验纬度是否符合规范
// 纬度
function checkLat(lat) {
	var latreg = /^(\-|\+)?([0-8]?\d{1}\.\d{0,6}|90\.0{0,6}|[0-8]?\d{1}|90)$/;
	if (!latreg.test(lat)) {
		return true;
	}
	return false;
}

function isIP(ip) 
{ 
	var re =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/ 
	return re.test(ip); 
}
