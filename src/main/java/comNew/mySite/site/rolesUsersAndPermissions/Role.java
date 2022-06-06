package comNew.mySite.site.rolesUsersAndPermissions;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole userRoles;


	public Role() {
	}

	public Role(ERole userRolesEnum) {
		this.userRoles = userRolesEnum;
	}

	public ERole getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(ERole userRoles) {
		this.userRoles = userRoles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
