package aibroker.agents;

import java.security.Permission;

public class ExitTrap {

    @SuppressWarnings("serial")
    public static class ExitTrappedException extends SecurityException {
    }

    public static void enableSystemExitCall() {
        System.setSecurityManager(null);
    }

    public static void forbidSystemExitCall() {
        final SecurityManager securityManager = new SecurityManager() {

            @Override
            public void checkExit(final int status) {
                throw new ExitTrappedException();
            }

            @Override
            public void checkPermission(final Permission permission) {
                final String permissionName = permission.getName();
                if (permissionName != null && permissionName.contains("exitVM")) {
                throw new ExitTrappedException();
                }
            }

        };
        System.setSecurityManager(securityManager);
    }

}
