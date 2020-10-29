function renderCards(reimbursements){
    if (reimbursements == undefined){
        return;
    }

    for (const reimb of reimbursements){
        const baseDiv = document.createElement("div");
        const headDiv = document.createElement("div");
        const bodyDiv = document.createElement("div");
        const type = document.createElement("h5");
        const desc = document.createElement("p");
        const amnt = document.createElement("p");
        const submitted = document.createElement("p");
        const resolved = document.createElement("p");
        const creatorName = document.createElement("p");
        const resolverName = document.createElement("p");

        baseDiv.classList.add("card", "text-white", "bg-secondary", "col", "mb-4");

        baseDiv.style = "max-width: 18rem; margin: 20px";

        headDiv.classList.add("card-header");
        headDiv.innerText = reimb.status.status;

        bodyDiv.classList.add("card-body");

        type.classList.add("card-title", "text-center", "mx-auto");
        desc.classList.add("card-text");
        amnt.classList.add("card-text");
        submitted.classList.add("card-text");;
        resolved.classList.add("card-text");
        creatorName.classList.add("card-text");
        resolverName.classList.add("card-text");

        type.innerText = reimb.type.type;
        desc.innerText = "Description: " + reimb.description;
        amnt.innerText = "Amount: $" + reimb.amount;
        submitted.innerText = "Submitted: " + reimb.dateSubmitted.month + " " + reimb.dateSubmitted.dayOfMonth + ", " + reimb.dateSubmitted.year;
        creatorName.innerText = "Requested By: " + reimb.author.firstName + " " + reimb.author.lastName;

        resolverName.innerText = "Resolved By: ";
        resolved.innerText = "Resolved: ";

        baseDiv.append(headDiv, bodyDiv);
        bodyDiv.append(type, desc, amnt, submitted, creatorName, resolved, resolverName);
        baseDiv.id = reimb.id;

        //reject button
        const rejectButton = document.createElement("button");
        rejectButton.type = "submit";
        rejectButton.value = reimb.id;
        rejectButton.classList.add("btn", "btn-danger", "mx-auto", "text-center", "float-right");
        rejectButton.style = "padding: 5px;";
        rejectButton.name = "id";
        rejectButton.innerText = "Reject";

        rejectButton.addEventListener("click", function(){ rejectReimbursement(reimb.id) });

        //accept button
        const acceptButton = document.createElement("button");
        acceptButton.type = "submit";
        acceptButton.value = reimb.id;
        acceptButton.classList.add("btn", "btn-success", "mx-auto", "text-center", "float-left");
        acceptButton.style = "padding: 5px;";
        acceptButton.name = "id";
        acceptButton.innerText = "Accept";

        acceptButton.addEventListener("click", function(){ acceptReimbursement(reimb.id) });

        const buttonDiv = document.createElement("div");
        buttonDiv.append(acceptButton, rejectButton);
        baseDiv.append(buttonDiv);

        document.getElementById("cards").append(baseDiv);
    }
}

function destroyCards(){
    const parent = document.getElementById("cards");

    while(parent.firstChild){
        parent.removeChild(parent.firstChild);
    }
}

async function asyncFetch(url, expression){
    const response = await fetch(url);
    const json = await response.json();
    expression(json);
}

async function rejectReimbursement(id){
    const cardId = {
        cid: id
    }

    const fetched = await fetch('http://localhost:8080/AmatuerPhotoshop/reject.json', {
        method: 'post',
        body: JSON.stringify(cardId)
    });

    destroyCards();

    asyncFetch(
        "http://localhost:8080/AmatuerPhotoshop/allReimbursements.json",
        renderCards
    )
}

async function acceptReimbursement(id){
    const cardId = {
        cid: id
    }
    
    const fetched = await fetch('http://localhost:8080/AmatuerPhotoshop/accept.json', {
        method: 'post',
        body: JSON.stringify(cardId)
    });
    
    destroyCards();
    
    asyncFetch(
        "http://localhost:8080/AmatuerPhotoshop/allUserReimbursements.json",
        renderCards
    );
}

//When page loads, grab all reimbursements
asyncFetch(
    "http://localhost:8080/AmatuerPhotoshop/allReimbursements.json",
    renderCards
);

function addUsers(users){
    for (const user of users){
        const nextOption = document.createElement("option");
        nextOption.value = user.id;
        nextOption.innerText = user.firstName + " " + user.lastName;

        document.getElementById("users").append(nextOption);
    }
}

async function sortByUser(id){
    const userId = {
        uid: id
    }

    const fetched = await fetch('http://localhost:8080/AmatuerPhotoshop/sorted.json', {
        method: 'post',
        body: JSON.stringify(userId)
    });

    const json = await fetched.json();

    destroyCards();

    renderCards(json);
}

asyncFetch(
    "http://localhost:8080/AmatuerPhotoshop/user.json",
    addUsers
);