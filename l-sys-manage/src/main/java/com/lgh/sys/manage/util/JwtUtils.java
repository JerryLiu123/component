package com.lgh.sys.manage.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.lgh.sys.manage.bean.auto.Role;
import com.lgh.sys.manage.bean.auto.UserDetail;
import com.lgh.sys.manage.config.SysConstant;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName: JwtUtils
 * @Description: jwt 工具类
 * @Author lizhiting
 * @DateTime May 16, 2019 9:45:58 AM
 */
@Component
public class JwtUtils {
	private Logger LOG = LoggerFactory.getLogger(JwtUtils.class);
    public static final String ROLE_REFRESH_TOKEN = "ROLE_REFRESH_TOKEN";

    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";
    
    @Autowired
    private SysConstant sysConstant;

    //private Map<String, String> tokenMap = new ConcurrentHashMap<>(32);
    
//    @Value("${jwt.secret}")
//    private String secret;

//    @Value("${jwt.expiration}")
//    private Long access_token_expiration;

//    @Value("${jwt.expiration}")
//    private Long refresh_token_expiration;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public UserDetail getUserFromToken(String token) {
        UserDetail userDetail;
        try {
            final Claims claims = getClaimsFromToken(token);
            String userId = getUserIdFromToken(token);
            String username = claims.getSubject();
            List roleName = (List) claims.get(CLAIM_KEY_AUTHORITIES);
            Set<Role> roles = new HashSet<>();
            roleName.stream().forEach(obj -> {
            	Map r = (Map) obj;
            	roles.add(new Role(r.get("id").toString(), r.get("name").toString()));
            });
            userDetail = new UserDetail(userId, username, "", roles);
            //userDetail = null;
        } catch (Exception e) {
        	LOG.error("获得用户信息失败，", e);
            userDetail = null;
        }
        return userDetail;
    }

    public String getUserIdFromToken(String token) {
        String userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = String.valueOf(claims.get(CLAIM_KEY_USER_ID));
        } catch (Exception e) {
            userId = "0";
        }
        return userId;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public String generateAccessToken(UserDetail userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        claims.put(CLAIM_KEY_AUTHORITIES, JSONArray.toJSON(userDetail.getAuthorities()));
        return generateAccessToken(userDetail.getUsername(), claims);
    }
    
    public String generateAccessToken(UserDetail userDetail, long expiration) {
        Map<String, Object> claims = generateClaims(userDetail);
        claims.put(CLAIM_KEY_AUTHORITIES, JSONArray.toJSON(userDetail.getAuthorities()));
        return generateAccessToken(userDetail.getUsername(), claims, expiration);
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateAccessToken(claims.getSubject(), claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
    
    
    public String refreshToken(String token, long expiration) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateAccessToken(claims.getSubject(), claims, expiration);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }    


    public Boolean validateToken(String token, UserDetails userDetails) {
        UserDetail userDetail = (UserDetail) userDetails;
        final String userId = getUserIdFromToken(token);
        final String username = getUsernameFromToken(token);
        
        return (userId.equals(userDetail.getId())
                && username.equals(userDetail.getUsername())
                && !isTokenExpired(token)
        );
    }
    
    
    public Boolean validateToken(HttpServletRequest request, String token, UserDetails userDetails) {
        //验证token超时时间
        if(isTokenExpired(token)) {
        	return false;
        }
//        //验证菜单权限
//        String url = request.getRequestURI();
//        Set<Role> roles = (Set<Role>) userDetails.getAuthorities(); 
//        for(Role ro : roles) {
//        	if(url.equals(ro.getNameZh())) {
//        		return true;
//        	}
//        }
//        return false;
        return true;
    }
    
    public String generateRefreshToken(UserDetail userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        // 只授于更新 token 的权限
        String roles[] = new String[]{JwtUtils.ROLE_REFRESH_TOKEN};
        claims.put(CLAIM_KEY_AUTHORITIES, JSONArray.toJSON(roles));
        return generateRefreshToken(userDetail.getUsername(), claims);
    }
    
    
    public String generateRefreshToken(UserDetail userDetail, long expiration) {
        Map<String, Object> claims = generateClaims(userDetail);
        // 只授于更新 token 的权限
        String roles[] = new String[]{JwtUtils.ROLE_REFRESH_TOKEN};
        claims.put(CLAIM_KEY_AUTHORITIES, JSONArray.toJSON(roles));
        return generateRefreshToken(userDetail.getUsername(), claims, expiration);
    }

//    public void putToken(String userName, String token) {
//        tokenMap.put(userName, token);
//    }
//
//    public void deleteToken(String userName) {
//        tokenMap.remove(userName);
//    }
//
//    public boolean containToken(String userName, String token) {
//        if (userName != null && tokenMap.containsKey(userName) && tokenMap.get(userName).equals(token)) {
//            return true;
//        }
//        return false;
//    }
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
        	LOG.debug("-----加盐：{}", sysConstant.getSecret());
            claims = Jwts.parser()
                    .setSigningKey(sysConstant.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Map<String, Object> generateClaims(UserDetail userDetail) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USER_ID, userDetail.getId());
        return claims;
    }

    private String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, sysConstant.getAccessTokenExpiration());
    }
    
    private String generateAccessToken(String subject, Map<String, Object> claims, long expiration) {
        return generateToken(subject, claims, expiration);
    }    

    private List authoritiesToArray(Collection<? extends GrantedAuthority> authorities) {
        List<String> list = new ArrayList<>();
        for (GrantedAuthority ga : authorities) {
            list.add(ga.getAuthority());
        }
        return list;
    }


    private String generateRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, sysConstant.getRefreshTokenExpiration());
    }
    
    private String generateRefreshToken(String subject, Map<String, Object> claims, long expiration) {
        return generateToken(subject, claims, expiration);
    }



    private String generateToken(String subject, Map<String, Object> claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(expiration))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, sysConstant.getSecret())
                .compact();
    }

}
