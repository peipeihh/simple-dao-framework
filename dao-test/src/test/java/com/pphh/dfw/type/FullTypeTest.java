package com.pphh.dfw.type;

import com.pphh.dfw.Dao;
import com.pphh.dfw.DaoFactory;
import com.pphh.dfw.FullTypeMysqlEntity;
import org.junit.Assert;
import org.junit.Test;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 2019/3/20
 */
public class FullTypeTest {

    private Dao dao;

    public FullTypeTest() throws Exception {
        dao = DaoFactory.create("fullTypeDb");
    }

    @Test
    public void testQuery() throws Exception {
        FullTypeMysqlEntity entity = new FullTypeMysqlEntity();
        entity.setId(1);

        FullTypeMysqlEntity result = dao.query(entity);
        Assert.assertNotNull("the result is not empty.", result);
        Assert.assertEquals(1, result.getId().intValue());
        Assert.assertEquals(2, result.getMediumIntVal().intValue());
        Assert.assertEquals(3, result.getIntegerVal().intValue());
        Assert.assertEquals(4, result.getTinyIntVal().intValue());
        Assert.assertEquals(5, result.getSmallIntVal().intValue());
        Assert.assertEquals(6, result.getBigIntVal().intValue());
        Assert.assertEquals(7, result.getFloatVal(), 0.0001);
        Assert.assertEquals(8, result.getDoubleVal(), 0.0001);
        Assert.assertEquals(9, result.getNumricVal().intValue());
        Assert.assertEquals(10, result.getDecimalVal().intValue());
        Assert.assertEquals("1", result.getCharVal());
        Assert.assertEquals("12", result.getVarchar45Val());
        String tinyBlobVal = new String(result.getTinyBlobVal().getBytes(1, (int) result.getTinyBlobVal().length()));
        Assert.assertEquals("13", tinyBlobVal);
        String blobVal = new String(result.getBlobVal().getBytes(1, (int) result.getBlobVal().length()));
        Assert.assertEquals("14", blobVal);
        String longBlobVal = new String(result.getLongBlobVal().getBytes(1, (int) result.getLongBlobVal().length()));
        Assert.assertEquals("15", longBlobVal);
        Assert.assertEquals("16", result.getTinyTextVal());
        Assert.assertEquals("17", result.getTextVal());
        Assert.assertEquals("18", result.getMediumTextVal());
        Assert.assertEquals("19", result.getLongTextVal());
        Assert.assertEquals("2019-01-01", result.getDateVal().toLocalDate().toString());
        Assert.assertEquals(2019, result.getYearVal().toLocalDate().getYear());
        Assert.assertEquals("00:00:00", result.getTimeVal().toString());
        Assert.assertEquals("2019-01-01 00:00:00.0", result.getDatetimeVal().toString());
        Assert.assertNull(result.getTimestampVal());
    }
}
