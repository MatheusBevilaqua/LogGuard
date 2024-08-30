const hamburger = document.querySelector(".hamburger");
const navMenu = document.querySelector(".nav-menu");

hamburger.addEventListener("click", mobileMenu);

function mobileMenu() {
    hamburger.classList.toggle("active");
    navMenu.classList.toggle("active");
}


// when we click on hamburger icon its close 

const navLink = document.querySelectorAll(".nav-link");

navLink.forEach(n => n.addEventListener("click", closeMenu));

function closeMenu() {
    hamburger.classList.remove("active");
    navMenu.classList.remove("active");
}

// -- ANIMAÇÃO DE FUNDO -- //
document.getElementById('register-btn').addEventListener('click', function () {
    document.body.classList.add('fade-out');
    setTimeout(function () {
        window.location.href = '../login.html';
    }, 500); // Tempo da animação (em ms)
});