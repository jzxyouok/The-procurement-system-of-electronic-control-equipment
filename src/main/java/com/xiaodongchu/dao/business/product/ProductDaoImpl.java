package com.xiaodongchu.dao.business.product;

import com.xiaodongchu.dao.JdbcDaoSupportAbstract;
import com.xiaodongchu.entity.business.Product;
import com.xiaodongchu.vo.page.Page;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/10.
 */
@Repository
public class ProductDaoImpl extends JdbcDaoSupportAbstract implements ProductDao {
    @Override
    public List<Product> pageByExample(Product productExample, Page page) {
        StringBuilder sql = new StringBuilder("SELECT bp.id, bp.product_create_time, bp.product_status, bp.product_title, bp.product_content," +
                " bp.product_avatar, bp.product_price, bp.product_stock, bp.product_sales, bp.product_brand,bp.product_model,bp.product_public_time," +
                "bp.product_weight,bp.product_working_temperature" +
                " FROM b_product bp");
        StringBuilder orderSQL = new StringBuilder(" ORDER BY bp.product_create_time DESC");
        List<Object> params = new LinkedList<>();
        if(page != null) {
            setPageParams(page, sql.toString(), orderSQL, params);
        }
        return getJdbcTemplate().query(sql.append(orderSQL).toString(), params.toArray(), BeanPropertyRowMapper.newInstance(Product.class));
    }

    @Override
    public Integer insert(Product product) {
        String sql = "INSERT INTO b_product (product_create_time, product_status, product_title, product_content, product_avatar, product_price, product_stock, product_brand,product_model,product_public_time,product_weight,product_working_temperature) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        return getJdbcTemplate().update(sql, new Object[]{new Date(), product.getProductStatus(), product.getProductTitle(), product.getProductContent(), product.getProductAvatar(), product.getProductPrice(), product.getProductStock(), product.getProductBrand(), product.getProductModel(), product.getProductPublicTime(), product.getProductWeight(), product.getProductWorkingTemperature()});
    }

    @Override
    public Integer update(Product product) {
        String sql = "UPDATE b_product SET product_status = ?, product_title = ?, product_content = ?, product_avatar = ?, product_price = ?,product_stock=?,product_brand=?,product_model=?,product_public_time=?,product_weight=?,product_working_temperature=? WHERE id = ?";
        return getJdbcTemplate().update(sql, new Object[]{product.getProductStatus(), product.getProductTitle(), product.getProductContent(), product.getProductAvatar(), product.getProductPrice(),product.getProductStock(), product.getProductBrand(), product.getProductModel(), product.getProductPublicTime(), product.getProductWeight(), product.getProductWorkingTemperature(), product.getId()});
    }

    @Override
    public Integer delete(Product product) {
        String sql = "DELETE FROM b_product WHERE id = ?";
        return getJdbcTemplate().update(sql, new Object[]{product.getId()});
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT bp.id, bp.product_create_time, bp.product_status, bp.product_title, bp.product_content, bp.product_avatar, bp.product_price, bp.product_stock, bp.product_sales, bp.product_brand,bp.product_model,bp.product_public_time,bp.product_weight,bp.product_working_temperature FROM b_product bp WHERE bp.id = ?";
        return getJdbcTemplate().queryForObject(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(Product.class));
    }
}
