package com.ehu.utils.jpa;

import com.ehu.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/22 9:52
 */
@Service
@Slf4j
public class JpaUtils {
    private static String mapperPath;

    @Value("${application.sql.mapper.path}")
    public void setMapperPath(String mapperPath) {
        JpaUtils.mapperPath = mapperPath;
    }

    /** sql文件中的返回类型属性名称 */
    private static final String RESULT_TYPE = "resultType";

    /** 唯一标识查询id的属性 */
    private static final String SQL_ID = "id";

    /**
     * 替换sql中的?
     *
     * @param sql
     * @param params
     * @return
     */
    public static String getRealSql(String sql, Object[] params) {
        StringBuffer result = new StringBuffer();
        Pattern pattern = Pattern.compile("\\?");
        Matcher matcher = pattern.matcher(sql);
        int i = 0;
        while (matcher.find()) {
            String matchString = matcher.group();
            matchString.replace("?", "");
            Object value = params[i];
            if (value instanceof String) {
                matcher.appendReplacement(result, "'" + value.toString() + "'");
            } else {
                matcher.appendReplacement(result, value.toString());
            }
            i++;
        }
        return matcher.appendTail(result).toString();
    }

    /**
     * 执行sql，获取执行结果（List<Object[]>）
     *
     * @param sql
     * @param params
     * @param em
     * @return
     */
    public static Object excuteSql(String sql, Object[] params, EntityManager em) {
        Query query = em.createNativeQuery(getRealSql(sql, params));
        return query.getResultList();
    }

    /**
     * 执行sql, 获取一条执行结果（Object[]）
     *
     * @param sql
     * @param params
     * @param em
     * @return
     */
    public static Object queryOne(String sql, Object[] params, EntityManager em) {
        Query query = em.createNativeQuery(getRealSql(sql, params));
        return query.getSingleResult();
    }

    /**
     * 执行sql，获取条执行结果（Class<T>）
     *
     * @param sql
     * @param params
     * @param em
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T queryOne(String sql, Object[] params, EntityManager em, Class<T> clazz) {
        return BeanUtil.objArrToBean((Object[]) queryOne(sql, params, em), clazz);
    }

    /**
     * 执行sql，获取执行结果（List<Class<T>）
     *
     * @param sql
     * @param params
     * @param em
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> excuteSql(String sql, Object[] params, EntityManager em, Class<T> clazz) {
        List<Object[]> objList = (List<Object[]>) excuteSql(sql, params, em);
        return BeanUtil.listObjToListBean(objList, clazz);
    }

    /**
     * 执行sql文件中的查询方法
     *
     * @param sqlId sql路径(mapper之后的路径)+sql文件中定义的id
     * @param params 参数
     * @param em EntityManager
     * @return java.util.List
     */
    public static List excute(String sqlId, Object[] params, EntityManager em) {
        List resultList = null;
        // 判断输入sqlId格式是否符合要求
        Pattern pattern = Pattern.compile("([a-zA-Z]+\\.[a-zA-Z]+)+");
        if (!pattern.matcher(sqlId).matches()) {
            throw new IllegalArgumentException("sqlId格式不符合解析格式，请仔细检查，输入格式：test.query或者test.order.query等");
        }

        // 解析sqlId，拆分获取查询id，及转换成相应的路径
        String path = sqlId.substring(0, sqlId.lastIndexOf(".")).replace(".", "/") + ".xml";
        String[] seperatedSqlId = sqlId.split("\\.");
        String selectId = seperatedSqlId[seperatedSqlId.length - 1];
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(ResourceUtils.getFile("classpath:" + mapperPath + "/" + path).getPath());
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            for (Element element : elements) {
                if (selectId.equals(element.attributeValue(SQL_ID))) {
                    String resultType = element.attributeValue(RESULT_TYPE);
                    String querySql = element.getTextTrim();
                    Class clazz = Class.forName(resultType);
                    resultList = excuteSql(querySql, params, em, clazz);
                    if (resultList == null) {
                        resultList = new ArrayList();
                        log.info("查询结果为空, 参数:{}，sql:{}", params, querySql);
                    }
                }
            }
        } catch (DocumentException e) {
            log.error(e.getMessage());
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return resultList;
    }

    /**
     * 获取一条数据
     *
     * @param sqlId sql路径(mapper之后的路径)+sql文件中定义的id
     * @param params 参数
     * @param em EntityManager
     * @return java.util.List
     */
    public static Object execute(String sqlId, Object[] params, EntityManager em) {
        List resultList = excute(sqlId, params, em);
        return resultList.size() == 0 ? null : resultList.get(0);
    }
}
