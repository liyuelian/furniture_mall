package com.li.furns.web;

import com.li.furns.entity.Furn;
import com.li.furns.entity.Page;
import com.li.furns.service.FurnService;
import com.li.furns.service.impl.FurnServiceImpl;
import com.li.furns.utils.DataUtils;
import com.li.furns.utils.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class FurnServlet extends BasicServlet {

    private FurnService furnService = new FurnServiceImpl();

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Furn> furns = furnService.queryFurns();
        //将furns集合放入到request域中
        req.setAttribute("furns", furns);
        //请求转发
        req.getRequestDispatcher("/views/manage/furn_manage.jsp")
                .forward(req, resp);
    }

    /**
     * 处理添加家居的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //获取家居信息
//        String name = req.getParameter("name");
//        String maker = req.getParameter("maker");
//        String price = req.getParameter("price");
//        String sales = req.getParameter("sales");
//        String stock = req.getParameter("stock");
//        //图片暂且使用默认值，后面再修改
//        String defaultImg = "assets/images/product-image/default.jpg";
//
//        //数据格式校验
//        //方案一 使用java的正则表达式验证数据格式，
//        // 如果没有通过校验，就直接返回furn_add.jsp，并且在request域中提示错误信息，在前端jsp页面中展示
//
//        //方案二：直接将数据进行转换,
//        // 我们可以直接在构建Furn对象的地方捕获异常。这样，无论是哪一个数据格式出现错误都会被捕获异常。
//
//        //构建Furn对象
//        Furn furn = null;
//        try {
//            furn = new Furn(null, name, maker, new BigDecimal(price),
//                    new Integer(sales), new Integer(stock), defaultImg);
//        } catch (NumberFormatException e) {
//            req.setAttribute("errInfo", "添加数据格式不对");
//            //跳转到添加页面
//            req.getRequestDispatcher("/views/manage/furn_add.jsp").forward(req, resp);
//            return;
//        }

        //底层使用BeanUtils，完成自动封装JavaBean
        Furn furn = DataUtils.copyParamToBean(req.getParameterMap(), new Furn());

        int addRes = furnService.addFurn(furn);
        if (addRes != -1) {//插入成功,跳转到页面furn_manage.jsp
            //跳转之前，需要重新获取Furn列表，然后将furns集合放入到request域中，再请求转发
            //即需要重新走一下FurnServlet的list方法
            //req.getRequestDispatcher("/manage/furnServlet?action=list")
            // .forward(req, resp);

            //这里如果使用请求转发，当用户刷新页面时，会重新发出一次添加请求，造成数据重复提交
            //因此使用重定向
            //因为重定向实际是让浏览器重新发送请求，所以我们回送完整的url比较保险
            //resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
            resp.sendRedirect(req.getContextPath() +
                    "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
        } else {
            resp.getWriter().write("添加失败");
        }
    }

    /**
     * 处理删除家居的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求删除的家居id
        String id = req.getParameter("id");

        //防止接收的id不是一个数字型的字符串
        furnService.deleteFurnById(DataUtils.parseInt(id, 0));
        //重定向到家居列表页-该地址由浏览器解析
        //resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
        resp.sendRedirect(req.getContextPath() +
                "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    /**
     * 处理显示单个家居信息的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求显示的家居id
        String id = req.getParameter("id");
        //在数据库中查询，得到furn对象
        Furn furn = furnService.queryFurnById(DataUtils.parseInt(id, 0));
        //将furn放入到request域中
        req.setAttribute("furn", furn);

        //如果请求带来的参数，是请求转发到下一个页面，在下一个页面可以使用param.pageNo获取

        //请求转发到furn_update.jsp中，在该页中显示furn信息
        //这里使用请求转发是因为如果使用重定向，当刷新页面之后就没有了request域中的信息
        req.getRequestDispatcher("/views/manage/furn_update.jsp")
                .forward(req, resp);
    }

    /**
     * 处理修改家居的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如果表单为 enctype="multipart/form-data"
        //那么使用request.getParameter("attrName")的方式将不能直接从表单中得到attribute
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到对应的furn对象[从数据库中获取]
        Furn furn = furnService.queryFurnById(id);
        if (furn == null) {
            return;
        }

        //1.先判断是不是文件表单(enctype="multipart/form-data")
        if (ServletFileUpload.isMultipartContent(req)) {
            //2.创建DiskFileItemFactory对象，用于构建一个解析上传数据的工具对象
            DiskFileItemFactory diskFileItemFactory =
                    new DiskFileItemFactory();
            //3.创建一个解析上传数据的工具对象
            /**
             *      前端表单提交的就是input元素
             *      <input type="file" name="pic" id="" value="" onchange="prev(this)"/>
             *     家居名: <input type="text" name="name"><br/>
             *     <input type="submit" value="上传"/>
             */
            ServletFileUpload servletFileUpload =
                    new ServletFileUpload(diskFileItemFactory);
            //解决接收到的文件名是中文乱码问题
            servletFileUpload.setHeaderEncoding("utf-8");
            //4.关键地方
            // servletFileUpload对象可以把表单提交的数据text或文件
            // 将其封装到 FileItem文件项中
            try {
                List<FileItem> list =
                        servletFileUpload.parseRequest(req);
                //遍历并分别处理
                for (FileItem fileItem : list) {
                    //判断是不是一个文件
                    /**
                     * isFormField()方法用于
                     * 判断FileItem类对象封装的数据是一个普通文本表单字段，还是一个文件表单字段，
                     * 如果是普通表单字段则返回true，否则返回false
                     */
                    if (fileItem.isFormField()) {//为true，说明普通表单项
                        if ("name".equals(fileItem.getFieldName())) {//家居名
                            furn.setName(fileItem.getString("utf-8"));
                        } else if ("maker".equals(fileItem.getFieldName())) {//制造商
                            furn.setMaker(fileItem.getString("utf-8"));
                        } else if ("price".equals(fileItem.getFieldName())) {//价格
                            furn.setPrice(new BigDecimal(fileItem.getString()));
                        } else if ("sales".equals(fileItem.getFieldName())) {//销量
                            furn.setSales(new Integer(fileItem.getString()));
                        } else if ("stock".equals(fileItem.getFieldName())) {//库存
                            furn.setStock(new Integer(fileItem.getString()));
                        }
                    } else {//说明是一个文件表单项
                        //获取上传的文件的名字
                        String name = fileItem.getName();
                        //如果用户没有选择新的图片，那么获取的文件名将会是空串
                        if (!"".equals(name)) {//如果获取的文件名不为空串
                            //把上传到服务器temp目录下的文件保存到指定目录
                            //1.指定一个目录,比如我们网站的工作目录下
                            String filePath = "/" + WebUtils.FURN_IMG_DIRECTORY;
                            //但是一般来说，工作目录是不确定的，所以我们动态获取
                            //2.获取完整目录/路径
                            String fileRealPath =
                                    req.getServletContext().getRealPath(filePath);
                            //下面的目录是和根据你web项目运行环境改变而改变的（动态的）
                            //fileRealPath=
                            // D:\IDEA-workspace\furniture_mall\out\artifacts\
                            // furniture_mall_war_exploded\assets\images\product-image

                            //3.创建上传的文件的目录
                            //  写一个工具类，可以返回一个日期，如2024/11/11,
                            //  这样可以将不同日期上传的文件放到不同目录下，
                            //  防止一个文件夹存放的文件过多造成访问速度变慢
                            File fileRealPathDirectory =
                                    new File(fileRealPath);

                            if (!fileRealPathDirectory.exists()) {//如果文件目录不存在
                                fileRealPathDirectory.mkdirs();//创建
                            }
                            //4.上传到服务器temp目录下的文件拷贝到上述创建的目录下
                            //构建文件上传的完整路径：目录+文件名
                            //防止出现文件覆盖问题，把获取到的用户上传文件名加一个前缀，保证文件名唯一即可
                            //如果担心在高并发的情况下会出现UUID相同，可以在UUID后再加上一个系统当前毫秒数
                            name = UUID.randomUUID().toString() + "_" + name;
                            String fileFullPath = fileRealPathDirectory + "/" + name;
                            fileItem.write(new File(fileFullPath));//保存

                            fileItem.getOutputStream().close();//关闭流

                            //更新家居的文件图片路径
                            furn.setImgPath(WebUtils.FURN_IMG_DIRECTORY + "/" + name);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //更新furn对象
        furnService.updateFurn(furn);
        //可以请求转发到更新成功的页面
        req.getRequestDispatcher("/views/manage/update_ok.jsp").forward(req, resp);
    }


//    /**
//     * 处理修改家居的请求
//     *
//     * @param req
//     * @param resp
//     * @throws ServletException
//     * @throws IOException
//     */
//    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //自动填充Javabean-将提交修改的家居信息封装成Furn对象
//        Furn furn = DataUtils.copyParamToBean(req.getParameterMap(), new Furn());
//        //调用updateFurn，更改数据
//        furnService.updateFurn(furn);
//        ////修改成功后重定向，显示列表家居
//        //resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
//
//        //这里我们考虑分页，并带上pageNo
//        resp.sendRedirect(req.getContextPath() +
//                "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
//    }

    /**
     * 处理分页请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //调用service方法，获取page对象
        Page<Furn> page = furnService.page(pageNo, pageSize);
        //将page对象放入request域中
        req.setAttribute("page", page);
        //请求转发到furn_manage.jsp
        req.getRequestDispatcher("/views/manage/furn_manage.jsp")
                .forward(req, resp);
    }
}
