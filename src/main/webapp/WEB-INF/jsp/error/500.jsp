<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Server Error - Health Cycle</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            text-align: center;
        }
        .error-container {
            margin-top: 100px;
        }
        h1 {
            font-size: 36px;
            color: #e74c3c;
        }
        .error-code {
            font-size: 120px;
            margin: 0;
            color: #e74c3c;
        }
        .error-message {
            font-size: 24px;
            margin-bottom: 30px;
        }
        .home-link {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }
        .home-link:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-container">
            <h1 class="error-code">500</h1>
            <p class="error-message">Oops! Something went wrong on our end.</p>
            <p>We're experiencing some technical difficulties. Please try again later or contact support if the problem persists.</p>
            <a href="/" class="home-link">Go to Homepage</a>
        </div>
    </div>
</body>
</html>