function checkFormValidity() {
  var form = document.getElementById("loginForm");
  var submitButton = document.getElementById("submitButton");
  var username = document.getElementById("username");
  var password = document.getElementById("password");

  if (form.checkValidity() && username.value.length >= 3 && password.value.length >= 5) {
    submitButton.disabled = false;
  } else {
    submitButton.disabled = true;
  }
}

function validateInput(input) {
  if (!input.checkValidity() && input.value.trim() !== '') {
    input.classList.add('invalid');
  } else {
    input.classList.remove('invalid');
  }
  checkFormValidity();
}

function goToMainPage() {
  var currentUrl = window.location.href;
  var urlParts = currentUrl.split('/');
  urlParts[urlParts.length - 1] = 'home.html';
  var newUrl = urlParts.join('/');
  window.location.href = newUrl;
}

document.getElementById("loginForm").addEventListener("submit", function (event) {
  event.preventDefault();
  var formEmail = document.getElementById("username").value;
  var formPassword = document.getElementById("password").value;
  console.log("Email: " + formEmail);
  console.log("Parola: " + password);

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

  callPOST("authenticate", callBody, successFct);
  document.getElementById("loginForm").reset();
});

function removeFocusStyles(input) {
  input.style.borderColor = "#ccc";
  input.style.boxShadow = "none";
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