export class Recipe{
    constructor(id, name, author, tags, steps, ingredients, time, image) {
        this.id = id
        this.name = name
        this.author = author
        this.tags = tags
        this.steps = steps
        this.ingredients = ingredients
        this.time = time
        this.image = image
    }
}

export function search(searchType,status){
    let requestBody;
    let searchInput;
    searchInput = '/recipe/search'

    if(searchType === 'empty'){
        requestBody = {
            pageNumber: 0,
            pageSize: 10,
            author: "",
            keyword: "",
            tags: []
        }
        document.getElementById("searchResult").innerHTML = "";
    }
    if(searchType === "short"){
        requestBody = {
            pageNumber: 0,
            pageSize: 10,
            author: "",
            keyword: document.getElementById('search').value,
            tags: []
        }
        document.getElementById("searchResult").innerHTML = "";
    }
    if(searchType === "detailed"){
        //Get tags
        const tags = document.querySelectorAll('input[type="checkbox"]');
        const checkedValues = [];
        tags.forEach(tag => {
            if (tag.checked) {
                checkedValues.push(tag.value);
            }
        });

        let author;

        if(!status){
             author = localStorage.getItem('username');
            searchInput = '/recipe/search/private'
        }else{
            author = "";
        }
        requestBody = {
            pageNumber: 0,
            pageSize: 10,
            author: author,
            keyword: document.getElementById('r-name').value,
            tags: checkedValues
        }
        document.getElementById("searchResult").innerHTML = "";
    }

    fetch(searchInput, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Request failed');
            }
        })
        .then(data => {
            if(data.length === 0){
                document.getElementById("searchResult").innerHTML = 'Nie ma przepisów spełniających twoje kryteria wyszukiwania'
            }
            data.forEach(recipe => {
                const id = recipe.id;
                const name = recipe.name;
                const author = recipe.author;
                const tags = recipe.tags;
                const image = recipe.image;

                let recipeDisplay = new Recipe(id,name,author,tags,[],[],0, image);
                addRecipeAd(recipeDisplay,document.getElementById("searchResult"));
            });
        })
        .catch(error => {
            console.error(error);
            document.getElementById("searchResult").innerHTML = 'Nie ma przepisów spełniających twoje kryteria wyszukiwania'
        });
}
export function addRecipeAd(recipe, div) {
    let divRecipeAd = document.createElement("div");
    let imgRecipe = document.createElement("img");
    let divInfo = document.createElement("div");
    let h3Name = document.createElement("h3");
    let h5Author = document.createElement("h5");
    let divTags = document.createElement("div");

    div.appendChild(divRecipeAd);
    divRecipeAd.className = "recipead";
    imgRecipe.className = "recad-img";
    divInfo.className = "recad-info";
    h3Name.className = "recad-name";
    h5Author.className = "recad-author";
    divTags.className = "recad-tags";
    divRecipeAd.addEventListener("click", function () {
        localStorage.setItem('recipeId', recipe.id);
        if(localStorage.getItem('username')!=null){
            window.location.href = 'wyswietl-przepis.html';
        }else{
            window.location.href = 'wyswietl-przepis-niezalogowany.html';
        }
    })
    const base64Image = recipe.image;
    imgRecipe.src = `data:image/jpeg;base64,${base64Image}`;

    h3Name.textContent = recipe.name;
    h5Author.textContent = `Autor: ${recipe.author}`;
    divRecipeAd.appendChild(imgRecipe);
    divRecipeAd.appendChild(divInfo);
    divInfo.appendChild(h3Name)
    divInfo.appendChild(h5Author)
    divInfo.appendChild(divTags);
    let id = document.createElement("span");
    id.textContent = recipe.id;
    id.style.display = "none";
    divInfo.appendChild(id)
    createTags(recipe.tags, divTags)

}


export function createTags(tags, divTags){
    for (let tag of tags) {
        let divTag = document.createElement("div");
        divTag.className = "tag"
        let imgTag = document.createElement("img");
        imgTag.src = `img/${tag}.png`;
        imgTag.alt = ""
        imgTag.height = 14
        divTag.appendChild(imgTag)
        divTag.appendChild(document.createTextNode(tag))
        divTags.appendChild(divTag)
    }
}

