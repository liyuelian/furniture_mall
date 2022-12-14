<%@page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>家居网购</title>
    <!--为了确定页面的固定资源，使用一个固定的参考路径，后面再优化-->
    <%--优化：jsp页面表达式--%>
    <base href="<%=request.getContextPath()+"/"%>">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css"/>
    <!--这里先使用相对路径，后面再修改，使用base-->
    <!--引入jquery-->
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function () {//页面加载完毕后执行的function

            //给注册模块的用户名输入框绑定一个失去焦点事件
            $("#username").blur(function () {
                //获取输入的用户名
                var username = this.value;
                //发出ajax请求（使用jquery的$.getJSON()）
                //jQuery.getJSON(url,data,success(data,status,xhr))
                $.getJSON(
                    "memberServlet",
                    //使用json格式发送数据
                    {
                        "action": "isExistUserName",
                        "username": username,
                    },
                    function (data) {
                        if (data.isExist) {
                            $("span.errorMsg").text("用户名已经存在，不能使用");
                        } else {
                            $("span.errorMsg").text("用户名可用");
                        }
                    })
            })


            //直接在页面的structure结构中找到对应的注册接口==>id="sub-btn"
            //ctrl+home 定位到页面最上方
            //ctrl+end 定位到页面最下方

            //模拟一个点击事件，选中注册
            //决定是显示登录还是注册tab
            //如果注册失败，显示注册tab，而不是默认的登录tab
            if ("${requestScope.active}" == "register") {
                $("#register_tab")[0].click();//模拟点击，让页面跳转到注册表单
            }//如果不是，会默认跳转到登录tab

            //点击绑定事件
            $("#sub-btn").click(function () {

                //获取用户输入的用户名==>自己看前端给的页面
                var usernameVal = $("#username").val();
                // alert(usernameVal)
                // 编写正则表达式进行验证
                //1. 验证用户名，必须由字母、数字、下划线组成，并且长度为6到10位
                var usernamePattern = /^[\w_]{6,10}$/;
                if (!usernamePattern.test(usernameVal)) {
                    //展示错误提示-属性过滤器
                    $("span[class='errorMsg']").text("用户名格式不对，必须由字母、数字、下划线组成，并且长度为6到10位");
                    return false;//不提交，返回false
                }

                // 2. 验证密码，必须由字母、数字、下划线组成，并且长度为6到10位
                var passwordVal = $("#password").val();
                // alert(password)
                var passwordPattern = /^[\w_]{6,10}$/;
                if (!passwordPattern.test(passwordVal)) {
                    //展示错误提示-基本过滤器
                    $("span.errorMsg").text("密码格式不对，必须由字母、数字、下划线组成，并且长度为6到10位");
                    return false;//不提交，返回false
                }

                //3.两次密码要相同
                //得到第二次输入的密码
                var repwdVal = $("#repwd").val();
                if (repwdVal != passwordVal) {
                    //展示错误提示
                    $("span.errorMsg").text("输入的两次密码不相同");
                    return false;//不提交，返回false
                }

                // 4. 邮箱格式验证：常规验证即可
                var emailVal = $("#email").val();
                //在java中转义符为两个\\，在js中转义符为一个\
                var emailPattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
                if (!emailPattern.test(emailVal)) {
                    //展示错误提示
                    $("span.errorMsg").text("邮箱格式不正确");
                    return false;//不提交，返回false
                }

                //5.验证码不能为空
                var codeText = $("#code").val();
                //去掉验证码前后空格
                var codeText = $.trim(codeText);
                if (codeText == null || codeText == "") {
                    //提示
                    $("span.errorMsg").text("验证码不能为空");
                    return false;
                }

                //如果上面的信息格式都正确，就可以提交表单信息了
                //因为还没有写后台代码，这里暂时使用提示信息表示
                $("span.errorMsg").text("验证通过...");
                //目前我们写了后台，当用户验证通过时，返回true
                return true;
            })

            //给验证码图片绑定单击事件,可以获取新的验证码
            $("#codeImg").click(function () {
                //在url没有变化的时候，图片不会发出新的请求（因为图片已经被缓存了）
                //为了防止不刷新，可以携带一个变化的参数
                this.src = "<%=request.getContextPath()%>/kaptchaServlet?d=" + new Date();
            })
        })
    </script>
</head>

<body>
<!-- Header Area start  -->
<div class="header section">
    <!-- Header Top Message Start -->
    <!-- Header Top  End -->
    <!-- Header Bottom  Start -->
    <div class="header-bottom d-none d-lg-block">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.html"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->

            </div>
        </div>
    </div>
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.html"><img width="280px" src="assets/images/logo/logo.png"
                                                  alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->
            </div>
        </div>
    </div>
    <!-- Main Menu Start -->
    <div style="width: 100%;height: 50px;background-color: black"></div>
    <!-- Main Menu End -->
