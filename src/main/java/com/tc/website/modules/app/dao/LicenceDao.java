/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.dao;

import com.tc.website.common.persistence.CrudDao;
import com.tc.website.common.persistence.annotation.MyBatisDao;
import com.tc.website.modules.app.entity.Licence;

/**
 * 许可授权DAO接口
 * @author Jack Yu
 * @version 2018-08-13
 */
@MyBatisDao
public interface LicenceDao extends CrudDao<Licence> {

    /**
     *  通过机器码查找许可授权
     * @param licence
     * @return
     */
    public Licence findOneByMachineCode(Licence licence);

}