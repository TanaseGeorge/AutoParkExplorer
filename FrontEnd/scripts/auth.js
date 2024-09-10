function checkPasswordMatch() {
  var password = document.getElementById("password").value;
  var confirmPassword = document.getElementById("confirmPassword").value;
  var passwordMatchElement = document.getElementById("passwordMatch");
  var confirmPasswordInput = document.getElementById("confirmPassword");
  var passwordInput = document.getElementById("password");
  if (password.length >= passwordInput.minLength && confirmPassword.length >= passwordInput.minLength) {
    if (password !== confirmPassword) {
      confirmPasswordInput.classList.add("invalid");
      passwordMatchElement.textContent = "Parolele nu se potrivesc";
      passwordMatchElement.classList.add("password-mismatch");
    } else {
      confirmPasswordInput.classList.remove("invalid");
      passwordMatchElement.textContent = "";
    }
  } else {
    confirmPasswordInput.classList.remove("invalid");
    passwordMatchElement.textContent = "";
  }
  checkFormValidity();
}
function checkPasswordStrength(password) {
  var regexes = [
    /[A-Z]/,
    /[a-z]/,
    /[0-9]/,
    /.{8,}/
  ];
  var strength = 0;
  regexes.forEach(function (regex) {
    if (regex.test(password)) {
      strength += 25;
    }
  });
  return strength;
}
function updatePasswordStrength() {
  var password = document.getElementById("password").value;
  var strength = checkPasswordStrength(password);
  var strengthText = "";
  if (strength >= 75) {
    strengthText = "Puternica";
  } else if (strength >= 50) {
    strengthText = "Medie";
  } else if (strength >= 25) {
    strengthText = "Slaba";
  } else {
    strengthText = "Foarte slaba";
  }
  var passwordStrengthElement = document.getElementById("passwordStrength");
  passwordStrengthElement.textContent = "Puterea parolei: " + strengthText;
}
document.getElementById("password").addEventListener("input", updatePasswordStrength);
document.getElementById("signInForm").addEventListener("submit", function (event) {
  event.preventDefault();
  var username = document.getElementById("username").value;
  var formEmail = document.getElementById("email").value;
  var formPassword = document.getElementById("password").value;
  var confirmPassword = document.getElementById("confirmPassword").value;
  var dob = document.getElementById("dob").value;
  console.log("Nume de utilizator: " + username);
  console.log("Email: " + email);
  console.log("Parola: " + password);
  console.log("Confirmare parola: " + confirmPassword);
  console.log("Data nasterii: " + dob);


  var callBody = JSON.stringify({
    email: formEmail,
    userName: username,
    password: formPassword,
    role: (username === "admin") ? "admin" : "normal user"
  });

  var passFct = (data) => {
    if (data.status === 201) {
      var callBody = JSON.stringify({
        email: formEmail,
        password: formPassword
      });
    
      var successFct = (data) => {
        if(data.tokenJwt) {
          sessionStorage.setItem("jwt", data.tokenJwt);
          sessionStorage.setItem("username", data.user.userName);
          sessionStorage.setItem("email", data.user.email);
          sessionStorage.setItem("role", data.user.role);
          window.location.href = "home.html";
        }
      }
    
      callPOST("authenticate", callBody, successFct).then(() => { window.location.href = "home.html"; });
    }
  }

  callPOST("register", callBody, passFct);
  document.getElementById("signInForm").reset();
});
document.getElementById("password").addEventListener("input", checkPasswordMatch);
document.getElementById("confirmPassword").addEventListener("input", checkPasswordMatch);
function checkFormValidity() {
  var form = document.getElementById("signInForm");
  var submitButton = document.getElementById("submitButton");
  var username = document.getElementById("username");
  var password = document.getElementById("password");
  var confirmPassword = document.getElementById("confirmPassword");
  var passwordMatchElement = document.getElementById("passwordMatch");
  var passwordMatch = password.value === confirmPassword.value;
  if (form.checkValidity() && username.value.length >= 4 && password.value.length >= 3 && (password.value.length > 0 || confirmPassword.value.length > 0)) {
    submitButton.disabled = false;
    passwordMatchElement.textContent = "";
  } else {
    submitButton.disabled = true;
  }
}
function validateInput(input) {
  if (input.id.toLowerCase().includes("passw")) {
    if (document.getElementById("password").value === document.getElementById("confirmPassword").value) {
      document.getElementById("password").classList.remove('invalid');
      document.getElementById("confirmPassword").classList.remove('invalid');
    } else {
      document.getElementById("password").classList.add('invalid');
      document.getElementById("confirmPassword").classList.add('invalid');
    }
  } else {
    if (!input.checkValidity() && input.value.trim() !== '') {
      input.classList.add('invalid');
    } else {
      input.classList.remove('invalid');
    }
  }
  checkFormValidity();
}
function goToMainPage() {
  window.location.href = "home.html";
}
function removeFocusStyles(input) {
  if (!input.id.toLowerCase().includes("passw")) {
    input.style.borderColor = "#ccc";
    input.style.boxShadow = "none";
  }
}
document.querySelectorAll('input').forEach(function (input) {
  input.addEventListener('blur', function () {
    removeFocusStyles(input);
  });
});
function removeInvalidClass() {
  document.querySelectorAll('input').forEach(function (input) {
    removeFocusStyles(input);
  });
}
window.addEventListener('load', removeInvalidClass);
document.addEventListener('keyup', function (event) {
  var capslockWarning = document.getElementById("capslockWarning");
  var keyCode = event.keyCode ? event.keyCode : event.which;
  var shiftKey = event.shiftKey ? event.shiftKey : (keyCode == 16 ? true : false);
  if (keyCode == 20) {
    capslockWarning.style.display = event.getModifierState('CapsLock') ? 'block' : 'none';
  } else if ((keyCode >= 65 && keyCode <= 90) && !shiftKey) {
    capslockWarning.style.display = 'block';
  } else {
    capslockWarning.style.display = 'none';
  }
});