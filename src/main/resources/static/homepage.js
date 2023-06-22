import {search} from "./common.js";

const searchForm= document.getElementById('searchForm')
searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});

search("empty",true);