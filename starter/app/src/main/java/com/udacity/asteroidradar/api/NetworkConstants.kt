package com.udacity.asteroidradar.api

object NetworkConstants {

    const val API_KEY = "DEMO_KEY"
    const val BASE_URL = "https://api.nasa.gov/"
    const val HTTP_GET_FEED_PATH = "neo/rest/v1/feed"
    const val HTTP_GET_PIC_OF_DAY_PATH = "planetary/apod"
    const val QUERY_START_DATE_ARG = "start_date"
    const val QUERY_END_DATE_ARG = "end_date"
    const val QUERY_API_KEY_ARG = "api_key"
    const val API_QUERY_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
}