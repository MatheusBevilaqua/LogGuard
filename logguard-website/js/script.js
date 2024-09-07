// Seleciona os elementos necessários
const hamburger = document.querySelector(".hamburger");
const navMenu = document.querySelector(".nav-menu");

// Adiciona um evento de clique no ícone de hambúrguer
hamburger.addEventListener("click", function() {
    // Alterna a classe "active" no ícone de hambúrguer e no menu de navegação
    hamburger.classList.toggle("active");
    navMenu.classList.toggle("active");
});

// Adiciona um evento de clique em todo o documento
document.addEventListener("click", function(event) {
    // Verifica se o clique foi em um link de navegação
    if (event.target.classList.contains("nav-link")) {
        // Remove a classe "active" do ícone de hambúrguer e do menu de navegação
        hamburger.classList.remove("active");
        navMenu.classList.remove("active");
    }
});