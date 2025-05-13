package org.example.rm_back.global;

import org.example.rm_back.auth.UserPrincipal;

public interface PermissionService {

    boolean hasAccess(UserPrincipal user, String path, String method);
}
