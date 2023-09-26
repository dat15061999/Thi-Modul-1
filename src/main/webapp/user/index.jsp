<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 9/18/2023
  Time: 9:38 AM
  To change this template use File | Settings | File Templates.
--%>
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
        td {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="card container px-6" style="height: 100vh">
        <h3 class="text-center">Management User</h3>
        <c:if test="${message != null}">
            <h6 class="d-none" id="message">${message}</h6>
        </c:if>
        <c:if test="${!isShowRestore}">
            <div class="row">
                <a href="/user?action=create" class="btn btn-primary mb-2 mr-1 col-1">Create</a>
                <a href="/user?action=restore" class="btn btn-primary mb-2 ml-1 col-1">Restore</a>
                <form action="/user?page=${page.currentPage}" class="d-flex justify-content-center">
                    <input type="text" id="searchBook" value="${search}" name="search" class="col-8"
                           placeholder="Search User Title">
                    <button id="searchButton" class="btn btn-primary">Search</button>
                    <p class="col-1"></p>
                </form>
            </div>
        </c:if>
        <c:if test="${isShowRestore}">
        <form action="/user?action=restore" method="post">
            <div>
                <a href="/user" class="btn btn-primary mb-2">Home</a>
                <button type="submit" class="btn btn-primary mb-2">Restore All</button>
            </div>
            </c:if>

            <table class="table table-striped">
                <tr>
                    <td>
                        Id
                    </td>
                    <td>
                        Last Name
                    </td>
                    <td>
                        First Name
                    </td>
                    <td>
                        User Name
                    </td>
                    <td>
                        Email
                    </td>
                    <td>
                        DOB
                    </td>
                    <td>
                        Role
                    </td>
                    <td>
                        Gender
                    </td>
                    <td id="selectAllCheckbox">
                        Action
                    </td>
                </tr>
                <c:forEach var="user" items="${page.content}">
                    <tr>
                        <td>
                                ${user.id}
                        </td>
                        <td>
                                ${user.lastname}
                        </td>
                        <td>
                                ${user.firstname}
                        </td>
                        <td>
                                ${user.username}
                        </td>
                        <td>
                                ${user.email}
                        </td>
                        <td>
                                ${user.dob}
                        </td>
                        <td>
                                ${user.getRoles()}
                        </td>
                        <td>
                                ${user.gender}
                        </td>

                        <td>
                            <c:if test="${!isShowRestore}">
                                <a class="btn btn-danger" data-bs-toggle="modal"
                                   data-bs-target="#exampleModal-${user.id}"
                                   onclick="return confirm('Do you want edit?')"
                                   href="">
                                    Edit
                                </a>
                                <a class="btn btn-danger" onclick="return confirm('Do you want remove?')"
                                   href="/user?action=delete&id=${user.id}">
                                    Delete
                                </a>
                            </c:if>
                            <c:if test="${isShowRestore}">
                                <input type="checkbox" name="restore" value="${user.id}"
                                       class="form-check-input checkbox"/>
                            </c:if>
                        </td>
                    </tr>
                    <c:if test="${!isShowRestore}">
                        <div class="modal fade" id="exampleModal-${user.id}" tabindex="-1"
                             aria-labelledby="exampleModalLabel-${user.id}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">UPDATE USER</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="container">
                                            <div class="card container px-6">
                                                <h3 class="text-center">Create Product</h3>
                                                <form action="/user?action=edit&id=${user.id}" method="post">
                                                    <div class="mb-3">
                                                        <label for="firstname" class="form-label">First Name</label>
                                                        <input type="text" class="form-control" id="firstname"
                                                               name="firstname" value="${user.firstname}" required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="lastname" class="form-label">Last Name</label>
                                                        <input type="text" class="form-control" id="lastname"
                                                               name="lastname" value="${user.lastname}" required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="username" class="form-label">User Name</label>
                                                        <input type="text" class="form-control" id="username"
                                                               name="username" value="${user.username}" required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="email" class="form-label">Email</label>
                                                        <input type="email" class="form-control" name="email" id="email"
                                                               value="${user.email}" required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="dob" class="form-label">Dob</label>
                                                        <input type="date" class="form-control" name="dob" id="dob"
                                                               value="${user.dob}">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="form-label">Role</label>
                                                        <c:forEach var="role" items="${roles}">
                                                            <div class="form-check">
                                                                <c:set var="checkRole" value="0"/>
                                                                <c:forEach var="userRole" items="${user.userRole}">
                                                                    <c:if test="${userRole.role.name.equals(role.name)}">
                                                                        <c:set var="checkRole" value="1"/>
                                                                    </c:if>
                                                                </c:forEach>
                                                                <input type="checkbox" class="form-check-input" name="role" value="${role.id}" id="role-${role.id}"
                                                                       <c:if test="${checkRole == '1'}">checked</c:if>
                                                                >
                                                                <label class="form-check-label" for="role-${role.id}">${role.name}</label>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="gender" class="form-label">Gender</label>
                                                        <select class="form-control" name="gender" id="gender">
                                                            <c:forEach var="gender" items="${genders}">
                                                                <option value="${gender}"
                                                                        <c:if test="${ gender == user.gender}">selected</c:if>>${gender}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>


                                                    <button type="submit" class="btn btn-primary">Submit</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </table>
            <c:if test="${isShowRestore}">
        </form>
        </c:if>
        <nav aria-label="...">
            <c:set var="url" value="/user?page="/>
            <c:if test="${isShowRestore}">
                <c:set var="url" value="/user?action=restore&page="/>
            </c:if>
            <ul class="pagination">
                <li class="page-item <c:if test="${page.currentPage == 1}">disabled</c:if>">
                    <a class="page-link" href="/user?page=${page.currentPage - 1}" tabindex="-1"
                       aria-disabled="true">Previous</a>
                </li>
                <c:forEach var="number" begin="1" end="${page.totalPage}">
                    <c:if test="${number == page.currentPage}">
                        <li class="page-item active" aria-current="page">
                            <a class="page-link" href="${url}${number}">${number}</a>
                        </li>
                    </c:if>
                    <c:if test="${number != page.currentPage}">
                        <li class="page-item">
                            <a class="page-link" href="${url}${number}">${number}</a>
                        </li>
                    </c:if>
                </c:forEach>
                <li class="page-item <c:if test="${page.currentPage == page.totalPage}">disabled</c:if>">
                    <a class="page-link" href="${url}${(page.currentPage + 1)}">Next</a>
                </li>
            </ul>
        </nav>
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

    const selectAllCheckbox = document.getElementById('selectAllCheckbox');
    const checkboxes = document.getElementsByClassName('checkbox');
    let checked = true;
    selectAllCheckbox.addEventListener('click', function () {
        Array.from(checkboxes).forEach(function (checkbox) {
            checkbox.checked = checked;

        });
        checked = !checked;
    });
</script>
</body>
</html>