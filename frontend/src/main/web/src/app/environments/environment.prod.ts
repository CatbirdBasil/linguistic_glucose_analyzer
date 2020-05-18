export const environment = {
  production: true,
  ws: 'http://localhost:8080/socket'
  // ws: '/socket'
};

// export const title = 'TripGod';
export const title = 'D-Health';


export const BASE_URL = "http://localhost:8080/";
export const API_URL = "http://localhost:51862/api/";
// export const BASE_URL = "/";

export const LOGIN_URL = BASE_URL + "api/auth/signin";
export const REGISTRATION_URL = BASE_URL + "api/auth/signup";
export const REGISTRATION_SPECIAL_USER_URL = BASE_URL + "api/account/users";
export const PASSWORD_RECOVERY_URL = BASE_URL + "api/auth/recovery";
export const REGISTRATION_CONFIRM_URL = BASE_URL + "api/auth/signup/";

export const GET_ALL_USERS = BASE_URL + "api/account/users";
export const GET_CURRENT_USER = BASE_URL + "api/account/summary";
export const GET_USER_BY_ID = BASE_URL + "api/account/summary/";

export const DELETE_USER_BY_ID = BASE_URL + "api/account/users";

export const EDIT_SUMMARY = BASE_URL + "api/account/summary/edit";
export const CHANGE_PASSWORD_SUMMARY = BASE_URL + "api/account/summary/changePassword";

export const GET_ADMIN_DASHBOARDS = BASE_URL + "api/account/dashboards/admin";
export const GET_CARRIER_DASHBOARDS = BASE_URL + "api/account/dashboards/carrier";
export const GET_APPROVER_DASHBOARDS = BASE_URL + "api/account/dashboards/approver";

export const GET_ALL_TRIPS = BASE_URL + "api/account/trips";
export const GET_ALL_TRIPS_WITH_DETAIL = BASE_URL + "api/account/trips/withDetail";
export const GET_ALL_TRIPS_FOR_CURRENT_USER = BASE_URL + "api/account/trips/currentUser";
export const GET_TRIP_BY_ID = BASE_URL + "api/account/trips/";
export const GET_TRIP_BY_PROVIDER_ID = BASE_URL + "api/account/trips/forProvider/";
export const GET_ALL_TRIPS_SORTED_BY_IMG = BASE_URL + "api/account/trips/withImg";
export const CREATE_OR_UPDATE_TRIP_URL = BASE_URL + "api/account/trips";

export const CREATE_BUNDLE_URL = BASE_URL + "api/account/bundles";

export const GET_PURCHASED_SERVICES = BASE_URL + "api/account/services/purchased";
export const GET_TROUBLE_TICKET_BY_ID = BASE_URL + "api/account/troubleTickets/";
export const GET_TROUBLE_TICKET_BY_SERVICE_ID = BASE_URL + "api/account/troubleTickets/service/";
export const GET_TROUBLE_TICKET_MESSAGES_BY_ID = BASE_URL + "api/account/troubleTickets/messages/";
export const CREATE_TROUBLE_TICKET = BASE_URL + "api/account/troubleTickets";
export const POST_TT_MESSAGE = BASE_URL + "api/account/troubleTickets/messages/";
export const POST_TT_FEEDBACK = BASE_URL + "api/account/troubleTickets/feedback";
export const CHANGE_TT_STATUS = BASE_URL + "api/account/troubleTickets";

export const GET_SERVICE_MESSAGE = BASE_URL + "api/trip/spec/";
export const POST_CLARIFICATION_REQUEST = BASE_URL + "api/trip/spec";
export const CHANGE_STATUS_APPROVER = BASE_URL + "api/trip/spec/approver";
export const CHANGE_STATUS_PROVIDER = BASE_URL + "api/trip/spec/carrier";

export const GET_TRIP_FEEDBACK_BY_TRIP_ID = BASE_URL + "api/feedback/trip/";
export const CHECK_USER_FEEDBACK_PERMIT_FOR_TRIP_ID = BASE_URL + "api/feedback/trip/checkFeedbackPermit/";
export const ADD_FEEDBACK = BASE_URL + "api/feedback";
export const UPDATE_FEEDBACK = BASE_URL + "api/feedback";
export const DELETE_FEEDBACK = BASE_URL + "api/feedback/";

// export const GET_ALL_TOURS = BASE_URL + "api/account/report/";

export const GET_REPORT_URL = BASE_URL + "api/account/report/";

