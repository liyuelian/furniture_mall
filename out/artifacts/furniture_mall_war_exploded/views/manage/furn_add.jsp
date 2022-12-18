<%@page contentType="text/html;charset=utf-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>家居网购</title>
    <base href="<%=request.getContextPath()+"/"%>">
    <!-- 移动端适配 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css">
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //增加数据类型校验
            //点击绑定事件
            $("input[type='submit']").click(function () {

                //1.验证家居名
                //获取用户输入的家居名
                var nameVal = $("input[name='name']").val();
                // 编写正则表达式进行验证
                // 验证家居名，必须由字母、数字、汉字组成,长度为1-15
                var namePattern = /^([\w]|[\u4e00-\u9fa5]){1,15}$/;
                if (!namePattern.test(nameVal)) {
                    //展示错误提示-属性过滤器
                    $("span[class='errorMsg']").text("家居名称格式错误，应由字母、数字、汉字组成,长度为1-15");
                    return false;//不提交，返回false
                }

                //2.验证商家名
                //获取用户输入的商家名
                var makerVal = $("input[name='maker']").val();
                // 编写正则表达式进行验证
                // 验证商家名，必须由字母、数字、汉字组成,长度为1-15
                var makerPattern = /^([\w]|[\u4e00-\u9fa5]){1,15}$/;
                if (!makerPattern.test(makerVal)) {
                    //展示错误提示-属性过滤器
                    $("span[class='errorMsg']").text("商家名称格式错误，应由字母、数字、汉字组成,长度为1-15");
                    return false;//不提交，返回false
                }

                //3.验证价格
                //获取用户输入的价格
                var priceVal = $("input[name='price']").val();
                // 编写正则表达式进行验证
                // 验证价格，非零开头的最多带两位小数的数字
                var pricePattern = /^([1-9][0-9]*)+(.[0-9]{1,2})?$/;
                if (!pricePattern.test(priceVal)) {
                    //展示错误提示-属性过滤器
                    $("span[class='errorMsg']").text("价格格式错误，应是非零开头的并最多带两位小数的正整数");
                    return false;//不提交，返回false
                }

                //4.验证销量
                //获取用户输入的销量
                var salesVal = $("input[name='sales']").val();
                // 编写正则表达式进行验证
                // 验证价格，零或非零开头的数字
                var salesPattern = /^(0|[1-9][0-9]*)$/;
                if (!salesPattern.test(salesVal)) {
                    //展示错误提示-属性过滤器
                    $("span[class='errorMsg']").text("销量格式错误，应是零或非零开头的数字");
                    return false;//不提交，返回false
                }

                //5.验证库存
                //获取用户输入的销量
                var stockVal = $("input[name='stock']").val();
                // 编写正则表达式进行验证
                // 验证价格，零或非零开头的数字
                var stockPattern = /^(0|[1-9][0-9]*)$/;
                if (!stockPattern.test(stockVal)) {
                    //展示错误提示-属性过滤器
                    $("span[class='errorMsg']").text("库存格式错误，应是零或非零开头的数字");
                    return false;//不提交，返回false
                }

                //如果以上验证都通过了，就提交表单
                return true;
            })
        })
    </script>
</head>

<body>
<!-- Header Area start  -->
<div class="header section">
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

                <!-- Header Action Start -->
                <div class="col align-self-center">
                    <div class="header-actions">

                        <!-- Single Wedge Start -->
                        <div class="header-bottom-set dropdown">
                            <a href="#">家居管理</a>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="#">订单管理</a>
                        </div>
                    </div>
                </div>
                <!-- Header Action End -->
            </div>
        </div>
    </div>
    <!-- Header Bottom  End -->
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.jsp"><img width="280px" src="assets/images/logo/logo.png" alt="Site Logo"/></a>
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
<!-- Cart Area Start -->
<div class="cart-main-area pt-100px pb-100px">
    <div class="container">
        <h3 class="cart-page-title">家居后台管理-添加家居</h3>
        <div class="row">
              <span class="errorMsg"
                    style="float: right; font-weight: bold; font-size: 15pt; color: red ">
                  <%--   数据校验错误提示--%>
                  ${requestScope.errInfo}
              </span>
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <form action="manage/furnServlet" method="post">
                    <%--增加隐藏域，action=add--%>
                    <input type="hidden" name="action" value="add">
                    <div class="table-content table-responsive cart-table-content">
                        <table>
                            <thead>
                            <tr>
                                <th>图片</th>
                                <th>家居名</th>
                                <th>商家</th>
                                <th>价格</th>
                                <th>销量</th>
                                <th>库存</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="product-thumbnail">
                                    <a href="#"><img class="img-responsive ml-3"
                                                     src="assets/images/product-image/default.jpg"
                                                     alt=""/></a>
                                </td>
                                <td class="product-name"><input name="name" style="width: 60%" type="text"
                                                                value=""/></td>
                                <td class="product-name"><input name="maker" style="width: 90%" type="text"
                                                                value=""/></td>
                                <td class="product-price-cart"><input name="price" style="width: 90%" type="text"
                                                                      value=""/></td>
                                <td class="product-quantity">
                                    <input name="sales" style="width: 90%" type="text" value=""/>
                                </td>
                                <td class="product-quantity">
                                    <input name="stock" style="width: 90%" type="text" value=""/>
                                </td>
                                <td>
                                    <!--                                    <a href="#"><i class="icon-pencil"></i></a>-->
                                    <!--                                    <a href="#"><i class="icon-close"></i></a>-->
                                    <input type="submit"
                                           style="width: 90%;background-color: silver;border: silver;border-radius: 20%;"
                                           value="添加家居"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Cart Area End -->

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
                                        <li class="li"><a class="single-link" href="login.html">登录</a></li>
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