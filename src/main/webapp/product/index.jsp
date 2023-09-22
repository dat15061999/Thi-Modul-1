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
</head>
<body>
<div class="container">
  <div class="card container px-6" style="height: 100vh">
    <h3 class="text-center">Management Product</h3>
    <c:if test="${message != null}">
      <h6 class="d-none" id="message">${message}</h6>
    </c:if>
    <div>
      <a href="/product?action=create" class="btn btn-primary mb-2">Create</a>
      <a href="/product?action=showRestore" class="btn btn-primary mb-2">Restore</a>
    </div>

    <table class="table table-striped">
      <tr>
        <td>
          Id
        </td>
        <td>
          Name
        </td>
        <td>
          Price
        </td>
        <td>
          Description
        </td>
        <td>
          Category
        </td>
        <td>
          Action
        </td>
      </tr>
      <c:forEach var="product" items="${page.content}">
        <tr>
          <td>
              ${product.id}
          </td>
          <td>
              ${product.name}
          </td>
          <td>
              ${product.price}
          </td>
          <td>
              ${product.description}
          </td>
          <td>
              ${product.category.name}
          </td>
          <td>
            <a class="btn btn-danger" data-bs-toggle="modal"
               data-bs-target="#exampleModal-${product.id}" onclick="return confirm('Do you want edit?')"
               href="">
              Edit
            </a>
          </td>
        </tr>
        <div class="modal fade" id="exampleModal-${product.id}" tabindex="-1"
             aria-labelledby="exampleModalLabel-${product.id}" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title">UPDATE PRODUCT</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"
                        aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <div class="container">
                  <div class="card container px-6">
                    <h3 class="text-center">Create Product</h3>
                    <form action="/product?action=edit&id=${product.id}" method="post">
                      <div class="mb-3">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name" value="${product.name}">
                      </div>
                      <div class="mb-3">
                        <label for="price" class="form-label">Price</label>
                        <input type="number" class="form-control" name="price" id="price" value="${product.price}">
                      </div>
                      <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <input type="text" class="form-control" name="description" id="description" value="${product.description}">
                      </div>
                      <div class="mb-3">
                        <label for="category" class="form-label">Category</label>
                        <select class="form-control" name="category" id="category">
                          <c:forEach var="category" items="${categories}">
                            <option value="${category.id}">${category.name} <c:if test="${product.category.id = category.id}">selected</c:if></option>
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
      </c:forEach>
    </table>
    <nav aria-label="...">
      <ul class="pagination">
        <li class="page-item <c:if test="${page.currentPage == 1}">disabled</c:if>">
          <a class="page-link"  href="/product?page=${page.currentPage - 1}" tabindex="-1" aria-disabled="true">Previous</a>
        </li>
        <c:forEach var="number" begin="1" end="${page.totalPage}">
          <c:if test="${number == page.currentPage}">
            <li class="page-item active" aria-current="page">
              <a class="page-link" href="/product?page=${number}">${number}</a>
            </li>
          </c:if>
          <c:if test="${number != page.currentPage}">
            <li class="page-item">
              <a class="page-link" href="/product?page=${number}">${number}</a>
            </li>
          </c:if>
        </c:forEach>
        <li class="page-item <c:if test="${page.currentPage == page.totalPage}">disabled</c:if>">
          <a class="page-link" href="/product?page=${page.currentPage + 1}">Next</a>
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
</script>
</body>
</html>