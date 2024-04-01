function togglePasswordVisibility() {
  var passwordInput = document.getElementById("password");
  var retypedPasswordInput = document.getElementById("confirmPassword")

  if (passwordInput.type === "password") {
    passwordInput.type = "text";
    retypedPasswordInput.type="text";
  } else {
    passwordInput.type = "password";
    retypedPasswordInput.type="password"
  }
}

function redirectToLogin() {
  window.location.href = "index.html";
}


document.getElementById("registerForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission
  
    // Get the values from the registration form
    var fullName = document.getElementById("fullname").value;
    var email = document.getElementById("email").value;
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
  
    // You can add your own validation here
    if (password !== confirmPassword) {
      alert("Passwords do not match. Please try again.");
      return;
    }
  
    // You can perform further validation and registration process here
    // For demonstration, let's just log the values for now
    console.log("Full Name:", fullName);
    console.log("Email:", email);
    console.log("Username:", username);
    console.log("Password:", password);
  
    // Optionally, you can redirect the user to another page upon successful registration
  });
  