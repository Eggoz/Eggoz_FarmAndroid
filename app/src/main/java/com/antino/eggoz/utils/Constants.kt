package com.antino.eggoz.utils

class Constants {
    companion object {

                        const val AppUrl="https://stage.backend.eggoz.in/"
//        const val AppUrl = "https://backend.eggoz.in/"
        const val ImageSize = 640

        const val ACCESS_TOKEN_PREFERENCE = "accessToken"
        const val ID = "id"
        const val TEMPID = "tempid"
        const val LANG = "lang"

        const val DB_NAME = "CartInformation"

        const val hashcode = "Rf5ih0hbFJ5"

        const val OpenWeatherkey = "0eb68d5f7bf69e4fbbc67c8305042586"
        const val OpenWeatherBaseUrl = "http://api.openweathermap.org/data/2.5/weather"

        const val OpenWeatherBaseUrl2 = "http://api.openweathermap.org/geo/1.0/reverse"

        const val IotUrl =
            "https://l2kre966we.execute-api.us-east-2.amazonaws.com/poultryCurrentData"
        const val privacypolicyUrl ="https://eggoz.in/privacy-policy.html"
        const val IotHeader =
            "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ijh2OFp2alN2MWY2UWg3V1hvNG9qZSJ9"


        const val apiend_Login = "user/login/"
        const val apiend_generate = "login_otp/generate/"
        const val apiend_validate = "login_otp/validate/"
        const val apiend_submit_password = "user/submit_password/"
        const val apiend_city = "base/city"
        const val apiend_farm = "farmer/farm/"
        const val apiend_farmbyid = "farmer/farm/{id}/"
        const val apiend_flock_graph = "farmer/flock_graph/"
        const val apiend_flock_breed = "farmer/flock_breed/"
        const val apiend_shed = "farmer/shed/"
        const val apiend_order = "farmer/order/"
        const val apiend_flock = "farmer/flock/"
        const val apiend_daily_input = "farmer/daily_input/"
        const val apiend_feed_medicine = "farmer/feed_medicine/"
        const val apiend_party = "farmer/party/"
        const val apiend_division = "product/division/"
        const val apiend_sub_division = "product/sub_division/"
        const val apiend_expenses = "farmer/expenses/"
        const val apiend_farmer = "farmer/"
        const val apiend_farmer_id = "/farmer/{id}/"
        const val apiend_registration = "farmer/registration/"
        const val apiend_post = "farmer/post/"
        const val apiend_post_like = "farmer/post_like/"
        const val apiend_post_comment_like = "farmer/post_comment_like/"
        const val apiend_post_comment = "farmer/post_comment/"
        const val apiend_feed_product = "feed/feed_product/"
        const val apiend_feed_product_id = "feed/feed_product/{id}/"
        const val apiend_feed_division = "feed/division/"
        const val apiend_feedback_farmer = "api/feedback/farmer/"
        const val apiend_feed_order = "feed/feed_order/"
        const val apiend_add_address = "users/add_address/"
        const val apiend_banner = "farmer/banner/"
        const val apiend_feed_product_ispopular = "feed/feed_product?is_popular=true"
        const val apiend_necc_zone = "farmer/necc_zone/"
        const val apiend_necc_city_rate = "farmer/necc_city_rate/"
        const val apiend_wordpress = "farmer/wordpress/"
        const val apiend_weather = "farmer/weather/"
        const val apiend_weatherbypincode = "https://api.openweathermap.org/data/2.5/weather/"
        const val apiend_video = "base/video/"
        const val apiend_update_user = "users/update_user/"
        const val apiend_iot_enquiry = "farmer/iot_enquiry/"
        const val apiend_daily_input_id = "/farmer/daily_input/{id}/"
        const val apiend_shedbyid = "farmer/shed/{id}/"
        const val apiend_flockbyid = "farmer/flock/{id}/"

    }
}