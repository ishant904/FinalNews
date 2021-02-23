package com.example.finalnews.Utilities

import android.util.Log

class TranslateSources {
    companion object {
        fun translateNewsPublisher(newsPublisher: String): String {
            return when (newsPublisher) {
                "ABC News" -> "abc-news"
                "ABC News (AU)" -> "abc-news-au"
                "Aftenposten" -> "aftenposten"
                "AlJazeera (ENG)" -> "al-jazeera-english"
                "BBC" -> "bbc-news"
                "CBS News" -> "cbs-news"
                "CNN News" -> "cnn"
                "Entertainment Weekly" -> "entertainment-weekly"
                "ESPN" -> "espn"
                "Financial Post" -> "financial-post"
                "Financial Times" -> "financial-times"
                "Fox News" -> "fox-news"
                "Fox Sports" -> "fox-sports"
                "IGN" -> "ign"
                "Independent" -> "independent"
                "L'Equipe" -> "lequipe"
                "Metro" -> "metro"
                "MSNBC" -> "msnbc"
                "MTV News" -> "mtv-news"
                "Nat. Geo." -> "national-geographic"
                "NBC News" -> "nbc-news"
                "New Scientist" -> "new-scientist"
                "NY Magazine" -> "new-york-magazine"
                "Talk Sport" -> "talksport"
                "TechRadar" -> "techradar"
                "The Guardian" -> "the-guardian-uk"
                "NYT" -> "the-new-york-times"
                "Wall Street Journal" -> "the-wall-street-journal"
                else -> "No Match"
            }
        }

        fun translateCountry(country: String): String {
            return when (country) {
                "Argentina" -> "ar"
                "Australia" -> "au"
                "Austria" -> "at"
                "Belgium" -> "be"
                "Brazil" -> "br"
                "Bulgaria" -> "bg"
                "Canada" -> "ca"
                "China" -> "cn"
                "Colombia" -> "co"
                "Cuba" -> "cu"
                "Czech Rep." -> "cz"
                "Egypt" -> "eg"
                "France" -> "fr"
                "Germany" -> "de"
                "Greece" -> "gr"
                "Hong Kong" -> "hk"
                "Hungary" -> "hu"
                "India" -> "in"
                "Indonesia" -> "id"
                "Ireland" -> "ie"
                "Israel" -> "il"
                "Italy" -> "it"
                "Japan" -> "jp"
                "Latvia" -> "lv"
                "Lithuania" -> "lt"
                "Malaysia" -> "my"
                "Mexico" -> "mx"
                "Morocco" -> "ma"
                "Netherlands" -> "nl"
                "New Zealand" -> "nz"
                "Nigeria" -> "ng"
                "Norway" -> "no"
                "Philippines" -> "ph"
                "Poland" -> "pl"
                "Portugal" -> "pt"
                "Romania" -> "ro"
                "Russia" -> "ru"
                "Saudi Arabia" -> "sa"
                "Serbia" -> "rs"
                "Singapore" -> "sg"
                "Slovakia" -> "sk"
                "Slovenia" -> "si"
                "South Africa" -> "za"
                "South Korea" -> "kr"
                "Sweden" -> "se"
                "Switzerland" -> "ch"
                "Taiwan" -> "tw"
                "Thailand" -> "th"
                "Turkey" -> "tr"
                "Ukraine" -> "ua"
                "U.A.E" -> "ae"
                "U.K" -> "gb"
                "U.S.A" -> "us"
                "Venezuela" -> "ve"
                else -> "No Match"
            }
        }

        fun translateCategory(category: String): String {
            return when (category) {
                "Business" -> "business"
                "Entertainment" -> "entertainment"
                "Health" -> "health"
                "Science" -> "science"
                "Sports" -> "sports"
                "Technology" -> "technology"
                else -> "No Match"
            }
        }

        fun formatPublishedDate(publishedAtDate: String): String {
            val parts: List<String> = publishedAtDate.split("T")
            val YYYYmmDD = parts[0]

            val parts2 = YYYYmmDD.split("-")
            val YYYY = parts2[0]
            val mm = parts2[1]
            val DD = parts2[2]

            val month: String = translateMonth(mm)

            return "$DD $month $YYYY"
        }

        private fun translateMonth(mm: String): String {
            return when (mm) {
                "01" -> "January"
                "02" -> "February"
                "03" -> "March"
                "04" -> "April"
                "05" -> "May"
                "06" -> "June"
                "07" -> "July"
                "08" -> "August"
                "09" -> "September"
                "10" -> "October"
                "11" -> "November"
                "12" -> "December"
                else -> "No month"
            }
        }
    }
}