package org.example.rm_back.global;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.rm_back.admin.enums.AdminRole;
import org.example.rm_back.auth.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final Map<String, String[]> permissionMap = Map.of(
        "/admin/add", new String[]{AdminRole.SUPER.name()}
    );

    @Override
    public boolean hasAccess(UserPrincipal user, String path, String method) {
        String[] requiredRoles = permissionMap.getOrDefault(path, new String[0]);

        for (String s : requiredRoles) {
            if (user.getRoles().contains(s)) {
                return true;
            }
        }
        return requiredRoles.length == 0;
    }
}
