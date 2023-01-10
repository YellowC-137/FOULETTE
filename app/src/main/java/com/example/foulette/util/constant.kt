package com.example.foulette.util

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody

const val REQUEST_CODE = 96
const val SEARCH_NEARBY = "https://maps.googleapis.com/maps/api/place/nearbysearch/"
const val KAKAO_SEARCH_CATEGORY ="https://dapi.kakao.com/v2/local/search/"
const val NAVER_ROUTE = "https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/"
const val TMAP_ROUTE = "https://apis.openapi.sk.com/tmap/routes/"
const val PAGE_SIZE = 10
const val STARTING_KEY = 1
const val PAGING_DELAY = 1000L