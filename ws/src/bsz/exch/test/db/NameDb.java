package bsz.exch.test.db;

import bsz.exch.utils.NameUtils;

/**
 * 默认名称处理handler
 * 
 * User: liyd
 * Date: 2/12/14
 * Time: 4:51 PM
 */
public class NameDb {

    /** 主键后缀 */
    private static final String PRI_SUFFIX = "_id";

    /**
     * 根据实体名获取表名
     *
     * @param entityClass
     * @return
     */
    public static String getTableName(Class<?> entityClass) {
        //Java属性的骆驼命名法转换回数据库下划线“_”分隔的格式
        return NameUtils.getUnderlineName(entityClass.getSimpleName());
    }

    /**
     * 根据表名获取主键名
     *
     * @param entityClass
     * @return
     */
    public static String getPKName(Class<?> entityClass) {
        String underlineName = NameUtils.getUnderlineName(entityClass.getSimpleName());
        //主键以表名加上“_id” 如user表主键即“user_id”
        return underlineName + PRI_SUFFIX;
    }

    /**
     * 根据属性名获取列名
     *
     * @param fieldName
     * @return
     */
    public static String getColumnName(String fieldName) {
        String underlineName = NameUtils.getUnderlineName(fieldName);
        return underlineName;
    }

}
