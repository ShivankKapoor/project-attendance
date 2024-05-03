// Listen for the DOMContentLoaded event to initialize the script after the DOM is fully loaded.
document.addEventListener("DOMContentLoaded", function () {
	getLocation();
});

var city = "";
var classes;

// Fetches the user's location based on their IP address using the ip-api service.
async function getLocation() {
	const checkInButton = document.getElementById("checkInButton");
	await fetch("http://ip-api.com/json/?fields=8208")
		.then((response) => response.json())
		.then((data) => {
			city = data.city;
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

function updateDropdown(classes) {
	const classSelect = document.getElementById("class");
	classSelect.innerHTML = ""; // Clear existing entries

	if (Array.isArray(classes)) {
		classes.forEach((cls) => {
			const option = document.createElement("option");
			option.value = cls.classId; // Using classId as the value
			option.textContent = cls.className; // Using className for display
			classSelect.appendChild(option);
		});
	} else {
		console.error("Expected an array for classes, received:", classes);
	}
}

async function getClasses(utdId) {
	const url = "http://localhost:8080/getAllClassesStudent";

	const requestBody = JSON.stringify({
		utdId: utdId,
	});

	const requestOptions = {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: requestBody,
	};

	try {
		const response = await fetch(url, requestOptions);
		const data = await response.json();
		console.log("Received data:", data.classes); // Debug: Log the received data

		// Assuming the received data is directly the array as shown
		if (Array.isArray(data.classes)) {
			updateDropdown(data.classes);
		} else {
			console.error("Data is not an array:", data);
		}
	} catch (error) {
		console.error("Error fetching classes:", error);
	}
}

document.addEventListener("DOMContentLoaded", function () {
	// This function is run after the HTML document has finished loading
	var submitButton = document.getElementById("submitButton");
	var utdId = document.getElementById("utdId");
	// Add a click event listener to the button
	submitButton.addEventListener("click", function () {
		getClasses(utdId.value); // Call the getClasses function when the button is clicked
	});
});

// Handles the check-in process when the "checkInButton" is clicked. It validates form input fields and displays an alert.
document
	.getElementById("checkInButton")
	.addEventListener("click", async function (event) {
		event.preventDefault(); // Prevent form submission

		// Get values from form fields
		const utdId = document.getElementById("utdId").value;
		const classId = document.getElementById("class").value;
		const passcode = document.getElementById("passcode").value;

		// Check if any field is empty
		if (!utdId || !classId || !passcode) {
			alert("Check in failed: All fields must be filled.");
			return; // Exit the function if any field is empty
		}

		// Data to be sent to the server
		const requestData = {
			courseId: classId,
			utdId: utdId,
			password: passcode,
		};

		try {
			// Make a PUT request to the server
			const response = await fetch("http://localhost:8080/checkIn", {
				method: "PUT",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify(requestData),
			});

			if (!response.ok) {
				throw new Error(`HTTP error! status: ${response.status}`);
			}

			const result = await response.json(); // Assuming the server responds with JSON

			// Use the server's response to determine the alert
			if (result.isCheckIn) {
				alert("Check-in successful");
			} else {
				alert("Check-in failed: Please try again.");
			}
		} catch (error) {
			console.error("Failed to process check-in:", error);
			alert("Check-in failed: Unable to connect to the server.");
		}
	});

// Enables the "checkInButton" within a specific date and time range if the current date and time fall within that range.
function enableCheckInForDateTimeAndDuration(dateTimeString, durationMinutes) {
	const now = new Date();
	const startTime = new Date(dateTimeString);
	const endTime = new Date(startTime.getTime() + durationMinutes * 60000);

	console.log("Current time:", now);
	console.log("Start time:", startTime);
	console.log("End time:", endTime);

	const checkInButton = document.getElementById("checkInButton");

	// Check if 'now' is within the time window
	const isWithinTimeWindow = now >= startTime && now <= endTime;

	console.log(isWithinTimeWindow);
	// Check if 'now' is on the same day as 'startTime'
	const isSameDay = now.toDateString() === startTime.toDateString(); // Simplified day comparison

	console.log("THIS IS THE CITY:", city);
	// Determine if the button should be enabled based on time and location
	if (city === "Richardson" && isWithinTimeWindow && isSameDay) {
		checkInButton.disabled = false;
		checkInButton.classList.remove("disabled");
		console.log("Check-in enabled");
	} else {
		checkInButton.disabled = true;
		checkInButton.classList.add("disabled");
		console.log("Check-in disabled");
	}
}

document.addEventListener("DOMContentLoaded", function () {
	const classDropdown = document.getElementById("class");

	classDropdown.addEventListener("change", async function () {
		const courseId = this.value; // Assuming the value of the dropdown options are course IDs
		console.log(courseId);
		// Make a POST request to get timings
		try {
			const response = await fetch("http://localhost:8080/getTimings", {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ classId: courseId }),
			});

			if (!response.ok) {
				throw new Error(`HTTP error! status: ${response.status}`);
			}

			const data = await response.json();
			console.log("Timings data received:", data);

			if (data.classes && data.classes.length > 0) {
				// Assuming we're taking the first class for simplicity
				const classInfo = data.classes[0];

				// Construct full start date and time string (ISO 8601 format)
				const dateTimeString = `${classInfo.startDate}T${classInfo.startTime}`;

				// Convert time buffer (duration) to minutes
				const timeBuffer = classInfo.timeBuffer; // Format "HH:MM:SS"
				const parts = timeBuffer.split(":");
				const durationMinutes =
					parseInt(parts[0], 10) * 60 + parseInt(parts[1], 10); // Convert hours and minutes to total minutes

				// Call your function with these parameters
				enableCheckInForDateTimeAndDuration(
					dateTimeString,
					durationMinutes
				);
			} else {
				console.error("No class timings available");
			}
		} catch (error) {
			console.error("Failed to fetch timings:", error);
		}
	});
});

// Sanitizes the input for the "utdId" field to only allow digits and limit the length to 10 digits.
document.getElementById("utdId").addEventListener("input", function (e) {
	// Remove any characters that are not digits
	this.value = this.value.replace(/[^0-9]/g, "");

	// Limit the value length to 10 digits
	if (this.value.length > 10) {
		this.value = this.value.slice(0, 10);
	}
});

// Validates and sanitizes the input for the "class" field to match a specific format (e.g., "CS1234.001").
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

document.addEventListener("DOMContentLoaded", function () {
	// Listen for click event on the submit button
	document
		.getElementById("submitButton")
		.addEventListener("click", async function (event) {
			event.preventDefault(); // Prevent form submission

			// Get values from form fields
			const utdId = document.getElementById("utdId").value;

			// Check if utdId is not empty
			if (utdId.trim() !== "") {
				// Disable the UTD-ID input
				document.getElementById("utdId").disabled = true;

				// Hide submit button and show additional fields and check-in button
				document.getElementById("submitButton").style.display = "none";
				document.getElementById("additionalFields").style.display = "block";
				document.getElementById("checkInButton").style.display = "block";

				// Fetch classes from the server
				// try {
				// 	// Check if classes were successfully fetched
				// 	if (classes && classes.length > 0) {
				// 		const classSelect = document.getElementById("class");
				// 		classSelect.innerHTML =
				// 			'<option value="">Select Class</option>'; // Reset dropdown
				// 		// Populate dropdown with classes
				// 		classes.forEach((cls) => {
				// 			const option = document.createElement("option");
				// 			option.value = cls.id; // Assuming 'id' is the property for class identifier
				// 			option.textContent = cls.name; // Assuming 'name' is the property for class name
				// 			classSelect.appendChild(option);
				// 		});
				// 	} else {
				// 		console.log("No classes found for the given UTD ID.");
				// 	}
				// } catch (error) {
				// 	console.error("Error fetching classes:", error);
				// }
			} else {
				alert("UTD-ID is required.");
			}
		});
});
