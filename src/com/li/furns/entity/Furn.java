package com.li.furns.entity;

import java.math.BigDecimal;

/**
 * Furn Javabean 和表 furn进行映射
 *
 * @author 李
 * @version 1.0
 */
public class Furn {
    private Integer id; //id - 使用包装类，防止null
    private String name; //家居名
    private String maker; //厂商
    private BigDecimal price; //价格
    private Integer sales; //销量
    private Integer stock; //库存
    /**
     * 这里有一个细节，即实体类的属性名imgPath和对应表中的字段名img_path不一致
     * 解决方法是：在查询时，在对应的字段旁加上 别名
     */
    private String imgPath = "assets/images/product-image/default.jpg"; //家居图片路径

    public Furn() {
    }

    public Furn(Integer id, String name, String maker, BigDecimal price,
                Integer sales, Integer stock, String imgPath) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
        if (!(null == imgPath || "".equals(imgPath))) {
            //当参数imgPath的值不为空，或者不为空串时，才将其赋给当前对象的imgPath
            this.imgPath = imgPath;
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Furn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maker='" + maker + '\'' +
                ", price=" + price +
                ", sales=" + sales +
                ", stock=" + stock +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
