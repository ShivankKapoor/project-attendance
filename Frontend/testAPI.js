function redirectToHome() {
  window.location.href = "home.html";
}

document
  .getElementById("loginForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent form submission
    console.log("Test");
    const apiUrl = "http://localhost:8080/checkIn";
    const data = {
      courseId: document.getElementById("courseID").value, // "0"
      utdId: document.getElementById("utdID").value, // "12345678"
      netId: document.getElementById("netID").value, //"sureeal1900048"
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
        return response.json(); // Parse JSON once
      })
      .then((data) => {
        console.log(data);
        console.log("Check In Good");
        redirectToHome();
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  });

