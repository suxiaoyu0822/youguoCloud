<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
</head>
<body>
<script type="text/javascript">
class Message{
	constructor(type,content){
		this.type=type;
		this.content=content;
	}
}

class ClientConnectReq {
	constructor(code){
		this.time=(new Date().getTime());
		this.code=code;
	}
}

let webSocket=new WebSocket("ws://192.168.1.119:8081/ServiceWS");
webSocket.onerror=function() {
	console.log("ERROR!");
};
webSocket.onopen=function() {
	console.log("Link established.");

	let connectionReq=new ClientConnectReq(1);
	let connectionReqMsg=new Message('ClientConnectReq',connectionReq);
	console.log(connectionReqMsg);
	/*webSocket.send("connectionReqMsg");*/
};
webSocket.onmessage=(event)=>{
	console.log(event);
};
webSocket.onclose=()=>{
	console.log("Closed.");
};




</script>
</body>
</html>