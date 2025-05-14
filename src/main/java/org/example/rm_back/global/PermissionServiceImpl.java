package org.example.rm_back.global;

import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.rm_back.admin.enums.AdminRole;
import org.example.rm_back.auth.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final Map<String, String[]> permissionMap = Map.of(
        "/api/v1/admin", new String[]{AdminRole.SUPER.name()}
    );

    @Override
    public boolean hasAccess(UserPrincipal user, String path, String method) {
        if (!permissionMap.containsKey(path)) {
            return false;
        }

        String[] requiredRoles = permissionMap.get(path);
        if (requiredRoles.length == 0) {
            return true;
        }

        String[] userRoles = AdminRole.toRoleArray(user.getRoles());

        for (String s : requiredRoles) {
            if (Arrays.stream(userRoles)
                .anyMatch(role -> role.equalsIgnoreCase(s))) {
                return true;
            }
        }
        return false;
    }
}
