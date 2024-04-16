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

document
	.getElementById("checkInButton")
	.addEventListener("click", function (event) {
		event.preventDefault(); // Prevent form submission

		// Get values from form fields
		const utdId = document.getElementById("utdId").value;
		const netId = document.getElementById("netId").value;
		const name = document.getElementById("name").value;
		const passcode = document.getElementById("passcode").value;

		// Check if any field is empty
		if (!utdId || !netId || !name || !passcode) {
			alert("Check in failed");
		} else {
			alert("Check in successful");
		}
	});

function enableCheckInForDateTimeAndDuration(dateTimeString, durationMinutes) {
	const now = new Date();
	const startTime = new Date(dateTimeString);
	const endTime = new Date(startTime.getTime() + durationMinutes * 60000); // Add duration in minutes to start time

	const checkInButton = document.getElementById("checkInButton");

	// Check if 'now' is on the same day as 'startTime'
	const isSameDay =
		now.getFullYear() === startTime.getFullYear() &&
		now.getMonth() === startTime.getMonth() &&
		now.getDate() === startTime.getDate();

	// Enable the button only if 'now' is within the specified time window on the same day
	if (now >= startTime && now <= endTime && isSameDay) {
		checkInButton.disabled = false;
		checkInButton.classList.remove("disabled");
	} else {
		checkInButton.disabled = true;
		checkInButton.classList.add("disabled");
	}
}

// Example usage
document.addEventListener("DOMContentLoaded", function () {
	// Assuming the start DateTime is "2024-04-16 08:00:00" and the margin is 15 minutes
	enableCheckInForDateTimeAndDuration("2024-04-15 21:00:00", 15);
});

document.getElementById("utdId").addEventListener("input", function (e) {
	// Remove any characters that are not digits
	this.value = this.value.replace(/[^0-9]/g, "");

	// Limit the value length to 10 digits
	if (this.value.length > 10) {
		this.value = this.value.slice(0, 10);
	}
});

document.getElementById("class").addEventListener("input", function (e) {
	const regex = /^[A-Za-z]{2}\d{4}\.\d{3}$/;
	// Allows valid input or a subset of the valid input that could lead to a valid state
	const tempRegex = /^[A-Za-z]{0,2}(\d{0,4}(\.\d{0,3})?)?$/;

	if (!tempRegex.test(this.value)) {
		this.value = this.oldValue || "";
	} else {
		this.oldValue = this.value;
	}
});
