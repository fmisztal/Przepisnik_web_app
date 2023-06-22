import {search} from "./common.js";

const privateRadio = document.getElementById('own');
const publicRadio = document.getElementById('all');

let status = true;
if(privateRadio!=null) {
    privateRadio.addEventListener('click', function () {
        status = false;
    });
    publicRadio.addEventListener('click', function () {
        status = true;
    });
}
const searchForm= document.getElementById('searchForm')
const longSearchForm = document.getElementById("long-search-form")
longSearchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("detailed", status);
});
searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});

const submitButton = document.getElementById('submitbutton');

submitButton.addEventListener("click", function (){
    search("detailed", status);
})