function Submit() {
  const apiUrl = "http://localhost:8080/checkIn";
  const data = {
    "courseId": "0",
    "utdId": "12345678",
    "netId": "sureeal1900048"
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
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}
