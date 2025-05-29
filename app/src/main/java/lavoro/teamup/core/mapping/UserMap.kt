package lavoro.teamup.core.mapping

import com.google.firebase.auth.FirebaseUser
import lavoro.teamup.core.ROLE_ADMIN
import lavoro.teamup.core.ROLE_OWNER
import lavoro.teamup.core.ROLE_TEAM
import lavoro.teamup.data.model.user.RemoteUser
import lavoro.teamup.data.model.user.User
import lavoro.teamup.data.room.user.RoomUser


private fun String.isOwnerEmail(): Boolean =
    this == "thisismohalawa@gmail.com"

private fun String.isAdminEmail(): Boolean =
    this == "barmagah.dev.services@gmail.com"


internal val FirebaseUser.toUser: User
    get() = User(
        uid = this.uid,
        name = this.displayName ?: "username",
        email = this.email ?: "email@teamup",
        phone = this.phoneNumber ?: "",
        roleId = if (this.email?.isOwnerEmail() == true) ROLE_OWNER
        else if (this.email?.isAdminEmail() == true) ROLE_ADMIN else ROLE_TEAM,
        activated = if (this.email?.isOwnerEmail() == true ||
            this.email?.isAdminEmail() == true
        ) true else false
    )
internal val RemoteUser.toUser: User
    get() = User(
        uid = this.uid ?: "",
        name = this.name ?: "guest",
        email = this.email ?: "guest@teamup",
        phone = this.phone ?: "",
        roleId = this.roleId ?: ROLE_TEAM,
        activated = this.activated ?: false
    )
internal val RoomUser.toUser: User
    get() = User(
        uid = this.uid,
        name = this.name,
        email = this.email,
        phone = this.phone,
        roleId = this.roleId,
        activated = this.activated
    )
internal val User.toRoomUser: RoomUser
    get() = RoomUser(
        uid = this.uid,
        name = this.name,
        email = this.email,
        phone = this.phone,
        roleId = this.roleId,
        activated = this.activated
    )