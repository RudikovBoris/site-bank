package comNew.mySite.site.rolesUsersAndPermissions;

public enum UserRolePermission {
    FOUND_USER("foundUserByNameAndId"),
    SEND_MONEY("sendMoney"),
    TRANSACTION_MIDL_ACCOUNT("transactionMidlAccount"),
    PAY("pay"),

    MAKE_USER("makeNewUser"),

    FOUND_ALL_USERS("foundAllUsersByNameAndId"),
    MAKE_ANOTHER_ACCOUNT_FOR_USER("makeAnotherAccountForUser"),
    SEND_ANOTHER_USER_ACCOUNTS("sendAnotherUserAccounts"),
    PUT_MONEY("putMoney"),
    TAKE_MONEY_CASH("takeMoneyCash"),

    PERMISSION_USER_ACCOUNT_ACCESS("permissionUserAccountAccess"),
    DELETE_USER("deleteUser");

    private final String permission;
    UserRolePermission(String permission) {
        this.permission=permission;
    }

    public String getPermission() {
        return permission;
    }
}
