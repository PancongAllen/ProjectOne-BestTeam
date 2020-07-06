package com.itheima.health.service;

import com.itheima.health.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * Description: No Description
 * User: Eric
 */
public interface MemberService {
    /**
     * 通过手机号码查询会员信息
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 添加会员
     * @param member
     */
    void add(Member member);

    /**
     * 统计过去一年的会员总数
     * @param months
     * @return
     */
    List<Integer> getMemberReport(List<String> months);

    /**
     * 按照性别统计会员人数
     */
    Map<String,Object> getMemberReportBySex();

    Map<String, Object> getMemberReportByAge();
}
