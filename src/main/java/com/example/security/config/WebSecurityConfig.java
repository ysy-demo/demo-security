package com.example.security.config;

import com.example.security.filte.AfterLoginFilter;
import com.example.security.filte.AtLoginFilter;
import com.example.security.filte.BeforeLoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author shiyuan
 * @create 2019/4/30
 */
@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Spring Security提供的Filter不少，有十多个，过滤器顺序从上到下：
     *
     * （1）ChannelProcessingFilter（访问协议控制过滤器）
     *
     * 如果你访问的channel错了，那首先就会在channel之间进行跳转，如http变为https。
     *
     * （2）SecurityContextPersistenceFilter（SecurityContext持久化过滤器）
     *
     * 用来创建一个SecurityContext并存储在SecurityContextHolder中，因为后续filter需要用SecurityContext存储的认证相关信息，所以需要在请求一开始就要把这些信息设置好，这样也能使在认证过程中对SecurityContext的任何修改都可以保存下来，并在请求结束后存储在HttpSession中（以在下次请求时使用）
     *
     * （3）ConcurrentSessionFilter（并发访问控制过滤器）
     *
     * 主要是判断session是否过期以及更新最新访问时间。
     *
     * （4）HeaderWriterFilter（请求头部写入过滤器）
     *
     * 往该请求的Header中添加相应的信息
     *
     * （5）CsrfFilter（CSRF过滤器）
     *
     *        为了防止跨站提交攻击。
     *
     * （6）LogoutFilter（退出过滤器）
     *
     *        退出当前登录的账号。
     *
     * （7）X509AuthenticationFilter（X509认证过滤器）
     *
     *        基于X509证书的认证过滤器。
     *
     * （8）AbstractPreAuthenticatedProcessingFilter
     *
     *        处理form登陆的过滤器，与form登陆有关的所有操作都是在此进行的。这个请求应该是用户使用form登陆后的提交地址。
     *
     * （9）CasAuthenticationFilter（CAS认证过滤器）
     *
     *        基于CAS的认证过滤器。
     *
     * （10）UsernamePasswordAuthenticationFilter（用户名密码认证过滤器）
     *
     *        基于用户名和密码的认证过滤器。
     *
     * （11）BasicAuthenticationFilter（basic认证过滤器）
     *
     *        此过滤器用于进行basic验证，功能与AuthenticationProcessingFilter类似，只是Basic验证方式相比较而言用的不是太多，默认会对密码进行base64加密。
     *
     * （12）SecurityContextHolderAwareRequestFilter
     *
     *        此过滤器用来包装客户的请求。通过查看其源码可以发现其doFilter方法中会创建一个包装类型SecurityContextHolderAwareRequestWrapper对ServletRequest对象进行包装，主要实现了servlet api的一些接口方法isUserInRole、getRemoteUser，为后续程序提供一些额外的数据。即可以从request对象中获取到用户信息。
     *
     * （13）JaasApiIntegrationFilter
     *
     *        如果SecurityContextHolder中拥有的Authentication是一个JaasAuthenticationToken，那么该Filter将使用包含在JaasAuthenticationToken中的Subject继续执行FilterChain。
     *
     * （14）RememberMeAuthenticationFilter（记住我认证过滤器）
     *
     *        当用户没有登录而直接访问资源时, 从cookie里找出用户的信息, 如果Spring Security能够识别出用户提供的remember me cookie, 用户将不必填写用户名和密码, 而是直接登录进入系统. 它先分析SecurityContext里有没有Authentication对象. 如果有, 则不做任何操作, 直接跳到下一个过滤器. 如果没有, 则检查request里有没有包含remember-me的cookie信息.如果有, 则解析出cookie里的验证信息, 判断是否有权限。
     *
     * （15）AnonymousAuthenticationFilter（匿名认证过滤器）
     *
     *        用于支持Spring Security的匿名访问，适用于 一些公共资源希望所有人都可以看到。对于匿名访问的用户，Spring Security支持为其建立一个匿名的AnonymousAuthenticationToken存放在SecurityContextHolder中，这就是所谓的匿名认证。这样在以后进行权限认证或者做其它操作时我们就不需要再判断SecurityContextHolder中持有的Authentication对象是否为null了，而直接把它当做一个正常的Authentication进行使用就OK了。
     *
     * （16）SessionManagementFilter
     *
     *        根据认证的安全实体信息跟踪session，保证所有关联一个安全实体的session都能被跟踪到。
     *
     * （17）ExceptionTranslationFilter
     *
     *        解决在处理一个请求时产生的指定异常。
     *
     * （18）FilterSecurityInterceptor
     *
     *        简化授权和访问控制决定，委托一个AccessDecisionManager完成授权的判断。
     *
     * （19）SwitchUserFilter
     *
     * SwitchUserFilter是用来做账户切换的
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login");
        http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new AfterLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(new AtLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
