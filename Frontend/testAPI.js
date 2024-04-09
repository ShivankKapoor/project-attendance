
function redirectToHome() {
    window.location.href = "home.html";
}

function Submit() {
  const apiUrl = "http://localhost:8080/checkIn";
  const data = {
    "courseId": document.getElementById("courseID").value,// "0"
    "utdId": document.getElementById("utdID").value, // "12345678"
    "netId": document.getElementById("netID").value //"sureeal1900048"
  };

  const requestOptions = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  };

  fetch(apiUrl, requestOptions)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((data) => {
      outputElement.textContent = JSON.stringify(data, null, 2);
      console.log("Check In Good")
    })
    .catch((error) => {
      console.error("Error:", error);
    });
    redirectToHome()
}
