package com.SpringSecurityPart1.SpringSecurityPart1.utils;

import com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Permission;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Permission.POST_UPDATE;
import static com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Role.*;
import static javax.swing.UIManager.get;

public class PermissionMapping {

  private static final   Map<Role, Set<Permission>>map= Map.of(
            USER,Set.of(Permission.USER_VIEW,Permission.POST_VIEW),
            CREATOR,Set.of(Permission.POST_CREATE,Permission.USER_UPDATE,POST_UPDATE),
            ADMIN,Set.of(Permission.POST_CREATE,Permission.USER_UPDATE,POST_UPDATE,Permission.USER_DELETE,Permission.USER_CREATE,Permission.POST_DELETE)
    );

  public static Set<SimpleGrantedAuthority>getAuthoritiesForRole(Role role){
      return map.get(role).stream().map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toSet());
  }
}
