package lavoro.teamup.home

interface HomeActivityInteract {
    fun startAuthActivity()
    fun restartHomeActivity()
    fun finishHomeActivity()
    fun displayToast(msg: String?)
    fun updateProgressLoad(loading: Boolean)
    fun updateActionBaTitle(title: String)
    fun updateActionBarSubTitle(title: String)
    fun updateActionBarSubTitleColor(isError: Boolean)
    fun actionCopyText(text: String)
    fun actionShareText(text: String)
    fun actionDial(phoneNum: String)
    fun displaySnack(
        text: String,
        actionText: String? = null,
        action: (() -> Unit)? = null
    )
    fun checkWriteStoragePermission(
        action: (() -> Unit)? = null
    )
}