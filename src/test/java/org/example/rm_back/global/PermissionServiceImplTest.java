package org.example.rm_back.global;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import org.example.rm_back.admin.enums.AdminRole;
import org.example.rm_back.auth.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PermissionServiceImplTest {

    @Mock
    PermissionServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PermissionServiceImpl();
    }

    @SuppressWarnings("unchecked")
    private void setPermissionService(Map<String, Map<String, Set<AdminRole>>> map)
        throws Exception {
        Field f = PermissionServiceImpl.class.getDeclaredField("permissionMap");
        f.setAccessible(true);
        f.set(service, map);
    }

    @Test
    void pathNotInMap_accessDenied() {
        // given
        UserPrincipal user = new UserPrincipal("hama", "SUPER");

        // when & then
        assertFalse(service.hasAccess(user, "/no/test", "POST"));
    }

    @Test
    void noRolesRequired_accessAllowed() throws Exception {
        // given
        setPermissionService(Map.of(
            "/public", Map.of("GET", Set.of())
        ));
        UserPrincipal user = new UserPrincipal("hama", "NOTICE_READ");

        // when & then
        assertTrue(service.hasAccess(user, "/public", "GET"));
    }


    @Test
    void hasRequiredRole_accessAllowed() {
        // given
        UserPrincipal superUser = new UserPrincipal("hama", "SUPER");

        // when & then
        assertTrue(service.hasAccess(superUser, "/api/v1/admin", "POST"));
    }

    @Test
    void lacksRequiredRole_accessDenied() {
        // given
        UserPrincipal user = new UserPrincipal("hama", "NOTICE_READ");

        // when & then
        assertFalse(service.hasAccess(user, "/api/v1/admin", "POST"));
    }

    @Test
    void caseInsensitiveRoleComparison() throws Exception {
        // given
        setPermissionService(Map.of(
            "/case", Map.of("GET", Set.of(AdminRole.SUPER))
        ));
        UserPrincipal mixedCaseUser = new UserPrincipal("eve", "sUpEr");

        // when & then
        assertTrue(service.hasAccess(mixedCaseUser, "/case", "GET"));
    }
}
