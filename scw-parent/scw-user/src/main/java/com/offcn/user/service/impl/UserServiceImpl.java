package com.offcn.user.service.impl;

import com.offcn.user.enums.UserExceptionEnum;
import com.offcn.user.exception.UserException;
import com.offcn.user.mapper.TMemberMapper;
import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberExample;
import com.offcn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Auther: lhq
 * @Date: 2021/4/14 09:29
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TMemberMapper memberMapper;

    /**
     * 注册会员
     *
     * @param tMember
     */
    @Override
    public void register(TMember tMember) {
        //1.判断账号是否存在
        TMemberExample tMemberExample = new TMemberExample();
        TMemberExample.Criteria criteria = tMemberExample.createCriteria();
        criteria.andLoginacctEqualTo(tMember.getLoginacct());
        //查询记录数   	SELECT count(*) from t_member where loginacct=?
        long count = memberMapper.countByExample(tMemberExample);
        if (count > 0) {
            throw new UserException(UserExceptionEnum.LOGINACCT_EXIST);
        }
        //2.对密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(tMember.getUserpswd());
        tMember.setUserpswd(password);
        //3.设置默认属性
        tMember.setUsername(tMember.getLoginacct());
        tMember.setAuthstatus("0");      //实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证
        tMember.setUsertype("0");     //用户类型 0.个人  1.企业
        tMember.setAccttype("2");    //账户类型  2 个人
        //4.完成保存
        memberMapper.insertSelective(tMember);
    }

    /**
     * 用户登录
     *
     * @param loginacct
     * @param password
     * @return
     */
    @Override
    public TMember login(String loginacct, String password) {
        //1.根据账号取得用户信息
        TMemberExample tMemberExample = new TMemberExample();
        TMemberExample.Criteria criteria = tMemberExample.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);
        List<TMember> tMemberList = memberMapper.selectByExample(tMemberExample);
        if (!CollectionUtils.isEmpty(tMemberList)) {
            TMember tMember = tMemberList.get(0);
            //2.匹配密码是否正确
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            //判断加密密码是否一致 参数一  明文密码  参数二  加密密码
            boolean flag = encoder.matches(password, tMember.getUserpswd());
            return flag ? tMember : null;
        }
        return null;
    }

    /**
     * 根据Id查询会员信息
     *
     * @param id
     * @return
     */
    @Override
    public TMember findMemberById(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }
}
