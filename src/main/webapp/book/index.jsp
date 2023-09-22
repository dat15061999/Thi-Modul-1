<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 9/18/2023
  Time: 9:38 AM
  To change this template use File | Settings | File Templates.7y
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/toastr@2.1.4/build/toastr.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/toastr@2.1.4/build/toastr.min.css" rel="stylesheet">
    <style>
        .productCheckbox {
            content: '';
            top: 0;
            left: 0;
            width: 24px;
            height: 30px;
            border: 2px solid #000;
            background-color: #FFF;
        }

        td {
            width: 55px;
            padding: 10px;
            vertical-align: middle;
            text-align: center;
        }

        /*.col-1{*/
        /*    flex: 0 0 auto;*/
        /*    width: 100px;*/
        /*    height: 45px;*/
        /*    margin-right: 10px;*/
        /*}*/
    </style>
</head>
<body>
<div class="container">
    <div class="card container px-6" style="height: 100vh">
        <h3 class="text-center">Management Book</h3>
        <c:if test="${message != null}">
            <h6 class="d-none" id="message">${message}</h6>
        </c:if>
        <div class="row">
            <a href="/book" class="btn btn-primary mb-2 col-1">Home</a>
            <a href="/book?action=create" class="btn btn-primary mb-2 col-1">Create</a>
            <a href="/book?action=showRestore" class="btn btn-primary mb-2 col-1">Restore</a>
            <p class="col-2"></p>
            <form action="/book?action=search" method="post" class="col-5 justify-content-center d-flex">
                <input type="search" class="form-control" name="search" placeholder="Search" id="searchInput"
                       onkeyup="searchStudents()">
                <button type="submit" class="btn btn-primary" style="text-align: right" name="">Search</button>
            </form>
            <p class="col-1"></p>
            <a class="btn btn-danger mb-2 col-1" style="float:right;" id="deleteSelectedBtn"
               href="/book?message=Deleted">
                Delete
            </a>

        </div>

        <table class="table table-striped">
            <tr class="table-dark">
                <td>
                    Id
                </td>
                <td>
                    Title
                </td>
                <td>
                    Price
                </td>
                <td>
                    Description
                </td>
                <td>
                    Publish Date
                </td>
                <td>
                    Category
                </td>
                <td>
                    Authors
                </td>
                <td>
                    Check
                </td>
                <td>
                    Action
                </td>
            </tr>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>
                            ${book.id}
                    </td>
                    <td>
                            ${book.title}
                    </td>
                    <td>
                        <fmt:formatNumber value="${book.price}" pattern="###,### đ"/>
                    </td>
                    <td>
                            ${book.description}
                    </td>
                    <td>
                            ${book.publishDate}
                    </td>
                    <td>
                            ${book.category.name}
                    </td>
                    <td>
                            ${book.getAuthors()}
                    </td>
                    <td>
                        <input type="checkbox" name="box" style="text-align: center" id="myCheckbox-${book.id}"
                               class="productCheckbox" data-product-id="${book.id}">
                    </td>
                    <td>
                        <a class="btn btn-danger" data-bs-toggle="modal"
                           data-bs-target="#exampleModal-${book.id}" onclick="return confirm('Do you want edit?')"
                           href="">
                            Edit
                        </a>
                    </td>
                </tr>
                <div class="modal fade" id="exampleModal-${book.id}" tabindex="-1"
                     aria-labelledby="exampleModalLabel-${book.id}" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">UPDATE BOOK</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="/book?action=update&id=${book.id}" method="post">
                                    <!-- Các trường sửa đổi sách -->
                                    <input type="hidden" name="id" value="${book.id}">
                                    <div class="mb-3">
                                        <label for="title" class="form-label">Name</label>
                                        <input class="form-control" type="text" name="title" id="title"
                                               value="${book.title}" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="price" class="form-label">Price</label>
                                        <input class="form-control" type="number" name="price" id="price"
                                               value="${book.price}" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="category" class="form-label">Category</label>
                                        <select class="form-control" name="category" id="category">
                                            <c:forEach var="ct" items="${categories}">
                                                <option
                                                        <c:if test="${book.category.name==ct.name}">selected</c:if>
                                                        value="${ct.id}">
                                                        ${ct.name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label for="description" class="form-label">Description</label>
                                        <textarea class="form-control" name="description" id="description"
                                                  required>${book.description}</textarea>
                                    </div>

                                    <!-- Thêm các trường còn thiếu từ "Create Book" -->
                                    <div class="mb-3">
                                        <label for="publishDate" class="form-label">Publish Date</label>
                                        <input class="form-control" type="date" name="publishDate" id="publishDate"
                                               value="${book.publishDate}">
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Author</label>
                                        <c:forEach var="author" items="${authors}">
                                            <div class="form-check">
                                                <c:set var="checkAuthor" value="0"/>
                                                <c:forEach var="bookAuthor" items="${book.getBookAuthors()}">
                                                    <c:if test="${bookAuthor.author.id == author.id}">
                                                        <c:set var="checkAuthor" value="1"/>
                                                    </c:if>
                                                </c:forEach>
                                                <input type="checkbox" class="form-check-input" name="author"
                                                       value="${author.id}" id="author-${author.id}"
                                                       <c:if test="${checkAuthor == '1'}">checked</c:if>
                                                >
                                                <label class="form-check-label"
                                                       for="author-${author.id}">${author.name}</label>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close
                                        </button>
                                        <button type="submit" class="btn btn-primary">Save changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </table>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script>
    const message = document.getElementById('message');
    if (message !== null && message.innerHTML) {
        toastr.success(message.innerHTML);
    }
    //xoa
    const deleteSelectedBtn = document.getElementById('deleteSelectedBtn');
    deleteSelectedBtn.addEventListener('click', () => {
        var selectedProductIds = [];

        // Lặp qua tất cả các ô checkbox sản phẩm
        const productCheckboxes = document.querySelectorAll('.productCheckbox');
        productCheckboxes.forEach(checkbox => {
            if (checkbox.checked) {
                selectedProductIds.push(checkbox.getAttribute('data-product-id'));
            }
        });

        if (selectedProductIds.length === 0) {
            alert('Please select at least one product to delete.');
        } else {
            if (confirm('Are you sure you want to delete the selected products?')) {
                // Sử dụng Ajax để gửi selectedProductIds lên máy chủ và xử lý xóa dữ liệu ở đây
                $.ajax({
                    url: '/book?action=delete&id=' + selectedProductIds.join(','),
                    method: 'GET'
                });
            }
        }
    });

    function toggleCheckboxes() {
        var checkboxes = document.querySelectorAll('.productCheckbox');

        // Lặp qua tất cả các ô checkbox và đảo ngược trạng thái của chúng
        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = !isChecked;
        }

        // Cập nhật trạng thái isChecked
        isChecked = !isChecked;
    }

    function searchStudents() {
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("searchInput");
        filter = input.value.toUpperCase();
        table = document.querySelector("table");
        tr = table.getElementsByTagName("tr");

        for (i = 1; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[1]; // Điều này dựa vào cột "NAME" của bảng
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
</script>
</body>
</html>