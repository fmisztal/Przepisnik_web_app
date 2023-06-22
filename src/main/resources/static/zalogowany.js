import {changeTheme, logout} from "./common.js";

const logoutButton = document.getElementById('wyloguj');

logoutButton.addEventListener('click', function() {
    logout();
})

//Dark mode
const toggle = document.getElementById("mode-toggle");

const storedTheme = localStorage.getItem('theme') || (window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light");
if (storedTheme)
    document.documentElement.setAttribute('data-theme', storedTheme)
toggle.addEventListener('click', function (){
    changeTheme();
})
