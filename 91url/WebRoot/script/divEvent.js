var changeOver = function(div){
	div.style.backgroundColor = "#999999";
	div.style.color="white";
	var nodes = div.childNodes;
	if(nodes.length==2){ //页面生成的node跟js添加的node的数量不同,所以要作个判断
	    nodes[0].style.visibility ="visible";
	}else{
	    nodes[1].style.visibility ="visible";
	}
}
var changeBlur = function(div){
	div.style.backgroundColor = "#EEEEEE";
	div.style.color="black";
	var nodes = div.childNodes;
	if(nodes.length==2){
	    nodes[0].style.visibility ="hidden";
	}else{
	    nodes[1].style.visibility ="hidden";
	}
}

var changeImgOver = function(div){
	alert(1);
	div.innerHTML = "<img src='img/delete_0.png' />";
}

var changeImgBlur = function(div){
	div.innerHTML = "<img src='img/delete.png' />";
}