export function showNotification(message) {
    const alertElement = document.querySelector('.alert');
    const alertTextElement = document.querySelector('.alertText');
    const closeButton = document.querySelector('.alertClose');

    alertTextElement.textContent = message;
    alertElement.style.display = 'block';

    closeButton.addEventListener('click', function() {
        alertElement.style.display = 'none';
    });
}


const username = document.getElementById('name');
const email = document.getElementById('email');
const password = document.getElementById('psw');
const password2 = document.getElementById('psw2');
export function openForm(id) {
    document.getElementById(id).style.display = "block";
}
export function closeForm() {
    document.getElementById("form").reset();
    setDefault(username);
    setDefault(email);
    setDefault(password);
    setDefault(password2);
    document.getElementById("rejestracja").style.display = "none";
}
export function closeFromId(id){
    document.getElementById("form").reset();
    document.getElementById(id).style.display = "none";
}

export function login(loginForm){
    const formData = new FormData(loginForm);

    fetch('/auth/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(Object.fromEntries(formData))
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error();
            }
        })
        .then(data => {
            loginForm.reset();
            const jwtToken = data.token;

            localStorage.setItem('jwtToken', jwtToken);
            localStorage.setItem('username', formData.get('username').toString());

            window.location.href = 'homepage.html';
        })
        .catch(error => {
            console.error(error.message);
            showNotification('Niepoprawny login lub hasło');
        });
}

export function register(e, registrationForm){
    if(validate(e)){
        const formData = new FormData(registrationForm);

        const requestBody = {
            username: formData.get('name'),
            email: formData.get('email'),
            password: formData.get('psw')
        };
        fetch('/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (response.ok) {
                    showNotification('Gratulacje! Zarejestrowałeś się');
                    closeForm();
                } else {
                    throw new Error('Registration failed');
                }
            })
            .catch(error => {
                console.error(error.message);
                setError(username, 'Istnieje już użytkownik o takiej nazwie');
            });
    }
}


//Validating registration input
const validate = (e) => {
    const usernameValue = username.value.trim();
    const emailValue = email.value.trim();
    const passwordValue = password.value.trim();
    const password2Value = password2.value.trim();

    if(usernameValue === '') {
        setError(username, 'Podaj nazwę użytkownika');
        return false;
    } else {
        setSuccess(username);
    }

    if(emailValue === '') {
        setError(email, 'Podaj adres email');
        return false;

    } else if (!isValidEmail(emailValue)) {
        setError(email, 'Email nieprawidłowy');
        return false;

    } else {
        setSuccess(email);
    }

    if(passwordValue === '') {
        setError(password, 'Podaj hasło');
        return false;

    } else if (passwordValue.length < 6 ) {
        setError(password, 'Hasło musi mieć przynjamniej 6 znaków.')
        return false;
    } else {
        setSuccess(password);
    }

    if(password2Value === '') {
        setError(password2, 'Potwierdź hasło');
        return false;
    } else if (password2Value !== passwordValue) {
        setError(password2, "Hasła są różne");
        return false;
    } else {
        setSuccess(password2);
    }
    return true;
};

const setError = (element, message) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.err');

    errorDisplay.innerText = message;
    inputControl.classList.add('error');
    inputControl.classList.remove('success')
}

const setSuccess = element => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.err');

    errorDisplay.innerText = '';
    inputControl.classList.add('success');
    inputControl.classList.remove('error');
};

const setDefault = element => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.err');

    errorDisplay.innerText = '';
    inputControl.classList.remove('success');
    inputControl.classList.remove('error');
};

const isValidEmail = email => {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}


export function logout(){
    localStorage.clear();
    document.cookie = "token"+'=; Max-Age=-99999999;';
    window.location.href = '/index.html';

}

export function changeTheme(){
    const currentTheme = document.documentElement.getAttribute("data-theme");
    let targetTheme = "light";

    if (currentTheme === "light") {
        targetTheme = "dark";
    }
    document.documentElement.setAttribute('data-theme', targetTheme)
    localStorage.setItem('theme', targetTheme);
}


