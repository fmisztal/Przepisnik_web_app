import {search} from "./common.js";

const searchForm= document.getElementById('searchForm')
searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});
localStorage.setItem('username', null);
localStorage.setItem('jwtToken', null);
localStorage.setItem('recipeId', null);
search("empty",true);