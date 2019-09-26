package com.yffd.jysg.sso.cas.app1.config;

import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;

import java.util.regex.Pattern;

public class SimpleUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {
    private Pattern pattern;

    @Override
    public boolean matches(String url) {
        //额外的扩展，比如判断是insert也放行，可以通过查询数据库来进行动态判断
        if(url.contains("/insert")){
            return true;
        }

        //默认是根据cas.ignore-pattern来判断是否否满足过滤
        return this.pattern.matcher(url).find();
    }

    /**
     * application.properties中配置的cas.ignore-pattern的值
     * @param pattern
     */
    @Override
    public void setPattern(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }
}
