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
    td{
      width: 55px;
      padding: 10px;
      vertical-align: middle;
      text-align: center;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="card container px-6" style="height: 100vh">
    <h3 class="text-center">Restore Book</h3>
    <c:if test="${message != null}">
      <h6 class="d-none" id="message">${message}</h6>
    </c:if>
    <div>
      <a href="/book" class="btn btn-primary mb-2">Home</a>
      <a href="/book?action=showRestore&message=Restored" class="btn btn-primary mb-2" id="restoreSelectedBtn">Restore</a>
      <a class="btn btn-danger mb-2"  style="float:right" onclick="return confirm('Do you want to delete All?')" id="deleteAllSelectedBtn"
         href="">
        Delete All
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
            <fmt:formatNumber value="${book.price}" pattern="###,### đ" />
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
        </tr>

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
  //restore
  const deleteSelectedBtn = document.getElementById('restoreSelectedBtn');
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
      alert('Please select at least one book to restore.');
    } else {
      if (confirm('Are you sure you want to restore the selected books?')) {
        // Sử dụng Ajax để gửi selectedProductIds lên máy chủ và xử lý xóa dữ liệu ở đây
        $.ajax({
          url: '/book?action=restore&id=' + selectedProductIds.join(','),
          method: 'GET'
        });
      }
    }
  });
  //restore
  const deleteSelectedBtn2 = document.getElementById('deleteAllSelectedBtn');
  deleteSelectedBtn2.addEventListener('click', () => {
    var selectedProductIds2 = [];

    // Lặp qua tất cả các ô checkbox sản phẩm
    const productCheckboxes2 = document.querySelectorAll('.productCheckbox');
    productCheckboxes2.forEach(checkbox => {
      if (checkbox.checked) {
        selectedProductIds2.push(checkbox.getAttribute('data-product-id'));
      }
    });

    if (selectedProductIds2.length === 0) {
      alert('Please select at least one book to delete all.');
    } else {
      if (confirm('Are you sure you want to delete all the selected books?')) {
        // Sử dụng Ajax để gửi selectedProductIds lên máy chủ và xử lý xóa dữ liệu ở đây
        $.ajax({
          url: '/book?action=deleteAll&id=' + selectedProductIds2.join(','),
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
</script>
</body>
</html>