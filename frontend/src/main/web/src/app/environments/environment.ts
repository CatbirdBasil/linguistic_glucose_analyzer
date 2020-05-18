export const environment = {
  production: false,
  ws: 'http://localhost:8080/socket'
};

export const title = 'D-Health';

// export const API_URL = "http://localhost:51862/";
export const API_URL = "http://localhost:8080/api/";

export const LOGIN_URL = API_URL + "auth/signin";
export const REGISTRATION_URL = API_URL + "auth/signup";

export const GLUCOSE_ENDPOINT_URL = API_URL + "glucose/";
export const GET_GLUCOSE_RECORDS_FOR_CURR_USER_URL = GLUCOSE_ENDPOINT_URL + "user/";

//
// export const TEST_CONTROLLER = API_URL + "api/test/";
// export const USER_TESTS_URL = TEST_CONTROLLER + "user/";
// export const SEARCH_TESTS_CONTAINING_NAME_URL = TEST_CONTROLLER + "search/";
// export const SEARCH_TESTS_BY_GROUP_NAME_URL = TEST_CONTROLLER + "group/";
//
// export const QUESTION_CONTROLLER = API_URL + "api/question/";
//
// export const ANSWER_CONTROLLER = API_URL + "api/answer/";
//
// export const TEST_HISTORY_CONTROLLER = API_URL + "api/history/test/";
// export const TEST_STATISTICS_URL = TEST_HISTORY_CONTROLLER + "statistic/";
// export const TEST_STATISTICS_COMPLETION_URL = TEST_STATISTICS_URL + "completion/";
// export const USER_TEST_HISTORY_URL = TEST_HISTORY_CONTROLLER + "user/";
//
// export const ANSWER_HISTORY_CONTROLLER = API_URL + "api/history/test/";
