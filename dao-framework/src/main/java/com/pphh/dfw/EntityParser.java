package com.pphh.dfw;

import com.pphh.dfw.core.IEntity;
import com.pphh.dfw.core.IEntityParser;
import com.pphh.dfw.table.GenericTable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by mh on 2018/10/5.
 */
public class EntityParser implements IEntityParser<GenericTable> {

    @Override
    public GenericTable parse(IEntity iEntity) {
        GenericTable table = null;

        // 获取table name, table field and field value
        Class clazz = iEntity.getClass();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnno = (Table) clazz.getAnnotation(Table.class);
            table = new GenericTable(tableAnno.name());

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = (Column) field.getAnnotation(Column.class);
                    try {
                        String fieldName = field.getName();
                        String fieldDef = column.name();
                        Type filedType = field.getGenericType();
                        Object filedValue = field.get(iEntity);
                        table.insertFields(fieldName, fieldDef, filedType, filedValue, field.isAnnotationPresent(Id.class));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        return table;
    }

    @Override
    public GenericTable parse(Class<? extends IEntity> clazz) {
        GenericTable table = null;

        // 获取table name, table field and field value
        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnno = (Table) clazz.getAnnotation(Table.class);
            table = new GenericTable(tableAnno.name());

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = (Column) field.getAnnotation(Column.class);
                    String fieldName = field.getName();
                    String fieldDef = column.name();
                    Type filedType = field.getGenericType();
                    table.insertFields(fieldName, fieldDef, filedType, null, field.isAnnotationPresent(Id.class));
                }
            }
        }

        return table;
    }
}
