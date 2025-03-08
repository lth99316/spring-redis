


// Login Form
document.getElementById("login-form").onsubmit = function() {

    const xhttpr = new XMLHttpRequest();
    xhttpr.open('POST', "http://" + document.location.host + "/api/login", true);

    let username = document.getElementById("username");
    let password = document.getElementById("password");

    if (username != null && password != null) {
        xhttpr.send({ username: username, password: password });

        xhttpr.onload = ()=> {
          if (xhttpr.status === 200) {
              const response = JSON.parse(xhttpr.response);

              // Todo: refactor
              localStorage.setItem("token", JSON.stringify(response))

              xhttpr.open('POST', "http://" + document.location.host + "/ui/home", true);
              xhr.setRequestHeader('Content-Type', 'text/html');
              xhr.setRequestHeader('Authorization', 'Bearer ' + response.accessToken );
              xhttpr.send()

          } else {
              // Handle error
              alert("Have Error")
          }
        };

    } else {
        alert("Missing username or password")
    }
}

document.getElementById("chat-message").onsubmit = function() {
    let message = document.getElementById("message");

    if (message != null) {
        connectWebSocket();
        conn.send(message.value)
    }
}

var conn;
function connectWebSocket() {
    if (conn == null) {
        if (window["WebSocket"]) {
            console.log("Support WebSocket");

            conn = new WebSocket("ws://" + document.location.host + "/ws");
        } else {
            alert("Not support websocket");
        }
    }
}