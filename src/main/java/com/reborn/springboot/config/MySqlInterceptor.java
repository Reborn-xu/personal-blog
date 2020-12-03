package com.reborn.springboot.config;

import com.reborn.springboot.entity.User;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

public class MySqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /*StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        String id = mappedStatement.getId();
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        String msql = sql;*/



        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        Field[] fields = parameter.getClass().getDeclaredFields();
        Date currentDate = new Date();
        //User currentUser = new User();
        //currentUser.setId(11111L);
        /*if(SqlCommandType.UPDATE==sqlCommandType) {
            for (Field field : fields) {
                if (AnnotationUtils.getAnnotation(field, LastModifiedBy.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter,currentUser.getId());
                    field.setAccessible(false);
                }
                if (AnnotationUtils.getAnnotation(field, LastModifiedDate.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter,currentDate);
                    field.setAccessible(false);
                }
            }
        } else if(SqlCommandType.INSERT==sqlCommandType){
            for (Field field : fields) {
                if (AnnotationUtils.getAnnotation(field, CreatedBy.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter,currentUser.getId());
                    field.setAccessible(false);
                }
                if (AnnotationUtils.getAnnotation(field, CreatedDate.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter,currentDate);
                    field.setAccessible(false);
                }
            }
        }*/
        return invocation.proceed();
    }

    public Object plugin(Object target) {

        /*if (target instanceof StatementHandler) {

            return Plugin.wrap(target, this);

        } else {

            return target;

        }

*/
        return null;
    }



}
