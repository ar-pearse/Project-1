function renderCards(reimbursements){
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

        if (reimb.status.status === "PENDING"){
            baseDiv.classList.add("card", "text-white", "bg-secondary", "col", "mb-4");
        }
        else if (reimb.status.status === "REJECTED"){
            baseDiv.classList.add("card", "text-white", "bg-danger", "col", "mb-4");
        }
        else {
            baseDiv.classList.add("card", "text-white", "bg-success", "col", "mb-3");
        }
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

        if(reimb.resolver != null) {
            resolverName.innerText = "Resolved By: " + reimb.resolver.firstName + " " + reimb.resolver.lastName;
            resolved.innerText = "Resolved: " + reimb.dateResolved.month + " " + reimb.dateResolved.dayOfMonth + ", " + reimb.dateResolved.year;
        }
        else {
            resolverName.innerText = "Resolved By: ";
            resolved.innerText = "Resolved: ";
        }

        baseDiv.append(headDiv, bodyDiv);
        bodyDiv.append(type, desc, amnt, submitted, creatorName, resolved, resolverName);
        baseDiv.id = reimb.id;

        if (reimb.status.status === "PENDING"){
            const deleteButton = document.createElement("button");
            deleteButton.type = "submit";
            deleteButton.value = reimb.id;
            deleteButton.classList.add("btn", "btn-danger", "mx-auto", "text-center");
            deleteButton.style = "padding: 5px;";
            deleteButton.name = "id";
            deleteButton.innerText = "Cancel";

            deleteButton.addEventListener("click", function(){ cancelReimbursement(reimb.id) });

            baseDiv.append(deleteButton);
        }

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

asyncFetch(
    "http://localhost:8080/AmatuerPhotoshop/allUserReimbursements.json",
    renderCards
)

async function cancelReimbursement(id){
    const cardId = {
        cid: id
    }

    const fetched = await fetch('http://localhost:8080/AmatuerPhotoshop/cancel.json', {
        method: 'post',
        body: JSON.stringify(cardId)
    });

    destroyCards();

    asyncFetch(
        "http://localhost:8080/AmatuerPhotoshop/allUserReimbursements.json",
        renderCards
    );
}