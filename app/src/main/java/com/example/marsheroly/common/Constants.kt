package com.example.marsheroly.common

class Constants {
    companion object {

        // Calendar
        const val CALENDAR_REFERENCE_ID = "chat_reference_id"
        const val CALENDAR_REFERENCE_TYPE = "chat_reference_type"
        const val CALENDAR_REFERENCE_DATE = "chat_reference_date"

        // Chat
        const val CHAT_KEY_EMAIL = "email"
        const val CHAT_KEY_NAME = "name"
        const val CHAT_KEY_NAME_LOWERCASE = "name-lowercase"
        const val CHAT_KEY_PICTURE = "pictureURL"
        const val CHAT_KEY_NOTIFICATION_THREAD = "thread-entity-id"
        const val CHAT_OPEN_THREAD_ID = "open_thread_id"

        // Date formats
        const val HOUR_MINUTE_FORMAT = "HH:mm"
        const val HOUR_MINUTE_12_FORMAT = "h:mm a"
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val DATE_FORMAT_AMERICAN = "MM/dd/yyyy"
        const val DATE_FORMAT_AMERICAN_REDUCED = "MM/dd/yy"
        const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm"
        const val DATE_WEEK_FORMAT = "EEE, d MMM"
        const val DATE_TIME_WEEK_FORMAT = "EEE, d MMM HH:mm"
        const val WEEK_FORMAT = "EEE"
        const val COMPLETE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

        // Delay
        const val SHORT_DELAY = 200L
        const val RECYCLER_SELECT_DELAY = 250L

        // Fixed amounts
        const val DASHBOARD_ARTICLES_NUMBER = 5
        const val DASHBOARD_UPCOMING_NUMBER = 4

        // Health trackers
        const val PAIN_SIDE_FRONT = "front"
        const val PAIN_SIDE_BACK = "back"

        // Navigation
        const val NAVIGATE_TO_TAB = "navigate_to_tab"
        const val TAB_BUNDLE = "tab_bundle"

        // Notifications
        const val KEY_TYPE = "type"
        const val KEY_CARD_TYPE = "cardType"
        const val KEY_REFERENCE_ID = "referenceId"
        const val KEY_DATE_TIME = "dateTime"
        const val KEY_INVITATION = "invitation"
        const val N_TYPE_CARD = "CARD"
        const val N_TYPE_ARTICLE = "ARTICLE"
        const val N_TYPE_INVITATION = "INVITATION"
        const val INV_INVITATION_ID = "id"
        const val INV_TOKEN = "token"
        const val INV_INVITED_CAREGIVER_ID = "invitedCaregiverId"
        const val INV_HOST_CAREGIVER_NAME = "hostCaregiverName"
        const val INV_CARE_CIRCLE_NAME = "careCircleName"
        const val INV_CARE_CIRCLE_ID = "careCircleId"

        // Repositories
        const val COMMON_CACHE_TIME = 43200000L //12 HOURS
    }
}