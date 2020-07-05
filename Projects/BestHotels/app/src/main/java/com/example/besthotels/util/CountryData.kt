package com.example.besthotels.util

import com.example.besthotels.model.Country


class CountryData {

    private val countryList: MutableList<Country> = mutableListOf()

    private fun populateList() {

        countryList.add(
            Country(
                1,
                "England",
                "https://images.unsplash.com/photo-1488747279002-c8523379faaa?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                2,
                "France",
                "https://images.unsplash.com/photo-1549144511-f099e773c147?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                3,
                "Germany",
                "https://images.unsplash.com/photo-1467269204594-9661b134dd2b?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                4,
                "Italy",
                "https://images.unsplash.com/photo-1516483638261-f4dbaf036963?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                7,
                "Spain",
                "https://images.unsplash.com/photo-1512753360435-329c4535a9a7?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                8,
                "Portugal",
                "https://images.unsplash.com/photo-1555881400-74d7acaacd8b?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                9,
                "Poland",
                "https://images.unsplash.com/photo-1532157345990-d3ab4df99902?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                10,
                "Greece",
                "https://images.unsplash.com/photo-1533105079780-92b9be482077?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                11,
                "Norway",
                "https://images.unsplash.com/photo-1506701160839-34cfdecaf53c?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                12,
                "Sweden",
                "https://images.unsplash.com/photo-1509356843151-3e7d96241e11?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                13,
                "Denmark",
                "https://images.unsplash.com/photo-1551651767-d5ffbdd04b83?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                14,
                "Finland",
                "https://images.unsplash.com/photo-1536420124982-bd9d18fc47ed?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
        countryList.add(
            Country(
                16,
                "China",
                "https://images.unsplash.com/photo-1508804185872-d7badad00f7d?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjEyNTIwM30"
            )
        )
    }

    fun getCountryList(): MutableList<Country> {
        populateList()
        return countryList
    }


}