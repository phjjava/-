package com.jp.entity;

import java.util.ArrayList;
import java.util.List;

import com.jp.entity.GenUser;
import com.jp.util.JacksonUtil;

public class GenUserVO {
    // 节点用户
    private GenUser user;

    // 配偶用户
    private GenUser mateuser;


    // 孩子
    private List<GenUserVO> children;

	public List<GenUserVO> getChildren() {
        return children;
    }

    public void setChildren(List<GenUserVO> children) {
        this.children = children;
    }

    public GenUser getUser() {
        return user == null ? null : user;
    }

    public void setUser(GenUser user) {
        this.user = user;
    }

    public GenUser getMateuser() {
        return mateuser == null ? null : mateuser;
    }

    public void setMateuser(GenUser mateuser) {
        this.mateuser = mateuser;
    }

    public static void main(String[] args) {



        GenUserVO genuser = new GenUserVO();
        GenUser user = new GenUser();
        user.setUserid("1");
        user.setUsername("大兒子");

        GenUser user1 = new GenUser();
        user1.setUserid("3");
        user1.setUsername("大儿媳");
        genuser.setUser(user1);
        genuser.setMateuser(user1);


        List<GenUserVO> genUserVOs = new ArrayList<GenUserVO>();
        GenUserVO genUserVO = new GenUserVO();
        GenUser user2 = new GenUser();
        user2.setUserid("4");
        user2.setUsername("小孫子");
        genUserVO.setUser(user2);
        genUserVOs.add(genUserVO);
        genuser.setChildren(genUserVOs);

        System.out.println(("" + null));


        System.out.println(JacksonUtil.fromObjectToJson(genuser));

        GenUserVO ss = JacksonUtil.fromJsonToObject(JacksonUtil.fromObjectToJson(genuser),
                GenUserVO.class);

    }

}

