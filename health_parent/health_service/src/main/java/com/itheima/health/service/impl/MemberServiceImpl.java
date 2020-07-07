package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Description: No Description
 * User: Eric
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 通过手机号码查询会员信息
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
     * 添加会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    /**
     * 统计过去一年的会员总数
     * @param months
     * @return
     */
    @Override
    public List<Integer> getMemberReport(List<String> months) {
        //select count(1) from t_member where regTime<='2020-06-31';  <= Before
        List<Integer> memberCount = new ArrayList<Integer>();
        if(months != null) {
            // 循环12个，每个月查询一下
            for (String month : months) {
                // 到这个month为，一会有多少个会员
                Integer count = memberDao.findMemberCountBeforeDate(month + "-31");
                memberCount.add(count);
            }
        }
        return memberCount;
    }

    /**
     * 按照性别统计会员人数
     * @return
     */
    @Override
    public Map<String, Object> getMemberReportBySex() {
        List<String> sexNameList = new ArrayList<>();
        List<Map<String,Object>> dataInfo = new ArrayList<>();
        List<Map<String, String>> maps = memberDao.countBySex();
        for (Map<String, String> map : maps) {
            String name = map.get("name");
            if(null != name && "2".equals(name)){
                Map<String,Object> dataInfoMap = new HashMap<>();
                dataInfoMap.put("value",map.get("value"));
                dataInfoMap.put("name","男");
                sexNameList.add("男");
                dataInfo.add(dataInfoMap);
            }
            if(null != name && "1".equals(name)){
                Map<String,Object> dataInfoMap = new HashMap<>();
                dataInfoMap.put("value",map.get("value"));
                dataInfoMap.put("name","女");
                sexNameList.add("女");
                dataInfo.add(dataInfoMap);
            }
        }
        Map<String,Object> ResultMap = new HashMap<>();
        ResultMap.put("sexNameList",sexNameList);
        ResultMap.put("dataInfo",dataInfo);
        return ResultMap;
    }

    @Override
    public Map<String, Object> getMemberReportByAge() {
        List<String> age_group = new ArrayList<>();
        List<String> age_value = new ArrayList<>();
        List<Map<String,Object>> dataInfo = new ArrayList<>();
        String Age_0 = memberDao.findByAge_0()+"";
        String Age_1 = memberDao.findByAge_1()+"";
        String Age_2 = memberDao.findByAge_2()+"";
        String Age_3 = memberDao.findByAge_3()+"";
        age_value.add(Age_0);
        age_value.add(Age_1);
        age_value.add(Age_2);
        age_value.add(Age_3);
        age_group.add("0-20岁");
        age_group.add("20-40岁");
        age_group.add("40-60岁");
        age_group.add("60岁以上");
        for(int j=0; j<=3 ; j++){
            Map<String,Object> dataInfoMap = new HashMap<>();
            dataInfoMap.put("name",age_group.get(j));
            dataInfoMap.put("value",age_value.get(j));
            dataInfo.add(dataInfoMap);
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("age_group_list",age_group);
        resultMap.put("dataInfo",dataInfo);
        return resultMap;
    }
}
