import {createTags, search, showNotification} from "./common.js";

const recipeId = localStorage.getItem('recipeId');
const username = localStorage.getItem('username');
fetch(`/recipe/${recipeId}`)
    .then(response => response.json())
    .then(recipe => {
        console.log(recipe);
        if(recipe.author === username){
            let shareButton = document.getElementById("share-button");
            shareButton.style.display = "block";
            shareButton.addEventListener("click", function (){
                fetch(`/recipe/change-status/${recipeId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            showNotification('Przepis został udostępniony');
                        } else {
                            throw new Error('Coś poszło nie tak');
                        }
                    })
                    .catch(error => {
                        console.error(error.message);
                        showNotification('Nieudostępniono');
                    });
            })
        }
        document.getElementById("rec-name").innerText = recipe.name
        document.getElementById("rec-author").innerText = `Autor: ${recipe.author}`
        document.getElementById("time").innerText = `Czas przygotowania: ${recipe.timeMinutes}min`
        const imgRecipe = document.getElementById('recipe-image');
        const base64Image = recipe.image;
        imgRecipe.src = `data:image/jpeg;base64,${base64Image}`;
        let divTags = document.getElementById("rec-tags")
        createTags(recipe.tags,divTags )
        createOrderedList(recipe.ingredients, "ingredients-list")
        createOrderedList(recipe.steps, "steps-list")
    });

function createOrderedList(list, id){
    let listOrdered = document.getElementById(id)
    for (let item of list){
        let li = document.createElement("li")
        li.innerText = item
        listOrdered.appendChild(li)
    }
}


function addFolders(names) {
    let select = document.getElementById("sel-folder");
    names.shift();

    names.forEach((name) => {
        let option = document.createElement("option");
        option.text = name;
        option.value = name;
        if (name === "moje ulubione przepisy") {
            option.selected = true;
        }
        select.appendChild(option);
    });
}

const addButton = document.getElementById('add-to-folder');

if(addButton) {
    addButton.addEventListener("click", function () {
        let chosenFolder = document.getElementById("sel-folder").value;
        fetch(`/folder/add/${username}/${chosenFolder}/${recipeId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    showNotification('Dodano do folderu ' + chosenFolder);
                } else {
                    throw new Error();
                }
            })
            .catch(error => {
                console.error(error.message);
                showNotification('Coś poszło nie tak');
            });
    })

    fetch(`/folder/${username}`)
        .then(response => response.json())
        .then(names => {
            addFolders(names)
        })

}

const searchForm= document.getElementById('searchForm')

searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});
