package com.rdt.rdtminer

enum class FragId(val i: Int) {
    NONE(0),
    HOME(1),
    DASHBOARD(2),
    TRANSACTION(3)
}

enum class SPrefKey(val s: String) {
    NONE(""),
    SERVER("my_server"),
    USER("my_name"),
    PASSWORD("my_password")
}

enum class RestResponseMsg(val i: Int) {
    NONE(0),
    SERVER_NOT_FOUND(1),
    SERVER_OK(2),
    REGISTER_FAILED(11),
    REGISTER_OK(12),
    UNREGISTER_FAILED(11),
    UNREGISTER_OK(12),
    LOGIN_FAILED(31),
    LOGIN_OK(32),
    LOGOUT_FAILED(41),
    LOGOUT_OK(42),
    TRANSACT_GEN_FAILED(31),
    TRANSACT_GEN_OK(32),
    TRANSACT_FAILED(33),
    TRANSACT_OK(34),
    TRANSACT_DATA(35),
    MINE_FAILED(41),
    MINE_OK(42),
    MINE_DATA(43),
    WALLET_GEN_FAILED(51),
    WALLET_GEN_OK(52),
    WALLET_SAVE_FAILED(53),
    WALLET_SAVE_OK(54),
    WALLET_SHOW_FAILED(55),
    WALLET_SHOW_OK(56),
    WALLET_DATA(57),
    NEIGHBOR_FAILED(61),
    NEIGHBOR_OK(62),
    NEIGHBOR_DATA(63)
}

enum class RestResponse(val s: String) {
    R_CODE("rcode"),
    R_MESSAGE("rmessage"),
    R_DATA("rdata"),
    R_TRANSACT("rtransact"),
    R_SIGN("rsign"),
    R_PRI_KEY("rpri_key"),
    R_PUB_KEY("rpub_key")
}

enum class RestRequest(val s: String) {
    NONE(""),
    USER_NAME("username"),
    USER_PASSWORD("password"),
    SENDER("sender"),
    RECIPIENT("recipient"),
    AMOUNT("amount"),
    SIGNATURE("signature")
}

/* EOF */
