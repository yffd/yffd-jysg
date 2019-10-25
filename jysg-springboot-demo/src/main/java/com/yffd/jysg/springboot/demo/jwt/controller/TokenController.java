package com.yffd.jysg.springboot.demo.jwt.controller;

import com.yffd.jysg.springboot.demo.jwt.entity.TokenInfoEntity;
import com.yffd.jysg.springboot.demo.jwt.entity.UserInfoEntity;
import com.yffd.jysg.springboot.demo.jwt.repository.TokenRepo;
import com.yffd.jysg.springboot.demo.jwt.repository.UserInfoRepo;
import com.yffd.jysg.springboot.demo.jwt.vo.TokenResult;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/jwt")
public class TokenController {
    @Autowired
    private TokenRepo tokenRepo;
    @Autowired
    private UserInfoRepo userInfoRepo;

    /**
     * 获取token，更新token
     * @param appId 用户编号
     * @param appSecret 用户密码
     * @return
     */
    @RequestMapping(value = "/token", method = {RequestMethod.POST, RequestMethod.GET})
    public TokenResult token(@RequestParam String appId, @RequestParam String appSecret) {
        TokenResult token = new TokenResult();
        //appId is null
        if(appId == null || "".equals(appId.trim())) {
            token.setFlag(false);
            token.setMsg("appId is not found!");
            return token;
        }
        //appSecret is null
        if(appSecret == null || "".equals(appSecret.trim())) {
            token.setFlag(false);
            token.setMsg("appSecret is not found!");
            return token;
        }

        //根据appId查询用户实体
        UserInfoEntity userDbInfo = userInfoRepo.findOne(new Specification<UserInfoEntity>() {
            @Override
            public Predicate toPredicate(Root<UserInfoEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.where(criteriaBuilder.equal(root.get("appId"), appId));
                return null;
            }
        }).orElse(null);
        //如果不存在
        if (userDbInfo == null) {
            token.setFlag(false);
            token.setMsg("appId : " + appId + ", is not found!");
            return token;
        }

        System.out.println(new String(userDbInfo.getAppSecret()));
        //验证appSecret是否存在
        if (!new String(userDbInfo.getAppSecret()).equals(appSecret.replace(" ","+"))) {
            token.setFlag(false);
            token.setMsg("appSecret is not effective!");
            return token;
        }
        //检测数据库是否存在该appId的token值
        TokenInfoEntity tokenDBEntity = tokenRepo.findOne(new Specification<TokenInfoEntity>() {
            @Override
            public Predicate toPredicate(Root<TokenInfoEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.where(criteriaBuilder.equal(root.get("appId"), appId));
                return null;
            }
        }).orElse(null);
        //返回token值
        String tokenStr = null;
        if(tokenDBEntity == null) {
            //tokenDBEntity == null -> 生成newToken -> 保存数据库 -> 写入内存 -> 返回newToken
            //生成jwt,Token
            tokenStr = createNewToken(appId);
            //将token保持到数据库
            tokenDBEntity = new TokenInfoEntity();
            tokenDBEntity.setAppId(userDbInfo.getAppId());
            tokenDBEntity.setBuildTime(String.valueOf(System.currentTimeMillis()));
            tokenDBEntity.setToken(tokenStr.getBytes());
            tokenRepo.save(tokenDBEntity);
        } else {
            //tokenDBEntity != null -> 验证是否超时 ->
            //不超时 -> 直接返回dbToken
            //超时 -> 生成newToken -> 更新dbToken -> 更新内存Token -> 返回newToken

            //判断数据库中token是否过期，如果没有过期不需要更新直接返回数据库中的token即可
            //数据库中生成时间
            long dbBuildTime = Long.valueOf(tokenDBEntity.getBuildTime());
            //当前时间
            long currentTime = System.currentTimeMillis();
            //如果当前时间 - 数据库中生成时间 < 7200 证明可以正常使用
            long second = TimeUnit.MILLISECONDS.toSeconds(currentTime - dbBuildTime);
            if (second > 0 && second < 7200) {
                tokenStr = new String(tokenDBEntity.getToken());
            } else {
                //超时
                //生成newToken
                tokenStr = createNewToken(appId);
                //更新token
                tokenDBEntity.setToken(tokenStr.getBytes());
                //更新生成时间
                tokenDBEntity.setBuildTime(String.valueOf(System.currentTimeMillis()));
                //执行更新
                tokenRepo.save(tokenDBEntity);
            }
        }
        //设置返回token
        token.setToken(tokenStr);
        return token;
    }
    /**
     * 创建新token
     * @param appId
     * @return
     */
    private String createNewToken(String appId){
        //获取当前时间
        Date now = new Date(System.currentTimeMillis());
        //过期时间
        Date expiration = new Date(now.getTime() + 7200000);
        return Jwts.builder()
                //.setId(UUID.randomUUID().toString())//jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击
                .setSubject(appId)//jwt所面向的用户
                .setIssuedAt(now)//jwt的签发时间
                .setIssuer("Online YAuth Builder")//jwt签发者
                .setExpiration(expiration)//jwt的过期时间，这个过期时间必须要大于签发时间
                .signWith(SignatureAlgorithm.HS256,"Authv1.0.0")
                .compact();
    }

}
