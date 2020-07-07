package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.DateUtils;

import java.util.Date;

/***
 *
 * Author: zy
 * com.itheima.health.controller
 * 2020/7/6
 */
public class ClearOrderSettingJob {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 每个月最后1天删除以前的数据
     */
    public void ClearOrderSetting() {
        //获取每个月的第最后1天
        Date date = DateUtils.getLastDayOfThisMonth();
        try {
            //转换成数据库的格式2020-07-07
            String lastDateOfMouth = DateUtils.parseDate2String(date, "yyyy-MM-dd");
            orderSettingService.deleteByDate(lastDateOfMouth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
