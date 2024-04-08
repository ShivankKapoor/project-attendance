function togglePasswordVisibility() {
  var passwordInput = document.getElementById("password");
  if (passwordInput.type === "password") {
    passwordInput.type = "text";
  } else {
    passwordInput.type = "password";
  }
}

function redirectToRegister() {
  window.location.href = "register.html";
}

function redirectToHome() {
  window.location.href = "home.html";
}

document
  .getElementById("loginForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent form submission

    // Get the username and password from the form
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    // You can add your own validation here
    if (username === "test" && password === "test") {
      redirectToHome();
    } else {
      alert("Invalid username or password. Please try again.");
    }
  });