export const GET_ALL_BUNDLE_WITH_IMG = BASE_URL + "api/account/bundles/withImg";
export const GET_ALL_BUNDLES = BASE_URL + "api/account/bundles";
export const GET_BUNDLE_BY_ID = BASE_URL + "api/account/bundles/";
export const DELETE_BUNDLE_BY_ID = BASE_URL + "api/account/bundles/";
export const GET_ORDERS_OF_USER = BASE_URL + "api/account/orders/";

export const GET_ALL_COUNTRIES = BASE_URL + "api/account/search/country";
export const GET_SEARCHED_TRIPS_BY_RATING = BASE_URL + "api/account/search/rating";
export const GET_SEARCHED_TRIPS_BY_PROVIDER = BASE_URL + "api/account/search/provider";
export const GET_SEARCHED_TRIPS_BY_DISCOUNT = BASE_URL + "api/account/search/discount";
export const GET_SEARCHED_TRIPS_BY_PRICE = BASE_URL + "api/account/search/price";
export const GET_SEARCHED_TRIPS_BY_LENGTH = BASE_URL + "api/account/search/length";
export const GET_SEARCHED_TRIPS_BY_BUNDLE = BASE_URL + "api/account/search/bundle";

export const DISCOUNT_CONTROLLER = BASE_URL + "api/discounts";
export const DELETE_DISCOUNT = BASE_URL + "api/discounts/";
export const GET_ALL_TRIP_RELATED_DISCOUNTS_BY_TRIP_ID = BASE_URL + "api/discounts/trip/";

export const VIEW_CONTROLLER = BASE_URL + "api/views";
export const COUNT_VIEWS_BY_TRIP_ID = VIEW_CONTROLLER + "/trip/";

export const GET_SERVICES_BY_PROVIDER_ID = BASE_URL + "api/account/services/provider";

export const GET_ALL_NOT_READ_NOTIFICATION_FOR_APPROVER = BASE_URL + "api/notifications/approver/";
export const GET_ALL_NOT_READ_NOTIFICATION_FOR_PROVIDER = BASE_URL + "api/notifications/provider/";
export const GET_ALL_NOT_READ_NOTIFICATION_FOR_USER = BASE_URL + "api/notifications/user/";

// export const GET_ALL_MESSAGES_FOR_USER_BY_ID = BASE_URL + "api/messages/user/";

export const GET_CHAT_FOR_CURRENT_USER = BASE_URL + "api/chat";
export const GET_CHATS_FOR_APPROVER = BASE_URL + "api/chat/approver";
export const GET_CHAT_MESSAGES_BY_CHAT_ID = BASE_URL + "api/chat/";
export const POST_CHAT_MESSAGE_WITH_CHAT_ID = BASE_URL + "api/chat/";
export const PUT_CHANGING_CHAT_ASSIGNMENT = BASE_URL + "api/chat";

export const GET_USER_BASKET = BASE_URL + "api/basket";
export const ADD_TO_USER_BASKET = BASE_URL + "api/basket/";
export const COMPLETE_ORDER = BASE_URL + "api/basket/pay";

export const SUBSCRIBE_BY_ID = BASE_URL + "api/subscribe/";
export const GET_SUB_COUNT_FOR_PROVIDER = BASE_URL + "api/subscribe/count/";
export const GET_USER_SUBSCRIPTIONS = BASE_URL + "api/subscribe";
export const GET_COUNTRIES = BASE_URL + "api/countries";

export const GET_CITIES_BY_COUNTRY_ID = BASE_URL + "api/cities/country/";

export const GRAVATAR_BASE_URL = "https://www.gravatar.com/avatar/";

export const GOOGLE_DRIVE_PICTURE_BASE_URL = "https://drive.google.com/uc?id=";
export const GOOGLE_DRIVE_OAUTH2_REFRESH_TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";

// export const CVET_YAIZ_DROZDA = "#1fcecb";

export const TEST_CONTROLLER = API_URL + "test/";
export const USER_TESTS_URL = TEST_CONTROLLER + "user/";


export const QUESTION_CONTROLLER = API_URL + "question/";


export const ANSWER_CONTROLLER = API_URL + "answer/";


export const TEST_HISTORY_CONTROLLER = API_URL + "history/test/";
export const TEST_STATISTICS_URL = TEST_HISTORY_CONTROLLER + "statistics/";


export const ANSWER_HISTORY_CONTROLLER = API_URL + "history/test/";
