# Foulette

Summary
현재 위치를 중심으로 음식점을 하나 추천하는 어플리케이션
구글지도를 사용해서 근처의 맛집들을 검색후 그중 하나를 선택하여
경로를 보여주고 이를 히스토리에 저장해줍니다.

기술 스택

Structure
```
├── common
│   ├── base
│   ├── constant
│   ├── di
│   ├── extension
│   └── util
├── data
│   ├── local
│   │   ├── dao
│   │   ├── datasource
│   │   ├── datasourceimpl
│   │   └── entity
│   ├── paging
│   └── repositoryimpl
├── domain
│   ├── model
│   ├── repository
│   └── usecase
└── presentation
    ├── adapter
    ├── view
    │   ├── sensor_history_list
    │   ├── sensor_history_measure
    │   ├── sensor_history_play
    │   └── sensor_history_show
    └── viewmodel
```