</div>
<!-- Header Area End  -->
<!-- login area start -->
<div class="login-register-area pt-70px pb-100px">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 col-md-12 ml-auto mr-auto">
                <div class="login-register-wrapper">
                    <div class="login-register-tab-list nav">
                        <a class="active" data-bs-toggle="tab" href="#lg1">
                            <h4>会员登录</h4>
                        </a>
                        <a id="register_tab" data-bs-toggle="tab" href="#lg2">
                            <h4>会员注册</h4>
                        </a>
                    </div>
                    <div class="tab-content">
                        <div id="lg1" class="tab-pane active">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <%--提示错误信息--%>
                                    <span class="errorMsg"
                                          style="float: right; font-weight: bold; font-size: 20pt; margin-left: 10px;">
                                        ${requestScope.errInfo}
                                    </span>
                                    <form action="memberServlet" method="post">
                                        <%--增加隐藏域，表示login请求--%>
                                        <input type="hidden" name="action" value="login"/>
                                        <input type="text" name="username" placeholder="Username"
                                               value="${requestScope.username}"/>
                                        <input type="password" name="password" placeholder="Password"/>
                                        <div class="button-box">
                                            <div class="login-toggle-btn">
                                                <input type="checkbox"/>
                                                <a class="flote-none" href="javascript:void(0)">Remember me</a>
                                                <a href="#">Forgot Password?</a>
                                            </div>
                                            <button type="submit"><span>Login</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div id="lg2" class="tab-pane">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <span class="errorMsg"
                                          style="float: right; font-weight: bold; font-size: 20pt; margin-left: 10px;">
                                        ${requestScope.errInfo}
                                    </span>
                                    <!--注册表单-->
                                    <form action="memberServlet" method="post">
                                        <%--增加隐藏域，表示register请求--%>
                                        <input type="hidden" name="action" value="register"/>
                                        <input type="text" id="username" name="username"
                                               value="${requestScope.username}" placeholder="Username"/>
                                        <input type="password" id="password" name="password" placeholder="输入密码"/>
                                        <input type="password" id="repwd" name="repassword" placeholder="确认密码"/>
                                        <input name="email" id="email" placeholder="电子邮件" value="${requestScope.email}"
                                               type="email"/>
                                        <input type="text" name="code" style="width: 50%" id="code" placeholder="验证码"/>
                                        <img id="codeImg" alt="" src="kaptchaServlet" style="width: 120px;height: 50px">
                                        <div class="button-box">
                                            <button type="submit" id="sub-btn"><span>会员注册</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- login area end -->

<!-- Footer Area Start -->
<div class="footer-area">
    <div class="footer-container">
        <div class="footer-top">
            <div class="container">
                <div class="row">
                    <!-- Start single blog -->
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-sm-6 col-lg-3 mb-md-30px mb-lm-30px" data-aos="fade-up"
                         data-aos-delay="400">
                        <div class="single-wedge">
                            <h4 class="footer-herading">信息</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="about.html">关于我们</a></li>
                                        <li class="li"><a class="single-link" href="#">交货信息</a></li>
                                        <li class="li"><a class="single-link" href="privacy-policy.html">隐私与政策</a></li>
                                        <li class="li"><a class="single-link" href="#">条款和条件</a></li>
                                        <li class="li"><a class="single-link" href="#">制造</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-2 col-sm-6 mb-lm-30px" data-aos="fade-up" data-aos-delay="600">
                        <div class="single-wedge">
                            <h4 class="footer-herading">我的账号</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="my-account.html">我的账号</a>
                                        </li>
                                        <li class="li"><a class="single-link" href="cart.html">我的购物车</a></li>
                                        <li class="li"><a class="single-link" href="login.jsp">登录</a></li>
                                        <li class="li"><a class="single-link" href="wishlist.html">感兴趣的</a></li>
                                        <li class="li"><a class="single-link" href="checkout.html">结账</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-3" data-aos="fade-up" data-aos-delay="800">

                    </div>
                    <!-- End single blog -->
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <div class="row flex-sm-row-reverse">
                    <div class="col-md-6 text-right">
                        <div class="payment-link">
                            <img src="#" alt="">
                        </div>
                    </div>
                    <div class="col-md-6 text-left">
                        <p class="copy-text">Copyright &copy; 2021 韩顺平教育~</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer Area End -->
<script src="assets/js/vendor/vendor.min.js"></script>
<script src="assets/js/plugins/plugins.min.js"></script>
<!-- Main Js -->
<script src="assets/js/main.js"></script>
</body>
</html>