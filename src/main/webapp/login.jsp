<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">

<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>文献管理系统</title>
    <link rel="stylesheet" href="source/static/bootstrap/css/bootstrap.css" />
    <style type="text/css">
      body {
        padding-top: 80px;
        padding-bottom: 40px;
      }
      .login_header_container{
        border-bottom: 1px dashed #6FAF44;
        margin-bottom: 30px;
        padding-bottom: 20px;
        text-align: center;
      }
    </style>

  </head>
   
  <body>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner"></div>
    </div>

    <div class="container">
      <div class="login_header_container">
        <h2>欢迎来到文献管理系统</h2>  
      </div>
      
      <div class="row" id="div_login">
        <div class="col-xs-1"></div>
        <div style="background-color:#CCEEFF;" class="col-xs-5">
          <form class="form-signin" role="form" action="account/login" method="POST" name="login">
            <h2 class="form-signin-heading" style="text-align:center;">登录</h2>
            <br/>&nbsp;&nbsp;
            <span class="glyphicon glyphicon-user"></span>
            <input type="text" name="username" class="form-control" placeholder="用户名" required autofocus>
            <br/>&nbsp;&nbsp;
            <span class="glyphicon glyphicon-lock"></span>
            <input type="password" name="password" class="form-control" placeholder="密码" required>
            
            <label class="checkbox">
             &nbsp;<input type="checkbox" value="remember-me">记住密码
            </label>
            <br/><br/>
            <button class="btn btn-lg btn-success btn-block" type="submit"><span class="glyphicon glyphicon-black glyphicon-log-in"></span>&nbsp;&nbsp;登录</button>
          </form>
          <br/>
        </div>

        <div class="col-xs-4"><img src="source/image/background.jpg" /></div>
      </div> 
    </div>
  </body>
</html>