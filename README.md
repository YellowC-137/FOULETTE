# Foulette

Summary
현재 위치를 중심으로 음식점을 하나 추천하는 어플리케이션

구글지도를 사용해서 근처의 맛집들을 검색후 그중 하나를 선택하여
경로를 보여주고 이를 히스토리에 저장해줍니다.

기술 스택

Structure
```
├── util
├── data
│   ├── local
│   │   ├── dao
│   │   ├── database
│   │   ├── datasource
│   │   ├── datasourceimpl
│   │   ├── entity
│   │   ├── paging
│   │   └── repositoryimpl
│   ├── remote
│   │   ├── api
│   │   ├── datasource
│   │   ├── datasourceimpl
│   │   ├── repositoryimpl
│   │   └── response
├── domain
│   ├── mapper
│   ├── model
│   ├── repository
│   └── usecase
├── ui
│   ├── base
│   ├── history
│   ├── main
│   ├── map
│   ├── roulette
│   └── usecase
└── di
```

