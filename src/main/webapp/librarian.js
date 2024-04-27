getBooksFromLibrary();

function getBooksFromLibrary() {
    const shelf = document.querySelector('.shelf__inner');

    fetch('/LibrarianServlet')
        .then(res => res.json())
        .then(libObj => {
            shelf.innerHTML = '';
            let html = '';
            libObj.books.forEach(book => {
                html += `<div class="book">
                            <img class="book__cover" src="${book.coverLink}" alt="${book.title}">
                            <p class="book__name">${book.title}</p>
                            <p class="book__author">${book.author}</p>
                            <p class="book__year">${book.year}</p>
                        </div>`;
            });
            shelf.innerHTML = html;
            console.log(`Книги (${libObj.books.length}) успешно получены с сервера!`);
        });
}

function addNewBook() {
    const bookName = document.querySelector('#name-input').value;
    const authorName = document.querySelector('#author-input').value;
    const coverLink = document.querySelector('#cover-input').value;
    const year = document.querySelector('#year-input').value;
    const ISBN = document.querySelector('#ISBN-input').value;

    fetch('/LibrarianServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            "title": bookName,
            "author": authorName,
            "coverLink": coverLink,
            "year": year,
            "ISBN": ISBN
        })
    }).then(() => {
        getBooksFromLibrary();

        document.querySelector('#name-input').value = '';
        document.querySelector('#author-input').value = '';
        document.querySelector('#cover-input').value = '';
        document.querySelector('#year-input').value = '';
        document.querySelector('#ISBN-input').value = '';
    })
}