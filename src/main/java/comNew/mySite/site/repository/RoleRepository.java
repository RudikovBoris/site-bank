package comNew.mySite.site.repository;


import comNew.mySite.site.rolesUsersAndPermissions.ERole;
import comNew.mySite.site.rolesUsersAndPermissions.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByUserRoles(ERole userRoles);
	Boolean existsByUserRoles(ERole userRoles);



}
