document.addEventListener("DOMContentLoaded", function () {
  getLocation();
});

function getLocation() {
  fetch("http://ip-api.com/json/?fields=8208")
    .then((response) => response.json())
    .then((data) => {
      console.log(data.city);
      console.log(data.query);
      const formattedData = `
                <p>City: ${data.city}</p>
                <p>IP: ${data.query}</p>
                <!-- Add more fields as needed -->
            `;
      document.getElementById("dataContainer").innerHTML = formattedData;
    })
    .catch((error) => console.error("Error fetching data:", error));
  console.log("Will Call page location");
}

document.getElementById("checkInButton").addEventListener("click", function () {
  alert("You have checked in!");
});
