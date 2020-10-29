async function addReimbursement(){
    const reimb = {
        amount: parseFloat(document.getElementById("amount").value),
        description: document.getElementById("description").value,
        type: parseInt(document.getElementById("type").value),
    }

    console.log(JSON.stringify(reimb));

    await fetch('http://localhost:8080/AmatuerPhotoshop/reimb.json', {
        method: 'post',
        body: JSON.stringify(reimb)
    });

}

document.getElementById("create").addEventListener('click', addReimbursement);