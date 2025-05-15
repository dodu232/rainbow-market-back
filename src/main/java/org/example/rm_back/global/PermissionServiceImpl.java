package org.example.rm_back.global;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.rm_back.admin.enums.AdminRole;
import org.example.rm_back.auth.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final Map<String, Map<String, Set<AdminRole>>> permissionMap = Map.of(
        "/api/v1/admin", Map.of(
            "POST", Set.of(AdminRole.SUPER)
        )
    );

    @Override
    public boolean hasAccess(UserPrincipal user, String path, String method) {
        Map<String, Set<AdminRole>> methodMap = permissionMap.get(path);
        if (methodMap == null) {
            return false;
        }

        Set<AdminRole> requiredRoles = methodMap.get(method);
        if (requiredRoles == null) {
            return false;
        }

        if (requiredRoles.isEmpty()) {
            return true;
        }

        Set<AdminRole> userRoles = Arrays.stream(AdminRole.toRoleArray(user.getRoles()))
            .map(AdminRole::valueOf)
            .collect(Collectors.toSet());

        for (AdminRole r : userRoles) {
            if (requiredRoles.contains(r)) {
                return true;
            }
        }

        return false;
    }
}
