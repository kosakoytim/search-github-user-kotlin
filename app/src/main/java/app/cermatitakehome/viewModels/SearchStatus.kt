package app.cermatitakehome.viewModels

enum class SearchStatus {
    LOADING,
    COMPLETE,
    ERROR_403,
    ERROR_422,
    ERROR_UNKNOWN,
    AWAITING_INPUT,
    NO_INPUT,
    NOT_FOUND
}