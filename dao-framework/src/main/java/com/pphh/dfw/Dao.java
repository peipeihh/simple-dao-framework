package com.pphh.dfw;

import com.pphh.dfw.core.*;
import com.pphh.dfw.core.dao.IBatchSqlBuilder;
import com.pphh.dfw.core.dao.IDao;
import com.pphh.dfw.core.sqlb.ISqlBuilder;
import com.pphh.dfw.core.table.ITableField;
import com.pphh.dfw.sqlb.SqlBuilder;
import com.pphh.dfw.table.GenericTable;

import java.util.List;
import java.util.function.Function;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/21/2018
 */
public class Dao implements IDao {

    private String logicDbName;
    private SqlBuilder sqlBuilder;
    private EntityParser entityParser;

    public Dao(String logicDbName) {
        this.logicDbName = logicDbName;
        this.sqlBuilder = new SqlBuilder();
        this.entityParser = new EntityParser();
    }

    @Override
    public <T extends IEntity> T queryByPk(T entity) {
        // parse entity, 获取entity definition
        GenericTable table = this.entityParser.parse(entity);

        if (table != null) {
            // 主键Id + 主键
            ITableField primaryKey = table.getPkField();
            String keyDefName = primaryKey.getFieldName();
            Object value = table.getFieldValue(primaryKey);
            sqlBuilder.select().from(table).where(primaryKey.equal(value)).into(entity.getClass());
            String sql = sqlBuilder.buildOn(logicDbName);
            System.out.println(sql);
        }

        return null;
    }

    @Override
    public <T extends IEntity> List<T> queryBySample(T entity) {
        return null;
    }

    @Override
    public <T extends IEntity> List<T> queryAll(Long limit) {
        return null;
    }

    @Override
    public <T extends IEntity> long countBySample(T entity) {
        return 0;
    }

    @Override
    public <T extends IEntity> int insert(T entity) {
        return 0;
    }

    @Override
    public <T extends IEntity> int[] insert(List<T> entities) {
        return new int[0];
    }

    @Override
    public <T extends IEntity> int delete(T entity) {
        return 0;
    }

    @Override
    public <T extends IEntity> int deleteBySample(T entity) {
        return 0;
    }

    @Override
    public <T extends IEntity> int[] delete(List<T> entities) {
        return new int[0];
    }

    @Override
    public <T extends IEntity> int update(T entity) {
        return 0;
    }

    @Override
    public <T extends IEntity> int[] update(List<T> entities) {
        return new int[0];
    }

    @Override
    public <T extends IEntity> T queryForObject(ISqlBuilder sqlBuilder) {
        return null;
    }

    @Override
    public <T extends IEntity> List<T> queryForList(ISqlBuilder sqlBuilder) {
        return null;
    }

    @Override
    public int execute(Function function) {
        return 0;
    }

    @Override
    public int run(ISqlBuilder sqlBuilder) {
        return 0;
    }

    @Override
    public int[] run(IBatchSqlBuilder sqlBuilder) {
        return new int[0];
    }

    @Override
    public IHints getHints() {
        return null;
    }
}
