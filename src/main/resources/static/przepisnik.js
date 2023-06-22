import {addRecipeAd, closeFromId, openForm, Recipe, search, showNotification} from "./common.js";

const searchForm= document.getElementById('searchForm')
const username = localStorage.getItem('username');
localStorage.removeItem('whatdelete');
localStorage.removeItem('recipeId');
searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});

const folderNameElement = document.getElementById("folder-name");
function createRecipeAdWithDelete(recipe){
    let folderContentDiv = document.getElementById("folder-content-recipes")
    let newDiv = document.createElement("div");
    newDiv.className = "recip";
    let deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.className = "actionbutton w-button";

    let deleteImage = document.createElement("img");
    deleteImage.src = "img/delete.png";
    deleteImage.height = 25;
    deleteImage.alt = "";
    deleteImage.className = "photo";
    deleteImage.title = "Usuń przepis";
    deleteButton.appendChild(deleteImage);

    let folderElementNameText = folderNameElement.innerText;
    if(folderElementNameText === 'moje autorskie przepisy'){
        localStorage.setItem('whatdelete','recipe');
        localStorage.setItem('recipeId', recipe.id);
        deleteButton.addEventListener("click", function (){
            openForm('confirm');
        })
    }else{
        deleteButton.addEventListener('click', function (){
            fetch(`/folder/${username}/${folderElementNameText}/${recipe.id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        folderContentDiv.innerHTML = '';
                        getRecipesFromFolder(folderElementNameText);
                    } else {
                        throw new Error();
                    }
                })
                .catch(error => {
                    console.error(error.message);
                    showNotification('Coś poszło nie tak');
                });
        })
    }

    addRecipeAd(recipe, newDiv)
    newDiv.appendChild(deleteButton)
    folderContentDiv.appendChild(newDiv)
}

function addFolderButtons(folderNames) {
    let tabsDiv = document.getElementById("tabs");

    folderNames.forEach(folderName => {
        let folderButton = document.createElement("button");
        folderButton.className = "folder-tab";
        folderButton.innerText = folderName;
        folderButton.addEventListener("click", function (){
            folderNameElement.textContent = folderName;
            if(folderName === "moje autorskie przepisy" || folderName === "moje ulubione przepisy"){
                deleteFolderButton.style.display = "none";
            }else{
                deleteFolderButton.style.display = "inline-flex";
                deleteFolderButton.style.flexDirection = "row-reverse"
            }
            let folderContentDiv = document.getElementById("folder-content-recipes")
            folderContentDiv.innerHTML = '';
            getRecipesFromFolder(folderName);
        })

        tabsDiv.appendChild(folderButton);
    });
    let folderButton = document.createElement("button");
    folderButton.className = "folder-tab";
    folderButton.innerText = "+";
    folderButton.addEventListener("click", function (){
        openForm('new-name');
    })
    tabsDiv.appendChild(folderButton);
}

const newNameText = document.getElementById('new-folder-name');
const newNameClose = document.getElementById('edit-close');
const newConfirmButton = document.getElementById('name-confirm');
newNameClose.addEventListener("click", function (){
    document.getElementById('new-name').style.display = "none";
    newNameClose.innerText = '';
})
newConfirmButton.addEventListener('click', function (){
    const folderName = newNameText.value;
    fetch(`/folder/new/${username}/${folderName}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                document.getElementById('new-name').style.display = "none";
                newNameText.innerText = '';
                fetch(`/folder/${username}`)
                    .then(response => response.json())
                    .then(names => {
                        document.getElementById("tabs").innerHTML = '';
                        addFolderButtons(names)
                    })
            } else {
                throw new Error('Zła nazwa');
            }
        })
        .catch(error => {
            console.error(error.message);
            showNotification('Nie możesz utowrzyć folderu o takiej samej nazwie jak już istniejący');
        });
})
const deleteClose = document.getElementById('delete-close');
const deleteYes = document.getElementById('yes-button');
const deleteNo = document.getElementById('no-button');
const deleteFolderButton = document.getElementById('deleteFolder');

deleteFolderButton.addEventListener("click", function (){
    openForm('confirm');
    localStorage.setItem('whatdelete','folder');
})
deleteClose.addEventListener('click', function (){
    closeFromId('confirm');
    localStorage.removeItem('whatdelete')
})
deleteNo.addEventListener('click', function (){
    closeFromId('confirm');
    localStorage.removeItem('whatdelete')
})

deleteYes.addEventListener('click', function (){
    const whatDelete = localStorage.getItem('whatdelete');
    let folderName = document.getElementById('folder-name').textContent
    if(whatDelete === 'folder'){
        fetch(`/folder/${username}/${folderName}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    showNotification('Usunięto folder');
                    document.getElementById("tabs").innerHTML = '';
                    getFolders();
                } else {
                    throw new Error();
                }
            })
            .catch(error => {
                console.error(error.message);
                showNotification('Coś poszło nie tak');
            });
    }

    if(whatDelete === 'recipe'){
        fetch(`/recipe/${localStorage.getItem('recipeId')}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    showNotification('Usunięto przepis');
                    getRecipesFromFolder(folderNameElement.textContent);
                } else {
                    throw new Error();
                }
            })
            .catch(error => {
                console.error(error.message);
                showNotification('Coś poszło nie tak');
            });
    }

    localStorage.removeItem('whatdelete')
    document.getElementById('confirm').style.display = "none";
})

function getRecipesFromFolder(folderName){
    fetch(`/folder/${username}/${folderName}`)
        .then(response => {
            if (response.ok) {
                return response.json();
            }else{
                throw new Error('Pusty folder');
            } })
        .then(data => {
            if(data!==undefined){
                document.getElementById('folder-content-recipes').innerHTML = '';
                data.forEach(recipe => {
                    const id = recipe.id;
                    const name = recipe.name;
                    const author = recipe.author;
                    const tags = recipe.tags;
                    const image = recipe.image;

                    let recipeDisplay = new Recipe(id,name,author,tags,[],[],0, image);
                    createRecipeAdWithDelete(recipeDisplay);
                });
            }
        })
        .catch(error => {
            console.error(error.message);
            document.getElementById('folder-content-recipes').innerHTML = 'W tym folderze nie ma jeszcze przepisów :(';
        });
}
getFolders();
function getFolders(){
    fetch(`/folder/${username}`)
        .then(response => response.json())
        .then(names => {
            addFolderButtons(names)
            folderNameElement.textContent = names[0];
            getRecipesFromFolder(names[0]);
        })
}