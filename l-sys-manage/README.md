# 功能描述：
1. 同一用户只允许一个登陆，后一个会把前一个顶掉
2. 密码错误次数验证，分布式的话需要自己实现接口
3. 密码错误超过限制后锁定用户，分布式的话需要自己实现接口
4. 可支持分布式token，需要自己实现接口
5. https（没有测试）
6. 基本的菜单权限验证
7. jwt
8. 支持自定义密码加密规则，需要自己实现接口
9. 登陆成功后事件发布
10. 登陆人数限制
11. 密码过期校验
# 使用方式
1. 引入jar包
2. 进行配置(详细配置见下方)
3. 运行sql，初始化库
4. 配置数据源
# 详细配置(括号中为默认值)
1. jwt.token.header：在header中存储Token的名称（Authorization）
2. jwt.token.prefix：token 前缀(Bearer )
3. jwt.token.secret：token 加盐(lghsecurityjwttoken001)
4. jwt.token.expiration：默认的token保存时间-秒(8400)
5. jwt.token.rememverexpiration：保存密码时roken保存时间-秒(604800)
6. jwt.pwd.errnum：密码错误次数，0为不校验(0)
7. jwt.pwd.errtimeinterval：密码输入错误到达限制后重新输入密码间隔-秒(3600)
8. jwt.pwd.iserrlock：密码错误超过次数后是否锁定，当设置为true时达到限制后将不能输入密码，直到解锁(false)
9. jwt.pwd.isupdate：密码更新时间校验(false)
10. jwt.pwd.day：密码更新时间超过多少天后将不可登陆-天(30)
11. jwt.login.restrictusernum：登陆人数限制，0为不限制(0)
12. jwt.login.isonly：同一用户是否只能唯一登陆(false)
13. jwt.intercept.matcher：不需要拦截的方法List(/api/v1/login, /api/v1/logout, /api/v1/sign, /error/**, /test/login)
14. jwt.intercept.webmatcher：不需要拦截的静态地址List(/**/*.css, /**/*.js, /**/*.png, /**/*.gif)
15. jwt.ssl.is：是否使用https(false)
16. jwt.ssl.matcher：https强制拦截的地址List(/api/v1/login, /api/v1/sign)
 