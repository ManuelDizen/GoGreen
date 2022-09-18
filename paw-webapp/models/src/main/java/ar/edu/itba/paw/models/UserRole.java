package ar.edu.itba.paw.models;

/* Comentario breve: Este model mapea el id de usuario con
el id de role. De esta manera, uno se puede traer una lista
de role_ids, y luego traer esos roles correspondientes con
el dao de roles directamente.
 */
public class UserRole {
    private final long id;
    private final long userId;
    private final long roleId;

    public UserRole(long id, long userId, long roleId){
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoleId() {
        return roleId;
    }
}
