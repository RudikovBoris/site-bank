package comNew.mySite.site.rolesUsersAndPermissions;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static comNew.mySite.site.rolesUsersAndPermissions.UserRolePermission.*;

public enum ERole {
	ROLE_USER(Sets.newHashSet(
			FOUND_USER
			,SEND_MONEY
			,TRANSACTION_MIDL_ACCOUNT
			,PAY)),
	ROLE_ADMIN(Sets.newHashSet(
			MAKE_USER
			,FOUND_ALL_USERS
			,MAKE_ANOTHER_ACCOUNT_FOR_USER
			,SEND_ANOTHER_USER_ACCOUNTS
			,PUT_MONEY
			, TAKE_MONEY_CASH)),
	ROLE_MODERATOR(Sets.newHashSet(
			FOUND_ALL_USERS
			,PERMISSION_USER_ACCOUNT_ACCESS
			,DELETE_USER));

	private final Set<UserRolePermission> permission;


	ERole(Set<UserRolePermission> permission) {
		this.permission = permission;
	}



	public Set<UserRolePermission> getPermissionsFromUserRoles() {
		return permission;
	}

	public Set<SimpleGrantedAuthority> grantedAuthorities(){
		Set<SimpleGrantedAuthority> authoritiesPermission =  getPermissionsFromUserRoles().stream()
				.map(mapPermission->new SimpleGrantedAuthority(mapPermission.getPermission()) )
				.collect(Collectors.toSet());
		authoritiesPermission.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		return authoritiesPermission;

	}
}
