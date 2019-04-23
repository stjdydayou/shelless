package com.axungu.common;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/23 16:10
 */
public abstract class AbstractEnumTypeHandler<T> extends BaseTypeHandler<T> {


    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        int i = rs.getInt(columnName);

        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的code值，定位CardLogStatus子类
            return codeToEnum(i);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的code值，定位CardLogStatus子类
            return codeToEnum(i);
        }
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的code值，定位CardLogStatus子类
            return codeToEnum(i);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        // baseTypeHandler已经帮我们做了parameter的null判断
        ps.setInt(i, enumTocode(parameter));
    }

    /**
     * 枚举类型转换
     *
     * @param code 数据库中存储的自定义code属性
     * @return code对应的枚举类
     */
    protected abstract T codeToEnum(int code);

    /**
     * 枚举类型转换
     *
     * @param parameter
     * @return
     */
    protected abstract int enumTocode(T parameter);
}
