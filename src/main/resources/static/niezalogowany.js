import {changeTheme, closeForm, login, openForm, register} from "./common.js";

const registrationForm = document.getElementById('form');
const loginForm = document.getElementById('login-form');

//Processing login form
loginForm.addEventListener('submit', function(event) {
    event.preventDefault();
    login(loginForm);
});

//Opening registration form
const registerOpen = document.getElementById('register-open');
const registerClose = document.getElementById('register-close');
registerOpen.addEventListener('click', function() {
    openForm('rejestracja')
})

registerClose.addEventListener('click', function() {
    closeForm()
})
localStorage.removeItem('username');
//Processing registration form
registrationForm.addEventListener('submit', e => {
    e.preventDefault();
    register(e, registrationForm);
});

//Dark mode
const toggle = document.getElementById("mode-toggle");

const storedTheme = localStorage.getItem('theme') || (window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light");
if (storedTheme)
    document.documentElement.setAttribute('data-theme', storedTheme)
toggle.addEventListener('click', function (){
    changeTheme();
})