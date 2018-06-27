<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <#list  studentList as student>
        <tr>
            <#--索引从零开始-->
            <td>${student_index}</td>
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.age}</td>

        </tr>
    </#list>
</body>
</html>