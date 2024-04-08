<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/home.css" type="text/css">
        <title>Caça Níquel</title>
    </head>
    <body>
        <div class="players">
            <p>Players Online: <strong id="players-online">1</strong></p>
        </div>
        <div class="machine">
                <div class="content-wrapper">
                    <div class="content-spin">
                        <div class="content">😀</div>
                        <div class="content">😃</div>
                        <div class="content">😄</div>
                    </div>
                </div>

            <div class="bottom">
                <p class="valuesButton">Saldo: R$<strong id="amount">200.00</strong></p>
                <p class="valuesButton">Valor de aposta: R$<strong id="betValue">5.00</strong></p>
                <button type="button" class="button" onclick="bet()">Spin</button>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/scripts/home.js"></script>
    </body>
</html>