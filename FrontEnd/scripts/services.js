function callService(endPoint, body, method, passFct, errorFct) {
    if(errorFct === undefined) {
        errorFct = (error) => {
            error => console.error('Error:', error);
        }
    }

    debugger;

    var link = "http://" + window.location.hostname + ":8080/api/" + endPoint;
    fetch(link, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: body
    })
    .then(response => response.json())
    .then(passFct)
    .catch(errorFct);

}

function callPOST(endPoint, body, passFct, errorFct) {
    callService(endPoint, body, "POST", passFct, errorFct);
}

function callGET() {
    callService(endPoint, body, "GET", passFct, errorFct);
}