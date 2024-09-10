const menuToggle = document.querySelector('.menu-toggle');
const mainNav = document.querySelector('.main-nav');

menuToggle.addEventListener('click', function () {
    mainNav.classList.toggle('active');
});

document.addEventListener('click', function (event) {
    const isClickInsideMenu = mainNav.contains(event.target);
    const isClickOnToggle = menuToggle.contains(event.target);

    if (!isClickInsideMenu && !isClickOnToggle) {
        mainNav.classList.remove('active');
    }
});

if(sessionStorage.getItem("jwt")) {
    document.getElementById("authBtn").style.display = "none";
} else {
    document.getElementById("logoutBtn").style.display = "none";
}

if(sessionStorage.getItem("role") != "admin") {
    document.getElementById("admin_page_ref").remove();
}

document.getElementById("logoutBtn").addEventListener("click", function() {
    sessionStorage.clear();
    location.reload();
});