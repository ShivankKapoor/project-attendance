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

document.addEventListener("DOMContentLoaded", function () {
	// Set up the time window for check-in
	enableCheckInWithinTimeWindow("18:00", "21:15");
});

function enableCheckInWithinTimeWindow(startTime, endTime) {
	const now = new Date();
	const [startHours, startMinutes] = startTime
		.split(":")
		.map((num) => parseInt(num, 10));
	const [endHours, endMinutes] = endTime
		.split(":")
		.map((num) => parseInt(num, 10));
	const startDate = new Date(
		now.getFullYear(),
		now.getMonth(),
		now.getDate(),
		startHours,
		startMinutes
	);
	const endDate = new Date(
		now.getFullYear(),
		now.getMonth(),
		now.getDate(),
		endHours,
		endMinutes
	);

	const checkInButton = document.getElementById("checkInButton");

	if (now >= startDate && now <= endDate) {
		checkInButton.disabled = false;
		checkInButton.classList.remove("disabled"); // Remove the 'disabled' class
	} else {
		checkInButton.disabled = true;
		checkInButton.classList.add("disabled"); // Add the 'disabled' class
	}
}

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
