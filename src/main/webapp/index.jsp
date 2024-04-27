<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png">
    <link rel="stylesheet" href="index.css">
    <title>LibWay</title>
</head>
<body>
<div class="header">
    <div class="container">
        <div class="header__inner">
            <div class="site-logo">
                <img class="site-logo__img" src="https://static.thenounproject.com/png/751623-200.png">
                <p class="site-logo__name">Lib<span class="site-text-span">Way</span></p>
            </div>
            <!--            <p class="header__title">Простая и быстрая онлайн-библиотека</p>-->
        </div>
    </div>
</div>
<div class="add-book">
    <div class="container">
        <div class="add-book__inner">
            <p class="site-block-title">— Добавьте свою книгу —</p>
            <div class="add-book__inputs">
                <input id="name-input" class="add-book__input" type="text" placeholder="Название книги">
                <input id="author-input" class="add-book__input" type="text" placeholder="Автор книги">
                <input id="cover-input" class="add-book__input" type="text" placeholder="Ссылка на обложку книги">
                <input id="year-input" class="add-book__input" type="text" placeholder="Год выхода книги">
                <input id="ISBN-input" class="add-book__input" type="text" placeholder="ISBN книги">
            </div>
            <button class="add-book__button" onclick="addNewBook()">Добавить</button>
        </div>
    </div>
</div>
<div class="shelf">
    <div class="container">
        <div class="shelf__inner"></div>
    </div>
</div>
<script src="librarian.js"></script>
</body>
</html>